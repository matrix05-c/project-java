package project.bibliotheque.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.Properties;

public class Env {
  public static Env DB;
  public static Env TABLE;

  private Properties props;
  
  public Env() {
    props = new Properties();
  }

  public static void load() {
    Dotenv env = Dotenv.load();
    String db = env.get("DB_NAME");
    String dbms = env.get("DB_DBMS");
    String host = env.get("DB_HOST");
    String port = env.get("DB_PORT");
    String username = env.get("DB_USERNAME");
    String password = env.get("DB_PASSWORD");
    String account_table = env.get("ACCOUNT_TABLE");
    
    String member_table = env.get("MEMBER_TABLE");
    String preter_table = env.get("PRETER_TABLE");
    String livre_table = env.get("LIVRE_TABLE");
    String rendre_table = env.get("RENDRE_TABLE");

    DB = new Env();
    DB.props.setProperty("username", username);
    DB.props.setProperty("password", password);
    DB.props.setProperty("url", "jdbc:" + dbms + "://" + host + ":" + port + "/" + db + "?currentSchema=public");

    TABLE = new Env();
    TABLE.props.setProperty("account", account_table);
    
    TABLE.props.setProperty("member", member_table);
    TABLE.props.setProperty("preter", preter_table);
    TABLE.props.setProperty("livre", livre_table);
    TABLE.props.setProperty("rendre", rendre_table);
  }

  public String getProperty(String name) {
    return props.getProperty(name);
  }
}
