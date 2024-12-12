package shared;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JTextArea;

/**
 * 메세지 출력을 위한 JDialog 구현.
 * 여러 줄의 메세지 출력을 지원한다.
 * 
 * 초기화 후 setVisible(true)로 표시해주면 된다.
 */
public class MsgDialog extends JDialog {
  public MsgDialog(String title, String message, boolean modal) {
    setSize(400, 200);
    setLocationRelativeTo(this);

    if (modal) {
      // 상위 윈도우 접근을 막음
      setModalityType(ModalityType.APPLICATION_MODAL);
    }

    JTextArea label = new JTextArea(message);

    label.setMargin(new Insets(8, 8, 8, 8));
    label.setEditable(false);
    label.setLineWrap(true);
    label.setAlignmentX(0.5f);
    add(label, BorderLayout.CENTER);

    // 이 Dialog가 유일한 창이고, 이 창이 꺼질 때 -> 프로세스 종료
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  public MsgDialog(String title, String message) {
    this(title, message, true);
  }
}
