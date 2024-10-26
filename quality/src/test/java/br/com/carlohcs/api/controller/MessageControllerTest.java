package br.com.carlohcs.api.controller;

import br.com.carlohcs.api.model.Message;
import br.com.carlohcs.api.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MessageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MessageService messageService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        MessageController messageController = new MessageController(messageService);
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @AfterEach
    void teraDown() throws Exception {
        mock.close();
    }

    @Nested
    class RegisterMessage {

        @Test
        void registerMessage() throws Exception {
//            Arrange
            var message = buildMessage();
            when(messageService.registerMessage(any(Message.class)))
                    .thenAnswer(( invocation -> invocation.getArgument(0)));

//            Act
            mockMvc.perform(post("/messages")
                    .content(asJsonString(message)))
                    .andExpect(status().isCreated());

//            Assert
            verify(messageService, times(1)).registerMessage(any(Message.class));
        }
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Message buildMessage() {
        return Message.builder()
                .id(UUID.randomUUID())
                .content("Hello, World!")
                .build();
    }
}
