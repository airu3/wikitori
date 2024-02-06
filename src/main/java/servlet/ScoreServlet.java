package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ScoreDao;

/**
 * スコアを更新するサーブレット
 */
@WebServlet("/scoreUpdate")
public class ScoreServlet extends HttpServlet {
	private ScoreDao scoreDao = new ScoreDao();

	/**
	 * スコアを更新
	 * 
	 * @param request  リクエスト
	 * @param response レスポンス
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("user");
		int score = Integer.parseInt(request.getParameter("score"));
		System.out.println("User Name: " + userName);
		System.out.println("Score: " + score);

		if (!scoreDao.isUserExists(userName)) {
			scoreDao.registerUser(userName);
			int userId = scoreDao.getUserId(userName);
			scoreDao.setScore(userId, score);
		} else {
			int userId = scoreDao.getUserId(userName);
			scoreDao.updateScore(userId, score);
		}

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("Score updated successfully!");
	}
}