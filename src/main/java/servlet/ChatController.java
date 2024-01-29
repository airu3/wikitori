package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ChatHistory;
import model.ShiritoriModel;
import model.TitleInfo;
import util.StringUtil;

@WebServlet("/chat")
public class ChatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShiritoriModel shiritoriModel = new ShiritoriModel();

	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// セッションにしりとりの単語リストを保存
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<ChatHistory> chatHistory = session.getAttribute("chatHistory") == null ? new ArrayList<>()
				: (List<ChatHistory>) session.getAttribute("chatHistory");

		session.setAttribute("chatHistory", chatHistory);
		// chat.htmlにフォワード
		request.getRequestDispatcher("chat.html").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得
		String userMsg = request.getParameter("userMsg");
		System.out.println("User Message: " + userMsg);
		// セッションからしりとりの単語リストを取得
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<ChatHistory> chatHistory = (List<ChatHistory>) session.getAttribute("chatHistory");

		if (chatHistory == null) {
			chatHistory = new ArrayList<>();
		}

		if (StringUtil.isValidInput(userMsg)) {
			// しりとりの進行を行い、結果を取得
			TitleInfo result;
			boolean isDuplicate;
			do {
				TitleInfo resultTemp = shiritoriModel.playShiritori(userMsg);

				// チャット履歴に同じ単語があるかどうかを確認
				isDuplicate = chatHistory.stream()
						.anyMatch(entry -> entry.getMessage().equals(resultTemp.getTitle()));

				result = resultTemp;
				// もし同じ単語があれば、もう一度しりとりを行う
			} while (isDuplicate);

			// Create a ChatHistory object and add it to the chat history list
			ChatHistory chatEntry = new ChatHistory("Bot", result.getTitle());
			chatHistory.add(chatEntry);

			// Save the updated chat history in the session
			session.setAttribute("chatHistory", chatHistory);

			// 結果をJSON形式で返す
			String jsonResponse = "{ \"title\": \"" + result.getTitle() + "\", \"pageid\": \"" + result.getPageId() + "\" }";

			// チャット画面にボットの単語を画面遷移なしで送信
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse);
		} else {
			// チャット画面にボットの単語を画面遷移なしで送信
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("{ \"title\": \"\", \"pageid\": \"\" }");
		}
	}

}