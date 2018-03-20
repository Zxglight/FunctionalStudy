package com.xg.drools1;

import com.xg.drools.Account;
import com.xg.drools.AccountingPeriod;
import com.xg.drools.CashFlow;
import com.xg.util.KnowledgeSessionHelper;
import com.xg.util.OutPutDisplay;
import org.junit.After;
import org.junit.Before;
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

    @Before
    public void before(){
        System.out.println("------------------before ---------------------");
    }

    @After
    public void after(){
        System.out.println("--------------after------------------------");
    }

    @Test
    public void testFistOne() {
        sessionStateFull = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer,"ksession-rules2");
        OutPutDisplay outPutDisplay = new OutPutDisplay();
        sessionStateFull.setGlobal("showResult",outPutDisplay);
        Account account = new Account();
        sessionStateFull.insert(account);
        AccountingPeriod accountingPeriod = new AccountingPeriod();
        sessionStateFull.insert(accountingPeriod);
        sessionStateFull.fireAllRules();
    }

}
