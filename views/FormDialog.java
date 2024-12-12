package views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 레코드 값을 입력받기 위한 Dialog
 */
public class FormDialog extends JDialog {
  static final String CANCEL_LABEL = "취소";

  public static interface FormDialogListener {
    public void onSubmit(String[] values);
  }

  String title;
  String confirmLabel;
  String[] fields;
  FormDialogListener listener;

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

  void render() {
    JPanel c = new JPanel();

    c.setLayout(new BorderLayout());
    c.setSize(300, 300);
    c.setBorder(new EmptyBorder(new Insets(32, 32, 32, 32)));

    JPanel centerPanel = new JPanel();
    {
      GridLayout layout = new GridLayout(fields.length, 2, 8, 8);
      centerPanel.setLayout(layout);
      for (String fieldLabel : fields) {
        // LATER: 라벨의 너비를 조금 줄이기: GridBagLayout 활용?
        JLabel label = new JLabel(fieldLabel);
        centerPanel.add(label);

        TextField field = new TextField(20);
        // TODO: Interpolation
        centerPanel.add(field);
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
      // TODO: confirm interpolation
      bottomPanel.add(confirm);

      JButton cancel = new JButton(CANCEL_LABEL);
      bottomPanel.add(cancel);
    }
    c.add(bottomPanel, BorderLayout.SOUTH);

    setContentPane(c);
    pack();
    setTitle(title);
  }

  @Override
  public void setVisible(boolean b) {
    if (b) {
      render();
    }
    super.setVisible(b);
  }

}
