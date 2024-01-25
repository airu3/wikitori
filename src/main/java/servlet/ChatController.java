package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ShiritoriModel;

@WebServlet("/chat")
public class ChatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShiritoriModel shiritoriModel = new ShiritoriModel();

	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// index.htmlにフォワード
		request.getRequestDispatcher("chat.html").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得
		String userMsg = request.getParameter("userMsg");
		System.out.println("User Message: " + userMsg);

		// セッションからしりとりの単語リストを取得
		HttpSession session = request.getSession();
		List<String> userMsgs = (List<String>) session.getAttribute("userMsgs");

		if (isValidInput(userMsg)) {
			// しりとりの進行を行い、結果を取得
			String result = shiritoriModel.playShiritori(userMsg);

			// 結果をJSON形式で返す
			String[] parts = result.split(",");
			String jsonResponse = "{ \"word\": \"" + parts[0] + "\", \"link\": \"" + parts[1] + "\" }";

			// チャット画面にボットの単語を画面遷移なしで送信
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(jsonResponse);
		} else {
			// エラー処理: ユーザーからの入力が無効
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	private boolean isValidInput(String userMsg) {
		return userMsg != null && !userMsg.isEmpty();
	}
}