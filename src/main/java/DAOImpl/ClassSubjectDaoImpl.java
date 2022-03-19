
package DAOImpl;

import DAO.ClassSubjectDao;
import db.DatabaseConnection;
import model.SQLState;
import model.ClassSubject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassSubjectDaoImpl implements ClassSubjectDao {
    List<ClassSubject> classSubjects = new ArrayList();
    ClassSubject classSubject = null;
    Connection conn;

    public ClassSubjectDaoImpl() throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        this.conn = databaseConnection.getConnection();
    }

    public List<ClassSubject> getAllClassSubjects() throws SQLException {
        String sql ="select classes.class_name, subjects.subject_name, \n" +
                "classes.class_id, subjects.subject_id \n" +
                "from class_subject, classes, subjects\n" +
                "where class_subject.class_id = classes.class_id and\n" +
                "      class_subject.subject_id = subjects.subject_id";

        Statement st = this.conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            String classId = rs.getString("class_id");
            String subjectId = rs.getString("subject_id");
            String className = rs.getString("class_name");
            String subjectName = rs.getString("subject_name");

            ClassSubject classSubject = new ClassSubject(classId,subjectId, className,  subjectName);
            this.classSubjects.add(classSubject);
            System.out.format(" ----> %s, %s,%s, %s\n", classId,subjectId, className,  subjectName);
        }

        st.close();
        return this.classSubjects;
    }

}
