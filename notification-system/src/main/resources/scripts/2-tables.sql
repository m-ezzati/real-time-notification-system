CREATE TABLE users (
                       id NUMBER(19,0) NOT NULL,
                       user_name VARCHAR2(255 CHAR) NOT NULL,
                       email VARCHAR2(255 CHAR) NOT NULL,
                       phone_number VARCHAR2(255 CHAR) NOT NULL,

                       created_at DATE NOT NULL,
                       updated_at DATE NOT NULL,
                       version NUMBER(19,0),

                       CONSTRAINT pk_users PRIMARY KEY (id),
                       CONSTRAINT uq_users_email UNIQUE (email),
                       CONSTRAINT uq_users_phone UNIQUE (phone_number)
);

CREATE TABLE notifications (

                               id NUMBER(19,0) NOT NULL,
                               rule_id NUMBER(19,0) NOT NULL,

                               message VARCHAR2(1500 CHAR) NOT NULL,
                               email VARCHAR2(255 CHAR),
                               phone_number VARCHAR2(255 CHAR),

                               status VARCHAR2(50 CHAR) NOT NULL,
                               symbol VARCHAR2(255 CHAR) NOT NULL,
                               type VARCHAR2(50 CHAR) NOT NULL,

                               created_at TIMESTAMP NOT NULL,
                               updated_at TIMESTAMP NOT NULL,
                               version NUMBER(19,0),

                               CONSTRAINT pk_notifications PRIMARY KEY (id),

                               CONSTRAINT fk_notifications_rule
                                   FOREIGN KEY (rule_id)
                                       REFERENCES rules(id),

                               CONSTRAINT ck_notifications_status
                                   CHECK (status IN ('PENDING','STORED','FAILED','SENT','DELIVERED')),

                               CONSTRAINT ck_notifications_type
                                   CHECK (type IN ('SMS','EMAIL'))
);
CREATE TABLE rules (
                       id NUMBER(19,0) NOT NULL,

                       percentage_value FLOAT(53),
                       threshold_value FLOAT(53),
                       window_minutes NUMBER(10,0),

                       symbol VARCHAR2(255 CHAR) NOT NULL,
                       rule_type VARCHAR2(255 CHAR) NOT NULL,
                       notification_type VARCHAR2(255 CHAR) NOT NULL,

                       expiration_time DATE,
                       user_id NUMBER(19,0) NOT NULL,

                       created_at DATE NOT NULL,
                       updated_at DATE NOT NULL,
                       version NUMBER(19,0),

                       CONSTRAINT pk_rules PRIMARY KEY (id),

                       CONSTRAINT ck_rules_rule_type
                           CHECK (rule_type IN ('PRICE_THRESHOLD','PERCENT_CHANGE')),

                       CONSTRAINT ck_rules_notification_type
                           CHECK (notification_type IN ('SMS','EMAIL'))
);

CREATE TABLE OUTBOX_EVENT (
                              ID NUMBER(19,0) NOT NULL,
                              AGGREGATE_TYPE VARCHAR2(100),
                              AGGREGATE_ID NUMBER,
                              EVENT_TYPE VARCHAR2(100),
                              PAYLOAD CLOB,
                              STATUS VARCHAR2(20) DEFAULT 'PENDING',
                              CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);