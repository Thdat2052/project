// websocket.ts
import { Client, IMessage } from "@stomp/stompjs";
import SockJS from "sockjs-client";

const stompClient = new Client({
  webSocketFactory: () => new SockJS("http://localhost:9997/ws"),
  reconnectDelay: 5000,
  debug: (str) => console.log(str),
});

export const connectWebSocket = (
  onMessageCallback: (message: any) => void,
  onConnectCallback?: () => void,
  onErrorCallback?: (error: string) => void
) => {
  stompClient.onConnect = () => {
    console.log("Connected to WebSocket");
    onConnectCallback?.();

    stompClient.subscribe("/topic/turbidity", (message: IMessage) => {
      const body = JSON.parse(message.body);
      onMessageCallback(body);
    });
  };

  stompClient.onStompError = (frame) => {
    console.error("STOMP error:", frame);
    onErrorCallback?.(frame.body);
  };

  stompClient.activate();
};

export const disconnectWebSocket = () => {
  if (stompClient.connected) {
    stompClient.deactivate();
  }
};
