package app;

import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import shared.SQLRunner;
import views.DepartmentTableViewListener;
import views.EmployeeTableViewListener;
import views.ProjectTableViewListener;
import views.TableView;

/**
 * MainFrame의 콘텐츠
 */
public class MainContentPane extends JPanel {
  SQLRunner runner;

  public MainContentPane(SQLRunner runner) {
    this.runner = runner;
    
    // LATER: 직원이 Column이 많으므로 더 길게 배치?
    setLayout(new GridLayout(1, 3, 16, 0));

    setBorder(new EmptyBorder(new Insets(24, 24, 24, 24)));

    TableView employeeView = new TableView("직원 정보", new EmployeeTableViewListener(runner));
    TableView enrollmentView = new TableView("부서 정보", new DepartmentTableViewListener(runner));
    TableView projectView = new TableView("프로젝트 정보",new ProjectTableViewListener(runner));

    add(employeeView);
    add(enrollmentView);
    add(projectView);
  }
}
