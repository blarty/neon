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
 * ServiceConnector.java
 *Created on 22 September 2003, 11:43
 */

package org.jini.projects.neon.ui;

/**
 *@author  calum
 */
public class ServiceConnector {
    
    /** Creates a new instance of ServiceConnector */
    public ServiceConnector() {
        
    }
    
    public String[] getDomains(){
        return new String[] {"Global", "Secure", "Claims"};
    }
    
    public String[] getAgents(String Domain){
        return new String[] {"Service", "Transfer", "KnowledgeBaseImpl", "Callbacks", "Checkpoint","Recovery"};
    }
}
