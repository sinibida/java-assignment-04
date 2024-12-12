package app;

import java.sql.SQLException;

import shared.LoadingDialog;
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
      dialog = new LoadingDialog();
      dialog.setVisible(true);
      LoaderReturn ret = new LoaderReturn();
      ret.runner = SQLRunner.getInstance(args.dbUrl, args.dbUsername, args.dbPassword);

      return ret;
    } finally {
      if (dialog != null)
        dialog.dispose();
    }
  }
}
