package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.RestMenager;
import logic.ViewMenager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.ConnectException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class StarterController implements Initializable {

	@Autowired
	LoginController loginC;

	@Autowired
	RestMenager rm;

	@FXML
	StackPane stackPane;

	@Autowired
	ViewMenager views;

	SplashLoader splashLoader;

	public StarterController() {
		splashLoader = new SplashLoader();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		splashLoader.setName("splash");
		splashLoader.setDaemon(true);
		splashLoader.start();
	}

	class SplashLoader extends Thread {

		@Override
		public void run() {
			try {

				Thread.sleep(1000);

				try {

					if (rm.conectionTest("http://localhost:8080/SocialMediaDemo/login")) {

						Platform.runLater(new Runnable() {
							@Override
							public void run() {

								Stage LoginStage = new Stage();
								LoginStage.centerOnScreen();
								views.getStageMenager().setStages("MainStage", LoginStage);
								LoginStage.setResizable(false);

								AnchorPane pane = (AnchorPane) views.createAndShowViewOnGivenStage("/fxml/Login.fxml",
										"/css/style.css", loginC, "lala", LoginStage, "/images/welcome.jpg");

								stackPane.getScene().getWindow().hide();
							}
						});
					}

				} catch (Exception e) {

					Platform.runLater(() -> {

						Stage LoginStage = new Stage();
						LoginStage.centerOnScreen();
						views.getStageMenager().setStages("MainStage", LoginStage);
						LoginStage.setResizable(false);

						AnchorPane lostRootPane = null;
						try {
							lostRootPane = FXMLLoader.load(getClass().getResource("/fxml/connectLost.fxml"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}

						LoginStage.setScene(new Scene(lostRootPane));
						LoginStage.getScene().getStylesheets()
								.add(getClass().getResource("/css/style.css").toExternalForm());
						LoginStage.show();
						stackPane.getScene().getWindow().hide();

					});

				}

			} catch (Exception e) {
			}
		}
	}

}
