import java.sql.SQLException;

import app.Loader;
import app.Loader.LoaderArgs;
import app.Loader.LoaderReturn;
import app.MainFrame;

class Main {
  // !!! 주소, 계정명, 비밀번호 변경 후 사용 !!!
  final static String DB_URL = "jdbc:oracle:thin:@192.168.64.2:1521:xe";
  final static String DB_USERNAME = "juna";
  final static String DB_PASSWORD = "1234";

  static LoaderArgs getLoaderArgs() {
    LoaderArgs args = new LoaderArgs();
    args.dbUrl = DB_URL;
    args.dbUsername = DB_USERNAME;
    args.dbPassword = DB_PASSWORD;
    return args;
  }

  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    Loader loader = new Loader();
    // TODO: Handler Exception
    LoaderReturn result = loader.load(getLoaderArgs());

    new MainFrame(result.runner);
  }
}