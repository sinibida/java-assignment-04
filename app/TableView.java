package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class TableView extends JPanel {
  public static interface TableViewListener {
    public String[] getHeaders();

    public Object[][] getData();

    public void onInsert();

    public void onDelete();

    public void onUpdate();
  }

  JTable table = null;

  private void initTable() {
    table = new JTable();
    table.setBackground(Color.LIGHT_GRAY);
  }

  public TableView(String headerText, TableViewListener listener) {
    initTable();

    setLayout(new BorderLayout());

    JLabel header = new JLabel(headerText);
    add(header, BorderLayout.NORTH);

    add(table, BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel();
    {
      bottomPanel.setLayout(new FlowLayout());

      // TODO Interpolation
      JButton selectButton = new JButton("검색");
      JButton insertButton = new JButton("추가");
      JButton deleteButton = new JButton("삭제");
      JButton updateButton = new JButton("변경");

      bottomPanel.add(selectButton);
      bottomPanel.add(insertButton);
      bottomPanel.add(deleteButton);
      bottomPanel.add(updateButton);
    }
    add(bottomPanel, BorderLayout.SOUTH);
  }
}
