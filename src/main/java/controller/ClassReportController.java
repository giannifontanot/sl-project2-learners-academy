package controller;

import DAO.StudentDao;
import DAOImpl.StudentDaoImpl;
import com.google.gson.Gson;
import model.Clase;
import model.SQLState;
import model.Student;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/student-controller")
public class ClassReportController extends HttpServlet {
    private String message;

    public void init() {
        message = "";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        StudentDao studentDao = null;
        try {
            studentDao = new StudentDaoImpl();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        List<Student> studentsList = null;

        try {
            studentsList = studentDao.getAllStudents();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        List<Clase> classesList = null;

        try {
            classesList = studentDao.getAllClasses();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        request.setAttribute("classesList", classesList);
        request.setAttribute("studentsList", studentsList);
        request.getRequestDispatcher("students.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        StudentDao studentDao = null;
        try {
            studentDao = new StudentDaoImpl();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }


        try {
            // To read what the POST brings to the Servlet
            String jsonStringPOST = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            JSONObject jsonObject = new JSONObject(jsonStringPOST);
            System.out.println("jsonObject: " + jsonObject);
            String action = (String) jsonObject.get("action");
            System.out.println("action: " + action);

            // ACTION ? fetchOneStudent
            if (action.equals("fetchOneStudent")) {

                //Query the DB
                String studentId = (String) jsonObject.get("studentId");
                Student oneStudent = studentDao.fetchOneStudent(studentId);

                //Serialize the oneStudent object
                String studentJsonString = new Gson().toJson(oneStudent);
                // Returns call originated in the client
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                System.out.println("studentJsonString" + studentJsonString);
                out.println(studentJsonString);
                out.flush();
            }

            // ACTION ? updateOneStudent or saveNewStudent
            if (action.equals("updateOneStudent")) {

                //Query the DB
                assert studentDao != null;
                SQLState sqlState = studentDao.updateOneStudent(jsonObject);

                // Returns call originated in the client
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                out.println(new Gson().toJson(sqlState));
                out.flush();
            }

            // ACTION ? deleteOneStudent
            if (action.equals("deleteOneStudent")) {

                //Query the DB
                assert studentDao != null;
                SQLState sqlState = studentDao.deleteOneStudent(jsonObject);

                // Returns call originated in the client
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                out.println(new Gson().toJson(sqlState));
                out.flush();
            }

            // ACTION ? saveNewStudent
            if (action.equals("saveNewStudent")) {

                //Query the DB
                SQLState sqlState = studentDao.saveNewStudent(jsonObject);
                // Returns call originated in the client
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                out.println(new Gson().toJson(sqlState));
                out.flush();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void destroy() {
    }
}