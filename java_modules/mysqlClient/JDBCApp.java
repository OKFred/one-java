package java_modules.mysqlClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCApp {
  private static final String URL = "jdbc:mysql://172.28.239.33:3306/test";
  private static final String USER = "root";
  private static final String PASSWORD = "123456";

  public void queryDatabase() {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      // 注册JDBC驱动
      Class.forName("com.mysql.cj.jdbc.Driver");

      // 打开连接
      System.out.println("Connecting to database...连接到数据库🚀" + URL);
      conn = DriverManager.getConnection(URL, USER, PASSWORD);

      // 执行查询
      System.out.println("Creating statement...开始查询🔍");
      stmt = conn.createStatement();
      String sql = "SELECT id, name, age FROM Employees";
      rs = stmt.executeQuery(sql);

      // 展开结果集
      while (rs.next()) {
        // 检索
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int age = rs.getInt("age");

        // 显示值
        System.out.print("ID: " + id);
        System.out.print(", Name: " + name);
        System.out.println(", Age: " + age);
      }
    } catch (SQLException se) {
      se.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }
    System.out.println("Goodbye!");
  }
}
