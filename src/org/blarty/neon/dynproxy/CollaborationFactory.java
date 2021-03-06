/*
 * Copyright 2005 neon.jini.org project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * neon : org.jini.projects.neon.dynproxy
 * CollaborationFactory.java
 * Created on 20-Jan-2004
 *CollaborationFactory
 */
package org.jini.projects.neon.dynproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jini.projects.neon.agents.Agent;
import org.jini.projects.neon.agents.RemoteAgentState;
import org.jini.projects.neon.host.AsyncAgentEnvironment;
import org.jini.projects.neon.host.AsyncHandler;
import org.jini.projects.neon.host.ManagedDomain;

/**
 * Builds the dynamic proxies that represent an agent. Basically, all the
 * proxies generated by this factory are messaging adapters, designed to
 * manipulate the method call into a message and place it on the appropriate
 * channel.
 * 
 * @author calum
 */
public class CollaborationFactory {

	private static boolean useDirect = true;

	public static void setDirectProxyMode(boolean use) {
		useDirect = use;
	}

	/**
	 * 
	 */
	public CollaborationFactory() {
		super();
		// URGENT Complete constructor stub for CollaborationFactory
	}

	static Logger log = Logger.getLogger("org.jini.projects.neon.dynproxy");

	/**
	 * Creates a synchronous agent proxy, from a set of agent information,
	 * possibly from another Neon instance
	 * 
	 * @param ctx
	 *            the agent domain needed to send the messages through
	 * @param externalAgent
	 *            Information about the makeup of an agent
	 * @param caller
	 *            the agent instance that needs to be replied to
	 * @return a proxy instance implementing <i>at least</i> all
	 *         <code>Collaborative</code> interfaces specified in
	 *         externalAgent
	 */
	public static Object create(AsyncAgentEnvironment ctx, AgentProxyInfo externalAgent, Agent caller) {
		if (externalAgent.getAgentService() != null) {
			CollaborationMethodInvokerRemoteProxy p = new CollaborationMethodInvokerRemoteProxy(externalAgent.getId(),externalAgent.getAgentService(),caller,(ManagedDomain) ctx);
			log.finest("CollaborationRemoteProxy intfs req'd (external):");
			List<Class> classList = new ArrayList<Class>();
			for (int i = 0; i < externalAgent.getCollaborativeInterfaces().length; i++){
				log.finest("\t" + externalAgent.getCollaborativeInterfaces()[i].getName());
				classList.add(externalAgent.getCollaborativeInterfaces()[i]);				
			}
			classList.add(RemoteAgentState.class);
			return Proxy.newProxyInstance(caller.getClass().getClassLoader(), (Class[]) classList.toArray(new Class[]{}), p);
		} else {
			CollaborationProxy p = new CollaborationProxy(ctx, externalAgent.getId(), caller);
			log.finest("CollaborationRemoteProxy intfs req'd (external):");
			for (int i = 0; i < externalAgent.getCollaborativeInterfaces().length; i++)
				log.finest("\t" + externalAgent.getCollaborativeInterfaces()[i].getName());
			return Proxy.newProxyInstance(caller.getClass().getClassLoader(), externalAgent.getCollaborativeInterfaces(), p);
		}
	}

	/**
	 * Creates a synchronous agent proxy, from a set of agent information,
	 * possibly from another Neon instance
	 * 
	 * @param ctx
	 *            the agent domain needed to send the messages through
	 * @param linkedAgent
	 *            the agent instance to create the dynamic proxy from
	 * @param caller
	 *            the agent instance that needs to be replied to
	 * @return a proxy instance implementing <i>at least</i> all
	 *         <code>Collaborative</code> interfaces implemented by the Agent
	 */
	public static Object create(AsyncAgentEnvironment ctx, Agent linkedAgent, Agent caller) {
		try {
			InvocationHandler p;
			Class[] collabIntfs = linkedAgent.advertise().getCollaborativeClasses();
			if (useDirect)
				p = new CollaborationDirectProxy(linkedAgent, caller, linkedAgent.getIdentity());
			else
				p = new CollaborationProxy(ctx, linkedAgent.getIdentity(), caller);
			if (caller != null)
				return Proxy.newProxyInstance(linkedAgent.getClass().getClassLoader(), collabIntfs, p);
			else
				return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), collabIntfs, p);
		} catch (ClassCastException e) {
			// TODO Handle IllegalArgumentException
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates an asynchronous agent proxy, from another,<i>locally available</i>,
	 * Agent. This method returns a proxy that implements the synchronous
	 * interfaces of the agent, but all method calls will return immediately and
	 * for those that return a value, this proxy will make those methods return
	 * null.
	 * 
	 * @param ctx
	 *            the agent domain needed to send the messages through
	 * @param linkedAgent
	 *            the agent instance to create the dynamic proxy from
	 * @param caller
	 *            the agent instance that needs to be replied to
	 * 
	 * @param contextHandler
	 *            a single Asynchronous Handler that will be notified for all
	 *            return messages to method calls
	 * @return a proxy instance implementing <i>at least</i> all
	 *         <code>Collaborative</code> interfaces implemented by the Agent
	 */
	public static Object createQuasiAsync(AsyncAgentEnvironment ctx, Agent linkedAgent, Agent caller, AsyncHandler contextHandler) {
		Class[] collabIntfs = linkedAgent.advertise().getCollaborativeClasses();
		log.finest("CollaborationProxy intfs req'd (internal):");
		for (int i = 0; i < collabIntfs.length; i++)
			log.finest("\t" + collabIntfs[i].getName());
		try {
			InvocationHandler proxy = null;
			if (!useDirect)
				proxy = new CollaborationAsyncProxy(ctx, linkedAgent.getIdentity(), caller, contextHandler);
			else
				proxy = new CollaborationDirectAsyncProxy(linkedAgent.getIdentity(), linkedAgent,contextHandler);
			return Proxy.newProxyInstance(caller.getClass().getClassLoader(), collabIntfs, proxy);
		} catch (ClassCastException e) {
			// TODO Handle IllegalArgumentException
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates an asynchronous agent proxy, from another,<i>locally available</i>,
	 * Agent. This method returns a proxy that implements the asynchronous
	 * interfaces of the agent (if they can be found), unlike
	 * <code>createQuasiAsync(...)</code> the methods for these asynchronous
	 * interfaces will all return void, and an extra parameter will be accepted
	 * of AsyncHandler, allowing different AsyncHandler instances to be used on
	 * different method calls
	 * 
	 * @see AsyncGen
	 * @param ctx
	 *            the agent domain needed to send the messages through
	 * @param linkedAgent
	 *            the agent instance to create the dynamic proxy from
	 * @param caller
	 *            the agent instance that needs to be replied to
	 * 
	 * @return a proxy instance implementing <i>at least</i> all
	 *         <code>Collaborative</code> interfaces implemented by the Agent
	 */
	public static Object createAsync(AsyncAgentEnvironment ctx, Agent linkedAgent, Agent caller) {
		// String packageName = linkedAgent.getClass().getPackage().getName();
		Class[] collabs = linkedAgent.advertise().getCollaborativeClasses();
		ArrayList<Class> asyncClasses = new ArrayList<Class>();
		for (Class cl : collabs) {
			String packageName = cl.getPackage().getName();
			String classname = cl.getName().substring(cl.getName().lastIndexOf('.') + 1);
			String classToFind = packageName + ".Async" + classname;
			Class asyncClass = null;
			try {
				asyncClass = Class.forName(classToFind);

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				log.info("Asynchronous class for interface " + classToFind + " could not be found");
				
			}
			if (asyncClass != null)
				asyncClasses.add(asyncClass);
		}
		Class[] asyncIntf = (Class[]) asyncClasses.toArray(new Class[0]);
		InvocationHandler proxy = null;
		if (!useDirect)
			proxy = new CollaborationAsyncMapProxy(ctx, linkedAgent.getIdentity(), caller);
		else
			proxy = new CollaborationDirectAsyncMethodProxy(linkedAgent.getIdentity(), linkedAgent);
		return Proxy.newProxyInstance(caller.getClass().getClassLoader(), asyncIntf, proxy);
	}

	/**
	 * Creates an asynchronous agent proxy, from another,<i>externally
	 * available</i>, Agent. This method returns a proxy that implements the
	 * asynchronous interfaces of the agent (if they can be found), unlike
	 * <code>createQuasiAsync(...)</code> the methods for these asynchronous
	 * interfaces will all return void, and an extra parameter will be accepted
	 * of AsyncHandler, allowing different AsyncHandler instances to be used on
	 * different method calls
	 * 
	 * @see AsyncGen
	 * @param ctx
	 *            the agent domain needed to send the messages through
	 * @param externalAgent
	 *            set of class information regarding an agent, that can be used
	 *            to build a dynamic proxy to that agent
	 * @param caller
	 *            the agent instance that needs to be replied to
	 * 
	 * @return a proxy instance implementing <i>at least</i> all
	 *         <code>Collaborative</code> interfaces implemented by the Agent
	 */

	public static Object createAsync(AsyncAgentEnvironment ctx, AgentProxyInfo externalAgent, Agent caller) {
		// String packageName = linkedAgent.getClass().getPackage().getName();
		Class[] collabs = externalAgent.getCollaborativeInterfaces();
		ArrayList<Class> asyncClasses = new ArrayList<Class>();
		for (Class cl : collabs) {
			String packageName = cl.getPackage().getName();
			String classname = cl.getName().substring(cl.getName().lastIndexOf('.') + 1);
			String classToFind = packageName + ".Async" + classname;
			Class asyncClass = null;
			try {
				asyncClass = Class.forName(classToFind);

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (asyncClass != null)
				asyncClasses.add(asyncClass);
		}
		Class[] asyncIntf = (Class[]) asyncClasses.toArray(new Class[0]);
		CollaborationAsyncMapProxy p = new CollaborationAsyncMapProxy(ctx, externalAgent.getId(), caller);
		return Proxy.newProxyInstance(caller.getClass().getClassLoader(), asyncIntf, p);
	}

	/**
	 * Creates an asynchronous agent proxy, from another,<i>externally
	 * available</i>, Agent. This method returns a proxy that implements the
	 * synchronous interfaces of the agent, but all method calls will return
	 * immediately and for those that return a value, this proxy will make those
	 * methods return null.
	 * 
	 * @param ctx
	 *            the agent domain needed to send the messages through
	 * @param externalAgent
	 *            set of class information regarding an agent, that can be used
	 *            to build a dynamic proxy to that agent
	 * @param caller
	 *            the agent instance that needs to be replied to
	 * 
	 * @param contextHandler
	 *            a single Asynchronous Handler that will be notified for all
	 *            return messages to method calls
	 * @return a proxy instance implementing <i>at least</i> all
	 *         <code>Collaborative</code> interfaces implemented by the Agent
	 */
	public static Object createQuasiAsync(AsyncAgentEnvironment ctx, AgentProxyInfo externalAgent, Agent caller, AsyncHandler contextHandler) {
		CollaborationAsyncProxy p = new CollaborationAsyncProxy(ctx, externalAgent.getId(), caller, contextHandler);
		log.finest("CollaborationProxy intfs req'd (external):");
		for (int i = 0; i < externalAgent.getCollaborativeInterfaces().length; i++)
			log.finest("\t" + externalAgent.getCollaborativeInterfaces()[i].getName());
		return Proxy.newProxyInstance(caller.getClass().getClassLoader(), externalAgent.getCollaborativeInterfaces(), p);
	}

}
