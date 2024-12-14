package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class TableView extends JPanel {
  public static interface TableViewListener {
    public String[] getColumnNames();

    public Object[][] getData();

    public void onInsert();

    public void onDelete();

    public void onUpdate();
  }

  JTable table = null;
  JTableHeader tableHeader = null;
  TableViewListener listener;

  /**
   * table과 tableHeader 값을 설정한다.
   */
  private void initTable() {
    table = new JTable();
    table.setBackground(Color.LIGHT_GRAY);

    tableHeader = table.getTableHeader();
    tableHeader.setReorderingAllowed(false);
  }

  private void showData() {
    Object[][] data = listener.getData();
    String[] columnNames = listener.getColumnNames();

    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    model.setColumnIdentifiers(columnNames);
    table.setModel(model);
  }

  public TableView(String headerText, TableViewListener listener) {
    this.listener = listener;

    setLayout(new BorderLayout(0, 8));

    JLabel header = new JLabel(headerText);
    header.setFont(getFont().deriveFont(24.0f).deriveFont(Font.BOLD));
    add(header, BorderLayout.NORTH);

    JPanel centerPanel = new JPanel();
    {
      centerPanel.setLayout(new BorderLayout());

      initTable();
      centerPanel.add(table, BorderLayout.CENTER);

      centerPanel.add(tableHeader, BorderLayout.NORTH);
    }
    add(centerPanel, BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel();
    {
      bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

      JButton selectButton = new JButton("?");
      selectButton.setPreferredSize(new Dimension(25, 25));
      selectButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          showData();
        }
      });

      JButton insertButton = new JButton("+");
      insertButton.setPreferredSize(new Dimension(25, 25));
      insertButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          listener.onInsert();
        }
      });

      JButton deleteButton = new JButton("X");
      deleteButton.setPreferredSize(new Dimension(25, 25));
      deleteButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          listener.onDelete();
        }
      });

      JButton updateButton = new JButton(">");
      updateButton.setPreferredSize(new Dimension(25, 25));
      updateButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          listener.onUpdate();
        }
      });

      bottomPanel.add(selectButton);
      bottomPanel.add(insertButton);
      bottomPanel.add(deleteButton);
      bottomPanel.add(updateButton);
    }
    add(bottomPanel, BorderLayout.SOUTH);

    showData();
  }
}
