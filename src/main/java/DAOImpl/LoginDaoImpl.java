
package DAOImpl;

import DAO.LoginDao;
import db.DatabaseConnection;
import model.Clase;
import model.SQLState;
import model.User;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginDaoImpl implements LoginDao {
    List<Clase> classes = new ArrayList<>();
    Clase clase = null;
    Connection conn;

    public LoginDaoImpl() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        this.conn = databaseConnection.getConnection();
    }


    public boolean verifyCredentials(User user) {
        boolean authorized = false;
        ResultSet rs;
        Statement st = null;

        try {
            st = this.conn.createStatement();
            String sql = "select users.user_password " +
                    "from users " +
                    "where users.user_id = '" + user.getUserId() + "'" +
                    " and users.user_password = '" + user.getPassword() + "'";

            System.out.println(sql);
            rs = st.executeQuery(sql);

            if (rs.next()) {
                authorized = true;
            }
            st.close();
        } catch (SQLException e) {
            authorized = false;
        } finally {
            return authorized;
        }
    }
}
