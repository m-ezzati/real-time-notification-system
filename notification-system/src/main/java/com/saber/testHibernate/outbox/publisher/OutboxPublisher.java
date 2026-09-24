package com.saber.testHibernate.outbox.publisher;

import com.saber.testHibernate.entity.OutboxEvent;
import com.saber.testHibernate.entity.enums.OutboxStatus;
import com.saber.testHibernate.exception.EventPublisherNotFoundException;
import com.saber.testHibernate.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author M.Ezati
 * 11/05/2026
 */
@Service
public class OutboxPublisher {

    private final OutboxRepository repository;
    private final Map<String, EventPublisher> publishers;

    public OutboxPublisher(OutboxRepository repository, List<EventPublisher> publisherList) {
        this.repository = repository;
        this.publishers = publisherList
                .stream()
                .collect(Collectors
                        .toMap(EventPublisher::getEventType, Function.identity()));
    }

    @Scheduled(fixedRateString = "#{@notificationSysProperties.publishEventSchedular}")
    @Transactional
    public void publishEvents() {
        List<OutboxEvent> events = repository.findTopHundredByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);
        for (OutboxEvent event : events) {
            try {
                EventPublisher publisher = publishers.get(event.getEventType());
                if (publisher == null) {
                    throw new EventPublisherNotFoundException(event.getEventType());
                }
                publisher.publish(event.getPayload());
                event.setStatus(OutboxStatus.PUBLISHED);
            } catch (Exception e) {
                event.setStatus(OutboxStatus.FAILED);
            }
        }
    }
}

