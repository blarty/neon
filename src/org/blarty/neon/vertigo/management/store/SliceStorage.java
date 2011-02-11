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
 * neon : org.jini.projects.neon.vertigo.management.store
 * SliceStorage.java
 * Created on 02-Jun-2005
 *SliceStorage
 */
package org.jini.projects.neon.vertigo.management.store;

import java.io.IOException;

import net.jini.id.Uuid;

import org.jini.projects.neon.vertigo.slice.Slice;

/**
 * @author calum
 */
public interface SliceStorage {
	public void storeSlice(Slice s) throws IOException;
	public void removeSlice(Slice s) throws IOException;
	public Slice loadSlice(Uuid id) throws IOException;
    public void loadSlices() throws IOException;
}
