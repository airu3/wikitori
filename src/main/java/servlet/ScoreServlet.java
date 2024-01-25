package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ScoreDao;

@WebServlet("/scoreUpdate")
public class ScoreServlet extends HttpServlet {
    private ScoreDao scoreDao = new ScoreDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = "";//request.getParameter("userName");
        int score = 748997;//Integer.parseInt(request.getParameter("score"));

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