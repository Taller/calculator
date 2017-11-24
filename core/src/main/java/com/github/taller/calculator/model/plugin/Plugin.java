package com.github.taller.calculator.model.plugin;

import com.github.taller.calculator.exports.Operation;
import com.github.taller.calculator.exports.OperationsConfiguration;
import com.github.taller.calculator.loader.PluginUrlLoader;
import com.github.taller.calculator.log.Print;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Plugin {

    private URL url;

    private PluginUrlLoader pluginUrlLoader;

    private List<Action> actionList;

    public Plugin(URL url) throws Exception {
        this.url = url;
        this.actionList = new ArrayList<>();

        pluginUrlLoader = new PluginUrlLoader(new URL[]{url});
        Class configurationClass = pluginUrlLoader.findClass("com.github.taller.calculator.Configuration");
        if (configurationClass == null) {
            Print.msg("Class not found in package " + url.getFile());
            return;
        }

        OperationsConfiguration annotation = (OperationsConfiguration) configurationClass.getAnnotation(OperationsConfiguration.class);
        if (annotation == null) {
            Print.msg("Configuration wasn't defined correctly. Use OperationsConfiguration annotation.");
            return;
        }

        Class<Operation>[] operationList = (Class<Operation>[]) annotation.provides();
        for (Class<Operation> operation : operationList) {
            actionList.add(new Action(operation, url));
            Print.msg("action added " + operation);
        }

        if (actionList.isEmpty()) {
            Print.msg("OperationsConfiguration is empty.");
        }
    }

    public List<Action> getActionList() {
        return new ArrayList<>(actionList);
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Plugin{" + url + "}";
    }
}
