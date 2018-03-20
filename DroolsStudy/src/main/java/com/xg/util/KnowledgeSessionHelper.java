package com.xg.util;

import org.kie.api.KieServices;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

/**
 * @author xg.zhao   2018 03 18 16:04
 */
public class KnowledgeSessionHelper {

    public static KieContainer createRuleBase() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        return kieContainer;
    }

    public static StatelessKieSession getStatelessKnowledgeSession(KieContainer kieContainer, String sessionName) {
        StatelessKieSession kieSession = kieContainer.newStatelessKieSession(sessionName);
        return kieSession;
    }

    public static KieSession getStatefulKnowlegdeSession(KieContainer kieContainer, String sessionName) {
        KieSession kieSession = kieContainer.newKieSession(sessionName);
        return kieSession;
    }

    public static KieSession getStatefulKnowledgeSessionWithCallback(KieContainer kieContainer, String sessionName) {
        KieSession kieSession = getStatefulKnowlegdeSession(kieContainer, sessionName);
        kieSession.addEventListener(getRuleRuntimeEventListener());
        kieSession.addEventListener(getAgendaEventListener());
        return kieSession;
    }

    public static RuleRuntimeEventListener getRuleRuntimeEventListener() {
        return new RuleRuntimeEventListener() {
            @Override
            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println("Object  inserted \n" + event.getObject().toString());
            }

            @Override
            public void objectUpdated(ObjectUpdatedEvent event) {
                System.out.println("Object updated \n" + "new content is \n" + event.getObject().toString());
            }

            @Override
            public void objectDeleted(ObjectDeletedEvent event) {
                System.out.println("Object retracted \n" + event.getOldObject().toString());
            }
        };
    }

    public static AgendaEventListener getAgendaEventListener() {
        return new AgendaEventListener() {
            public void matchCreated(MatchCreatedEvent event) {
                System.out.println("The rule " + event.getMatch().getRule().getName() + " can be fired in agenda");
            }

            public void matchCancelled(MatchCancelledEvent event) {
                System.out.println("The rule " + event.getMatch().getRule().getName() + " cannot b in agenda");
            }

            public void beforeMatchFired(BeforeMatchFiredEvent event) {
                System.out.println("The rule " + event.getMatch().getRule().getName() + " will be fired");
            }

            public void afterMatchFired(AfterMatchFiredEvent event) {
                System.out.println("The rule " + event.getMatch().getRule().getName() + " has be fired");
            }

            public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
            }

            public void agendaGroupPushed(AgendaGroupPushedEvent event) {
            }

            public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

            }

            public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

            }

            public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

            }

            public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

            }

        };
    }
}
