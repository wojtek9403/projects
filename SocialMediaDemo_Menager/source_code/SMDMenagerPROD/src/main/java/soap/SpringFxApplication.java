package soap;

import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Session;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@ComponentScan(basePackages = { "controllers", "configuration.spring", "logic" })
@SpringBootApplication
public class SpringFxApplication extends Application {

	public boolean done;

	ConfigurableApplicationContext ctx;
	Parent rootNode;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/starter.fxml"));
		loader.setController(ctx.getBean("starterController"));
		rootNode = loader.load();

		stage.initStyle(StageStyle.UNDECORATED);
		stage.setAlwaysOnTop(true);
		stage.getIcons().add(new Image("/images/welcome.jpg"));
		stage.centerOnScreen();
		stage.setScene(new Scene(rootNode));
		stage.getScene().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		ctx.getBeanFactory().registerSingleton("initStage", stage);
		stage.show();
		ctx.getBeanFactory().registerSingleton("hostServices", getHostServices());

	}

	public void init() throws IOException {
		ctx = SpringApplication.run(SpringFxApplication.class);
	}

	public void stop() {

		var threadFX = Thread.currentThread();

		var x = new Thread(() -> {

			File userRepository = new File("userImg/");
			if (userRepository.exists()) {

				for (File f : userRepository.listFiles()) {

					f.delete();
					f.deleteOnExit();

				}

				while (true) {
					if (threadFX.getState() == Thread.State.TERMINATED) {

						try {
							FileUtils.deleteDirectory(userRepository);
						} catch (IOException e) {
							e.printStackTrace();
						}
						userRepository.deleteOnExit();
						break;
					}
				}
			}

		});
		x.start();

		((Session) ctx.getBean("session")).invalidate();
		ctx.close();
	}
}
