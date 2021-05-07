package controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Content;
import logic.RestMenager;

public class MainWindowController1 implements Initializable {

	@FXML
	BorderPane pane_Container;

	@FXML
	AnchorPane root;

	@Autowired
	RestMenager rm;

	@Autowired
	MediaView media_view;

	public Enum CurrentView;

	List<Object> listOfContents;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.root.setStyle("-fx-background-color: #dbdbdb;");
		this.preloader();
		this.fetchVideos();
	}

	public void preloader() {

		var b1 = new BorderPane();
		b1.setStyle("-fx-background-color: #dbdbdb;");

		var b2 = new BorderPane();
		b2.setStyle("-fx-background-color: #dbdbdb;");

		var b3 = new BorderPane();
		b3.setStyle("-fx-background-color: #dbdbdb;");

		var b4 = new BorderPane();
		b3.setStyle("-fx-background-color: #dbdbdb;");

		var im = new ImageView(new Image("/images/ajax-loader.gif"));
		im.setPreserveRatio(true);
		pane_Container.setCenter(im);
		pane_Container.setTop(b1);
		pane_Container.setBottom(b2);
		pane_Container.setRight(b3);
		pane_Container.setLeft(b4);

		listOfContents = new ArrayList<>();

	}

	public void fetch() {

		this.preloader();

		Thread x = new Thread(() -> {
			listOfContents = rm.fetch_data(Content.Pics);
			this.initWindow();

		});

		x.setName("fetcherXX");
		x.start();

	}

	public void initWindow() {

		CurrentView = View.Photo;

		pane_Container.prefHeight(200);
		pane_Container.prefWidth(200);

		root.setPrefSize(800, 600);

		var b1 = new BorderPane();
		var b2 = new BorderPane();
		var b3 = new BorderPane();
		var b4 = new BorderPane();
		var b5 = new BorderPane();
		var scrollPane = new ScrollPane();

		scrollPane.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

		AnchorPane content = new AnchorPane();
		int howMany = listOfContents.size();
		BorderPane[] picPanes = new BorderPane[howMany];

		for (int i = 0; i < howMany; i++) {
			BorderPane picContainer = new BorderPane();

			picContainer.setMinSize(150, 100);
			picContainer.setMaxHeight(100);
			picContainer.setMaxWidth(150);

			if (listOfContents.get(i) instanceof Image) {

				ImageView ni = new ImageView((Image) listOfContents.get(i));
				ni.setFitWidth(150);
				ni.setFitHeight(100);
				ni.setPreserveRatio(true);
				picContainer.setCenter(ni);
			}

			picContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

				BorderPane ix = ((BorderPane) event.getSource());
				ImageView imgv = (ImageView) ix.getCenter();

				var im = new ImageView(imgv.getImage());
				im.setPreserveRatio(true);
				im.fitHeightProperty().bind(b2.heightProperty());
				b2.setCenter(im);

			});

			Transform t = Transform.translate(150 * i, 0.0);

			picContainer.getTransforms().add(t);
			picPanes[i] = picContainer;

		}

		content.getChildren().addAll(picPanes);
		content.setMinSize(150 * howMany, 100);
		content.setMaxHeight(100);

		scrollPane.setContent(content);

		b1.setStyle("-fx-background-color: #dbdbdb;");
		b1.setPrefHeight(115);
		b1.setMaxHeight(115);
		b1.minHeight(115);
		b1.setCenter(scrollPane);

		b2.setStyle("-fx-background-color: #a0a0a0;");

		b2.setPrefSize(1115, 500);
		b2.setMinSize(500, 300);

		b3.setStyle("-fx-background-color: #dbdbdb;");
		b3.setPrefSize(200, 200);

		b4.setStyle("-fx-background-color: #dbdbdb;");
		b4.setPrefSize(200, 200);

		Menu menu = new Menu("Menu");

		String menu_item_string = "";

		if (CurrentView == View.Photo) {
			menu_item_string = View.Video.toString();
		}

		MenuItem menu_item = new MenuItem(menu_item_string);
		menu_item.setOnAction(e -> {

			this.fetchVideos();
		});

		menu.getItems().add(menu_item);
		MenuBar menu_bar = new MenuBar();
		menu_bar.getMenus().add(menu);

		VBox menu_cont = new VBox(menu_bar);

		Platform.runLater(() -> {

			pane_Container.setCenter(b2);
			pane_Container.setBottom(b1);
			pane_Container.setLeft(b3);
			pane_Container.setRight(b4);
			pane_Container.setTop(menu_cont);

		});
	}

	public void initVideoWindow() {

		CurrentView = View.Video;

		pane_Container.prefHeight(200);
		pane_Container.prefWidth(200);

		root.setPrefSize(800, 600);

		var b1 = new BorderPane();
		var b2 = new BorderPane();
		var b3 = new BorderPane();
		var b4 = new BorderPane();
		var scrollPane = new ScrollPane();

		scrollPane.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

		System.out.println("initVideoWindow-1");

		File userRepository = new File("userImg/");
		int u_length = userRepository.listFiles().length;
		File[] fil_tab = userRepository.listFiles();
		AnchorPane content = new AnchorPane();
		BorderPane[] picPanes = new BorderPane[u_length];

		if (userRepository.exists()) {

			if (u_length > 0) {

				for (int i = 0; i < u_length; i++) {

					BorderPane picContainer = new BorderPane();

					picContainer.setMinSize(150, 100);
					picContainer.setMaxHeight(100);
					picContainer.setMaxWidth(150);

					File fileX = fil_tab[i];

					Media media = null;
					try {
						media = new Media(fileX.toURI().toURL().toString());
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}

					MediaPlayer mediaPlayer = new MediaPlayer(media);
					MediaView mediaView = new MediaView(mediaPlayer);

					mediaView.setFitWidth(150);
					mediaView.setFitHeight(100);
					mediaView.setPreserveRatio(true);

					picContainer.setCenter(mediaView);

					mediaPlayer.setStartTime(new Duration(1000.0));
					mediaPlayer.setMute(true);

					picContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

						BorderPane ix = ((BorderPane) event.getSource());
						MediaView imgv = (MediaView) ix.getCenter();
						String source = imgv.getMediaPlayer().getMedia().getSource();

						MediaPlayer MP = new MediaPlayer(new Media(source));

						media_view = new MediaView(MP);
						media_view.getMediaPlayer().play();

						media_view.setPreserveRatio(true);
						media_view.fitHeightProperty().bind(b2.heightProperty());
						b2.setCenter(media_view);

					});

					Transform t = Transform.translate(150 * i, 0.0);

					picContainer.getTransforms().add(t);
					picPanes[i] = picContainer;

				}
			}
		}

		content.getChildren().addAll(picPanes);
		content.setMinSize(150 * u_length, 100);
		content.setMaxHeight(100);

		scrollPane.setContent(content);

		b1.setStyle("-fx-background-color: #dbdbdb;");
		b1.setPrefHeight(115);
		b1.setMaxHeight(115);
		b1.minHeight(115);
		b1.setCenter(scrollPane);

		b2.setStyle("-fx-background-color: #a0a0a0;");

		b2.setPrefSize(1115, 500);
		b2.setMinSize(500, 300);

		b3.setStyle("-fx-background-color: #dbdbdb;");
		b3.setPrefSize(200, 200);

		b4.setStyle("-fx-background-color: #dbdbdb;");
		b4.setPrefSize(200, 200);

		Menu menu = new Menu("Menu");

		String menu_item_string = "";

		if (CurrentView == View.Video) {
			menu_item_string = View.Photo.toString();
		}

		MenuItem menu_item = new MenuItem(menu_item_string);

		menu_item.setOnAction(e -> {
			if (media_view.getMediaPlayer() != null) {
				media_view.getMediaPlayer().pause();
			}
			this.fetch();
		});

		menu.getItems().add(menu_item);

		MenuBar menu_bar = new MenuBar();

		menu_bar.getMenus().add(menu);

		VBox menu_cont = new VBox(menu_bar);

		Platform.runLater(() -> {

			pane_Container.setCenter(b2);
			pane_Container.setBottom(b1);
			pane_Container.setLeft(b3);
			pane_Container.setRight(b4);
			pane_Container.setTop(menu_cont);

		});

	}

	public void fetchVideos() {
		this.preloader();

		Thread x = new Thread(() -> {
			listOfContents = rm.fetch_data(Content.Videos);
			this.initVideoWindow();

		});
		x.setName("videoFetcherXX");
		x.start();

	}

	public enum View {
		Photo, Video;
	}

}
