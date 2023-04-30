package com.eak74.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UniversalConsumerService {

    private static final String SINGLE_PARTITION_TOPIC_NAME = "one.partition.topic";
    private static final String MULTI_PARTITION_TOPIC_NAME = "many.partitions.topic";

    @KafkaListener(topics = {SINGLE_PARTITION_TOPIC_NAME,MULTI_PARTITION_TOPIC_NAME},
                   groupId = "consumer.group.1")
    public void consume(String message){
        log.info("Message \"{}\" successfully received",message);
    }

}
