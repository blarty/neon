@echo off
$[javaloc] -classpath ".;$[prodlibs];$[jinilibs];$[additionallibs];$[serviceuiloc]"  -Djava.security.policy=$[POLICY] -Djava.protocol.handler.pkgs=org.jini.users.cshawmackay.url  org.jini.projects.neon.util.AgentDeployLauncher %1 %2 %3 %4 %5 %6 %7 %8 %9
