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
const obj = document.getElementById("chat-box");
const SpeechRecognition = window.SpeechRecognition || webkitSpeechRecognition;
let speech;
const msg = new SpeechSynthesisUtterance();
msg.lang = "ja-JP"; //言語

let wordHistory = ["しりとり"];
let cpu_word = "";
let next_word = "り";
let IsWork = false;
const switchButton = $("#check");
const recordButton = $("#record_btn");
const recordButtonText = $("#record_btn_text");
const submitButton = $("#submit_btn");
const submitButtonText = $("#submit_btn_text");
const inputText = $("#word");
const chatBox = $("#chat-content-area");

$("#input_text").on("keydown", function (e) {
	if (e.keyCode === 13) {
		// エンターキーのキーコードは13
		SubmitButtonClick(); // SubmitButtonClick関数を呼び出す
		$("#shiritoriForm").submit(); // フォームを送信
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
			console.log(data);
		},
		error: function (xhr, status, error) {
			// リクエストが失敗したときの処理をここに書く
			console.error(error);
		},
		complete: function () {
			// リクエストが完了したとき（成功・失敗に関わらず）の処理をここに書く
			// テキストエリアの内容を空にする
			//$("#input_text").val("");

			// 送信ボタンを再度有効化
			$("#submit_btn").prop("disabled", false);
		},
	});
});

function disableButtonsDuringProcessing() {
	recordButton.prop("disabled", true);
	recordButtonText.text("処理中");
	submitButton.prop("disabled", true);
	submitButtonText.text("処理中");
}

// ローカルストレージからユーザー名を取得
let username = localStorage.getItem("username");

// function displayUserChat(text) {
// 	const userChatHtml = `
// 		<div class="kaiwa">
// 			<figure class="kaiwa-img-right">
// 				<img src="https://via.placeholder.com/150" alt="no-img1">
// 				<!-- ユーザー名を表示 -->
// 				<figcaption class="kaiwa-img-description">${username}</figcaption>
// 			</figure>
// 			<div class="kaiwa-text-left">
// 				<p class="kaiwa-text">「${text}」</p>
// 			</div>
// 		</div>`;
// 	chatBox.append(userChatHtml);
// 	obj.scrollTop = obj.scrollHeight;
// }

function createUserChatHtml(text) {
	return `
		<!-- START USER CHAT -->
		<div class="row user-chat-box">
			<div class="chat-icon">
				<img class="chatgpt-icon" src="images/user-icon.png" />
			</div>
			<div class="chat-txt">「${text}」</div>
		</div>
	`;
}

function displayUserChat(text) {
	const userChatHtml = createUserChatHtml(text);
	chatBox.append(userChatHtml);
	obj.scrollTop = obj.scrollHeight;
}

function processResultText(text) {
	if (next_word !== strChange(text, 1)[0]) {
		say("「" + next_word + "」から言葉を始めてね！", chatBox);
		ResetUI();
	} else if (wordHistory.indexOf(text) !== -1) {
		say("「" + text + "」は、もう使われた言葉だよ！", chatBox);
		ResetUI();
	} else {
		handleSiritoriResult(text);
	}
}

function handleSiritoriResult(text) {
	wordHistory.push(text);
	siritori(text)
		.then(function (values) {
			handleSiritoriSuccess(values);
		})
		.catch(function (error) {
			handleSiritoriError(error);
		})
		.finally(function () {
			IsWork = false;
		});
}

function handleSiritoriSuccess(values) {
	let value = values[0];
	let link = values[1];
	console.log(value);
	const startWord = strChange(value, -1)[0];
	inputText.attr("placeholder", "「" + startWord + "」から始まる言葉");
	next_word = startWord;
	say("「" + value + "」", chatBox, link);
	wordHistory.push(value);
	obj.scrollTop = obj.scrollHeight;

	if (switchButton.checked) {
		msg.text = value;
		speechSynthesis.speak(msg);
	}

	console.log("処理終了");
	ResetUI();
}

function handleSiritoriError(error) {
	alert("error:Wikipedia api\n" + error);
	console.log(error);
	say("エラーが起きました", chatBox);
	ResetUI();
}

function Submit(text) {
	disableButtonsDuringProcessing();
	console.log("リザルト");
	console.log(text); //textが結果
	//chatBox.html("");
	displayUserChat(text);
	obj.scrollTop = obj.scrollHeight;

	processResultText(text);
}

function SubmitButtonClick() {
	submitButton.css("background-color", "#999999");
	recordButton.css("background-color", "#999999");
	let text = inputText.val();
	if (text === "") {
		ResetUI();
		return; //何もないなら関数を終了させる
	}
	Submit(text);
}

function ResetUI() {
	inputText.val("");
	recordButton.prop("disabled", false);
	submitButton.prop("disabled", false);
	recordButtonText.text("マイク");
	submitButtonText.text("送信");
	recordButton.css("background-color", "#00bcd4");
	submitButton.css("background-color", "#00bcd4");
}

submitButton.click(SubmitButtonClick);

// Function to initialize speech recognition
function initializeSpeechRecognition() {
	if (window.SpeechRecognition !== undefined) {
		// Browser supports speech recognition
		const speech = new SpeechRecognition();
		speech.lang = "ja-JP";
		speech.interimResults = true;

		// Function to handle speech recognition start
		const handleSpeechRecognitionStart = function () {
			if (!IsWork) {
				IsWork = true;
				recordButton.prop("disabled", true);
				submitButton.prop("disabled", true);
				recordButtonText.text("マイクで録音中");
				recordButton.css("background-color", "#ff0000");
				speech.start();
			}
		};

		// Event listener for record button click to start speech recognition
		recordButton.click(handleSpeechRecognitionStart);

		// Event handling for speech recognition errors
		const handleSpeechRecognitionError = function () {
			console.log("認識できませんでした");
			say("認識できませんでした", chatBox);
			resetUIAfterSpeechRecognition();
		};

		speech.onnomatch = handleSpeechRecognitionError;
		speech.onerror = handleSpeechRecognitionError;

		// Event handling for speech recognition results
		speech.onresult = function (e) {
			if (!e.results[0].isFinal) {
				const speechText = e.results[0][0].transcript;
				console.log(speechText);
				inputText.attr("readonly", true);
				inputText.val(speechText);
				return;
			}

			recordButtonText.text("処理中");
			submitButtonText.text("処理中");
			submitButton.css("background-color", "#999999");
			recordButton.css("background-color", "#999999");
			console.log("リザルト");
			speech.stop();

			if (e.results[0].isFinal) {
				console.log("聞き取り成功！");
				const autoText = e.results[0][0].transcript;
				console.log(autoText);
				inputText.val(autoText);
				Submit(autoText);
			}
		};
	} else {
		// Browser doesn't support speech recognition
		recordButton.click(function () {
			alert("このブラウザは音声認識に対応していません");
		});
		recordButton.prop("disabled", true);
		recordButtonText.text("非対応");
	}
}

// Function to reset UI after speech recognition
function resetUIAfterSpeechRecognition() {
	IsWork = false;
	inputText.attr("readonly", false);
}

// Call the function to initialize speech recognition
initializeSpeechRecognition();

let words;
let links;

function siritori(user_msg) {
	return new Promise(function (resolve, reject) {
		words = [];
		links = [];
		let changes = strChange(user_msg, -1);
		let taskA = new Promise(function (resolve) {
			fetchWordsFromWikipedia(changes[0], resolve);
		});
		let taskB = new Promise(function (resolve) {
			fetchWordsFromWikipedia(changes[1], resolve);
		});
		Promise.all([taskA, taskB]).then(function () {
			console.log(words);
			if (words.length === 0) {
				say("負けました", chatBox);
				console.error("強すぎException");
				return;
			}
			let random = Math.floor(Math.random() * words.length);
			cpu_word = words[random];
			let wikiLink = links[random];
			if (strChange(cpu_word, -1)[0] === "ん") {
				do {
					words.splice(words.indexOf(cpu_word), words.indexOf(cpu_word));
					random = Math.floor(Math.random() * words.length);
					cpu_word = words[random];
					wikiLink = links[random];
				} while (strChange(cpu_word, -1)[0] === "ん");
				resolve([cpu_word, wikiLink]);
			} else {
				resolve([cpu_word, wikiLink]);
			}
		});
	});
}

let NG_word = [""];

function fetchWordsFromWikipedia(searchTerm, callback) {
	console.log(searchTerm);
	const url = `https://ja.wikipedia.org/w/api.php?format=json&action=query&list=prefixsearch&pssearch=${searchTerm}&pslimit=200&psnamespace=0`;

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
		if (value.title !== searchTerm) {
			let word = value.title.replace(/ *\([^)]*\) */g, "");
			if (
				NG_word.indexOf(word.slice(-1)) === -1 &&
				wordHistory.indexOf(word) === -1
			) {
				words.push(word);
				links.push(`http://ja.wikipedia.org/?curid=${value.pageid}`);
			}
		}
	}
}

//正規表現
const regex = /(?!\p{Lm})\p{L}|\p{N}/u;

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

/**
 * 引数ran
 1 先頭切り出し
 -1 末尾切り出し
 **/
function strChange(str, ran) {
	let range = ran;
	if (range === 1) {
		range = [0, 1];
	} else {
		range = [-1, undefined];
	}

	function generateCharRange(start, end) {
		const range = [];
		for (let i = start.charCodeAt(0); i <= end.charCodeAt(0); i++) {
			range.push(String.fromCharCode(i));
		}
		return range;
	}

	const hiragana = generateCharRange("\u3041", "\u3096"); // ひらがな
	const katakana = generateCharRange("\u30a1", "\u30f6"); // カタカナ

	let r = [];
	let word = str;

	//しりとり処理除外対象が二回連続でも対応
	//const del_str =func_str.slice(range[0], range[1]) != "ー" && func_str.slice(range[0], range[1]) != "-" && func_str.slice(range[0], range[1]) != "!" && func_str.slice(range[0], range[1]) != "?" && func_str.slice(range[0], range[1]) != "！" && func_str.slice(range[0], range[1]) != "？" && func_str.slice(range[0], range[1]) != "〜" && func_str.slice(range[0], range[1]) != "、"&&func_str.slice(range[0], range[1]) != "。"&&func_str.slice(range[0], range[1]) != "."

	if (hiragana.indexOf(word.slice(range[0], range[1])) !== -1) {
		//ひらがな
		r.push(word.slice(range[0], range[1]));
		r.push(katakana[hiragana.indexOf(word.slice(range[0], range[1]))]);
		console.log(r);
	} else if (katakana.indexOf(str.slice(range[0], range[1])) !== -1) {
		//カタカナ
		r.push(hiragana[katakana.indexOf(word.slice(range[0], range[1]))]);
		r.push(word.slice(range[0], range[1]));
		console.log(r);
	} else {
		//漢字
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
			if (range[0] === -1) {
				if (!regex.test(word.slice(-1))) {
					do {
						word = word.slice(0, word.length - 1);
					} while (!regex.test(word.slice(-1)));
					word = word.slice(-1);
				} else {
					word = word.slice(-1);
				}
			}

			r.push(word.slice(range[0], range[1])); // ひらがなを rに追加
			r.push(katakana[hiragana.indexOf(word.slice(range[0], range[1]))]); //カタカナをr に追加
			console.log(r);
		});
	}
	r[0] = convertSmallToLarge(r[0], hiraganaSmallToLarge);
	r[1] = convertSmallToLarge(r[1], katakanaSmallToLarge);

	console.log(r);

	return r;
}

// function createChatBubble(text, link) {
// 	const imageUrl = "https://via.placeholder.com/150";
// 	const imageAlt = "no-img2";
// 	const caption = "しりとり AI";

// 	let messageText = `<p class="kaiwa-text">${text}</p>`;
// 	if (link) {
// 		messageText = `<p class="kaiwa-text"><a href="${link}">${text}</a></p>`;
// 	}

// 	return `
// 		<div class="kaiwa">
// 			<figure class="kaiwa-img-left">
// 				<img src="${imageUrl}" alt="${imageAlt}">
// 				<figcaption class="kaiwa-img-description">${caption}</figcaption>
// 			</figure>
// 			<div class="kaiwa-text-right">
// 				${messageText}
// 			</div>
// 		</div>`;
// }

function createChatBubbleHtml(text, link) {
	return `
	<!-- START GPT CHAT -->
	<div class="row gpt-chat-box">
		<div class="chat-icon">
			<img class="chatgpt-icon" src="images/chatgpt-icon.png" />
		</div>
		<div class="chat-txt"><a href="${link}">${text}</a></div>
	</div>`;
}

function createChatBubble(text, link) {
	return createChatBubbleHtml(text, link);
}

function say(text, element, link) {
	const chatBubble = createChatBubble(text, link);
	element.append(chatBubble);
	obj.scrollTop = obj.scrollHeight;
}
