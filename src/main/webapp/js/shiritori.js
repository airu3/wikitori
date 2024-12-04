"use strict";
// Register Service Worker
if ("serviceWorker" in navigator) {
	navigator.serviceWorker
		.register("./serviceWorker.js")
		.then(function (registration) {
			if (typeof registration.update === "function") {
				registration.update();
			}
		})
		.catch(function (error) {
			console.log("Error Log: " + error);
		});
}

// Function to handle online status
function handleOnline() {
	if (navigator.onLine) {
		// Online
	} else {
		handleOffline();
	}
}

// Function to handle offline status
function handleOffline() {
	const reload = confirm(
		"インターネットに接続してください\n再読み込みしますか？"
	);
	if (reload) {
		location.reload(true);
	} else {
		window.addEventListener("online", () => {
			location.reload(true);
		});
		alert("インターネットにつながないとしりとりはできません。");
	}
}

// Event listeners for online and offline events
window.addEventListener("online", handleOnline);
window.addEventListener("offline", handleOffline);

//音声認識/合成の準備
const obj = document.getElementById("chat-content-area");
const SpeechRecognition = window.SpeechRecognition || webkitSpeechRecognition;
let speech;
const ssu = new SpeechSynthesisUtterance();
ssu.lang = "ja-JP"; //言語
ssu.rate = 1; // 速度を設定
ssu.pitch = 1; // 高さを設定
ssu.volume = 1; // 音量を設定

let wordHistory = ["しりとり"];
let score = 0;
let cpuWord = "";
let nextWord = "り";
let isWork = false;
const switchButton = $("#check");
const recordButton = $("#record_btn");
const recordButtonText = $("#record_btn_text");
const submitButton = $("#submit_btn");
const submitButtonText = $("#submit_btn_text");
const inputText = $("#input_text");
const chatBox = $("#chat-content-area");

let isProcessing = false;
// 初期状態で送信ボタンを無効化
submitButton.prop("disabled", true);

// 入力欄の内容が変更されたときに送信ボタンの状態を更新
inputText.on("input", function () {
	if (inputText.val().trim() === "") {
		submitButton.prop("disabled", true);
	} else {
		submitButton.prop("disabled", false);
	}
});

inputText.on("keydown", function (e) {
	if (e.keyCode === 13) {
		// エンターキーのキーコードは13
		if (isProcessing) {
			// 処理中の場合、キーイベントを無視する
			e.preventDefault();
			return;
		}

		isProcessing = true; // 処理開始をマークする

		submitButtonClick(); // SubmitButtonClick関数を呼び出す

		// 処理が終わったら、再度キーイベントを有効にする
		// ここでは、例として1秒後に有効にするようにしています
		setTimeout(function () {
			isProcessing = false;
		}, 1000);
	}
});

// フォームの送信を検知
$("#shiritoriForm").on("submit", function (e) {
	e.preventDefault(); // フォームのデフォルトの送信動作をキャンセル

	$.ajax({
		url: $(this).attr("action"), // フォームのaction属性からURLを取得
		type: "POST", // リクエストのタイプをPOSTに設定
		data: $(this).serialize(), // フォームのデータをシリアライズ
		success: function (data) {
			// リクエストが成功したときの処理をここに書く
			console.log("response data is", data);
			// ボットの単語をHTML要素に反映させる
			//$("#botWord").text(data.title);
			let link = `http://ja.wikipedia.org/?curid=${data.pageid}`;

			let results = [data.title, link];
			handleShiritoriSuccess(results);
		},
		error: function (xhr, status, error) {
			// リクエストが失敗したときの処理をここに書く
			console.error(error);
			handleShiritoriError(error);
		},
		complete: function () {
			// リクエストが完了したとき（成功・失敗に関わらず）の処理をここに書く
			// テキストエリアの内容を空にする
			//$("#input_text").val("");

			// 送信ボタンを再度有効化
			$("#submit_btn").prop("disabled", false);
			isWork = false;
		},
	});
});

// スコアの送信を処理する関数
$("#submitScore").click(function () {
	let nowScore = $("#score").text(); // スコアを取得
	let userName = "testUser"; // ユーザー名を設定（実際にはユーザーから入力を受け取る）

	$.ajax({
		url: "scoreUpdate", // スコアを送信するURL
		type: "POST", // HTTPメソッド
		data: {
			// 送信するデータ
			userName: userName,
			score: nowScore,
		},
		success: function (data) {
			alert("スコアが正常に送信されました。");
		},
		error: function (jqXHR, textStatus, errorThrown) {
			alert(
				"スコアの送信中にエラーが発生しました: " +
					textStatus +
					", " +
					errorThrown
			);
		},
	});

	// モーダルを閉じる
	$("#scoreModal").modal("hide");
});

//send-scoreボタンを押したらモーダルを表示する
$("#send-score").on("click", function () {
	$("#scoreModal").modal("show");
});

$("#chat-btn").on("click", function (e) {
	location.reload();
});

$("#scoreModal").on("show.bs.modal", function (event) {
	$(".score-point").text(score);
});

/**
 * 		入力されたテキストを処理する関数
 * @param {*} text - 入力されたテキスト
 */
function processResultText(text) {
	if (nextWord !== strChange(text, 1)[0]) {
		displayBotChat("「" + nextWord + "」から言葉を始めてね！", chatBox);
		ResetUI();
	} else if (wordHistory.indexOf(text) !== -1) {
		displayBotChat("「" + text + "」は、もう使われた言葉だよ！", chatBox);
		ResetUI();
	} else {
		handleShiritoriResult(text);
	}
}

/**
 * しりとりの結果を処理する関数
 * @param {*} text
 */
function handleShiritoriResult(text) {
	wordHistory.push(text);
	//単語の数x10点
	score += text.length * 100;
	//履歴の数x10点
	score += wordHistory.length * 100;
	//CPUの単語の数x10点
	score += cpuWord.length * 100;
	//テキストの最初の文字と最後���文字が同じなら+100点
	if (strChange(text, 1)[0] === strChange(cpuWord, -1)[0]) {
		score += 1000;
	}
	//履歴が10個になったらボーナス+1000点
	if (wordHistory.length == 10) {
		score += 1000;
	}
	//履歴が20個になったらボーナス+2000点
	if (wordHistory.length == 20) {
		score += 2000;
	}
	console.log("score", score);
	//スコアを表示
	document.querySelector(".score-point").textContent = score;

	$("#shiritoriForm").submit(); // フォームを送信
}

function handleShiritoriSuccess(values) {
	let value = values[0];
	let link = values[1];
	console.log("選んだ単語", value);
	const startWord = strChange(value, -1)[0];
	inputText.attr("placeholder", "「" + startWord + "」から始まる言葉");
	nextWord = startWord;
	displayBotChat("「" + value + "」", chatBox, link);
	wordHistory.push(value);
	obj.scrollTop = obj.scrollHeight;

	if (switchButton.prop("checked")) {
		ssu.text = value;
		console.log("読み上げ", ssu.text);
		window.speechSynthesis.speak(ssu);
	}

	console.log("処理終了");
	ResetUI();
}

function handleShiritoriError(error) {
	alert("error:Wikipedia api\n" + error);
	console.log(error);
	displayBotChat("エラーが起きました", chatBox);
	ResetUI();
}

function submitButtonClick() {
	let text = inputText.val();
	if (text.trim() === "") {
		alert("入力欄が空です。言葉を入力してください。");
		ResetUI();
		return; //何もないなら関数を終了させる
	}
	if (text === "") {
		ResetUI();
		return; //何もないなら関数を終了させる
	} else {
		recordButton.prop("disabled", true);
		submitButton.prop("disabled", true);

		console.log("input word", text);

		displayUserChat(text);
		obj.scrollTop = obj.scrollHeight;

		processResultText(text);
	}
}

function ResetUI() {
	inputText.val("");
	inputText.attr("readonly", false);
	recordButton.prop("disabled", false);
	submitButton.prop("disabled", false);
	recordButtonText.text("マイク");
	submitButtonText.text("送信");
	inputText.attr("placeholder", "「" + nextWord + "」から始まる言葉");
}

submitButton.click(submitButtonClick);

if (SpeechRecognition !== undefined) {
	// ユーザのブラウザは音声認識に対応しています。
	speech = new SpeechRecognition();
	speech.lang = "ja-JP";
	speech.interimResults = true;
	recordButton.click(function () {
		// 音声認識をスタート
		if (!isWork) {
			isWork = true;
			recordButton.prop("disabled", true);
			submitButton.prop("disabled", true);
			//recordButtonText.text("マイクで録音中");

			speech.start();
		}
	});
	speech.onnomatch = function () {
		console.log("認識できませんでした");
		displayBotChat("認識できませんでした", chatBox);
		ResetUI();
		isWork = false;
		inputText.attr("readonly", false);
	};
	speech.onerror = function () {
		console.log("認識できませんでした");
		displayBotChat("認識できませんでした", chatBox);
		ResetUI();
		isWork = false;
		inputText.attr("readonly", false);
	};
	//音声自動文字起こし機能
	speech.onresult = function (e) {
		if (!e.results[0].isFinal) {
			let speechText = e.results[0][0].transcript;
			console.log(speechText);
			inputText.attr("readonly", true);
			inputText.val(speechText);

			return;
		}

		recordButtonText.text("処理中");
		submitButtonText.text("処理中");

		console.log("リザルト");
		speech.stop();

		if (e.results[0].isFinal) {
			console.log("聞き取り成功！");
			let resultText = e.results[0][0].transcript;
			console.log(e);
			console.log(resultText); //resultTextが結果
			inputText.val(resultText);
			submitButtonClick();
		}
	};
} else {
	recordButton.click(function () {
		alert("このブラウザは音声認識に対応していません");
	});
	recordButton.prop("disabled", true);
	recordButtonText.text("非対応");
}
let words;
let links;

function shiritori(user_msg) {
	return new Promise(function (resolve, reject) {
		words = [];
		links = [];
		let changes = strChange(user_msg, -1);
		let taskA = new Promise(function (resolve) {
			fetchWordsFromWikipedia(changes[0], 50, resolve);
		});
		let taskB = new Promise(function (resolve) {
			fetchWordsFromWikipedia(changes[1], 50, resolve);
		});
		Promise.all([taskA, taskB]).then(function () {
			console.log(words);
			console.log(links);
			if (words.length === 0) {
				displayBotChat("負けました", chatBox);
				console.error("強すぎException");
				return;
			}
			let random = Math.floor(Math.random() * words.length);
			cpuWord = words[random];
			let wikiLink = links[random];
			if (strChange(cpuWord, -1)[0] === "ん") {
				do {
					words.splice(words.indexOf(cpuWord), words.indexOf(cpuWord));
					random = Math.floor(Math.random() * words.length);
					cpuWord = words[random];
					wikiLink = links[random];
				} while (strChange(cpuWord, -1)[0] === "ん");
				resolve([cpuWord, wikiLink]);
			} else {
				resolve([cpuWord, wikiLink]);
			}
		});
	});
}

let NG_word = [""];
function fetchWordsFromWikipedia(searchTerm, limit, callback) {
	console.log(searchTerm);
	const url = `https://ja.wikipedia.org/w/api.php?format=json&action=query&list=prefixsearch&pssearch=${searchTerm}&pslimit=${limit}&psnamespace=0`;

	$.ajax({
		type: "GET",
		timeout: 10000,
		dataType: "jsonp",
		url: url,
		async: false,
		success: function (json) {
			console.log(json);
			json.query.prefixsearch.forEach(processWord);
			callback();
		},
	});

	function processWord(value) {
		//console.log("Processing word: ", value.title);

		if (value.title !== searchTerm) {
			let word = value.title.replace(/ *\([^)]*\) */g, "");
			//console.log("Word after: ", word);

			if (
				NG_word.indexOf(word.slice(-1)) === -1 &&
				wordHistory.indexOf(word) === -1
			) {
				words.push(word);
				links.push(`http://ja.wikipedia.org/?curid=${value.pageid}`);
			} else {
				console.log(
					"Word is in NG_word or wordHistory, not adding to words and links"
				);
			}
		} else {
			console.log("Word is the same as searchTerm, not processing");
		}
	}
}

const hiraganaSmallToLarge = {
	ぁ: "あ",
	ぃ: "い",
	ぅ: "う",
	ぇ: "え",
	ぉ: "お",
	っ: "つ",
	ゃ: "や",
	ゅ: "ゆ",
	ょ: "よ",
	ゎ: "わ",
};

const katakanaSmallToLarge = {
	ァ: "ア",
	ィ: "イ",
	ゥ: "ウ",
	ェ: "エ",
	ォ: "オ",
	ヵ: "カ",
	ヶ: "ケ",
	ッ: "ツ",
	ャ: "ヤ",
	ュ: "ユ",
	ョ: "ヨ",
	ヮ: "ワ",
};

function convertSmallToLarge(char, map) {
	return map[char] || char;
}

//正規表現
const regex = /(?!\p{Lm})\p{L}|\p{N}/u;

/**
 * 文字列の一部を変換する関数
 *
 * @param {*} inputWord - 変換対象の文字列
 * @param {*} flag - 変換する範囲を指定するパラメータ
 * @returns {Array} - 変換後の結果を格納した配列 [ひらがな, カタカナ]
 */
function strChange(inputWord, flag) {
	// 範囲のデフォルト値を設定
	let range;
	if (flag === 1) {
		range = [0, 1];
	} else {
		range = [-1, undefined];
	}

	// 指定された範囲の文字を生成する関数
	function generateCharRange(start, end) {
		const range = [];
		for (let i = start.charCodeAt(0); i <= end.charCodeAt(0); i++) {
			range.push(String.fromCharCode(i));
		}
		return range;
	}

	// ひらがなとカタカナの文字を生成
	const hiragana = generateCharRange("\u3041", "\u3096"); // ひらがな
	const katakana = generateCharRange("\u30a1", "\u30f6"); // カタカナ

	let r = [];
	let word = inputWord;
	console.log("Before processing, word is: ", word);
	// ひらがなの
	console.log("word.slice(range[0], range[1])", word.slice(range[0], range[1]));
	//最初の文字がひらがなかカタカナか漢字かを判定
	if (hiragana.indexOf(word.slice(range[0], range[1])) !== -1) {
		// ひらがなの場合
		r.push(word.slice(range[0], range[1]));
		r.push(katakana[hiragana.indexOf(word.slice(range[0], range[1]))]);
		console.log("After processing hiragana, r is: ", r);
	} else if (katakana.indexOf(word.slice(range[0], range[1])) !== -1) {
		// カタカナの場合
		r.push(hiragana[katakana.indexOf(word.slice(range[0], range[1]))]);
		r.push(word.slice(range[0], range[1]));
		console.log("After processing katakana, r is: ", r);
	} else {
		// 漢字, 数字, 記号の場合
		$.ajax({
			type: "POST",
			timeout: 10000,
			url: "https://labs.goo.ne.jp/api/hiragana",
			async: false,
			headers: {
				"Content-Type": "application/json",
			},
			data: JSON.stringify({
				app_id:
					"d5b86171fcdc098cd38e9b056f8c46c84ec367c171b29ec686f3307e0f3030ef",
				sentence: word,
				output_type: "hiragana",
			}),
		}).done(function (data) {
			word = data.converted;
			console.log("Converted word: ", word); // Add logging

			if (range[0] === -1) {
				// 検索範囲が後ろからの場合、正規表現を用いて条件に合致するまで文字を削る
				let lastChar = word.slice(-1);
				console.log("Initial last character: ", lastChar); // Add logging

				if (!regex.test(lastChar)) {
					do {
						word = word.slice(0, word.length - 1);
						console.log("Trimmed word: ", word); // Add logging

						lastChar = word.slice(-1);
						console.log("New last character: ", lastChar); // Add logging
					} while (!regex.test(lastChar));

					word = lastChar;
				} else {
					word = lastChar;
				}

				console.log("Final word: ", word); // Add logging
			}

			r.push(word.slice(range[0], range[1])); // ひらがなを r に追加
			r.push(katakana[hiragana.indexOf(word.slice(range[0], range[1]))]); // カタカナを r に追加
			console.log("After processing kanji, r is: ", r);
		});
	}

	// 小文字を大文字に変換する関数を適用
	r[0] = convertSmallToLarge(r[0], hiraganaSmallToLarge);
	r[1] = convertSmallToLarge(r[1], katakanaSmallToLarge);
	console.log("Final state of r is: ", r);
	return r;
}

/**
 * ユーザのチャットを生成する関数
 * @param {*} text - ユーザのチャットに表示するテキスト
 * @returns {string} - ユーザのチャットのHTML要素
 */
function createUserChatHtml(text) {
	return `
		<!-- START USER CHAT -->
		<div class="row user-chat-box">
			<div class="chat-icon">
				<img class="chatgpt-icon" src="img/Face-Without-Mouth-Flat-icon.png" />
			</div>
			<div class="chat-txt">「${text}」</div>
		</div>
	`;
}

/**
 * ユーザのチャットを表示する関数
 * @param {*} text - ユーザのチャットに表示するテキスト
 */
function displayUserChat(text) {
	const userChatHtml = createUserChatHtml(text);
	chatBox.append(userChatHtml);
	obj.scrollTop = obj.scrollHeight;
}

/**
 * ボットのチャットを生成する関数
 * @param {*} text - ボットのチャットに表示するテキスト
 * @param {*} link - リンク
 * @returns {string} - ボットのチャットのHTML要素
 */
function createBotChatHtml(text, link) {
	let textHtml = link ? `<a href="${link}">${text}</a>` : text;
	return `
	<!-- START GPT CHAT -->
	<div class="row gpt-chat-box">
		<div class="chat-icon">
			<img class="chatgpt-icon" src="img/Smirking-Face-Flat-icon.png" />
		</div>
		<div class="chat-txt">${textHtml}</div>
	</div>`;
}

/**
 * ボットのチャットを表示する関数
 * @param {*} text - ボットのチャットに表示するテキスト
 * @param {*} element - チャットを表示するHTML要素
 * @param {*} link - リンク
 */
function displayBotChat(text, element, link) {
	const chatBubble = createBotChatHtml(text, link);
	element.append(chatBubble);
	obj.scrollTop = obj.scrollHeight;
}
