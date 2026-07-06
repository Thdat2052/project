package com.example.mainservice.controllers.websockets;

import com.example.mainservice.models.SensorDataModel;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    // Định nghĩa phương thức để nhận tin nhắn từ client gửi tới "/app/hello"
    @MessageMapping("/sensor")  // Khi client gửi dữ liệu đến "/app/sensor"
    @SendTo("/topic/sensor")  // Gửi kết quả đến tất cả client đang lắng nghe "/topic/sensor"
    public SensorDataModel sendData(SensorDataModel data) {
        return data;  // Trả về dữ liệu cảm biến
    }
}
