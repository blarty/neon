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
 * neon : org.jini.projects.neon.service.admin
 * AgentServiceAdminProxy.java
 * Created on 22-Sep-2003
 */
package org.jini.projects.neon.service.admin;

import java.rmi.Remote;

import net.jini.admin.JoinAdmin;

import com.sun.jini.admin.DestroyAdmin;

/**
 * @author calum
 */
public interface AgentServiceAdmin extends DestroyAdmin, JoinAdmin, AgentAdmin, Remote {

}
