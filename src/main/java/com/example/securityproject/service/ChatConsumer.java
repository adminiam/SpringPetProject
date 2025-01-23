package com.example.securityproject.service;

import com.example.securityproject.models.Message;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatConsumer {
    private final Gson gson = new Gson();
    private final KafkaConsumer<String, String> kafkaConsumer;

    public List<Map<String, Message>> consumeAllMessages() {
        String topic = "chat_topic";
        kafkaConsumer.subscribe(Collections.singletonList(topic));
        kafkaConsumer.poll(Duration.ZERO);
        kafkaConsumer.seekToBeginning(kafkaConsumer.assignment());

        List<Map<String, Message>> allMessages = new ArrayList<>();
        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));

        for (ConsumerRecord<String, String> record : records) {
            String key = record.key();
            String value = record.value();
            Message message = gson.fromJson(value, Message.class);

            Map<String, Message> map = new HashMap<>();
            map.put(key, message);
            allMessages.add(map);
        }
        return allMessages;
    }

    public List<Map<String, Message>> consumeExactUser(String key) {
        List<Map<String, Message>> allMessages = consumeAllMessages();

        return allMessages.stream()
                .filter(map -> map.containsKey(key))
                .toList();
    }
}
