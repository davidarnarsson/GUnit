package edu.chl.gunit;

import etse.core.classloader.ClazzLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;

/**
 * Created by davida on 17/02/15.
 */
public class ClassFinder {

    private final ClazzLoader loader;

    public ClassFinder(ClazzLoader loader) {
        this.loader = loader;
    }
    public HashSet<String> getClassNames(File root) {
        HashSet<String> classNames = new HashSet<>();

        scanDirectoryForClasses(root, "", classNames);

        return classNames;
    }

    private void scanDirectoryForClasses(File dir, String pathToHere, HashSet<String> classes) {
        if (!dir.isDirectory()) return;

        File[] files = dir.listFiles();
        pathToHere = normalize(pathToHere);

        for(File f : files) {

            if (f.isFile() && f.getName().endsWith(".class")) {
                try {
                    String className = f.getName().replace(".class", "");

                    Class c = loader.loadClass(pathToHere + className);

                    classes.add(c.getCanonicalName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            if (f.isDirectory()) {
                scanDirectoryForClasses(f,  pathToHere + f.getName(), classes);
            }
        }
    }

    private String normalize(String path) {
        if (!path.equals("")) {
            return path + ".";
        }
        return path;
    }
}
