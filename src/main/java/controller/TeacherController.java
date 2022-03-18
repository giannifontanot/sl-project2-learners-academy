package controller;

import DAO.TeacherDao;
import DAOImpl.TeacherDaoImpl;
import com.google.gson.Gson;
import model.Clase;
import model.SQLState;
import model.Teacher;
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

@WebServlet("/teacher-controller")
public class TeacherController extends HttpServlet {
    private String message;

    public void init() {
        message = "";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        TeacherDao teacherDao = null;
        try {
            teacherDao = new TeacherDaoImpl();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        List<Teacher> teachersList = null;

        try {
            teachersList = teacherDao.getAllTeachers();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        request.setAttribute("teachersList", teachersList);
        request.getRequestDispatcher("teachers.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        TeacherDao teacherDao = null;
        try {
            teacherDao = new TeacherDaoImpl();
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

            // ACTION ? fetchOneTeacher
            if (action.equals("fetchOneTeacher")) {

                //Query the DB
                String teacherId = (String) jsonObject.get("teacherId");
                Teacher oneTeacher = teacherDao.fetchOneTeacher(teacherId);

                //Serialize the oneTeacher object
                String teacherJsonString = new Gson().toJson(oneTeacher);
                // Returns call originated in the client
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                System.out.println("teacherJsonString" + teacherJsonString);
                out.println(teacherJsonString);
                out.flush();
            }

            // ACTION ? updateOneTeacher or saveNewTeacher
            if (action.equals("updateOneTeacher")) {

                //Query the DB
                assert teacherDao != null;
                SQLState sqlState = teacherDao.updateOneTeacher(jsonObject);

                // Returns call originated in the client
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                out.println(new Gson().toJson(sqlState));
                out.flush();
            }

            // ACTION ? deleteOneTeacher
            if (action.equals("deleteOneTeacher")) {

                //Query the DB
                assert teacherDao != null;
                SQLState sqlState = teacherDao.deleteOneTeacher(jsonObject);

                // Returns call originated in the client
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                out.println(new Gson().toJson(sqlState));
                out.flush();
            }

            // ACTION ? saveNewTeacher
            if (action.equals("saveNewTeacher")) {

                //Query the DB
                SQLState sqlState = teacherDao.saveNewTeacher(jsonObject);
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