# Real-Time Price Alert System

A side project I built to learn Kafka Streams. Users register alert rules for a symbol (a price threshold, or a percentage change), and the system creates a notification when incoming prices match a rule.

It's two small Spring Boot apps:

- **price-simulator** – generates fake price events and publishes them to Kafka. Also exposes the list of valid symbols.
- **notification-system** – REST API for users and rules, plus the Kafka Streams part that does the matching.

## How it works

1. A user creates a rule through the REST API. The rule is saved in Oracle together with an outbox record, and a scheduled job publishes it to the rules topic (outbox pattern, so the DB and Kafka don't go out of sync).
2. Prices arrive on the price topic. Kafka Streams aggregates them per symbol in a time window (first and last price).
3. The aggregated price is joined with the rules KTable. If the threshold or percentage condition is met, a notification event is produced.
4. A consumer reads it and stores the notification in Oracle. A rule only triggers one notification.

Most of my time went into the boring-but-tricky parts: not sending the same alert twice, and not losing one if the delivery fails.

## Stack

- Java, Spring Boot
- Kafka + Kafka Streams
- Oracle
- JPA /
- Hibernate
- WebClient

To run it you need Kafka on localhost:9092 and Oracle. SQL scripts are in `src/main/resources/scripts`.



