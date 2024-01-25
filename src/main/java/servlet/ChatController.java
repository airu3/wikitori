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

@WebServlet("/chat")
public class ChatController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// index.htmlにフォワード
		request.getRequestDispatcher("chat.html").forward(request, response);
	}

	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得
		String word = request.getParameter("word");

		// セッションからしりとりの単語リストを取得
		HttpSession session = request.getSession();
		List<String> words = (List<String>) session.getAttribute("words");

		if (words == null) {
			// 初めての単語の場合、新しいリストを作成
			words = new ArrayList<>();
		}

		// 単語をリストに追加
		words.add(word);

		// リストをセッションに保存
		session.setAttribute("words", words);

		// ページをリダイレクトせずに、JSON形式で成功を返す
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"success\": true}");
	}
}
