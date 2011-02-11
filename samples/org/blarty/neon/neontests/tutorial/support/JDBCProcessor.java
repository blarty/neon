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
 * neon : org.jini.projects.neon.org.jini.projects.neon.neontests.tutorial.support
 * JDBCProcessor.java
 * Created on 13-Oct-2003
 */
package org.jini.projects.neon.neontests.tutorial.support;

import java.io.Serializable;
import java.sql.Connection;

/**
 * @author calum
 */
public interface JDBCProcessor extends Serializable{
	public void setConnection(Connection conn);
	public void execute();
	public Object getResults();
}
