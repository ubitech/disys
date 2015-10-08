package drools.controller;

import org.drools.KnowledgeBase;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import drools.controller.Singleton;

public class Global extends GlobalSettings {

	@Override
    public void onStart(Application app) {
        Logger.info("Application has started");
        Singleton s = Singleton.getInstance();
        Logger.info("loaded the drools rules!");
    }
}