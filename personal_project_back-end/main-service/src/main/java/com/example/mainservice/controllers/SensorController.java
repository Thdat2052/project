package com.example.mainservice.controllers;

import com.example.common.service.utils.UrlUtils;
import com.example.mainservice.models.SensorDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlUtils.SENSOR_URL)
public class SensorController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public SensorController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public void receiveSensorData(@RequestBody SensorDataModel data) {
        System.out.println("📩 Received sensor data: " + data);

        messagingTemplate.convertAndSend("/topic/turbidity", data);
    }
}
