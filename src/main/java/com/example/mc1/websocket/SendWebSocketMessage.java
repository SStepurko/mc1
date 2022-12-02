package com.example.mc1.websocket;

import com.example.mc1.restservice.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * Sends to websocket microservice message
 */
public class SendWebSocketMessage {

	public static void sendWebSocket(Message message) {
		WebSocketClient client = new StandardWebSocketClient();
		WebSocketStompClient stompClient = new WebSocketStompClient(client);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
			@Override
			public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
				session.send("/app/message", message);
				super.afterConnected(session, connectedHeaders);
			}
		};
		stompClient.connectAsync("ws://mc2:10002/ws-server", sessionHandler);

	}

}
