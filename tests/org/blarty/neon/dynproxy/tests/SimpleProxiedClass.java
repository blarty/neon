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
 * SimpleProxiedClass.java
 * Created on 23-Sep-2003
 */
package org.jini.projects.neon.dynproxy.tests;

/**
 * @author calum
 */
public class SimpleProxiedClass implements SimpleProxyInterface{

	/**
	 * 
	 */
	public SimpleProxiedClass() {
		super();
		// TODO Complete constructor stub for SimpleProxiedClass
	}

	/* (non-Javadoc)
	 * @see org.jini.projects.neon.dynproxy.SimpleProxyInterface#sayHello(java.lang.String)
	 */
	public Object sayHello(String name) {
		// TODO Complete method stub for sayHello
		return "Hello " + name;
	}

}
