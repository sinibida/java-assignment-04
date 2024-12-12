package shared;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLRunner {
  private Connection connection;

  private SQLRunner() {
  }

  public static SQLRunner getInstance(
    String dbUrl,
    String dbUsername,
    String dbPassword
  ) throws ClassNotFoundException, SQLException {
    SQLRunner ret = new SQLRunner();
    Class.forName("oracle.jdbc.driver.OracleDriver");
    ret.connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    return ret;
  }

  /**
   * 
   * @param statement
   * @return ResultSet or Null (on SQLException)
   */
  public ResultSet runQuery(String statement) throws SQLException {
    try {
      Statement statementClass = connection.createStatement();
      return statementClass.executeQuery(statement);
    } catch (SQLException e) {
      System.err.println("SQL Execution Failed: " + statement);
      throw e;
    }
  }

  // https://stackoverflow.com/a/49236593
  public int runUpdate(String statement) throws SQLException {
    try {
      Statement statementClass = connection.createStatement();
      return statementClass.executeUpdate(statement);
    } catch (SQLException e) {
      System.err.println("SQL Execution Failed: " + statement);
      throw e;
    }
  }

  public void dispose() {
    try {
      connection.close();
    } catch (SQLException e) {
      System.err.println("Error on disposal");
      e.printStackTrace();
      System.exit(-1);
    }
  }
}
