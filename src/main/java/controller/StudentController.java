package controller;

import DAO.StudentDao;
import DAOImpl.StudentDaoImpl;
import com.google.gson.Gson;
import model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.*;

@WebServlet("/student-controller")
public class StudentController extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
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
            System.out.println(" -------> studentsList DAO: " + studentsList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

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

        Student oneStudent = null;

        try {
        // To read what the POST brings to the Servlet
        String jsonPOST = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject obj = new JSONObject(jsonPOST);
        String studentId = (String) obj.get("studentId");
        //Query the DB
        oneStudent = studentDao.getOneStudent(studentId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Serialize the oneStudent object
        String studentJsonString = new Gson().toJson(oneStudent);
        // Returns call originated in the client
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        out.println(studentJsonString);

    }

    public void destroy() {
    }
}