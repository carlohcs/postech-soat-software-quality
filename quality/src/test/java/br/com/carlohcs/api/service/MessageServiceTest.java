package br.com.carlohcs.api.service;

import br.com.carlohcs.api.exception.MessageNotFoundException;
import br.com.carlohcs.api.model.Message;
import br.com.carlohcs.api.repository.MessageRepository;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        messageService = new MessageServiceImpl(messageRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        mock.close();
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test to verify if the message is registered")
    void registerMessage() {
//        Arrange
        Message message = buildMessage();
        when(messageRepository.save(any(Message.class))).thenReturn(message)
//                the method will receive any Message object and return the same object
                .thenAnswer(invocation -> invocation.getArgument(0));

//        Act
        Message registeredMessage = messageService.registerMessage(message);

//        Assert
        assertThat(registeredMessage)
                .isInstanceOf(Message.class)
                .isNotNull();
        assertThat(registeredMessage.getId()).isNotNull();
        assertThat(registeredMessage.getContent()).isEqualTo(message.getContent());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify if the exception is thrown when the message is null")
    void findMessage() {
//        Arrange
        var id = UUID.randomUUID();
        var message = buildMessage();
        message.setId(id);
        when(messageRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(message));

//        Act
        var foundMessage = messageService.findMessage(id);

//        Assert
        assertThat(foundMessage).isInstanceOf(Message.class).isNotNull();

        assertThat(foundMessage.getId()).isEqualTo(id);
        assertThat(foundMessage.getContent()).isEqualTo(message.getContent());
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Test to verify if the exception is thrown when the message is not found")
    void findMessageException() {
//        Arrange
        var id = UUID.randomUUID();
        when(messageRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> messageService.findMessage(id)).isInstanceOf(MessageNotFoundException.class)
                .hasMessage("Message not found");
        verify(messageRepository, times(1)).findById(id);
    }

    @Test
    void findAllMessages() {
//        Arrange
        Message message1 = buildMessage();
        Message message2 = buildMessage();
        var messages = Arrays.asList(message1, message2);
        when(messageRepository.findAll()).thenReturn(messages);

//        Act
        var receivedMessages = messageService.findAllMessages();

//        Assert
        assertThat(receivedMessages).isNotNull().isNotEmpty().hasSize(2).containsExactlyInAnyOrder(message1, message2);
        verify(messageRepository, times(1)).findAll();
    }

    @Test
    void updateMessage() {
//        Arrange
        var id = UUID.fromString("8c9e72d1-cef1-4e35-b476-8f3f92fd33b2");

        var oldMessage = buildMessage();
        oldMessage.setId(id);

        var newMessage = Message.builder()
                .id(oldMessage.getId())
                .content(oldMessage.getContent())
                .build();
        newMessage.setContent("Hello, World! Updated");

        when(messageRepository.findById(id)).thenReturn(java.util.Optional.of(oldMessage));
        when(messageRepository.save(newMessage)).thenAnswer(invocation -> invocation.getArgument(0));

//        Act
        var updatedMessage = messageService.updateMessage(id, newMessage);

//        Assert
        assertThat(updatedMessage).isNotNull().isInstanceOf(Message.class);
        assertThat(updatedMessage.getId()).isEqualTo(id);
        assertThat(updatedMessage.getContent()).isEqualTo(newMessage.getContent());
        verify(messageRepository, times(1)).findById(id);
        verify(messageRepository, times(1)).save(newMessage);
    }

    @Test
    void deleteMessage() {
//        Arrange
        var id = UUID.randomUUID();
        var message = buildMessage();
        message.setId(id);
        when(messageRepository.findById(id)).thenReturn(java.util.Optional.of(message));

//        Act
        var messageDeleted = messageService.deleteMessage(id);

//        Assert
        assertThat(messageDeleted).isTrue();
        verify(messageRepository, times(1)).delete(message);
    }

    private Message buildMessage() {
        return Message.builder()
                .id(UUID.randomUUID())
                .content("Hello, World!")
                .build();
    }
}
