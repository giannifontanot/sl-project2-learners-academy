
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


    public boolean verifyCredentials(User user) throws SQLException {
        boolean authorized = false;

        String userId = user.getUserId();
        String userPassword = user.getPassword();

        Statement st = this.conn.createStatement();
        String sql = "select users.user_password from users where users.user_id = '" + userId + "'";

        System.out.println(sql);
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            userPassword = rs.getString("user_password");
            System.out.println("userPassword: " + userPassword);
            System.out.println("user.getPassword(): " + user.getPassword());
        }

        if (userPassword.equals(user.getPassword())) {
            authorized = true;
        }
        st.close();
        return authorized;
    }
}
