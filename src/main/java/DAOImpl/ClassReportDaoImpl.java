
package DAOImpl;

import DAO.ClassReportDao;
import DAO.StudentDao;
import db.DatabaseConnection;
import model.Clase;
import model.ClassFull;
import model.SQLState;
import model.Student;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassReportDaoImpl implements ClassReportDao {
    List<ClassFull> students = new ArrayList();
    List<Clase> classes = new ArrayList();
    Student student = null;
    Connection conn;

    public ClassReportDaoImpl() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        this.conn = databaseConnection.getConnection();
    }

    public List<ClassFull> getAllStudents() throws SQLException {

        String sql = "select classes.class_id, classes.class_name, subjects.subject_id,\n" +
                "subjects.subject_name, teachers.teacher_id,\n" +
                "teachers.teacher_name, students.student_id, students.student_name\n" +
                "from class_subject, subjects, classes, students, teachers\n" +
                "where class_subject.class_id = classes.class_id and\n" +
                "class_subject.subject_id = subjects.subject_id and\n" +
                "classes.class_id = students.class_id and\n" +
                "teachers.teacher_id=subjects.teacher_id\n" +
                "order by classes.class_id, subjects.subject_name";

        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            String classId = rs.getString("class_id");
            String className = rs.getString("class_name");
            String subjectId = rs.getString("subject_id");
            String subjectName = rs.getString("subject_name");
            int teacherId = rs.getInt("teacher_id");
            String teacherName = rs.getString("teacher_name");
            int studentId = rs.getInt("student_id");
            String studentName = rs.getString("student_name");

            ClassFull student = new ClassFull(classId, className, studentId, studentName, teacherId, teacherName, subjectId, subjectName);
            this.students.add(student);
        }

        st.close();
        return this.students;
    }

    public List<Clase> getAllClasses() throws SQLException {
        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery("select classes.class_id, classes.class_name from classes");

        while (rs.next()) {
            String classId = rs.getString("class_id");
            String className = rs.getString("class_name");
            Clase clase = new Clase(classId, className);
            this.classes.add(clase);
            System.out.format(" ----> %s, %s\n", classId, className);
        }

        st.close();
        return this.classes;
    }

    public Student fetchOneStudent(String id) {
        try {
            Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students where student_id = " + id);
            System.out.println("---> SELECT * FROM students where student_id = " + id);
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String classId = rs.getString("class_id");
                String studentName = rs.getString("student_name");
                student = new Student(studentId, classId, studentName);
            }

            st.close();
        } catch (SQLException e) {
            System.out.println("StudentDaoImp > fetchOneStudent > " + e.getSQLState() + " - " + e.getMessage());
        }
        return this.student;
    }


    @Override
    public SQLState deleteOneStudent(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> deleteOneStudent: ");
        try {
            // SQL String
            String delete = " delete from  students  where student_id = ?";
            System.out.println("----> delete: " + delete);

            PreparedStatement st = this.conn.prepareStatement(delete);
            st.setInt(1, jsonObject.getInt("deleteStudentId"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Student deleted successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to delete Student.");
            }
            return sqlState;

        } catch (SQLException err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        }
    }

    @Override
    public SQLState updateOneStudent(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> updateOneStudent: ");
        try {
            // SQL String
            String update = " update students  set class_id = ?, student_name = ? where student_id = ?";
            System.out.println("----> Update: " + update);
            PreparedStatement st = this.conn.prepareStatement(update);

            st.setString(1, jsonObject.getString("classId"));
            st.setString(2, jsonObject.getString("studentName"));
            st.setInt(3, jsonObject.getInt("studentId"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Student updated successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to update Student.");
            }
            return sqlState;

        } catch (SQLException err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        }
    }

    @Override
    public SQLState saveNewStudent(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> updateOneStudent: ");
        try {
            // SQL String
            String insert = " insert into students (student_id, class_id, student_name) values (?, ?, ?) ";
            System.out.println("----> insert: " + insert);
            PreparedStatement st = this.conn.prepareStatement(insert);

            st.setInt(1, jsonObject.getInt("studentId"));
            st.setString(2, jsonObject.getString("classId"));
            st.setString(3, jsonObject.getString("studentName"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Student created successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to create new Student.");
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
