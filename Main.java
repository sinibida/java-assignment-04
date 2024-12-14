import java.sql.SQLException;

import app.Loader;
import app.Loader.LoaderArgs;
import app.Loader.LoaderReturn;
import app.MainFrame;
import shared.MsgDialog;

class Main {
  // !!! 주소, 계정명, 비밀번호 변경 후 사용 !!!
  static final String DB_URL = "jdbc:oracle:thin:@192.168.64.2:1521:xe";
  static final String DB_USERNAME = "juna";
  static final String DB_PASSWORD = "1234";

  static LoaderArgs getLoaderArgs() {
    LoaderArgs args = new LoaderArgs();
    args.dbUrl = DB_URL;
    args.dbUsername = DB_USERNAME;
    args.dbPassword = DB_PASSWORD;
    return args;
  }

  public static void main(String[] args) {
    Loader loader = new Loader();
    LoaderReturn result;
    try {
      result = loader.load(getLoaderArgs());

      new MainFrame(result.runner);
    } catch (ClassNotFoundException e) {
      // https://stackoverflow.com/a/7191445
      new MsgDialog("JDBC 로드 오류", "JDBC 라이브러리(ojdbc6.jar)를 로드하는 데 오류가 발생했습니다.\n\n" + e.getStackTrace()).setVisible(true);
    } catch (SQLException e) {
      new MsgDialog("연결 오류", "데이터베이스에 연결하는 데 오류가 발생했습니다. Main.java 내 연결 정보를 재확인하십시오. (예: 사용자명, 비밀번호, URL)\n\n" + e.getStackTrace()).setVisible(true);
    }
  }
}