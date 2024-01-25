package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/start")
public class StartController extends HttpServlet {
<<<<<<< HEAD
    private static final long serialVersionUID = 1L;
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 初回アクセス時の処理
        // 例: ユーザーがログインしていれば直接チャット画面にリダイレクトするなど
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/chat");
        } else {
            // ユーザーが未ログインの場合、通常の処理に進む
            request.getRequestDispatcher("start.html").forward(request, response);
        }	
    }
=======
	private static final long serialVersionUID = 1L;
>>>>>>> 1170ef72c3ea59ceecee81f396581ea33e479b7f

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 初回アクセス時の処理
		// 例: ユーザーがログインしていれば直接チャット画面にリダイレクトするなど
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			response.sendRedirect(request.getContextPath() + "/chat");
		} else {
			// ユーザーが未ログインの場合、通常の処理に進む
			request.getRequestDispatcher("start.html").forward(request, response);
		}
	}

<<<<<<< HEAD
		if (user != null && !user.isEmpty()) {
			// セッションにユーザー名を保存
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

=======
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// フォームから名前を取得
		String user = request.getParameter("user");

		if (user != null && !user.isEmpty()) {
			// セッションにユーザー名を保存
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

>>>>>>> 1170ef72c3ea59ceecee81f396581ea33e479b7f
			// チャット画面にリダイレクト
			response.sendRedirect(request.getContextPath() + "/chat");
		} else {
			// 名前が入力されていない場合エラー処理などを実装する
			response.sendRedirect(request.getContextPath() + "/start.html");
		}
	}
}
