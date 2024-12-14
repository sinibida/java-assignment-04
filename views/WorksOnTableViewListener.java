package views;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

import shared.NumberFormatErrDialog;
import shared.SQLExceptionDialog;
import shared.SQLRunner;
import views.FormDialog.FormDialogListener;
import views.TableView.TableViewListener;

public class WorksOnTableViewListener implements TableViewListener {
  static final String[] ALL_COLUMNS = new String[] {
      "Employee_Ssn",
      "Project_Number",
      "Hours",
  };
  static final String[] ID_COLUMNS = new String[] {
      "Employee_Ssn",
      "Project_Number",
  };

  static final String SELECT_ALL_STATEMENT = "SELECT * FROM WORKS_ON";

  static final String SELECT_ID_STATEMENT = "SELECT * FROM WORKS_ON WHERE Employee_Ssn=? AND Project_Number=?";
  static final int[] SELECT_ID_TYPES = new int[] {
      Types.NUMERIC,
      Types.NUMERIC,
  };
  static final String SELECT_ID_TITLE = "변경할 데이터 조회";
  static final String SELECT_ID_BUTTON = "선택";

  static final String INSERT_STATEMENT = "INSERT INTO WORKS_ON VALUES (?, ?, ?)";
  static final int[] INSERT_TYPES = new int[] {
      Types.NUMERIC,
      Types.NUMERIC,
      Types.NUMERIC,
  };
  static final String INSERT_TITLE = "근무 정보 추가";
  static final String INSERT_BUTTON = "추가";

  static final String UPDATE_STATEMENT = "UPDATE WORKS_ON SET Employee_Ssn=?, Project_Number=?, Hours=? WHERE Employee_Ssn=? AND Project_Number=?";
  static final int[] UPDATE_TYPES = new int[] {
      Types.NUMERIC,
      Types.NUMERIC,
      Types.NUMERIC,
      Types.NUMERIC,
      Types.NUMERIC,
  };
  static final String UPDATE_TITLE = "근무 정보 변경";
  static final String UPDATE_BUTTON = "수정";

  static final String DELETE_STATEMENT = "DELETE FROM WORKS_ON WHERE Employee_Ssn=? AND Project_Number=?";
  static final int[] DELETE_TYPES = new int[] {
      Types.NUMERIC,
      Types.NUMERIC,
  };

  SQLRunner runner;

  PreparedStatement insertStatement;
  PreparedStatement selectIdStatement;
  PreparedStatement updateStatement;
  PreparedStatement deleteStatement;

  public WorksOnTableViewListener(SQLRunner runner) {
    this.runner = runner;

    try {
      insertStatement = runner.getPreparedQuery(INSERT_STATEMENT);
      selectIdStatement = runner.getPreparedQuery(SELECT_ID_STATEMENT);
      updateStatement = runner.getPreparedQuery(UPDATE_STATEMENT);
      deleteStatement = runner.getPreparedQuery(DELETE_STATEMENT);
    } catch (SQLException e) {
      new SQLExceptionDialog("Couldn't prepare SQL statements", null, e).setVisible(true);
    }
  }

  @Override
  public String[] getColumnNames() {
    return ALL_COLUMNS;
  }

  @Override
  public Object[][] getData() {
    try {
      return Utils.statementToObjectMatrix(runner, SELECT_ALL_STATEMENT);
    } catch (SQLException e) {
      new SQLExceptionDialog("Couldn't show SQL result.", SELECT_ALL_STATEMENT, e).setVisible(true);
      return new Object[][] {};
    }
  }

  @Override
  public void onInsert() {
    FormDialog dialog = new FormDialog(INSERT_TITLE, ALL_COLUMNS, INSERT_BUTTON, new FormDialogListener() {
      @Override
      public boolean onSubmit(String[] values) {
        try {
          Utils.runPreparedStatement(insertStatement, INSERT_TYPES, values);
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

  // This cannot be a local variable because Java(536871575)
  Object[] selectedRow = null;

  protected Object[] selectRowWithId() {
    FormDialog idDialog = new FormDialog(SELECT_ID_TITLE, ID_COLUMNS, SELECT_ID_BUTTON,
        new FormDialogListener() {
          @Override
          public boolean onSubmit(String[] values) {
            try {
              selectedRow = Utils.selectOne(selectIdStatement, SELECT_ID_TYPES, values);
              return selectedRow != null;
            } catch (SQLException e) {
              new SQLExceptionDialog("Couldn't run SQL update.",
                  SELECT_ID_STATEMENT + " + " + String.join(", ", values), e)
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
    return selectedRow;
  }

  @Override
  public void onDelete() {
    Object[] selectedRow = selectRowWithId();
    if (selectedRow == null)
      return;

    String[] values = new String[] { selectedRow[0].toString() };
    try {
      Utils.runPreparedStatement(deleteStatement, DELETE_TYPES, values);
    } catch (SQLException e) {
      new SQLExceptionDialog("Couldn't run SQL update.", DELETE_STATEMENT + " + " + String.join(", ", values), e)
          .setVisible(true);
    } catch (NumberFormatException e) {
      new NumberFormatErrDialog(e).setVisible(true);
    }
  }

  @Override
  public void onUpdate() {
    FormDialog dialog = new FormDialog(UPDATE_TITLE, ALL_COLUMNS, UPDATE_BUTTON, new FormDialogListener() {
      @Override
      public boolean onSubmit(String[] values) {
        try {
          String[] newValues = Arrays.copyOf(values, values.length + ID_COLUMNS.length);
          for (int i = 0; i < ID_COLUMNS.length; i++) {
            newValues[values.length + i] = values[i];
          }
          Utils.runPreparedStatement(updateStatement, UPDATE_TYPES, newValues);
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

    Object[] selectedRow = selectRowWithId();
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
