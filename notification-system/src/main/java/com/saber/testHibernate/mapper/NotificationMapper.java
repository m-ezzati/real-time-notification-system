package com.saber.testHibernate.mapper;

import com.saber.testHibernate.kafka.event.NotificationEvent;
import com.saber.testHibernate.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "rule", ignore = true)
    @Mapping(source = "notificationType", target = "type")
    @Mapping(source = "notificationStatus", target = "status")
    Notification toEntity(NotificationEvent event);
}
