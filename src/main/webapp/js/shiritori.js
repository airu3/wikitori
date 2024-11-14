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

$("#input_text").on("keydown", function (e) {
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

function handleShiritoriResult(text) {
	wordHistory.push(text);

	//単語の数x10点
	score += text.length * 100;
	//履歴の数x10点
	score += wordHistory.length * 100;
	//CPUの単語の数x10点
	score += cpuWord.length * 100;
	//テキストの最初の文字と最後の文字が同じなら+100点
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

	// shiritori(text)
	// 	.then(function (values) {
	// 		handleShiritoriSuccess(values);
	// 	})
	// 	.catch(function (error) {
	// 		handleShiritoriError(error);
	// 	})
	// 	.finally(function () {
	// 		isWork = false;
	// 	});
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
function createUserChatHtml(text) {
	return `
			<div class="flex items-start space-x-4 mb-6">
					<div class="flex-shrink-0">
							<img class="h-12 w-12 rounded-full" src="img/Face-Without-Mouth-Flat-icon.png" alt="User">
					</div>
					<div class="flex-1 space-y-1">
							<div class="inline-block rounded-lg bg-indigo-50 px-6 py-4 shadow-sm">
									<p class="text-lg text-gray-900">「${text}」</p>
							</div>
					</div>
			</div>
	`;
}

function displayUserChat(text) {
	const userChatHtml = createUserChatHtml(text);
	chatBox.append(userChatHtml);
	obj.scrollTop = obj.scrollHeight;
}

function createBotChatHtml(text, link) {
	const textContent = link
		? `<a href="${link}" class="text-indigo-600 hover:text-indigo-500 underline">「${text}」</a>`
		: `「${text}」`;

	return `
			<div class="flex items-start space-x-4 mb-6">
					<div class="flex-shrink-0">
							<img class="h-12 w-12 rounded-full" src="img/Smirking-Face-Flat-icon.png" alt="Bot">
					</div>
					<div class="flex-1 space-y-1">
							<div class="inline-block rounded-lg bg-gray-100 px-6 py-4 shadow-sm">
									<p class="text-lg text-gray-900">${textContent}</p>
							</div>
					</div>
			</div>
	`;
}

function displayBotChat(text, element, link) {
	const chatBubble = createBotChatHtml(text, link);
	element.append(chatBubble);
	obj.scrollTop = obj.scrollHeight;
}
