# Real-Time Price Alert System

A side project I built to learn Kafka Streams. It tracks price changes and sends a notification when a price hits the condition a user set (like "tell me when this drops below 50").

Price events come in through Kafka. A Kafka Streams app checks each one against the saved alert rules and fires a notification when there's a match. Rules and users live in Oracle, accessed with JPA/Hibernate. Notifications go out over HTTP using Spring WebClient.

Most of my time went into the boring-but-tricky parts: not sending the same alert twice, and not losing one if the delivery fails.

## Stack

- Java, Spring Boot
- Kafka + Kafka Streams
- Oracle
- JPA / Hibernate
- WebClient
