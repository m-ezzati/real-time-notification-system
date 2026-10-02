package service;

import com.maryam.notificationSystem.entity.Notification;
import com.maryam.notificationSystem.kafka.event.NotificationEvent;
import com.maryam.notificationSystem.mapper.NotificationMapper;
import com.maryam.notificationSystem.repository.NotificationRepository;
import com.maryam.notificationSystem.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private NotificationEvent notificationEvent;
    private Notification notification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        notificationEvent = new NotificationEvent();
        notification = new Notification();
    }

    @Test
    void createNotification_ShouldReturnSavedNotification() {
        when(notificationMapper.toEntity(notificationEvent)).thenReturn(notification);
        when(notificationRepository.save(notification)).thenReturn(notification);

        Notification result = notificationService.createNotification(notificationEvent);

        assertThat(result).isEqualTo(notification);
        verify(notificationMapper).toEntity(notificationEvent);
        verify(notificationRepository).save(notification);
    }
}