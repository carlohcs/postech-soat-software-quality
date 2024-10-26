package br.com.carlohcs.api.service;

import br.com.carlohcs.api.exception.MessageNotFoundException;
import br.com.carlohcs.api.model.Message;
import br.com.carlohcs.api.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public Message registerMessage(Message message) {
        message.setId(UUID.randomUUID());
        return messageRepository.save(message);
    }

    @Override
    public Message findMessage(UUID id) {
        return messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException("Message not found"));
    }

    @Override
    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message updateMessage(UUID id, Message updatedMessage) {
        var message = findMessage(id);

        if(!message.getId().equals(updatedMessage.getId())) {
            throw new MessageNotFoundException("Message not found");
        }

        message.setContent(updatedMessage.getContent());

        return messageRepository.save(message);
    }

    @Override
    public boolean deleteMessage(UUID id) throws MessageNotFoundException {
        var message = findMessage(UUID.fromString(String.valueOf(id)));

        if(!message.getId().equals(id)) {
            throw new MessageNotFoundException("Message not found");
        }

        messageRepository.delete(message);
        return true;
    }
}
