package controller;

import DAO.StudentDao;
import DAOImpl.StudentDaoImpl;
import model.Student;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/main-controller")
public class MainController extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String page = (request.getParameter("page") == null) ? "" : request.getParameter("page");
        System.out.println(" -------> page: " + page);
        System.out.println(" -------> doPost: ");

        page = page.equals("") ? "dashboard" : page;
        if (page.equals("dashboard")) {

        }

        if (page.equals("student")) {

        }

        response.sendRedirect(page + "-controller");
    }

    public void destroy() {

    }
}