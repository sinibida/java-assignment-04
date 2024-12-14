package views.table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import shared.MsgDialog;
import shared.SQLRunner;

class Utils {
  //// Queries

  /**
   * Set-up prepared statement IN-PLACE with values .
   * 
   * @param statement
   * @param types
   * @param values
   * @throws SQLException
   * @throws NumberFormatException
   */
  static void setupPreparedStatement(PreparedStatement statement, int[] types, String[] values)
      throws SQLException, NumberFormatException {
    for (int i = 0; i < values.length; i++) {
      int idx = i + 1;
      final String value = values[i];

      if (value.isEmpty()) {
        statement.setNull(idx, types[i]);
        continue;
      }

      if (types[i] == Types.NUMERIC) {
        // Ssn, Salary, Superbision, WorksFor
        statement.setInt(idx, Integer.parseInt(value));
      } else {
        statement.setString(idx, value);
      }
    }
  }

  static Object[][] resultSetToObjectMatrix(ResultSet result) throws SQLException {
    var metadata = result.getMetaData();
    int columnCnt = metadata.getColumnCount();

    Vector<Object[]> dataVector = new Vector<Object[]>();
    while (result.next()) {
      Object[] objects = new Object[columnCnt];
      for (int i = 0; i < columnCnt; i++) {
        objects[i] = result.getObject(i + 1);
      }
      dataVector.add(objects);
    }

    Object[][] data = new Object[dataVector.size()][columnCnt];
    dataVector.toArray(data);

    return data;
  }

  /**
   * @return 레코드 값. 존재하지 않을 시 null 반환.
   */
  static Object[] selectOne(PreparedStatement statement, int[] types, String[] values)
      throws NumberFormatException, SQLException {
    ResultSet result = Utils.queryPreparedStatement(statement, types, values);
    Object[][] matrix = Utils.resultSetToObjectMatrix(result);

    if (matrix.length == 0) {
      new MsgDialog("Invalid ID", "입력한 ID의 데이터를 찾을 수 없습니다.")
          .setVisible(true);
      return null;
    }

    return matrix[0];
  }

  static void runPreparedStatement(PreparedStatement statement, int[] types, String[] values)
      throws SQLException, NumberFormatException {
    setupPreparedStatement(statement, types, values);
    statement.executeUpdate();
  }

  static ResultSet queryPreparedStatement(PreparedStatement statement, int[] types, String[] values)
      throws SQLException, NumberFormatException {
    setupPreparedStatement(statement, types, values);
    return statement.executeQuery();
  }

  static Object[][] statementToObjectMatrix(SQLRunner runner, String statement) throws SQLException {
    return resultSetToObjectMatrix(runner.runQuery(statement));
  }
}
