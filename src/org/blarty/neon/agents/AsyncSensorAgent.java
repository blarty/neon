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

/*
 * Generated by AsyncGen
 * Original interface : SensorAgent
 * Generated @ Thu Apr 14 12:19:20 BST 2005
 */

public interface AsyncSensorAgent{
	public void addListener(org.jini.projects.neon.agents.AgentIdentity aAgentIdentity, org.jini.projects.neon.agents.sensors.SensorFilter aSensorFilter,  org.jini.projects.neon.host.AsyncHandler contextHandler);
}