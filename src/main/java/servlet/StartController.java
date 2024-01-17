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
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 初回アクセス時の処理
        // 例: ユーザーがログインしていれば直接チャット画面にリダイレクトするなど
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null) {
            response.sendRedirect(request.getContextPath() + "/chat");
        } else {
            // ユーザーが未ログインの場合、通常の処理に進む
            request.getRequestDispatcher("start.html").forward(request, response);
        }
    }
		
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // フォームから名前を取得
        String username = request.getParameter("username");

        if (username != null && !username.isEmpty()) {
            // セッションにユーザー名を保存
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // チャット画面にリダイレクト
            response.sendRedirect(request.getContextPath() + "/chat");
        } else {
            // 名前が入力されていない場合はエラー処理などを実装する
            response.sendRedirect(request.getContextPath() + "/start.html");
        }
    }
}
