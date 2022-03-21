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

import DAO.LoginDao;
import DAOImpl.LoginDaoImpl;
import model.User;

@WebServlet("/main-controller")
public class MainController extends HttpServlet {

    public void init() {
        //
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {




            String page = (request.getParameter("page") == null) ? "" : request.getParameter("page");
            page = page.equals("") ? "dashboard" : page;

            System.out.println("page: " + page);


                response.sendRedirect(page + "-controller");


    }

    public void destroy() {
        //
    }
}