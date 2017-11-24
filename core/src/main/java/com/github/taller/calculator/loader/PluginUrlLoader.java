package com.github.taller.calculator.loader;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;


public class PluginUrlLoader extends URLClassLoader {

    public PluginUrlLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public PluginUrlLoader(URL[] urls) {
        super(urls);
    }

    public PluginUrlLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> result = super.findClass(name);
        return result;
    }
}
