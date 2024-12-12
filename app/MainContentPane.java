package app;

import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import shared.SQLRunner;
import views.EmployeeTableViewListener;
import views.TableView;

/**
 * MainFrame의 콘텐츠
 */
public class MainContentPane extends JPanel {
  SQLRunner runner;

  public MainContentPane(SQLRunner runner) {
    this.runner = runner;
    
    setLayout(new GridLayout(1, 3, 16, 0));

    setBorder(new EmptyBorder(new Insets(24, 24, 24, 24)));

    TableView employeeView = new TableView("직원 정보", new EmployeeTableViewListener(runner));
    TableView enrollmentView = new TableView("부서 정보", null);
    TableView projectView = new TableView("프로젝트 정보", null);

    add(employeeView);
    add(enrollmentView);
    add(projectView);
  }
}
