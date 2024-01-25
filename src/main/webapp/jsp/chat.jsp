<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html lang="ja-JP">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="author" content="Sajjad Hussain" />
<title>Chat GPT Kit</title>

<!-- START EXTERNAL CSS RESOURCES -->

<!-- bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet" />

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css"
	rel="stylesheet" />

<!-- START INTERNAL CSS RESOURCES -->

<!-- favicon -->
<link rel="shortcut icon" href="images/chatgpt-favicon.png"
	type="image/x-icon" />
<!-- style -->
<link rel="stylesheet" href="css/style.css" />
</head>

<body>
	<!-- START MAIN CONTAINER -->
	<div class="container-fluid">
		<div class="row">
			<!-- START SIDENAV -->
			<div class="side-nav col-lg-3 col-md-12">
				<!-- START NEW CHAT BUTTON -->
				<div class="row p-2">
					<div class="chat-btn d-flex align-items-center">
						<span class="d-block">+ New Chat</span>
					</div>
				</div>

				<div class="row">
					<div class="col-12">
						<ul class="list-unstyled">
							<!-- START SETTINGS -->
							<li class="nav-item"><a class="nav-link text-white" href="#">
									<i class="fas fa-cog"></i> Settings
							</a></li>

							<!-- START GET HELP -->
							<li class="nav-item"><a class="nav-link text-white" href="#">
									<i class="fas fa-question-circle"></i> Get Help
							</a></li>

							<!-- START LOG OUT -->
							<li class="nav-item"><a class="nav-link text-white" href="#">
									<i class="fas fa-sign-out-alt"></i> Log Out
							</a></li>
						</ul>
					</div>
				</div>
			</div>

			<!-- START CONTENT -->
			<div class="content p-0 pt-2 col-lg-9 col-md-12">
				<div id="chat-content-area">
					<!-- START USER CHAT -->
					<div class="row user-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/user-icon.png" />
						</div>
						<div class="chat-txt">who are you?</div>
					</div>

					<!-- START GPT CHAT -->
					<div class="row gpt-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/chatgpt-icon.png" />
						</div>
						<div class="chat-txt">I am ChatGPT, a large language model
							developed by OpenAI. I am an artificial intelligence designed to
							process and generate human-like text based on the input provided
							to me. I am here to assist you with any questions or tasks you
							may have to the best of my abilities!</div>
					</div>

					<!-- START USER CHAT -->
					<div class="row user-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/user-icon.png" />
						</div>
						<div class="chat-txt">who are you?</div>
					</div>

					<!-- START GPT CHAT -->
					<div class="row gpt-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/chatgpt-icon.png" />
						</div>
						<div class="chat-txt">I am ChatGPT, a large language model
							developed by OpenAI. I am an artificial intelligence designed to
							process and generate human-like text based on the input provided
							to me. I am here to assist you with any questions or tasks you
							may have to the best of my abilities!</div>
					</div>

					<!-- START USER CHAT -->
					<div class="row user-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/user-icon.png" />
						</div>
						<div class="chat-txt">who are you?</div>
					</div>

					<!-- START GPT CHAT -->
					<div class="row gpt-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/chatgpt-icon.png" />
						</div>
						<div class="chat-txt">I am ChatGPT, a large language model
							developed by OpenAI. I am an artificial intelligence designed to
							process and generate human-like text based on the input provided
							to me. I am here to assist you with any questions or tasks you
							may have to the best of my abilities!</div>
					</div>

					<!-- START USER CHAT -->
					<div class="row user-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/user-icon.png" />
						</div>
						<div class="chat-txt">who are you?</div>
					</div>

					<!-- START GPT CHAT -->
					<div class="row gpt-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/chatgpt-icon.png" />
						</div>
						<div class="chat-txt">I am ChatGPT, a large language model
							developed by OpenAI. I am an artificial intelligence designed to
							process and generate human-like text based on the input provided
							to me. I am here to assist you with any questions or tasks you
							may have to the best of my abilities!</div>
					</div>

					<!-- START USER CHAT -->
					<div class="row user-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/user-icon.png" />
						</div>
						<div class="chat-txt">who are you?</div>
					</div>

					<!-- START GPT CHAT -->
					<div class="row gpt-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/chatgpt-icon.png" />
						</div>
						<div class="chat-txt">I am ChatGPT, a large language model
							developed by OpenAI. I am an artificial intelligence designed to
							process and generate human-like text based on the input provided
							to me. I am here to assist you with any questions or tasks you
							may have to the best of my abilities!</div>
					</div>

					<!-- START USER CHAT -->
					<div class="row user-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/user-icon.png" />
						</div>
						<div class="chat-txt">who are you?</div>
					</div>

					<!-- START GPT CHAT -->
					<div class="row gpt-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/chatgpt-icon.png" />
						</div>
						<div class="chat-txt">I am ChatGPT, a large language model
							developed by OpenAI. I am an artificial intelligence designed to
							process and generate human-like text based on the input provided
							to me. I am here to assist you with any questions or tasks you
							may have to the best of my abilities!</div>
					</div>

					<!-- START USER CHAT -->
					<div class="row user-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/user-icon.png" />
						</div>
						<div class="chat-txt">who are you?</div>
					</div>

					<!-- START GPT CHAT -->
					<div class="row gpt-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/chatgpt-icon.png" />
						</div>
						<div class="chat-txt">I am ChatGPT, a large language model
							developed by OpenAI. I am an artificial intelligence designed to
							process and generate human-like text based on the input provided
							to me. I am here to assist you with any questions or tasks you
							may have to the best of my abilities!</div>
					</div>

					<!-- START USER CHAT -->
					<div class="row user-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/user-icon.png" />
						</div>
						<div class="chat-txt"></div>
					</div>

					<!-- START GPT CHAT -->
					<div class="row gpt-chat-box">
						<div class="chat-icon">
							<img class="chatgpt-icon" src="images/chatgpt-icon.png" />
						</div>
						<div class="chat-txt"></div>
					</div>
				</div>

				<!-- START CHAT INPUTS -->
				<div class="chat-input-area overflow-hidden">
					<div class="row">
						<div class="col-12 chat-inputs-area-inner">
							<form action="chat" method="post" id="shiritoriForm"
								class="row chat-inputs-container">
								<textarea name="word" id="input_text" class="col-11"
									placeholder="「り」から始まる言葉"></textarea>
								<button type="submit" class="col-1" id="submit_btn">
									<i class="fa fa-paper-plane" aria-hidden="true"></i>
								</button>
								<button type="button" id="record_btn">
									<i class="fas fa-microphone"></i> <span id="record_btn_text">マイク</span>
								</button>
								<input type="checkbox" id="check" /> <label class="check"
									for="check">CPU読み上げ</label>
							</form>
						</div>
					</div>
				</div>
				<!--  -->
			</div>
		</div>
	</div>

	<!-- START EXTERNAL JS RESOURCES -->
	<!-- jquery -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<!-- material-ui -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/material-ui/5.0.0-beta.5/index.js"></script>
	<!-- bootstrap -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

	<!-- START INTERNAL JS RESOURCES -->
	<script src="js/siritori.js"></script>
</body>
</html>
