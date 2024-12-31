package com.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class MainApplication extends Application {

	private ConfigurableApplicationContext springContext;

	@Override
	public void init() throws Exception {
		springContext = new SpringApplicationBuilder(MainApplication.class).run();
	}

	@Override
	public void stop() throws Exception {
		springContext.close(); 	// 關閉 Spring 上下文
	}

	public static void main(String[] args) {
//		SpringApplication.run(MainApplication.class, args);
		launch();
		System.out.println("-------B專案啟動-------");
	}


	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/static/view/webSocketView.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 600, 600);
		stage.setTitle("連線系統");
		stage.setScene(scene);
		stage.show();
	}
}