package views;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import shared.MsgDialog;
import shared.SQLExceptionDialog;
import shared.SQLRunner;
import views.FormDialog.FormDialogListener;
import views.TableView.TableViewListener;

public class DepartmentTableViewListener implements TableViewListener {
  static final String INSERT_STATEMENT = "INSERT INTO DEPATMENT VALUES (?, ?, ?)";

  SQLRunner runner;

  PreparedStatement insertStatement;

  public DepartmentTableViewListener(SQLRunner runner) {
    this.runner = runner;

    try {
      insertStatement = runner.getPreparedQuery(INSERT_STATEMENT);
    } catch (SQLException e) {
      new SQLExceptionDialog("Couldn't prepare SQL statements", null, e).setVisible(true);
    }
  }

  @Override
  public String[] getColumnNames() {
    /**
     * 
  Num NUMBER NOT NULL PRIMARY KEY,
  Name VARCHAR(32),
  ManagedBy NUMBER,

     */
    return new String[] { "Num",
        "Name",
        "ManagesBy", };
  }

  @Override
  public Object[][] getData() {
    final String STATEMENT = "SELECT * FROM DEPATMENT";
    try {
      return Utils.statementToObjectMatrix(runner, STATEMENT);
    } catch (SQLException e) {
      new SQLExceptionDialog("Couldn't show SQL result.", STATEMENT, e).setVisible(true);
      return new Object[][] {};
    }
  }

  @Override
  public void onInsert() {
    FormDialog dialog = new FormDialog("직원 추가", getColumnNames(), "추가", new FormDialogListener() {
      @Override
      public boolean onSubmit(String[] values) {
        try {
          Utils.runPreparedStatement(insertStatement, new int[] {
              Types.NUMERIC,
              Types.VARCHAR,
              Types.NUMERIC,
          }, values);
          return true;
        } catch (SQLException e) {
          new SQLExceptionDialog("Couldn't run SQL update.", INSERT_STATEMENT + " + " + String.join(", ", values), e)
              .setVisible(true);
          return false;
        } catch (NumberFormatException e) {
          new MsgDialog("형식 에러", "알맞은 숫자 값을 입력해주세요: \n\n" + e.getMessage()).setVisible(true);
          ;
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
