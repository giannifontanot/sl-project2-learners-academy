package controller;

import DAO.LoginDao;
import DAOImpl.LoginDaoImpl;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login-controller")
public class LoginController extends HttpServlet {

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

                System.out.println("let Browser: " + letBrowser);

                if(letBrowser){
                    response.sendRedirect("dashboard-controller");
                    request.getSession(true);

                }else{
                    response.sendRedirect("loginFirst.jsp");
                }

            } else {
                response.sendRedirect("dashboard-controller");
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