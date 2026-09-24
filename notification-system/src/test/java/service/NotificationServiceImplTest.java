package service;

import com.saber.testHibernate.entity.Notification;
import com.saber.testHibernate.kafka.event.NotificationEvent;
import com.saber.testHibernate.mapper.NotificationMapper;
import com.saber.testHibernate.repository.NotificationRepository;
import com.saber.testHibernate.service.impl.NotificationServiceImpl;
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