
import com.mysql.jdbc.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LocalJDBCTransApplication {
   static   Logger logger= LoggerFactory.getLogger(LocalJDBCTransApplication.class);
    public static void main(String[] args) throws SQLException {
        Connection connection=getConnection();
        connection.setAutoCommit(false);
        String  superMan="update t_user set amount=amount-100 where username=?";
        String  batMan="update t_user set amount=amount+100 where username=?";
        PreparedStatement superManSql=connection.prepareStatement(superMan);
        PreparedStatement batManSql=connection.prepareStatement(batMan);
        superManSql.setString(1,"SuperMan");
        batManSql.setString(1,"BatMan");
        superManSql.executeUpdate();
        batManSql.executeUpdate();
        simulateError();
        connection.commit();
        superManSql.close();
        batManSql.close();
        connection.close();
        System.out.println("数据库初始化完成");
    }
    static  void  simulateError() throws SQLException {
        throw new  SQLException("抛出sql异常");
    }
    private static Connection getConnection() throws SQLException {
        String  driver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/dist_tran_course?useSSL=false";
        String  username="root";
        String  password="123456";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.error(e.getLocalizedMessage());
        }
        return (Connection) DriverManager.getConnection(url,username,password);
    }
}
