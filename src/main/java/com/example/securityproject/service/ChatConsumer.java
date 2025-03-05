package com.example.securityproject.service;

import com.example.securityproject.exception.SuppressedStackTraceException;
import com.example.securityproject.models.Message;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatConsumer {
    private final ConsumerFactory<String, String> consumerFactory;
    private final Gson gson = new Gson();
    private static final String TOPIC = "chat_topic";
    private static final Duration POLL_TIMEOUT = Duration.ofSeconds(10);

    public List<Map<String, Message>> consumeAllMessages() {
        List<Map<String, Message>> allMessages = new ArrayList<>();

        try (KafkaConsumer<String, String> consumer = (KafkaConsumer<String, String>) consumerFactory.createConsumer()) {
            consumer.subscribe(Collections.singletonList(TOPIC));

            consumer.poll(Duration.ZERO);
            consumer.seekToBeginning(consumer.assignment());

            ConsumerRecords<String, String> records = consumer.poll(POLL_TIMEOUT);

            for (ConsumerRecord<String, String> record : records) {
                try {
                    processRecord(record, allMessages);
                } catch (Exception e) {
                    throw new SuppressedStackTraceException(e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new SuppressedStackTraceException(e.getMessage());
        }
        return allMessages;
    }

    private void processRecord(ConsumerRecord<String, String> record, List<Map<String, Message>> allMessages) {
        try {
            String key = record.key();
            String value = record.value();
            Message message = gson.fromJson(value, Message.class);

            Map<String, Message> messageMap = new HashMap<>();
            messageMap.put(key, message);
            allMessages.add(messageMap);
        }
       catch (Exception e) {
           throw new SuppressedStackTraceException(e.getMessage());
       }
    }

    public List<Map<String, Message>> consumeExactUser(String key) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("User key cannot be null or empty");
        }

        try {
            List<Map<String, Message>> allMessages = consumeAllMessages();
            return allMessages.stream()
                    .filter(map -> map.containsKey(key))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to consume messages for user: " + key, e);
        }
    }
}