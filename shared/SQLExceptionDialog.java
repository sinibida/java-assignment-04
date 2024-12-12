package shared;

import java.sql.SQLException;

public class SQLExceptionDialog extends MsgDialog {
  public SQLExceptionDialog(String msg, String statement, SQLException e) {
    super("SQL Failure",
        msg + (statement == null ? "" : "\n\n" + statement) + "\n\n" + e.toString());
  }
}
