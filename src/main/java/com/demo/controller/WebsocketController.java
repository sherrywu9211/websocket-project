package com.demo.controller;

import jakarta.websocket.server.ServerEndpoint;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@ServerEndpoint("")
public class WebsocketController extends TextWebSocketHandler {

    private static Label statusLabel;
    private static Label clientIdLabel;
    private static Label clientMessageLabel;
    private static WebSocketSession currentSession; // 儲存目前的 WebSocketSession
    public static final Logger logger = LoggerFactory.getLogger(WebsocketController.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        try {
            currentSession = session; // 保存連線的 session
            // 更新 前端顯示Client ID
            Platform.runLater(() -> {
                statusLabel.setText("【連線狀況】: 已連接");
                clientIdLabel.setText("【Client ID】: " + session.getId());
            });
            // 回應A專案端
            session.sendMessage(new TextMessage("連接成功"));
            logger.info("client端已連線");
        }catch (Exception e){
            logger.error("afterConnectionEstablished() error：", e);
        }

    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            Platform.runLater(() -> clientMessageLabel.setText("【Client Message】: " + message.getPayload()));
            logger.info("來自Client端 訊息："+ message.getPayload());
        }catch (Exception e){
            logger.error("handleTextMessage() error：", e);
        }
    }

    public void showView(Stage stage){
        // UI 元件
        statusLabel = new Label("【連線狀況】: 未連接");
        clientIdLabel = new Label("【Client ID】: ");
        clientMessageLabel = new Label("【Client Message】: " );
        Label errorLabel = new Label("");
        TextField inputField = new TextField();
        inputField.setPromptText("請輸入訊息");

        Button sendButton = new Button("送出");
        sendButton.setOnAction(event -> {
            String message = inputField.getText();
            if (message.isEmpty()) {
                errorLabel.setText("訊息不可為空！");
            }

            else {
                if (currentSession != null && currentSession.isOpen()) {
                    try {
                        // 發送訊息至Client端
                         currentSession.sendMessage(new TextMessage(message));
                        errorLabel.setText("訊息發送成功！");
                        inputField.clear();
                        logger.info("server端 已發送訊息至client端：" + message);
                    } catch (Exception e) {
                        errorLabel.setText("訊息發送失敗！");
                        logger.error("server端 訊息發送失敗：", e);
                    }
                } else {
                    errorLabel.setText("目前沒有Client的連線");
                    logger.error("目前沒有Client的連線");
                }
            }
        });

        // 顯示標籤
        VBox vbox = new VBox(10, statusLabel, clientIdLabel, clientMessageLabel, inputField, sendButton, errorLabel);
        vbox.setPrefSize(400, 400);
        vbox.setStyle("-fx-padding: 20;");
        vbox.setSpacing(20);
        vbox.setAlignment(javafx.geometry.Pos.TOP_LEFT);

        Scene scene = new Scene(vbox);
        stage.setTitle("WebsocketApp");
        stage.setScene(scene);
        stage.show();
    }
}