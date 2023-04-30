package com.eak74.controller;

import com.eak74.service.SinglePartitionProducerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class SinglePartitionProducerController {

    @Autowired
    SinglePartitionProducerService singleTopicProducerService;

    @GetMapping("test")
    ResponseEntity<String> getMessage(){
            return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("send")
    ResponseEntity<String> sendMessage(@RequestBody String message){
        if(singleTopicProducerService.sendMessage(message)){
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred whent trying to send message.");
        }
    }


}
