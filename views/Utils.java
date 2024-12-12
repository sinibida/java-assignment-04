package views;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import shared.SQLRunner;

class Utils {
  static void runPreparedStatement(PreparedStatement statement, int[] types, String[] values)
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
    statement.executeUpdate();
  }

  static Object[][] statementToObjectMatrix(SQLRunner runner, String statement) throws SQLException {
    var result = runner.runQuery(statement);
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
}
