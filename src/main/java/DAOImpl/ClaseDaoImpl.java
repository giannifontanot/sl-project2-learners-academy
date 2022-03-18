
package DAOImpl;

import DAO.ClaseDao;
import DAO.ClaseDao;
import db.DatabaseConnection;
import model.Clase;
import model.SQLState;
import model.Clase;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaseDaoImpl implements ClaseDao {
    List<Clase> classes = new ArrayList<>();
    Clase clase = null;
    Connection conn;

    public ClaseDaoImpl() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        this.conn = databaseConnection.getConnection();
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

    public Clase fetchOneClass(String id) {
        try {
            Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM classes where class_id = " + id);
            System.out.println("---> SELECT * FROM classes where class_id = " + id);
            while (rs.next()) {
                String classId = rs.getString("class_id");
                String className = rs.getString("class_name");
                clase = new Clase(classId, className);
            }

            st.close();
        } catch (SQLException e) {
            System.out.println("ClaseDaoImp > fetchOneClase > " + e.getSQLState() + " - " + e.getMessage());
        }
        return this.clase;
    }


    @Override
    public SQLState deleteOneClass(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> deleteOneClase: ");
        try {
            // SQL String
            String delete = " delete from  classes  where class_id = ?";
            System.out.println("----> delete: " + delete);

            PreparedStatement st = this.conn.prepareStatement(delete);
            st.setInt(1, jsonObject.getInt("deleteClassId"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Clase deleted successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to delete Clase.");
            }
            return sqlState;

        } catch (SQLException err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        }
    }

    @Override
    public SQLState updateOneClass(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> updateOneClase: ");
        try {
            // SQL String
            String update = " update classes  set class_name = ? where class_id = ?";
            System.out.println("----> Update: " + update);
            PreparedStatement st = this.conn.prepareStatement(update);
            System.out.println("classId: " + jsonObject.getString("classId"));
            System.out.println("className: " + jsonObject.getString("className"));
            st.setString(1, jsonObject.getString("className"));
            st.setString(2, jsonObject.getString("classId"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Clase updated successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to update Class.");
            }
            return sqlState;

        } catch (SQLException err) {
            sqlState.setCode(-1);
            sqlState.setMessage(err.getMessage());
            return sqlState;
        }
    }

    @Override
    public SQLState saveNewClass(JSONObject jsonObject) {

        SQLState sqlState = new SQLState();
        System.out.println("-----> saveNewClase: ");
        try {
            // SQL String
            String insert = " insert into classes (class_id, class_name) values (?, ?) ";
            System.out.println("----> insert: " + insert);
            PreparedStatement st = this.conn.prepareStatement(insert);

            st.setString(1, jsonObject.getString("classId"));
            st.setString(2, jsonObject.getString("className"));

            // Update the database
            int code = st.executeUpdate();
            System.out.println("----> code: " + code);
            // Close the statement
            st.close();

            if (code > 0) {
                sqlState.setCode(0);
                sqlState.setMessage("Clase created successfully");
            } else {
                sqlState.setCode(-1);
                sqlState.setMessage("Error when trying to create new Class.");
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
