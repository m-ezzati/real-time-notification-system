package com.saber.testHibernate.service.manager;

import com.saber.testHibernate.entity.OutboxEvent;
import com.saber.testHibernate.entity.Rule;
import com.saber.testHibernate.entity.User;
import com.saber.testHibernate.kafka.event.RuleEvent;
import com.saber.testHibernate.mapper.RuleEventMapper;
import com.saber.testHibernate.outbox.factory.OutboxEventFactory;
import com.saber.testHibernate.repository.RuleRepository;
import com.saber.testHibernate.service.OutboxService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author M.Ezati
 * 13/05/2026
 */
@Service
public class RuleTransactionalService {
    private final RuleRepository ruleRepository;
    private final OutboxService outboxService;
    private final OutboxEventFactory outboxEventFactory;
    private final RuleEventMapper ruleEventMapper;

    public RuleTransactionalService(RuleRepository ruleRepository, OutboxService outboxService, OutboxEventFactory outboxEventFactory, RuleEventMapper ruleEventMapper) {
        this.ruleRepository = ruleRepository;
        this.outboxService = outboxService;
        this.outboxEventFactory = outboxEventFactory;
        this.ruleEventMapper = ruleEventMapper;
    }

    @Transactional
    public Rule saveRuleAndEvent(Rule rule, User user) {
        Rule savedRule = ruleRepository.save(rule);
        publishRuleCreatedEvent(user, savedRule);

        return savedRule;
    }

    private void publishRuleCreatedEvent(User user, Rule rule) {
        RuleEvent ruleEvent = buildRuleEvent(user, rule);
        OutboxEvent outboxEvent = buildOutboxEvent(ruleEvent);
        outboxService.saveEvent(outboxEvent);
    }

    private RuleEvent buildRuleEvent(User user, Rule rule){
        RuleEvent ruleEvent = ruleEventMapper.toRuleEvent(rule);
        ruleEvent.setEmail(user.getEmail());
        ruleEvent.setPhoneNumber(user.getPhoneNumber());
        return ruleEvent;
    }

    private OutboxEvent buildOutboxEvent(RuleEvent ruleEvent){
        return  outboxEventFactory.create(
                "Rule",
                ruleEvent.getRuleId(),
                "RULE_CREATED",
                ruleEvent
        );
    }
}
