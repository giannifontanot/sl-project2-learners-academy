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
        LoginDao userDao = null;

        try {
            userDao = new LoginDaoImpl();


            String page = (request.getParameter("page") == null) ? "" : request.getParameter("page");
            //page = page.equals("") ? "dashboard" : page;

            System.out.println("page: " + page);

            if (page.equals("")) {
                //Login Screen
                String usr = request.getParameter("username");
                String password = request.getParameter("password");
                User user = new User(usr, password);

                userDao = new LoginDaoImpl();
                boolean letBrowser = userDao.verifyCredentials(user);

                System.out.println("leBrowser: " + letBrowser);

                if(letBrowser){

                response.sendRedirect(page + "dashboard-controller");
                }else{
                response.sendRedirect(page + "loginFirst.jsp");

                }

            } else {
                response.sendRedirect(page + "-controller");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void destroy() {
        //
    }
}