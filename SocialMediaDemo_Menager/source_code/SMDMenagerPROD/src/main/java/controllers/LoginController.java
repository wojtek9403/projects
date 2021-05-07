package controllers;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.RestMenager;
import logic.Session;
import logic.ViewMenager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class LoginController {

	@Autowired
	ConfigurableApplicationContext ctx;

	@Autowired
	Session session;

	@Autowired
	MainWindowController1 mainWindowController1;

	@Autowired
	ViewMenager views;

	@FXML
	AnchorPane LoginRoot;

	@FXML
	TextField loginField;

	@FXML
	Label info;

	@FXML
	PasswordField passwordField;

	@FXML
	Hyperlink link;

	@Autowired
	RestMenager rm;

	@FXML
	public void close() {

		LoginRoot.getScene().getWindow().hide();

		try {
			views.getStageMenager().closeAll();
			Platform.exit();

		} catch (NullPointerException e) {
		}
	}

	@FXML
	public void github() {
		HostServices host = (HostServices) ctx.getBean("hostServices");
		host.showDocument("https://github.com/wojtek9403/projects.git");

	}

	@FXML
	public void login() throws IOException {

		String login = loginField.getText();
		String password = passwordField.getText();

		if (rm.login(login, password)) {
			Stage s = views.getStageMenager().getStages("MainStage");
			s.hide();
			s.setResizable(true);
			views.createAndShowViewOnGivenStage("/fxml/f1.fxml", "/css/style.css", mainWindowController1, "test", s,
					"/images/welcome.jpg");
			s.centerOnScreen();
			s.show();

		} else {

			info.setLayoutX(110.0);
			info.setTextFill(Color.color(1, 0, 0));
			info.setVisible(true);

		}

	}

}
