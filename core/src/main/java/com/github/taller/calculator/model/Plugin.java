package com.github.taller.calculator.model;

import com.github.taller.calculator.exports.Operation;
import com.github.taller.calculator.log.PrintMsg;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Plugin {

    private URL url;

    private URLClassLoader cl;

    private List<Action> actionList;

    public Plugin(URL url) throws Exception {
        this.url = url;

        this.cl = URLClassLoader.newInstance(new URL[]{url});
        PrintMsg.act("" + this.cl);

        Properties p = new Properties();
        URL properies = cl.findResource("operations.properties");
        if (properies == null) {
            properies = cl.getResource("operations.properties");
        }

        if (properies == null) {
            PrintMsg.act("operations.properties wasn't found.");
            return;
        }
        p.load(properies.openStream());

        if (p.stringPropertyNames() == null || p.stringPropertyNames().isEmpty()) {
            PrintMsg.act("operations.properties is empty.");
            return;
        }

        PrintMsg.act("adding actions from " + url.getFile());

        actionList = new ArrayList<>();
        for (String operation : p.stringPropertyNames()) {
            String className = p.getProperty(operation);
            Class<Operation> clazz = (Class<Operation>) cl.loadClass(className);
            actionList.add(new Action(clazz, url));
            PrintMsg.act("action added " + clazz.getCanonicalName());
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
