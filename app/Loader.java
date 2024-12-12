package app;

import java.sql.SQLException;

import shared.MsgDialog;
import shared.SQLRunner;

public class Loader {
  public static class LoaderArgs {
    public String dbUrl;
    public String dbUsername;
    public String dbPassword;
  }

  public static class LoaderReturn {
    public SQLRunner runner;
  }

  public LoaderReturn load(LoaderArgs args) throws ClassNotFoundException, SQLException {
    MsgDialog dialog = null;
    try {
      dialog = new MsgDialog("로딩 중", "로딩 중입니다...");
      LoaderReturn ret = new LoaderReturn();
      ret.runner = SQLRunner.getInstance(args.dbUrl, args.dbUsername, args.dbPassword);

      return ret;
    } finally {
      if (dialog != null)
        dialog.dispose();
    }
  }
}
