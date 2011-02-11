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
 * StatelessProxy.java
 * Created on 16-Jul-2004
 *StatelessProxy
 */

package org.jini.projects.neon.dynproxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import org.jini.projects.neon.host.AgentDomain;
import org.jini.projects.neon.util.MethodViewer;
import org.jini.projects.zenith.messaging.channels.connectors.PublishingQConnector;
import org.jini.projects.zenith.messaging.channels.connectors.ReceivingQConnector;
import org.jini.projects.zenith.messaging.messages.ExceptionMessage;
import org.jini.projects.zenith.messaging.messages.InvocationMessage;
import org.jini.projects.zenith.messaging.messages.Message;
import org.jini.projects.zenith.messaging.messages.MessageHeader;
import org.jini.projects.zenith.messaging.system.MessagingListener;
import org.jini.projects.zenith.messaging.system.MessagingService;

/**
 * Generated by Neon to provide a <i>Remote-like</i> proxy for an agent within Neon, to an external party.<br/>
 * It is not required, in this form, for an agent's methods
 *  to throw <code>RemoteException</code>, because
 * this proxy's remote references are channel Adapters, however, if you intend invoking this agent from outside of a Neon instance, it is advisable
 * that your agent's methods explicitly set that they throw RemoteException, like any normal remote object.  
 * @author calum
 */
public class ExportedAgentProxy implements InvocationHandler, MessagingListener, Serializable {

    private String agentName;

    private Object returnObject;

    Object o = new String("LOCK");

    private boolean isLocked = false;

    public boolean thrownException = false;

    private transient Logger l = Logger.getLogger("org.jini.projects.neon.dynproxy");

    private ReceivingQConnector adapter;

    private PublishingQConnector sender;

    private MessagingService msgSvc;

    public ExportedAgentProxy(String agentname, MessagingService msgSvc, ReceivingQConnector adapter, PublishingQConnector sender) {

        this.agentName = agentname;
        this.adapter = adapter;
        this.msgSvc = msgSvc;
        this.sender = sender;
    }

    public void finalize() {

        adapter = null;
        msgSvc = null;
        agentName = null;
        returnObject = null;
        sender = null;
    }

    /*
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
     *      java.lang.reflect.Method, java.lang.Object[])
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("toString"))
            return toString();
        if (l == null)
            l = Logger.getLogger("org.jini.projects.neon.dynproxy");
        // TODO Complete method stub for invoke

        // ReceiverChannel rec = svc.getTemporaryChannel(this);
        MessageHeader header = new MessageHeader();
        adapter.setListener(this);

        header.setReplyAddress(adapter.getChannelName());
        // System.out.println("Reply address is: " + rec.getName());
        // System.out.println("Routing to " + d.getDomainName() + "|" +
        // agentName + "With "+ method.getName());
        sender.sendMessage(new InvocationMessage(header, method.getName(), args, MethodViewer.getMethodShortString(method)));

        synchronized (this) {
            try {

                wait(0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        isLocked = false;
        l.finest("Notified returning");
        if (thrownException) {
            Throwable t = extractAppropriateException(null, (Throwable) returnObject, method.getExceptionTypes());
            if (t == null) {
                throw (Throwable) returnObject;
            } else {
                throw t;
            }
        }
        // d.returnTemporaryChannel(rec);
        return returnObject;
    }

    public String toString() {
        return agentName + " proxy";
    }

    
    private Throwable extractAppropriateException(Throwable parent, Throwable e, Class[] exceptionTypes) {
        if (e instanceof InvocationTargetException)
            return extractAppropriateException(e, e.getCause(), exceptionTypes);
        if (e instanceof UndeclaredThrowableException)
            return extractAppropriateException(e, e.getCause(), exceptionTypes);
        for (int i = 0; i < exceptionTypes.length; i++) {
            if (exceptionTypes[i].isInstance(e)) {
                return e;
            }
        }
        return null;
    }

    /*
     * @see org.jini.projects.zenith.messaging.system.MessagingListener#messageReceived(org.jini.projects.zenith.messaging.messages.Message)
     */
    public void messageReceived(Message m) {
        // TODO Complete method stub for messageReceived
        if (m instanceof ExceptionMessage) {
            returnObject = m.getMessageContent();
            thrownException = true;
        } else {
            Object o = m.getMessageContent();
            returnObject = o;
            l.finest("Reply Message received");
            this.thrownException = false;
        }
        synchronized (this) {
            try {
                notifyAll();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
