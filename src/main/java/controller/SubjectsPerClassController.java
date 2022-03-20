package controller;

import DAO.SubjectsPerClassDao;
import DAOImpl.SubjectsPerClassDaoImpl;
import com.google.gson.Gson;
import model.*;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/subjects-per-class-controller")
public class SubjectsPerClassController extends HttpServlet {
    private String message;

    public void init() {
        message = "";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        SubjectsPerClassDao subjectsPerClassDao = null;
        try {
            subjectsPerClassDao = new SubjectsPerClassDaoImpl();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        List<SubjectPerClass> subjectsPerClassList = null;
        Clase claseSelected = null;
        try {
            String pClaseId = (request.getParameter("pClaseId") == null) ? "1" : request.getParameter("pClaseId");
            String pClaseName = (request.getParameter("pClaseName") == null) ? "Room 101" : request.getParameter(
                    "pClaseName");

            claseSelected = new Clase(pClaseId, pClaseName);
            subjectsPerClassList = subjectsPerClassDao.getAllSubjectsPerClass(claseSelected);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        List<Clase> classesList = new ArrayList<>();
        try {
            classesList = subjectsPerClassDao.getAllClases();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        List<Subject> subjectsAvailableList = new ArrayList<>();
        try {
            subjectsAvailableList = subjectsPerClassDao.getAllSubjectsAvailable();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        request.setAttribute("claseSelected", claseSelected);
        request.setAttribute("classesList", classesList);
        request.setAttribute("subjectsAvailableList", subjectsAvailableList);
        request.setAttribute("subjectsPerClassList", subjectsPerClassList);
        request.getRequestDispatcher("subjectsPerClass.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        SubjectsPerClassDao subjectsPerClassDao = null;
        try {
            subjectsPerClassDao = new SubjectsPerClassDaoImpl();
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

            // ACTION ? subjectsAvailable
            if (action.equals("saveNewSubjectPerClass")) {
                System.out.println("---> saveNewSubjectPerClass ");
                //Query the DB
                assert subjectsPerClassDao != null;
                SQLState sqlState = subjectsPerClassDao.saveNewSubjectPerClass(jsonObject);
                System.out.println("---> back from subjectsPerClassDao.saveNewSubjectPerClass(jsonObject): "+sqlState);
                // Returns call originated in the client
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                System.out.println("new Gson().toJson(sqlState): " + new Gson().toJson(sqlState));
                out.println(new Gson().toJson(sqlState));
                out.flush();
            }

            // ACTION ? updateOneSubject or saveNewSubject
            if (action.equals("deleteOneSubject")) {
                System.out.println("---> deleteOneSubject ");
                //Query the DB
                assert subjectsPerClassDao != null;
                SQLState sqlState = subjectsPerClassDao.deleteOneSubjectPerClass(jsonObject);
                System.out.println("---> back from subjectsPerClassDao.deleteOneSubjectPerClass(jsonObject): "+sqlState);
                // Returns call originated in the client
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                System.out.println("new Gson().toJson(sqlState): " + new Gson().toJson(sqlState));
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