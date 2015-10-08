package drools.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message.Level;
import org.kie.api.io.KieResources;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import drools.controller.Global;

public class RuleRunner {
	public RuleRunner() {
	}

	public KnowledgeBase createKnowledgeBase(String[] rules){
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		for (String ruleFile : rules) {
			kbuilder.add(ResourceFactory.newClassPathResource(ruleFile),
					ResourceType.DRL);			
		}
		KnowledgeBase kbase = kbuilder.newKnowledgeBase();
		return kbase;
	}
	
	public void runUserRules(Object[] facts) {
		Singleton s = Singleton.getInstance();
		StatelessKnowledgeSession ksession = s.userKnowledgeBase
				.newStatelessKnowledgeSession();

		List<Object> ruleFacts = new ArrayList<Object>();
		for (Object o : facts)
			ruleFacts.add(o);

		ksession.execute(ruleFacts);		
	}
	
	public void runDishRules(Object[] facts) {
		Singleton s = Singleton.getInstance();
		StatelessKnowledgeSession ksession = s.dishKnowledgeBase
				.newStatelessKnowledgeSession();

		List<Object> ruleFacts = new ArrayList<Object>();
		for (Object o : facts)
			ruleFacts.add(o);

		ksession.execute(ruleFacts);		
	}
	
	public void runRules(String[] rules, Object[] facts) {

		for (String ruleFile : rules) {
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
					.newKnowledgeBuilder();
			kbuilder.add(ResourceFactory.newClassPathResource(ruleFile),
					ResourceType.DRL);
			KnowledgeBase kbase = kbuilder.newKnowledgeBase();
			StatelessKnowledgeSession ksession = kbase
					.newStatelessKnowledgeSession();

			List<Object> ruleFacts = new ArrayList<Object>();
			for (Object o : facts)
				ruleFacts.add(o);

			ksession.execute(ruleFacts);
		}
	}
}
