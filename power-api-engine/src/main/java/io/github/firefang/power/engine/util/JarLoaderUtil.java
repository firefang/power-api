package io.github.firefang.power.engine.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Utility class to get a JarClassLoader
 * 
 * @author xinufo
 *
 */
public abstract class JarLoaderUtil {
    private static final String JAR_SUFFIX = ".jar";
    private static final Map<String, URLClassLoader> LOADERS = new HashMap<>(4);

    public static void initClassLoader(String name, String folder) throws IOException {
        URLClassLoader loader = LOADERS.remove(name);
        if (loader != null) {
            loader.close();
        }
        LOADERS.put(name, createClassLoader(folder));
    }

    public static URLClassLoader getClassLoader(String name) {
        return LOADERS.get(name);
    }

    private static URLClassLoader createClassLoader(String folder) {
        File f = new File(folder);
        if (!f.exists()) {
            throw new IllegalArgumentException("Folder doesn't exist");
        }
        if (!f.isDirectory()) {
            throw new IllegalArgumentException("Path isn't a folder");
        }
        List<URL> urls = new LinkedList<>();
        for (File i : f.listFiles(path -> path.getName().endsWith(JAR_SUFFIX))) {
            try {
                urls.add(i.toURI().toURL());
            } catch (MalformedURLException e) {
            }
        }
        URL[] urlArr = new URL[urls.size()];
        return new URLClassLoader(urls.toArray(urlArr), Thread.currentThread().getContextClassLoader());
    }

}
