
package DAOImpl;

import DAO.SubjectDao;
import DAO.TeacherPerSubjectDao;
import db.DatabaseConnection;
import model.SQLState;
import model.Subject;
import model.Teacher;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherPerSubjectDaoImpl implements TeacherPerSubjectDao {
    List<Subject> subjects = new ArrayList();
    List<Teacher> teachers = new ArrayList();
    Subject subject = null;
    Connection conn;

    public TeacherPerSubjectDaoImpl() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        this.conn = databaseConnection.getConnection();
    }

    public List<Subject> getAllSubjects() throws SQLException {

        String sql = "select classes.class_id, classes.class_name, " +
                "subjects.subject_id, subjects.subject_name, " +
                "teachers.teacher_id, teachers.teacher_name\n" +
                "from class_subject, subjects, classes, teachers\n" +
                "where class_subject.class_id = classes.class_id and\n" +
                "class_subject.subject_id = subjects.subject_id and\n" +
                "teachers.teacher_id=subjects.teacher_id\n" +
                "order by classes.class_id";

        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            String classId = rs.getString("class_Id");
            String className = rs.getString("class_name");
            String subjectId = rs.getString("subject_id");
            String subjectName = rs.getString("subject_name");
            int teacherId = rs.getInt("teacher_id");
            String teacherName = rs.getString("teacher_name");
            Subject subject = new Subject(subjectId, subjectName, teacherId, teacherName);
            this.subjects.add(subject);
            System.out.format(" ----> %s, %s, %s\n", subjectId, subjectName, teacherId, teacherName);
        }

        st.close();
        return this.subjects;
    }

    public List<Teacher> getAllTeachers() throws SQLException {
        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery("select teachers.teacher_id, teachers.teacher_name from teachers");

        while (rs.next()) {
            int teacherId = rs.getInt("teacher_id");
            String teacherName = rs.getString("teacher_name");
            Teacher teacher = new Teacher(teacherId, teacherName);
            this.teachers.add(teacher);
            System.out.format(" ----> %s, %s\n", teacherId, teacherName);
        }

        st.close();
        return this.teachers;
    }

    public Subject fetchOneSubject(String id) {
        try {
            Statement st = this.conn.createStatement();
            String sql="select subjects.subject_id, subjects.subject_name, " +
                    "teachers.teacher_id, teachers.teacher_name\n" +
                    "from teachers, subjects\n" +
                    "where teachers.teacher_id=subjects.teacher_id\n" +
                    "and  subject_id = '" + id + "'";
            ResultSet rs = st.executeQuery(sql);
            System.out.println(sql);
            while (rs.next()) {
                String subjectId = rs.getString("subject_id");
                int teacherId = rs.getInt("teacher_id");
                String subjectName = rs.getString("subject_name");
                String teacherName = rs.getString("teacher_name");
                subject = new Subject(subjectId, subjectName, teacherId, teacherName);
            }

            st.close();
        } catch (SQLException e) {
            System.out.println("SubjectDaoImp > fetchOneSubject > " + e.getSQLState() + " - " + e.getMessage());
        }
        return this.subject;
    }


    @Override
    public SQLState deleteOneSubject(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> deleteOneSubject: ");
        try {
            // SQL String
            String delete = " delete from  subjects  where subject_id = ?";
            System.out.println("----> delete: " + delete);

            PreparedStatement st = this.conn.prepareStatement(delete);
            st.setString(1, jsonObject.getString("deleteSubjectId"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Subject deleted successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to delete Subject.");
            }
            return sqlState;

        } catch (SQLException err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        }
    }

    @Override
    public SQLState updateOneSubject(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> updateOneSubject: ");
        try {
            // SQL String
            String update = " update subjects  set teacher_id = ?, subject_name = ? where subject_id = ?";
            System.out.println("----> Update: " + update);
            System.out.println("----> jsonObject.getInt(\"teacherId\"): " + jsonObject.getInt("teacherId"));
            System.out.println("----> jsonObject.getString(\"subjectName\"): " + jsonObject.getString("subjectName"));
            System.out.println("----> jsonObject.getString(\"subjectId\"): " + jsonObject.getString("subjectId"));
            PreparedStatement st = this.conn.prepareStatement(update);

            st.setInt(1, jsonObject.getInt("teacherId"));
            st.setString(2, jsonObject.getString("subjectName"));
            st.setString(3, jsonObject.getString("subjectId"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Subject updated successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to update Subject.");
            }
            return sqlState;

        } catch (SQLException err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        }
    }

    @Override
    public SQLState saveNewSubject(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> updateOneSubject: ");
        try {
            // SQL String
            String insert = " insert into subjects (subject_id, teacher_id, subject_name) values (?, ?, ?) ";
            System.out.println("----> insert: " + insert);
            PreparedStatement st = this.conn.prepareStatement(insert);

            st.setString(1, jsonObject.getString("subjectId"));
            st.setInt(2, jsonObject.getInt("teacherId"));
            st.setString(3, jsonObject.getString("subjectName"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Subject created successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to create new Subject.");
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
