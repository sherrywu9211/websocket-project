package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLOutput;

@SpringBootApplication
public class WebsocketProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketProjectApplication.class, args);
		System.out.println("-------B專案啟動-------");
	}

}