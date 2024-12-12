package app;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TableView extends JPanel {
  public static interface TableViewListener {
    public String[] getHeaders();

    public Object[][] getData();

    public void onInsert();

    public void onDelete();

    public void onUpdate();
  }

  public TableView(String headerText, TableViewListener listener) {
    setLayout(new BorderLayout());

    JLabel header = new JLabel(headerText);
    add(header);
    // TODO
  }
}
