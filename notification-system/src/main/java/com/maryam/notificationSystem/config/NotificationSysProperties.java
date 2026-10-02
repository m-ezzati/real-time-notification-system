package com.maryam.notificationSystem.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@ConfigurationProperties(prefix = "notification-sys")
public class NotificationSysProperties {

    @Min(1)
    private int windowMinute;
    @Min(1)
    private int publishEventSchedular;

    private Kafka kafka = new Kafka();

    public int getWindowMinute() {
        return windowMinute;
    }

    public void setWindowMinute(int windowMinute) {
        this.windowMinute = windowMinute;
    }

    public int getPublishEventSchedular() {
        return publishEventSchedular;
    }

    public void setPublishEventSchedular(int publishEventSchedular) {
        this.publishEventSchedular = publishEventSchedular;
    }

    public Kafka getKafka() {
        return kafka;
    }

    public void setKafka(Kafka kafka) {
        this.kafka = kafka;
    }

    public static class Kafka {
        private Streams streams = new Streams();
        private Consumer consumer = new Consumer();

        public Streams getStreams() {
            return streams;
        }

        public void setStreams(Streams streams) {
            this.streams = streams;
        }

        public Consumer getConsumer() {
            return consumer;
        }

        public void setConsumer(Consumer consumer) {
            this.consumer = consumer;
        }
    }

    public static class Streams {
        @NotBlank
        private String priceStoreName;

        public String getPriceStoreName() {
            return priceStoreName;
        }

        public void setPriceStoreName(String priceStoreName) {
            this.priceStoreName = priceStoreName;
        }
    }

    public static class Consumer {
        @NotBlank
        private String notificationGroupId;

        public String getNotificationGroupId() {
            return notificationGroupId;
        }

        public void setNotificationGroupId(String notificationGroupId) {
            this.notificationGroupId = notificationGroupId;
        }
    }
}
