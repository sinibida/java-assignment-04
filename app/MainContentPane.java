package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.ScrollPane;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
      center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));

      TableView employeeView = new TableView("직원 정보", new EmployeeTableViewListener(runner));
      TableView enrollmentView = new TableView("부서 정보", new DepartmentTableViewListener(runner));
      TableView projectView = new TableView("프로젝트 정보", new ProjectTableViewListener(runner));

      // 각 TablweView의 너비 설정
      employeeView.setPreferredSize(new Dimension(800, 0));
      enrollmentView.setPreferredSize(new Dimension(400, 0));
      projectView.setPreferredSize(new Dimension(400, 0));

      // https://stackoverflow.com/a/46541119
      center.add(employeeView);
      center.add(Box.createHorizontalStrut(16));
      center.add(enrollmentView);
      center.add(Box.createHorizontalStrut(16));
      center.add(projectView);
    }
    JScrollPane scrollCenter = new JScrollPane(
        center,
        JScrollPane.VERTICAL_SCROLLBAR_NEVER,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    center.setBorder(new EmptyBorder(new Insets(8, 8, 8, 8 )));
    add(scrollCenter, BorderLayout.CENTER);

    JPanel south = new JPanel();
    {
      MeImage meImage = new MeImage();

      south.add(meImage);
    }
    add(south, BorderLayout.SOUTH);
  }
}
