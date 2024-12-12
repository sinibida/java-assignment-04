package views;

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
    return new String[] { "A", "B", "C" };
  }

  @Override
  public Object[][] getData() {
    return new Object[][] {
        {
            1, "a", 100
        },
        {
            1, "a", 100
        },
        {
            1, "a", 100
        },
    };
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
