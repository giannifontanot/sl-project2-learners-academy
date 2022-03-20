
package DAOImpl;

import DAO.SubjectDao;
import DAO.SubjectsPerClassDao;
import db.DatabaseConnection;
import model.*;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectsPerClassDaoImpl implements SubjectsPerClassDao {
    List<SubjectPerClass> subjectsPerClass = new ArrayList();
    //SubjectsPerClassDao subjectsPerClass = null;
    Connection conn;

    public SubjectsPerClassDaoImpl() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        this.conn = databaseConnection.getConnection();
    }

    public List<SubjectPerClass> getAllSubjectsPerClass(Clase clase) throws SQLException {
        String sql = "select classes.class_name,  \n" +
                "           subjects.subject_name,\n" +
                "            classes.class_id,    \n" +
                "           subjects.subject_id   \n" +
                "        from class_subject,      \n" +
                "                classes,         \n" +
                "                subjects         \n" +
                "        where class_subject.class_id = classes.class_id    \n" +
                "        and class_subject.subject_id = subjects.subject_id \n" +
                "        and classes.class_id = '"+clase.getClassId()+"'";

        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            String classId = rs.getString("class_id");
            String subjectId = rs.getString("subject_id");
            String className = rs.getString("class_name");
            String subjectName = rs.getString("subject_name");

            SubjectPerClass subjectPerClass = new SubjectPerClass(classId, subjectId, className, subjectName);
            this.subjectsPerClass.add(subjectPerClass);
            System.out.format(" ----> %s, %s, %s\n", classId, subjectId, className, subjectName);
        }

        st.close();
        return this.subjectsPerClass;
    }

    @Override
    public List<Clase> getAllClases() throws SQLException {
        List<Clase> classesList = new ArrayList<>();
        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery("select classes.class_id, classes.class_name  from classes");

        while (rs.next()) {
            String classId = rs.getString("class_id");
            String className = rs.getString("class_name");
            Clase clase = new Clase(classId, className);
            classesList.add(clase);
            System.out.format(" ----> %s, %s\n", classId, className);
        }
        st.close();
        return classesList;
    }

    public List<Subject> getAllSubjectsAvailable() throws SQLException {
        List<Subject> subjectsAvailableList = new ArrayList<Subject>();
        String sql = "select subjects.subject_id, subjects.subject_name\n" +
                "from subjects where subject_id not in(\n" +
                "select subject_id from class_subject)";

        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            String subjectId = rs.getString("subject_id");
            String subjectName = rs.getString("subject_name");
            Subject subject = new Subject(subjectId, subjectName, 0, "");
            subjectsAvailableList.add(subject);
        }

        st.close();
        return subjectsAvailableList;
    }

    @Override
    public SQLState deleteOneSubjectPerClass(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> deleteOneSubjectPerClass: ");
        try {
            // SQL String
            String delete = " delete from  class_subject  where class_id = ? and  subject_id = ?";
            System.out.println("----> delete: " + delete);

            PreparedStatement st = this.conn.prepareStatement(delete);
            st.setString(1, jsonObject.getString("deleteClassId"));
            st.setString(2, jsonObject.getString("deleteSubjectId"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Subject deleted successfully from class");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to delete Subject from Class.");
            }
            return sqlState;

        } catch (SQLException err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        }
    }

    @Override
    public SQLState saveNewSubjectPerClass(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> updateOneSubject: ");
        try {
            // SQL String
            String insert = " insert into class_subject (class_id, subject_id) values (?, ?) ";
            System.out.println("----> insert: " + insert);
            PreparedStatement st = this.conn.prepareStatement(insert);

            st.setString(1, jsonObject.getString("classId"));
            st.setString(2, jsonObject.getString("subjectId"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Subject assigned successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to assign Subject to class. ");
            }
            return sqlState;

        } catch (SQLException err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        } catch (Exception err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        }
    }
}
