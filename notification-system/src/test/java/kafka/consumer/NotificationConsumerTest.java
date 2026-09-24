package kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saber.testHibernate.entity.enums.NotificationStatus;
import com.saber.testHibernate.kafka.consumer.NotificationConsumer;
import com.saber.testHibernate.kafka.event.NotificationEvent;
import com.saber.testHibernate.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NotificationConsumerTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationConsumer consumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consume_shouldProcessValidMessage() throws Exception {
        String message = "{\"symbol\":\"AAPL\"}";

        NotificationEvent event = new NotificationEvent();
        event.setSymbol("AAPL");

        when(objectMapper.readValue(message, NotificationEvent.class)).thenReturn(event);

        consumer.consume(message);

        assertEquals(NotificationStatus.PENDING, event.getNotificationStatus());

        verify(notificationService, times(1)).createNotification(event);
    }

    @Test
    void consume_shouldHandleExceptionAndNotThrow() throws Exception {
        String badMessage = "invalid json";

        when(objectMapper.readValue(badMessage, NotificationEvent.class))
                .thenThrow(new RuntimeException("Parsing failed"));

        assertDoesNotThrow(() -> consumer.consume(badMessage));

        verify(notificationService, never()).createNotification(any());
    }
}