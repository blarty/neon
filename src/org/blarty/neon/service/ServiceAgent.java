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
 * neon : org.jini.projects.neon.service
 * ServiceAgent.java
 * Created on 23-Sep-2003
 */
package org.jini.projects.neon.service;

import java.util.List;

import org.jini.projects.neon.agents.SensorAgent;
import org.jini.projects.neon.collaboration.Collaborative;

public interface ServiceAgent extends Collaborative, SensorAgent{
	public abstract List getAgentHosts();
	public abstract List getNamedService(String name, Class serviceClass);
	public abstract List getService(Class serviceClass);
	public abstract Object getSingleService(Class serviceClass);
	public abstract Object getSingleNamedService(String name,Class serviceClass);	

}
