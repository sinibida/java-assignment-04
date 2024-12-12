package app;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import shared.SQLRunner;
import views.DepartmentTableViewListener;
import views.EmployeeTableViewListener;
import views.MeImage;
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
    setLayout(new BorderLayout());

    setBorder(new EmptyBorder(new Insets(24, 24, 24, 24)));

    JPanel center = new JPanel();
    {
      center.setLayout(new GridLayout(1, 3, 16, 0));

      TableView employeeView = new TableView("직원 정보", new EmployeeTableViewListener(runner));
      TableView enrollmentView = new TableView("부서 정보", new DepartmentTableViewListener(runner));
      TableView projectView = new TableView("프로젝트 정보",new ProjectTableViewListener(runner));
  
      center.add(employeeView);
      center.add(enrollmentView);
      center.add(projectView);
    }
    add(center, BorderLayout.CENTER);

    JPanel south = new JPanel();
    {
      MeImage meImage = new MeImage();
      
      south.add(meImage);
    }
    add(south, BorderLayout.SOUTH);
  }
}
