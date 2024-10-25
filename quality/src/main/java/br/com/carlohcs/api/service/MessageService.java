package br.com.carlohcs.api.service;

import br.com.carlohcs.api.model.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
        Message registerMessage(Message message);

        Message findMessage(UUID id);

        List<Message> findAllMessages();

        Message updateMessage(UUID id, Message updatedMessage);

        boolean deleteMessage(UUID id);
}
