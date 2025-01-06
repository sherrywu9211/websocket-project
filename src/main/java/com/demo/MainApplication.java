package com.demo;

import com.demo.controller.WebsocketController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MainApplication extends Application {

	private ConfigurableApplicationContext springContext;
	private WebsocketController websocketController = new WebsocketController();

	@Override
	public void init() throws Exception {
		springContext = new SpringApplicationBuilder(Main.class).run();
	}

	@Override
	public void stop() throws Exception {
		springContext.close();
	}

	@Override
	public void start(Stage stage) throws Exception {
		// 顯示主畫面
		websocketController.showView(stage);
	}

	public static void main(String[] args) {
		launch();
	}

}