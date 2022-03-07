package controller;

import DAO.StudentDao;
import DAOImpl.StudentDaoImpl;
import model.Student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

@WebServlet("/student-controller")
public class StudentController extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StudentDao studentDao = null;
        try {
            studentDao = new StudentDaoImpl();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();


        List<Student> students = null;

        try {
            students = studentDao.getAllStudents();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        Iterator<Student> studentIterator = students.iterator();
            out.println("<html><body style=\"background-color:lightgray\">");
        while (studentIterator.hasNext()) {
            out.println("<h1>" + ((Student)studentIterator.next()).getStudentName() + "</h1>");
        }
            out.println("</body></html>");
    }

    public void destroy() {
    }
}