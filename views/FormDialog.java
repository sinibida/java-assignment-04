package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * 레코드 값을 입력받기 위한 Dialog
 */
public class FormDialog extends JDialog {
  static final String CANCEL_LABEL = "취소";

  public static interface FormDialogListener {
    /**
     * @return 폼 제출이 성공적으로 실행되었을 시 true를 반환할 것
     */
    public boolean onSubmit(String[] values);
  }

  String title;
  String confirmLabel;
  String[] fields;
  FormDialogListener listener;

  Vector<JTextField> renderedFields;

  /**
   * @param title        Dialog의 타이틀
   * @param fields       각 필드의 목록
   * @param confirmLabel <확인> 버튼에 사용할 단어
   * @param listener
   */
  public FormDialog(String title, String[] fields, String confirmLabel, FormDialogListener listener) {
    this.title = title;
    this.confirmLabel = confirmLabel;
    this.fields = fields;
    this.listener = listener;
  }

  public String getConfirmLabel() {
    return confirmLabel;
  }

  public void setConfirmLabel(String confirmLabel) {
    this.confirmLabel = confirmLabel;
  }

  public String[] getFields() {
    return fields;
  }

  public void setFields(String[] fields) {
    this.fields = fields;
  }

  public FormDialogListener getListener() {
    return listener;
  }

  public void setListener(FormDialogListener listener) {
    this.listener = listener;
  }

  boolean confirm() {
    Vector<String> values = new Vector<>();
    for (JTextField field : renderedFields) {
      values.add(field.getText());
    }
    String[] ret = new String[values.size()];
    values.toArray(ret);
    return listener.onSubmit(ret);
  }

  void close() {
    setVisible(false);
    dispose();
  }

  void render() {
    setTitle(title);
    setModalityType(ModalityType.APPLICATION_MODAL);
    setLocationRelativeTo(null);

    JPanel c = new JPanel();

    c.setLayout(new BorderLayout());
    c.setSize(300, 300);
    c.setBorder(new EmptyBorder(new Insets(32, 32, 32, 32)));

    JPanel centerPanel = new JPanel();
    {
      renderedFields = new Vector<>();

      GridLayout layout = new GridLayout(fields.length, 2, 8, 8);
      centerPanel.setLayout(layout);
      for (String fieldLabel : fields) {
        // LATER: 라벨의 너비를 조금 줄이기: GridBagLayout 활용?
        JLabel label = new JLabel(fieldLabel);
        centerPanel.add(label);

        JTextField field = new JTextField(20);
        centerPanel.add(field);
        renderedFields.add(field);
      }
    }
    c.add(centerPanel, BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel();
    {
      FlowLayout layout = new FlowLayout();
      layout.setAlignment(FlowLayout.RIGHT);
      bottomPanel.setLayout(layout);

      JButton confirm = new JButton(confirmLabel);
      confirm.setFont(confirm.getFont().deriveFont(Font.BOLD));
      confirm.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (confirm())
            close();
        }
      });
      bottomPanel.add(confirm);

      JButton cancel = new JButton(CANCEL_LABEL);
      cancel.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          close();
        }
      });
      bottomPanel.add(cancel);
    }
    c.add(bottomPanel, BorderLayout.SOUTH);

    setContentPane(c);
    pack();
  }

  @Override
  public void setVisible(boolean b) {
    if (b) {
      render();
    }
    super.setVisible(b);
  }

}
