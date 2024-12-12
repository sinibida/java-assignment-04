package app;

import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * MainFrame의 콘텐츠
 */
public class MainContentPane extends JPanel {
  public MainContentPane() {
    setLayout(new GridLayout(1, 3));
    
    TableView employeeView  = new TableView("직원 정보", null);
    TableView enrollmentView = new TableView("부서 정보", null);
    TableView projectView = new TableView("프로젝트 정보", null);

    add(employeeView);
    add(enrollmentView);
    add(projectView);
  }
}
