@echo off
$[javaloc] -classpath ".;$[prodlibs];$[jinilibs];$[additionallibs];$[serviceuiloc]";%3 org.jini.projects.neon.util.AsyncGen %1 %2