package project.bibliotheque.config;

import java.net.URL;

import project.bibliotheque.App;


public class Resource {
  private static App app;

  public static void init(App app) {
    Resource.app = app;
  }

  public static String getUrl(String path) {
    if (Resource.app == null) return "";

    URL pathUrl = Resource.app.getClass().getResource(path);
    if (pathUrl == null) return "";

    return pathUrl.toExternalForm();
  }
}
