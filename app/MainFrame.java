package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import shared.SQLRunner;

public class MainFrame extends JFrame {
  SQLRunner runner = null;

  JTable table = null;

  JTextField statementField = null;

  private void initTable() {
    table = new JTable();
    table.setBackground(Color.LIGHT_GRAY);
  }

  private void initStatementField() {
    statementField = new JTextField("SELECT * FROM tab");
    statementField.addActionListener(event -> {
      // On Submit
      showStatementFieldResult();
    });
  }

  // Components

  private void showDialog(String title, String message) {
    JDialog dialog = new JDialog(this, title, true);
    dialog.setSize(400, 200);
    dialog.setLocationRelativeTo(this);

    JTextArea label = new JTextArea(message);

    label.setMargin(new Insets(8, 8, 8, 8));
    label.setEditable(false);
    label.setLineWrap(true);
    label.setAlignmentX(0.5f);
    dialog.add(label, BorderLayout.CENTER);

    dialog.setVisible(true);
  }

  private JButton EventButton(String label, ActionListener l) {
    JButton button = new JButton(label);
    button.addActionListener(l);
    return button;
  }

  // Feature Methods

  private boolean isQuery(String statement) {
    return statement.toLowerCase().startsWith("select");
  }

  private void showSQLResult(String statement) throws SQLException {
    var result = runner.runQuery(statement);
    var metadata = result.getMetaData();

    int columnCnt = metadata.getColumnCount();
    String[] columnNames = new String[columnCnt];
    for (int i = 0; i < columnCnt; i++) {
      columnNames[i] = metadata.getColumnLabel(i + 1);
    }

    Vector<Object[]> dataVector = new Vector<Object[]>();
    while (result.next()) {
      Object[] objects = new Object[columnCnt];
      for (int i = 0; i < columnCnt; i++) {
        objects[i] = result.getObject(i + 1);
      }
      dataVector.add(objects);
    }

    Object[][] data = new Object[dataVector.size()][columnCnt];
    dataVector.toArray(data);

    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    model.setColumnIdentifiers(columnNames);
    table.setModel(model);
  }

  private void runSQL(String statement) throws SQLException {
    var result = runner.runUpdate(statement);

    String[] columnNames = new String[] { "ROWS AFFECTED" };
    Object[][] data = new Object[][] { { result } };

    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    model.setColumnIdentifiers(columnNames);
    table.setModel(model);
  }

  private boolean ensureConnection() {
    if (runner == null) {
      showDialog("SQL Failure", "Please create a connection first.");
      return false;
    }

    return true;
  }

  private void showStatementFieldResult() {
    if (!ensureConnection())
      return;

    String statement = statementField.getText();
    if (!isQuery(statement)) {
      showDialog("SQL Canceled", "<Show> Button should be used with queries only.\n(ex: 'SELECT ... FROM' command)");
      return;
    }
    try {
      showSQLResult(statement);
    } catch (SQLException e) {
      showDialog("SQL Failure", "Couldn't show SQL result.\n\n" + statement + "\n\n" + e.toString());
    }
  }

  private void runStatementField() {
    if (!ensureConnection())
      return;

    String statement = statementField.getText();
    if (isQuery(statement)) {
      showDialog("SQL Canceled", "Please use <Show> Button for query statement.");
      return;
    }
    try {
      runSQL(statement);
    } catch (SQLException e) {
      showDialog("SQL Failure", "Couldn't show SQL result.\n\n" + statement + "\n\n" + e.toString());
    }
  }

  // Panels

  private JLabel BottomPanelHeader(String text) {
    JLabel label = new JLabel("[ " + text + " ]");
    label.setAlignmentX(0.5f);
    label.setFont(new Font(null, Font.BOLD, 14));
    return label;
  }

  private JPanel BottomPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JPanel buttonRow1 = new JPanel(new FlowLayout());


    buttonRow1.add(EventButton("Show", (event) -> {
      showStatementFieldResult();
    }));

    buttonRow1.add(EventButton("Run", (event) -> {
      runStatementField();
    }));

    JPanel buttonRow2 = new JPanel(new FlowLayout());

    buttonRow2.add(EventButton("조회", (event) -> {
      statementField.setText("SELECT * FROM Employee");
      showStatementFieldResult();
    }));
    buttonRow2.add(EventButton("삽입", (event) -> {
      statementField.setText(
          "INSERT INTO Employee VALUES (4, 'New', 'E.', 'M''ployee', '1990-12-31', 'female', '서울', 25000, 1, NULL)");
      runStatementField();
    }));
    buttonRow2.add(EventButton("삭제", (event) -> {
      statementField.setText("DELETE FROM Employee WHERE ssn=4");
      runStatementField();
    }));
    buttonRow2.add(EventButton("수정", (event) -> {
      statementField.setText("UPDATE Employee SET FName='EDITED' WHERE ssn=4");
      runStatementField();
    }));

    panel.add(BottomPanelHeader("Operations"));
    panel.add(buttonRow1);
    panel.add(BottomPanelHeader("데이터베이스 1분반 과제"));
    panel.add(buttonRow2);

    return panel;
  }

  private JLabel MeImage() throws IOException {
    Image imageMeSrc = ImageIO.read(new File("me.jpg"))
        .getScaledInstance(100, 100, Image.SCALE_AREA_AVERAGING);
    JLabel imageMe = new JLabel(new ImageIcon(imageMeSrc));
    imageMe.setSize(100, 100);
    return imageMe;
  }

  private JTableHeader getHeader(JTable table) {
    JTableHeader header = table.getTableHeader();
    header.setReorderingAllowed(false);
    return header;
  }

  private JPanel TableComponent() {
    JPanel ret = new JPanel();
    ret.setLayout(new BorderLayout());

    ret.add(getHeader(table), BorderLayout.NORTH);

    JScrollPane scroll = new JScrollPane(table);
    ret.add(scroll, BorderLayout.CENTER);

    return ret;
  }

  private JPanel CenterComponent() {
    JPanel ret = new JPanel();
    ret.setLayout(new BorderLayout());

    ret.add(TableComponent(), BorderLayout.CENTER);

    ret.add(statementField, BorderLayout.SOUTH);

    return ret;
  }

  // Main

  private void init() {
    initTable();
    initStatementField();

    setTitle("Database Test");
    setSize(1000, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container cp = getContentPane();

    LayoutManager layout = new BorderLayout();
    cp.setLayout(layout);

    try {
      cp.add(MeImage(), BorderLayout.WEST);
    } catch (IOException e) {
      System.err.println("Image not found.");
    }
    cp.add(BottomPanel(), BorderLayout.SOUTH);
    cp.add(CenterComponent(), BorderLayout.CENTER);

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
