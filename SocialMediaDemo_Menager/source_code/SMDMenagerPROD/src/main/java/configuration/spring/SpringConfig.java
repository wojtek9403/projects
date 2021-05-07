package configuration.spring;

import controllers.LoginController;
import controllers.MainWindowController1;
import controllers.StarterController;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

	@Bean
	public MainWindowController1 mainWindowController1() {
		return new MainWindowController1();
	}

	@Bean
	public LoginController loginController() {
		return new LoginController();
	}

	@Bean
	public StarterController starterController() {
		return new StarterController();
	}

	@Bean
	public MediaView media_view() {

		MediaView media_view = new MediaView();

		return media_view;
	}

}
