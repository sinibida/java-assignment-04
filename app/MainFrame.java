package app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import shared.SQLRunner;

public class MainFrame extends JFrame {
  SQLRunner runner = null;

  private void init() {
    setTitle("수강신청 창");
    setSize(1200, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setContentPane(new MainContentPane(runner));

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        if (runner != null)
          runner.dispose();
      }
    });

    setVisible(true);
  }

  public MainFrame(SQLRunner runner) {
    super();

    this.runner = runner;

    init();
  }
}
