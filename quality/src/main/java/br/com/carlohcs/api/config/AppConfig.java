package br.com.carlohcs.api.config;

import br.com.carlohcs.api.repository.MessageRepository;
import br.com.carlohcs.api.service.MessageService;
import br.com.carlohcs.api.service.MessageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final MessageRepository messageRepository;

    public AppConfig(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Bean
    public MessageService messageService() {
        return new MessageServiceImpl(messageRepository);
    }
}