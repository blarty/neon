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
 * neon : org.jini.projects.neon.host.transactions
 * TransactionalResource.java
 * Created on 11-Nov-2003
 *TransactionalResource
 */
package org.jini.projects.neon.host.transactions;

import java.util.concurrent.CountDownLatch;

import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionException;
import net.jini.id.Uuid;




/**
 * @author calum
 */
public interface TransactionalResource{
    /**
     * Sets the current transaction reference to the supplied reference
     * @param managerAndID the transactionAgent and local transaction ID reference
     * @throws TransactionException
     */
    public void setTransaction(String managerAndID) throws TransactionException;
    /**
     * Sets the current transaction reference to the supplied reference
     * @param managerAndID the transactionAgent and local transaction ID reference
     * @param txn The distributed transaction to join
     * @throws TransactionException
     */
    public void attachTransaction(String managerAndID, Transaction txn);
    
    /**
     * Get the transaction reference.<br>
     * TRansaction references are of the format<br>
     * <code>[TransactionAgent ID][Local transaction ID]</code>
     * @return
     */
    public String getTransaction();
    
/**
 * ensure that the transaction reference is cleared down and set to null
 * @param latch
 */
    public void clearTransaction(CountDownLatch latch);
    /**
     * Used to check if the transaction reference is set to null or not.
     * @return
     */
	public boolean inTransaction();
	
}
