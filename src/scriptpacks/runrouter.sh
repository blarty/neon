$[javaloc] -classpath ".:$[prodlibs]:$[jinilibs]:$[additionallibs]:$[serviceuiloc]" -Djava.security.policy=$[POLICY] -Djava.rmi.server.codebase=$[CODEBASE] -Xms20m org.jini.projects.zenith.router.RouterStartup $[group]