package com.github.taller.calculator.model.plugin;

import com.github.taller.calculator.exports.Operation;
import com.github.taller.calculator.log.Print;

import java.net.URL;
import java.util.*;

public final class PluginModel {

    private LinkedList<Operation> operations = new LinkedList<>();

    private LinkedList<String> data = new LinkedList<>();

    private Collection<Plugin> plugins = new HashSet<>();

    private Collection<Action> availableActions = new ArrayList<>();

    private PluginModel() {
    }

    public static PluginModel getInstance() {
        return HOLDER.MODEL;
    }

    private static class HOLDER {
        private static PluginModel MODEL = new PluginModel();
    }


    public void addPlugin(URL url) {
        try {
            Plugin plugin = new Plugin(url);
            Print.msg("" + plugin);
            plugins.add(plugin);

            availableActions.addAll(plugin.getActionList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removePlugin(URL url) {
        Iterator<Plugin> pit = plugins.iterator();

        while (pit.hasNext()) {
            Plugin plugin = pit.next();
            if (plugin.getUrl().equals(url)) {
                pit.remove();
            }
        }

        Iterator<Action> ait = availableActions.iterator();

        while (ait.hasNext()) {
            Action action = ait.next();
            if (action.getSource().equals(url)) {
                ait.remove();
            }
        }
    }

    public Collection<Plugin> getPlugins() {
        return new ArrayList<>(plugins);
    }

    public Collection<Action> getAvailableActions() {
        return new ArrayList<>(availableActions);
    }
}
