package com.eak74.service;

import com.eak74.config.MultiPartitionProducerConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class MultiPartitionProducerService {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    public boolean sendMessage(String message){
        int intPartition = new Random().nextInt(3);
        String strKey = Integer.toString(intPartition);
        var future = kafkaTemplate.send(
                MultiPartitionProducerConfig.TOPIC_NAME,
                intPartition,
                strKey,
                message);
        try {
            var result = future.get();
            log.info("Message \"{}\" was successfully sent to (topic: {}, key: {}, partition: {}, offset: {}",
                    message,
                    result.getProducerRecord().topic(),
                    result.getProducerRecord().key(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());
            return true;
        } catch (InterruptedException | ExecutionException e) {
            log.error("Failed to send message \"{}\" to (topic: {})",
                    message,
                    MultiPartitionProducerConfig.TOPIC_NAME);
            return false;
        }
    }

}
