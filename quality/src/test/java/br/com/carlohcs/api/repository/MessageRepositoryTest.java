package br.com.carlohcs.api.repository;

import br.com.carlohcs.api.model.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

// UNIT TESTS
// https://site.mockito.org/

// Mock MVC
// https://spring.io/guides/gs/testing-web/

// Lint
// https://checkstyle.sourceforge.io/
// Oracle checkstyle

// Google checkstyle
// https://google.github.io/styleguide/javaguide.html

// BDD testing
//https://cucumber.io/

// https://json-schema.org/
// We can use it in order to generate a JSON schema from a JSON object
// So we can use this schema to validate the JSON objects we are sending and receiving
// So we can respect contracts between the client and the server

public class MessageRepositoryTest {

    @Mock
    private MessageRepository messageRepository;

    AutoCloseable openMocks = null;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void registerAMessage() {
//         Arrange
        var message = Message.builder()
                .id(UUID.randomUUID())
                .username("carlohcs")
                .content("Hello, World!")
                .build();

        when(messageRepository.save(message)).thenReturn(message);

//         Act
        var savedMessage = messageRepository.save(message);

//         Assert
        assertThat(savedMessage).isNotNull().isEqualTo(message);

//         Check if the method 'save' was called once
//        verify(messageRepository, times(1)).save(message);

//         Is the same as: verify(messageRepository).save(message);
//         once someone calls the method 'save' with a Message class as argument
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void findAMessage() {
//         Arrange
        var id = UUID.randomUUID();
        var message = buildMessage();
        message.setId(id);

        when(messageRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(message));

//        Act
        var foundMessageOptional = messageRepository.findById(id);

//        Assert
        assertThat(foundMessageOptional).isPresent().containsSame(message);
        foundMessageOptional.ifPresent(foundMessage -> {
            assertThat(foundMessage.getId()).isEqualTo(message.getId());
            assertThat(foundMessage.getContent()).isEqualTo(message.getContent());
        });
        verify(messageRepository, times(1)).findById(any(UUID.class));
    }

//    We don't need to test the update method because the save method is already tested and
//    the update method is just a save method with a different name.
//    @Test
//    void updateAMessage() {
//        fail("Not implemented yet");
//    }

    @Test
    void deleteAMessage() {
//        Arrange
        var id = UUID.randomUUID();
        doNothing().when(messageRepository).deleteById(any(UUID.class));

//        Act
        messageRepository.deleteById(id);

//        Assert
        verify(messageRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void listMessages() {
//        Arrange
        var message1 = buildMessage();
        var message2 = buildMessage();
        var message3 = buildMessage();
        var messageList = Arrays.asList(message1, message2, message3);
        when(messageRepository.findAll()).thenReturn(messageList);

//        Act
        var receivedMessages = messageRepository.findAll();

//        Assert
        assertThat(receivedMessages).isNotNull().isNotEmpty().hasSize(3).containsExactlyInAnyOrder(message1, message2, message3);
        verify(messageRepository, times(1)).findAll();
    }

    private Message buildMessage() {
        return Message.builder()
                .id(UUID.randomUUID())
                .username("carlohcs")
                .content("Hello, World!")
                .build();
    }
}
