package com.saber.testHibernate.constants;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public final class KafkaTopics {
    public static final String PRICE_TOPIC = "prices";
    public static final String RULE_TOPIC = "rules";
    public static final String NOTIFICATION_TOPIC = "notifications";
    public static final String PRICE_RULE_OUTPUT_TOPIC = "price-rule-output";
    public static final String TEMP_PRICE_AGG_TOPIC  = "temp-price-agg-topic";

    private KafkaTopics(){}

}
