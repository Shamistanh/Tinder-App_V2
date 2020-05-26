package app;

import db.ConnDetails;

public class HerokuEnv implements HerokuEnvVars {
  ConnDetails connDetails = new  ConnDetails();

  public static int port() {
    try {
      return Integer.parseInt(System.getenv("PORT"));
    } catch (NumberFormatException ex) {
      return 5000;
    }
  }

  public static String jdbc_url() {
    String url = System.getenv("jdbc:postgresql://localhost:5432/postgres");
   // if (url == null) throw new IllegalArgumentException("JDBC_DATABASE_URL is empty!!!");
    return "jdbc:postgresql://localhost:5432/postgres";
  }

  public static String jdbc_username() {
    String url = System.getenv(username);
  //  if (url == null) throw new IllegalArgumentException("JDBC_DATABASE_USERNAME is empty!!!");
    return username;
  }

  public static String jdbc_password() {
    String url = System.getenv(password);
  //  if (url == null) throw new IllegalArgumentException("JDBC_DATABASE_PASSWORD is empty!!!");
    return password;
  }

}
