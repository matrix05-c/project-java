package project.bibliotheque;

import project.bibliotheque.config.Env;
import project.bibliotheque.config.Resource;
import project.bibliotheque.config.Style;
import project.bibliotheque.Components.*;
import project.bibliotheque.controllers.*;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;


public class App extends Application {
  private BorderPane mainPane;
  private StackPane pagePane;
  private Scene scene;

  private Header header;

  private PageController pager;
  private LoginController loginController;
  private HomeController homeController;
  private BookController bookController;
  private MemberController memberController;

  public static String db_username;
  public static String db_password;
  public static String db_url;

  public static void main(String[] args) {
    Env.load();
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    mainPane = new BorderPane();
    pagePane = new StackPane();
    scene = new Scene(mainPane, 1024, 600);
    header = new Header();

    pager = new PageController();

    Resource.init(this);
    Style.load(scene);

    setupHeaderHandler();
    initLoginPage();
    initHomePage();
    initBookPage();
    initMemberPage();

    mainPane.setTop(header);
    mainPane.setCenter(pagePane);

    gotoLoginPage();

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void setupHeaderHandler() {
    header.setBrandBtnHandler(e -> {
      gotoLoginPage();
    });
    header.setHomeBtnHandler(e -> {
      gotoHomePage();
    });
    header.setBookBtnHandler(e -> {
      gotoBookPage();
    });
    header.setMemberBtnHandler(e -> {
      gotoMemberPage();
    });
  }

  private void initLoginPage() {
    loginController = new LoginController();
    Pane login = loginController.getView();
    pagePane.getChildren().add(login);
    pager.registerPage("login", login);

    loginController.setLoginBtnHandler(event -> {
      if (loginController.isCredentialValid()) gotoHomePage();
      else loginController.notifyInvalidCredential();
    });
  }

  private void initHomePage() {
    homeController = new HomeController();
    Pane home = homeController.getView();
    pagePane.getChildren().add(home);
    pager.registerPage("home", home);

  }

  private void initMemberPage() {
    memberController = new MemberController();
    Pane member = memberController.getView();
    pagePane.getChildren().add(member);
    pager.registerPage("member", member);
  }

  private void initBookPage() {
    bookController = new BookController();
    
    Pane book = bookController.getView();
    pagePane.getChildren().add(book);
    pager.registerPage("book", book);
  }


  public void gotoLoginPage() {
    pager.gotoPage("login");
    header.setVisible(false);
  }

  public void gotoHomePage() {
    homeController.reload();
    pager.gotoPage("home");
    header.setVisible(true);
    homeController.reload();
    header.setAccentTo("home");
  }

  public void gotoBookPage() {
    bookController.reload();
    bookController.gotoListPage();

    pager.gotoPage("book");
    bookController.reload();
    bookController.gotoListPage();
    header.setVisible(true);
    header.setAccentTo("book");
  }

  public void gotoMemberPage() {
    memberController.reload();
    memberController.gotoListPage();

    pager.gotoPage("member");
    header.setVisible(true);
    header.setAccentTo("member");
  }
}
