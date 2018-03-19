package com.xg.drools;

import com.xg.util.KnowledgeSessionHelper;
import com.xg.util.OutPutDisplay;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 * @author xg.zhao   2018 03 18 16:14
 */
@SuppressWarnings("restriction")
public class FirstTry {

    static KieContainer kieContainer;
    StatelessKieSession sessionStateLess = null;
    KieSession sessionStateFull = null;

    @BeforeClass
    public static void beforClass() {
        kieContainer = KnowledgeSessionHelper.createRuleBase();
    }

    @Test
    public void testFistOne() {
        sessionStateFull = KnowledgeSessionHelper.getStatefulKnowlegdeSession(kieContainer, "ksession-rules");
        Account a = new Account();
        sessionStateFull.insert(a);
        sessionStateFull.fireAllRules();
    }

    @Test
    public void testFistTwo() {
        sessionStateFull = KnowledgeSessionHelper.getStatefulKnowlegdeSession(kieContainer, "ksession-rules");
        OutPutDisplay outPutDisplay = new OutPutDisplay();
        sessionStateFull.setGlobal("showResult", outPutDisplay);
        Account a = new Account();
        sessionStateFull.insert(a);
        sessionStateFull.fireAllRules();
    }

    @Test
    public void testFistThree() {
        sessionStateFull = KnowledgeSessionHelper.getStatefulKnowlegdeSession(kieContainer, "ksession-rules");
        //        添加 回调监听器
        sessionStateFull.addEventListener(new RuleRuntimeEventListener() {
            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println("Object  inserted \n" + event.getObject().toString());
            }

            public void objectUpdated(ObjectUpdatedEvent event) {
                System.out.println("Object updated \n" + "new content is \n" + event.getObject().toString());
            }

            public void objectDeleted(ObjectDeletedEvent event) {
                System.out.println("Object retracted \n" + event.getOldObject().toString());
            }
        });
        OutPutDisplay outPutDisplay = new OutPutDisplay();
        sessionStateFull.setGlobal("showResult", outPutDisplay);
        Account a = new Account();
        a.setAccountNo(1L);
        FactHandle insert = sessionStateFull.insert(a);
        a.setBalance(10.0);
        sessionStateFull.update(insert, a);
        sessionStateFull.delete(insert);
        sessionStateFull.fireAllRules();
        System.out.println("so you saw something");
    }
}
