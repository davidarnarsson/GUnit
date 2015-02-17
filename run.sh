#!/bin/sh


mvn -f testhound-maven-plugin/pom.xml install;

mvn -f test-project/pom.xml install;

