package edu.chl.coverage;

import junit.framework.Test;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runners.AllTests;
import edu.chl.linkedlist.LinkedList;
import etse.core.classloader.ClazzLoader;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.instr.Instrumenter;
import org.jacoco.core.runtime.IRuntime;
import org.jacoco.core.runtime.LoggerRuntime;
import org.jacoco.core.runtime.RuntimeData;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Based on
 * http://www.eclemma.org/jacoco/trunk/doc/examples/java/CoreTutorial.java
 *
 * Created by davida on 3.2.2015.
 */
public class CoverageRunner {
    /**
     * A class loader that loads classes from in-memory data.
     */

    private PrintStream out;

    public CoverageRunner(PrintStream o) {
        out = o;
    }

    public static class MemoryClassLoader extends ClassLoader {

        private final Map<String, byte[]> definitions = new HashMap<String, byte[]>();

        /**
         * Add a in-memory representation of a class.
         *
         * @param name
         *            name of the class
         * @param bytes
         *            class definition
         */
        public void addDefinition(final String name, final byte[] bytes) {
            definitions.put(name, bytes);
        }

        @Override
        protected Class<?> loadClass(final String name, final boolean resolve)
                throws ClassNotFoundException {
            final byte[] bytes = definitions.get(name);
            if (bytes != null) {
                return defineClass(name, bytes, 0, bytes.length);
            }
            return super.loadClass(name, resolve);
        }

    }
    public IClassCoverage execute(String clazz) throws Throwable {

        final IRuntime runtime = new LoggerRuntime();

        final Instrumenter instrumenter = new Instrumenter(runtime);

        byte[] instrumented = instrumenter.instrument(getTargetClass(clazz),clazz);

        final RuntimeData data = new RuntimeData();

        runtime.startup(data);

        final MemoryClassLoader memoryClassLoader = new MemoryClassLoader();
        memoryClassLoader.addDefinition(clazz, instrumented);
        final Class<?> targetClass = memoryClassLoader.loadClass(clazz);

        Computer computer = new Computer();

        JUnitCore core = new JUnitCore();
        Result r = core.run(computer, Class.forName("edu.david.linkedlisttests.LinkedListTests"));


        final ExecutionDataStore executionData = new ExecutionDataStore();
        final SessionInfoStore sessionInfos = new SessionInfoStore();
        data.collect(executionData, sessionInfos, false);
        runtime.shutdown();

        final CoverageBuilder coverageBuilder = new CoverageBuilder();
        final Analyzer analyzer = new Analyzer(executionData, coverageBuilder);
        analyzer.analyzeClass(getTargetClass(clazz), clazz);


        // Let's dump some metrics and line coverage information:
        for (final IClassCoverage cc : coverageBuilder.getClasses()) {
            out.printf("Coverage of class %s%n", cc.getName());

            printCounter("instructions", cc.getInstructionCounter());
            printCounter("branches", cc.getBranchCounter());
            printCounter("lines", cc.getLineCounter());
            printCounter("methods", cc.getMethodCounter());
            printCounter("complexity", cc.getComplexityCounter());

            for (int i = cc.getFirstLine(); i <= cc.getLastLine(); i++) {
                out.printf("Line %s: %s%n", Integer.valueOf(i), getColor(cc
                        .getLine(i).getStatus()));
            }
        }

        return (IClassCoverage)coverageBuilder.getClasses();
    }

    private void printCounter(final String unit, final ICounter counter) {
        final Integer missed = Integer.valueOf(counter.getMissedCount());
        final Integer total = Integer.valueOf(counter.getTotalCount());
        out.printf("%s of %s %s missed%n", missed, total, unit);
    }

    private String getColor(final int status) {
        switch (status) {
            case ICounter.NOT_COVERED:
                return "red";
            case ICounter.PARTLY_COVERED:
                return "yellow";
            case ICounter.FULLY_COVERED:
                return "green";
        }
        return "";
    }

    private InputStream getTargetClass(String name) {
        String file = '/' + name.replace('.', '/') + ".class";

        return getClass().getResourceAsStream(file);
    }



}
