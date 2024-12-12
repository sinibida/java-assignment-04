package views;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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

  @Override
  public void onInsert() {
    FormDialog dialog = new FormDialog("직원 추가", getColumnNames(), "추가", new FormDialogListener() {
      @Override
      public void onSubmit(String[] values) {
        try {
          for (int i = 0; i < values.length; i++) {
            insertStatement.setString(i, values[i]);
          }
          insertStatement.executeUpdate();
        } catch (SQLException e) {
          new SQLExceptionDialog("Couldn't run SQL update.", INSERT_STATEMENT + " + " + String.join(", ", values), e)
              .setVisible(true);
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
