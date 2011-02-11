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



package org.jini.projects.neon.agents;

import org.jini.projects.neon.host.transactions.Transactional;

/**
 * Represents an agent which can be controlled via a Jini transaction. The
 * transaction itself is handled by the agent host, therefore the interface is
 * removed from general transaction semantics and exceptions.
 * This represents a connection between an agent and a transaction
 * @see  org.jini.projects.neon.host.transactions.Transactional 
 */
public interface TransactionalAgent extends Agent, Transactional {
}
