package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard-controller")
public class DashboardController extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {
       // String page = (request.getParameter("page") == null) ? "":request.getParameter("page");

        request.getRequestDispatcher("dashboard.jsp").forward(request,response);
    }

    public void destroy() {
    }
}