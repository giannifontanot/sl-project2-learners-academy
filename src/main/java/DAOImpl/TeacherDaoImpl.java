
package DAOImpl;

import DAO.TeacherDao;
import db.DatabaseConnection;
import model.Clase;
import model.SQLState;
import model.Teacher;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDaoImpl implements TeacherDao {
    List<Teacher> teachers = new ArrayList();
    List<Clase> classes = new ArrayList();
    Teacher teacher = null;
    Connection conn;

    public TeacherDaoImpl() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        this.conn = databaseConnection.getConnection();
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

    public Teacher fetchOneTeacher(String id) {
        try {
            Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM teachers where teacher_id = " + id);
            System.out.println("---> SELECT * FROM teachers where teacher_id = " + id);
            while (rs.next()) {
                int teacherId = rs.getInt("teacher_id");
                String teacherName = rs.getString("teacher_name");
                teacher = new Teacher(teacherId, teacherName);
            }

            st.close();
        } catch (SQLException e) {
            System.out.println("TeacherDaoImp > fetchOneTeacher > " + e.getSQLState() + " - " + e.getMessage());
        }
        return this.teacher;
    }


    @Override
    public SQLState deleteOneTeacher(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> deleteOneTeacher: ");
        try {
            // SQL String
            String delete = " delete from  teachers  where teacher_id = ?";
            System.out.println("----> delete: " + delete);

            PreparedStatement st = this.conn.prepareStatement(delete);
            st.setInt(1, jsonObject.getInt("deleteTeacherId"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Teacher deleted successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to delete Teacher.");
            }
            return sqlState;

        } catch (SQLException err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        }
    }

    @Override
    public SQLState updateOneTeacher(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> updateOneTeacher: ");
        try {
            // SQL String
            String update = " update teachers  set teacher_name = ? where teacher_id = ?";
            System.out.println("----> Update: " + update);
            PreparedStatement st = this.conn.prepareStatement(update);

            st.setString(1, jsonObject.getString("teacherName"));
            st.setInt(2, jsonObject.getInt("teacherId"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Teacher updated successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to update Teacher.");
            }
            return sqlState;

        } catch (SQLException err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        }
    }

    @Override
    public SQLState saveNewTeacher(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> updateOneTeacher: ");
        try {
            // SQL String
            String insert = " insert into teachers (teacher_id, teacher_name) values (?, ?) ";
            System.out.println("----> insert: " + insert);
            PreparedStatement st = this.conn.prepareStatement(insert);

            st.setInt(1, jsonObject.getInt("teacherId"));
            st.setString(2, jsonObject.getString("teacherName"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Teacher created successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to create new Teacher.");
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
