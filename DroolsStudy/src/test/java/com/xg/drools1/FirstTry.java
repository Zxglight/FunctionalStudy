package com.xg.drools1;

import com.xg.drools.Account;
import com.xg.drools.AccountingPeriod;
import com.xg.drools.CashFlow;
import com.xg.util.KnowledgeSessionHelper;
import com.xg.util.OutPutDisplay;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

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
    public void before() {
        System.out.println("------------------before ---------------------");
    }

    @After
    public void after() {
        System.out.println("--------------after------------------------");
    }

    @Test
    public void testFistOne() {
        sessionStateFull = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "ksession-rules2");
        OutPutDisplay outPutDisplay = new OutPutDisplay();
        sessionStateFull.setGlobal("showResult", outPutDisplay);
        Account account = new Account();
        sessionStateFull.insert(account);
        AccountingPeriod accountingPeriod = new AccountingPeriod();
        sessionStateFull.insert(accountingPeriod);
        sessionStateFull.fireAllRules();
    }

    @Test
    public void testFistTwo() {
        sessionStateFull = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "ksession-rules2");
        OutPutDisplay outPutDisplay = new OutPutDisplay();
        sessionStateFull.setGlobal("showResult", outPutDisplay);
        CashFlow cashFlow = new CashFlow();
        //        cashFlow.setType(CashFlow.DEBIT);
        cashFlow.setType(CashFlow.CREDIT);
        cashFlow.setDate(new Date());
        sessionStateFull.insert(cashFlow);
        AccountingPeriod accountingPeriod = new AccountingPeriod();
        sessionStateFull.insert(accountingPeriod);
        sessionStateFull.fireAllRules();
    }

    @Test
    public void testFistTree() {
        sessionStateFull = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, "ksession-rules2");
        OutPutDisplay outPutDisplay = new OutPutDisplay();
        sessionStateFull.setGlobal("showResult", outPutDisplay);
        CashFlow cashFlow = new CashFlow();
        //        cashFlow.setType(CashFlow.DEBIT);
        cashFlow.setType(CashFlow.CREDIT);
        cashFlow.setDate(new Date());
        cashFlow.setAmount(20.4);
        Account account = new Account();
        sessionStateFull.insert(cashFlow);
        account.setBalance(10.2);
        sessionStateFull.insert(account);
        sessionStateFull.fireAllRules();
    }

}
