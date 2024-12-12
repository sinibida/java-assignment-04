package shared;

public class NumberFormatErrDialog extends MsgDialog {
  public NumberFormatErrDialog(NumberFormatException e) {
    super("형식 에러", "알맞은 숫자 값을 입력해주세요: \n\n" + e.getMessage());
  }
}
