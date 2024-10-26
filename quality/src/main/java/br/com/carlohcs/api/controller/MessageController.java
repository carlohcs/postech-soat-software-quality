package br.com.carlohcs.api.controller;

import br.com.carlohcs.api.model.Message;
import br.com.carlohcs.api.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    private final MessageService messageService;

//    How to test:
//    curl -X POST http://localhost:8080/messages -d "{\"content\": \"Olaaaa\", \"username\": \"carlohcs\"}" -H "Content-Type: application/json"
//{"id":"255bfd96-ba63-44c1-8e75-0422b4741c50","username":"carlohcs","content":"Olaaaa","createdAt":"2024-10-25 22:47:46.67056","updatedAt":"2024-10-25 22:47:46.67059","likes":0}%
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Message> registerMessage(@RequestBody Message message) {
        var registerMessage = messageService.registerMessage(message);

        return new ResponseEntity<>(registerMessage, HttpStatus.CREATED);
    }
}
