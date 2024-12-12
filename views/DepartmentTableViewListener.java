package views;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

import shared.MsgDialog;
import shared.NumberFormatErrDialog;
import shared.SQLExceptionDialog;
import shared.SQLRunner;
import views.FormDialog.FormDialogListener;
import views.TableView.TableViewListener;

public class DepartmentTableViewListener implements TableViewListener {
  static final String INSERT_STATEMENT = "INSERT INTO DEPATMENT VALUES (?, ?, ?)";
  static final String SELECT_ID_STATEMENT = "SELECT * FROM DEPATMENT WHERE Num=?";
  static final String UPDATE_STATEMENT = "UPDATE DEPATMENT SET Num=?, Name=?, ManagedBy=? WHERE Num=?";

  SQLRunner runner;

  PreparedStatement insertStatement;
  PreparedStatement selectIdStatement;
  PreparedStatement updateStatement;

  public DepartmentTableViewListener(SQLRunner runner) {
    this.runner = runner;

    try {
      insertStatement = runner.getPreparedQuery(INSERT_STATEMENT);
      selectIdStatement = runner.getPreparedQuery(SELECT_ID_STATEMENT);
      updateStatement = runner.getPreparedQuery(UPDATE_STATEMENT);
    } catch (SQLException e) {
      new SQLExceptionDialog("Couldn't prepare SQL statements", null, e).setVisible(true);
    }
  }

  // LEFT OFF: SQL 노가다!

  @Override
  public String[] getColumnNames() {
    /**
     * 
     * Num NUMBER NOT NULL PRIMARY KEY,
     * Name VARCHAR(32),
     * ManagedBy NUMBER,
     * 
     */
    return new String[] { "Num",
        "Name",
        "ManagedBy", };
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
    FormDialog dialog = new FormDialog("부서 추가", getColumnNames(), "추가", new FormDialogListener() {
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
          new NumberFormatErrDialog(e).setVisible(true);
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

  // This cannot be a local variable because Java(536871575)
  Object[] selectedRow = null;

  @Override
  public void onUpdate() {
    FormDialog idDialog = new FormDialog("변경할 데이터 조회", new String[] { "Num" }, "조회", new FormDialogListener() {
      @Override
      public boolean onSubmit(String[] values) {
        try {
          selectedRow = Utils.selectOne(selectIdStatement, new int[] {
              Types.NUMERIC,
          }, values);
          return selectedRow != null;
        } catch (SQLException e) {
          new SQLExceptionDialog("Couldn't run SQL update.", SELECT_ID_STATEMENT + " + " + String.join(", ", values), e)
              .setVisible(true);
          return false;
        } catch (NumberFormatException e) {
          new NumberFormatErrDialog(e).setVisible(true);
          return false;
        }
      }

    });

    FormDialog dialog = new FormDialog("부서 정보 변경", getColumnNames(), "수정", new FormDialogListener() {
      @Override
      public boolean onSubmit(String[] values) {
        // LATER: Abtract this try-catch w/ Abstract Class?
        try {
          String[] newValues = Arrays.copyOf(values, values.length + 1);
          newValues[values.length] = values[0];
          Utils.runPreparedStatement(updateStatement, new int[] {
              Types.NUMERIC,
              Types.VARCHAR,
              Types.NUMERIC,
              Types.NUMERIC,
          }, newValues);
          return true;
        } catch (SQLException e) {
          new SQLExceptionDialog("Couldn't run SQL update.", SELECT_ID_STATEMENT + " + " + String.join(", ", values), e)
              .setVisible(true);
          return false;
        } catch (NumberFormatException e) {
          new NumberFormatErrDialog(e).setVisible(true);
          return false;
        }
      }
    });

    selectedRow = null;
    idDialog.setVisible(true);
    if (selectedRow == null)
      return;
    String[] initialValue = new String[selectedRow.length];
    for (int i = 0; i < selectedRow.length; i++) {
      initialValue[i] = selectedRow[i] == null ? "" : selectedRow[i].toString();
    }
    dialog.setInitialValue(initialValue);
    dialog.setVisible(true);
  }

}
