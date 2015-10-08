package drools.controller;

import org.drools.KnowledgeBase;

public class Singleton {
    private static Singleton _instance;
    public KnowledgeBase userKnowledgeBase;
	public KnowledgeBase dishKnowledgeBase;

    private Singleton() { 
    	initKnowledgeBases();
    }

    public static synchronized Singleton getInstance() {
            if (null == _instance) {
                    _instance = new Singleton();
            }
            return _instance;
    }
    
    
	private void initKnowledgeBases(){
		RuleRunner ruleRunner = new RuleRunner();
		
		String[] basicRules = { "rules/basic/step1.drl",
				"rules/basic/step2.drl", "rules/basic/step3.drl",
				"rules/basic/step4.drl", "rules/basic/step5.drl",
				"rules/basic/step6.drl" };
		userKnowledgeBase = ruleRunner.createKnowledgeBase(basicRules);
		
		String[] dishRules = { "rules/exclude/step7_pregnancy.drl",
				"rules/exclude/step7_hyperuricemia_excludes.drl",
				"rules/exclude/step7_celiac_disease.drl",
				"rules/exclude/step7_anemia.drl",
				"rules/preferences/step8_bread.drl",
				"rules/preferences/step8_eggs.drl",
				"rules/preferences/step8_feta.drl",
				"rules/preferences/step8_kaseri.drl",
				"rules/preferences/step8_lactose.drl",
				"rules/preferences/step8_lamp.drl",
				"rules/preferences/step8_milk.drl",
				"rules/preferences/step8_pork.drl",
				"rules/preferences/step8_seeds.drl",
				"rules/preferences/step8_shells.drl",
				"rules/preferences/step8_sogia.drl",
				"rules/preferences/step8_veal.drl",
				"rules/preferences/step8_wheat.drl" };		
		dishKnowledgeBase = ruleRunner.createKnowledgeBase(basicRules);
	}
}
