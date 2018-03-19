package com.xg.util;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

/**
 * @author xg.zhao   2018 03 18 16:04
 */
public class KnowledgeSessionHelper {
    public static KieContainer createRuleBase(){
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        return kieContainer;
    }

    public static StatelessKieSession getStatelessKnowledgeSession(KieContainer kieContainer, String sessionName){
        StatelessKieSession kieSession = kieContainer.newStatelessKieSession(sessionName);
        return kieSession;
    }

    public static KieSession getStatefulKnowlegdeSession(KieContainer kieContainer, String sessionName){
        KieSession kieSession = kieContainer.newKieSession(sessionName);
        return kieSession;
    }
}
