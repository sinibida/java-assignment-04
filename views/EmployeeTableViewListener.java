package views;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Types;
import java.util.Vector;

import shared.MsgDialog;
import shared.SQLExceptionDialog;
import shared.SQLRunner;
import views.FormDialog.FormDialogListener;
import views.TableView.TableViewListener;

public class EmployeeTableViewListener implements TableViewListener {
  static final String INSERT_STATEMENT = "INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  SQLRunner runner;

  PreparedStatement insertStatement;

  public EmployeeTableViewListener(SQLRunner runner) {
    this.runner = runner;

    try {
      insertStatement = runner.getPreparedQuery(INSERT_STATEMENT);
    } catch (SQLException e) {
      new SQLExceptionDialog("Couldn't prepare SQL statements", null, e).setVisible(true);
    }
  }

  @Override
  public String[] getColumnNames() {
    return new String[] { "Ssn",
        "BDate",
        "Sex",
        "Address",
        "Salary",
        "Fname",
        "Minit",
        "Lname",
        "Supervision",
        "WorksFor", };
  }

  @Override
  public Object[][] getData() {
    final String STATEMENT = "SELECT * FROM EMPLOYEE";
    try {
      var result = runner.runQuery(STATEMENT);
      var metadata = result.getMetaData();

      int columnCnt = metadata.getColumnCount();
      String[] columnNames = new String[columnCnt];
      for (int i = 0; i < columnCnt; i++) {
        columnNames[i] = metadata.getColumnLabel(i + 1);
      }

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
    } catch (SQLException e) {
      new SQLExceptionDialog("Couldn't show SQL result.", STATEMENT, e).setVisible(true);
      return new Object[][] {};
    }
  }

  void runPreparedStatement(PreparedStatement statement, int[] types, String[] values)
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

  @Override
  public void onInsert() {
    FormDialog dialog = new FormDialog("직원 추가", getColumnNames(), "추가", new FormDialogListener() {
      @Override
      public boolean onSubmit(String[] values) {
        try {
          runPreparedStatement(insertStatement, new int[]{
            Types.NUMERIC,
            Types.DATE,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.NUMERIC,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.NUMERIC,
            Types.NUMERIC,
          }, values);
          return true;
        } catch (SQLException e) {
          new SQLExceptionDialog("Couldn't run SQL update.", INSERT_STATEMENT + " + " + String.join(", ", values), e)
              .setVisible(true);
          return false;
        } catch (NumberFormatException e) {
          new MsgDialog("형식 에러", "알맞은 숫자 값을 입력해주세요: \n\n" + e.getMessage()).setVisible(true);;
          return false;
        }
      }

    });
    dialog.setVisible(true);
  }

  @Override
  public void onDelete() {
    System.out.println("Update!");
  }

  @Override
  public void onUpdate() {
    System.out.println("Update!");
  }

}
