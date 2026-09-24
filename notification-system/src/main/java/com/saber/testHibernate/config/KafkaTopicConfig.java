package com.saber.testHibernate.config;

import com.saber.testHibernate.constants.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic createPriceTopic(){
        return TopicBuilder.name(KafkaTopics.PRICE_TOPIC).build();
    }
    @Bean
    public NewTopic createRuleTopic(){
        return TopicBuilder.name(KafkaTopics.RULE_TOPIC).build();
    }
    @Bean
    public NewTopic createNotificationTopic(){
        return TopicBuilder.name(KafkaTopics.NOTIFICATION_TOPIC).build();
    }
    @Bean
    public NewTopic createPriceRuleOutputTopic(){
        return TopicBuilder.name(KafkaTopics.PRICE_RULE_OUTPUT_TOPIC).build();
    }

}
