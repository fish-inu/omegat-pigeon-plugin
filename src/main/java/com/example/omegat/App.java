/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.example.omegat;

import java.util.Map;
import java.util.TreeMap;

import org.omegat.core.CoreEvents;
import org.omegat.core.events.IApplicationEventListener;
import org.omegat.core.machinetranslators.BaseTranslate;
import org.omegat.core.machinetranslators.MachineTranslators;
import org.omegat.util.JsonParser;
import org.omegat.util.Language;
import org.omegat.util.WikiGet;

public class App extends BaseTranslate {

    @Override
    public boolean isConfigurable() {
        return false;
    }

    // Plugin setup
    public static void loadPlugins() {
        CoreEvents.registerApplicationEventListener(new IApplicationEventListener() {
            @Override
            public void onApplicationStartup() {
                MachineTranslators.add(new App());
            }

            @Override
            public void onApplicationShutdown() {
                /* empty */
            }
        });
    }

    @Override
    protected String getPreferenceName() {
        return "allow_pigeon";
    }
    
    @Override
    public String getName() {
        return "pigeon translate";
    }

    @Override
    @SuppressWarnings("unchecked")
    protected String translate(Language sLang, Language tLang, String text) throws Exception {
        Map<String, String> params = new TreeMap<String, String>();
        Map<String, String> headers = new TreeMap<String, String>();
        params.put("source", sLang.getLanguageCode());
        params.put("target", tLang.getLanguageCode());
        params.put("text", text);
        String result = WikiGet.get("http://localhost:8887/translate", params, headers);
        Map<String, String> translation = (Map<String, String>) JsonParser.parse(result);
        String re = translation.get("translation");
        return re;
    }

}
