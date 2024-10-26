package br.com.carlohcs.api.repository;

import br.com.carlohcs.api.model.Message;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// INTEGRATION TESTS
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class MessageRepositoryIT {

    @Autowired
    private MessageRepository messageRepository;

//    When we add /test/resources/data.sql, it runs the script before the tests
//    So here, we will be having 3 messages already
    @Test
    void createTable() {
        var totalMessages = messageRepository.count();
        assertThat(totalMessages).isNotNegative();
    }

    @Nested
    class Register {

        @Test
        void registerAMessage() {
    //        Arrange
            var id = UUID.randomUUID();
            var message = buildMessage();
            message.setId(id);

    //        Act
            var savedMessage = messageRepository.save(message);

    //        Assert
            assertThat(savedMessage).isInstanceOf(Message.class).isNotNull();
            assertThat(savedMessage.getId()).isEqualTo(id);
            assertThat(savedMessage.getContent()).isEqualTo(message.getContent());
            assertThat(savedMessage.getUsername()).isEqualTo(message.getUsername());
        }

    }

    @Nested
    class Find {
        @Test
        void findAMessage() {
            // Arrange
//        Legacy way
//        var id = UUID.randomUUID();
//        var message = buildMessage();
//        message.setId(id);
//        saveMessage(message);
            var id = UUID.fromString("8c9e72d1-cef1-4e35-b476-8f3f92fd33b2");

            // Act
            var foundMessageOptional = messageRepository.findById(id);

            // Assert
            assertThat(foundMessageOptional).isPresent();

            foundMessageOptional.ifPresent((foundMessage) -> {
                assertThat(foundMessage.getId()).isEqualTo(id);
            });
        }

        @Test
        void findAllMessages() {
            // Act
            var messages = messageRepository.findAll();

            // Assert
            assertThat(messages).hasSizeGreaterThanOrEqualTo(3);
        }
    }

    @Nested
    class Update {
        @Test
        void updateAMessage() {
            // Arrange
            var id = UUID.fromString("8c9e72d1-cef1-4e35-b476-8f3f92fd33b2");
            var message = messageRepository.findById(id);

            // Act
            message.ifPresent((foundMessage) -> {
                foundMessage.setContent("Hello, World! Updated");
                messageRepository.save(foundMessage);
            });

            // Assert
            var updatedMessage = messageRepository.findById(id);
            assertThat(updatedMessage).isPresent();
            updatedMessage.ifPresent((foundMessage) -> {
                assertThat(foundMessage.getContent()).isEqualTo("Hello, World! Updated");
            });
        }
    }

    @Nested
    class Delete {
        @Test
        void deleteAMessage() {
            // Arrange
            var id = UUID.fromString("5c6088ae-90de-4d3d-95d9-1225aefbdb62");

            // Act
            messageRepository.deleteById(id);

            // Assert
            assertThat(messageRepository.findById(id)).isEmpty();
        }
    }

    private Message buildMessage() {
        return Message.builder()
                .id(UUID.randomUUID())
                .username("carlohcs")
                .content("Hello, World!")
                .build();
    }

    private Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
}
