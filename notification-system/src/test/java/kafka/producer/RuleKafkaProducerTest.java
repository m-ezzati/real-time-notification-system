package kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maryam.notificationSystem.kafka.event.RuleEvent;
import com.maryam.notificationSystem.kafka.producer.RuleKafkaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RuleKafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    private RuleKafkaProducer producer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        producer = new RuleKafkaProducer(kafkaTemplate, objectMapper);
    }

    @Test
    void sendRuleCreatedEvent_shouldSendJsonToKafka() throws Exception {
        RuleEvent event = new RuleEvent();
        event.setSymbol("AAPL");

        String json = "{\"symbol\":\"AAPL\",\"createdAt\":\"2023-01-01T10:00:00Z\"}";

        when(objectMapper.writeValueAsString(event)).thenReturn(json);

        producer.sendRuleCreatedEvent(event);

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> jsonCaptor = ArgumentCaptor.forClass(String.class);

        verify(kafkaTemplate, times(1)).send(topicCaptor.capture(), keyCaptor.capture(), jsonCaptor.capture());

        assertEquals("rules", topicCaptor.getValue());
        assertEquals("AAPL", keyCaptor.getValue());
        assertEquals(json, jsonCaptor.getValue());
    }

    @Test
    void sendRuleCreatedEvent_shouldLogErrorOnSerializationFailure() throws Exception {
        RuleEvent event = new RuleEvent();
        event.setSymbol("GOOG");

        JsonProcessingException exception = new JsonProcessingException("test error") {};

        when(objectMapper.writeValueAsString(event)).thenThrow(exception);

        producer.sendRuleCreatedEvent(event);

        verify(kafkaTemplate, never()).send(anyString(), anyString(), anyString());
    }
}