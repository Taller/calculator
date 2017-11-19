package com.github.taller.calculator.model.plugin;

import com.github.taller.calculator.exports.Operation;
import com.github.taller.calculator.log.PrintMsg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class Plugin {

    private URL url;

    private URLClassLoader cl;

    private List<Action> actionList;

    public Plugin(URL url) throws Exception {
        this.url = url;

        this.cl = URLClassLoader.newInstance(new URL[]{url});
        PrintMsg.act("" + this.cl);

        URL properties = cl.findResource("operations.properties");
        if (properties == null) {
            properties = cl.getResource("operations.properties");
        }

        if (properties == null) {
            PrintMsg.act("operations.properties wasn't found.");
            return;
        }

        actionList = new ArrayList<>();
        PrintMsg.act("adding actions from " + url.getFile());

        String line = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(properties.openStream()));
        while ( (line = br.readLine()) != null) {
            String[] props = line.split("=");
            if (props.length < 2) {
                continue;
            }

            String className = props[1];
            Class<Operation> clazz = (Class<Operation>) cl.loadClass(className);
            actionList.add(new Action(clazz, url));
            PrintMsg.act("action added " + clazz.getCanonicalName());
        }

        if (actionList.isEmpty()) {
            PrintMsg.act("operations.properties is empty.");
            return;
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
