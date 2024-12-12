package views;

import java.sql.SQLException;
import java.util.Vector;

import shared.MsgDialog;
import shared.SQLRunner;
import views.FormDialog.FormDialogListener;
import views.TableView.TableViewListener;

public class EmployeeTableViewListener implements TableViewListener {
  SQLRunner runner;

  public EmployeeTableViewListener(SQLRunner runner) {
    this.runner = runner;
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
      MsgDialog errDialog = new MsgDialog("SQL Failure",
          "Couldn't show SQL result.\n\n" + STATEMENT + "\n\n" + e.toString());
      errDialog.setVisible(true);
      return new Object[][] {};
    }
  }

  @Override
  public void onInsert() {
    FormDialog dialog = new FormDialog("직원 추가", getColumnNames(), "추가", new FormDialogListener() {
      @Override
      public void onSubmit(String[] values) {
        // TODO Submit SQL
        for (String string : values) {
          System.out.println(string);
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
