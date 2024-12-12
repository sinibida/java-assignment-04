package views;

import shared.SQLRunner;
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
    return new Object[][]{
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
    System.out.println("Update!");
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
