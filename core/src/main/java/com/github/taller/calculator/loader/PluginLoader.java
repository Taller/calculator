package com.github.taller.calculator.loader;

import com.github.taller.calculator.log.Print;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

public class PluginLoader extends ClassLoader {

    public static final String CONFIGURATION_ENTRY = "com/github/taller/calculator/Configuration.class";
    public static final String CONFIGURATION_CLASS = "com.github.taller.calculator.Configuration";

    private InputStream is;


    public PluginLoader(InputStream is) {
        super(PluginLoader.class.getClassLoader());
        this.is = is;
    }

//    public Class loadClass(String className) throws ClassNotFoundException {
//        return findClass(className);
//    }

    public Class findConfiguration() {
        byte classByte[];
        Class result = null;

        try {
//            JarInputStream jis = new JarInputStream(Main.class.getResource("/basic-operations-1.0-SNAPSHOT.jar").openStream());
            JarInputStream jis = new JarInputStream(is);
            ZipEntry innerEntry;

            while ((innerEntry = jis.getNextEntry()) != null) {
                if (!CONFIGURATION_ENTRY.equals(innerEntry.getName())) {
                    continue;
                }
                Print.msg(innerEntry.getName());
                byte[] buffer = new byte[1024 * 4];

                try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream()) {
                    int n = 0;
                    while ((n = jis.read(buffer)) != -1) {
                        byteStream.write(buffer, 0, n);
                    }

                    classByte = byteStream.toByteArray();
                }
                Print.msg("" + classByte.length);
                result = defineClass(CONFIGURATION_CLASS, classByte, 0, classByte.length, null);
            }

            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
