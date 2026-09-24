ALTER TABLE rules
    ADD CONSTRAINT fk_rules_user
        FOREIGN KEY (user_id)
            REFERENCES users(id);

ALTER TABLE notifications
    ADD CONSTRAINT fk_notifications_rule
        FOREIGN KEY (rule_id)
            REFERENCES rules(id);
