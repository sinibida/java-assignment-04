package app;

import java.sql.SQLException;

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
    // TODO: Show dialog here

    LoaderReturn ret = new LoaderReturn();
    ret.runner = SQLRunner.getInstance(args.dbUrl, args.dbUsername, args.dbPassword);

    return ret;
  }
}
