package interfaces;

import java.util.List;
import java.util.Map;

import customexception.CustomException;
import pojo.Accounts_pojo;
import pojo.CustomerPojo;
import pojo.RequestPojo;
import pojo.TransactionPojo;
import pojo.UserPojo;

public interface PersistentLayerPathway {
	
//	Map<Long, CustomerPojo> getCustomerMap() throws CustomException;//USERPOJO  //cant replace parent in the place of child
//	
//	 Map<Long,UserPojo> getUserMap() throws CustomException;
//	
//	double getCurrentBalance(long accountNumber)throws CustomException;
//	
//	void depositUpdate(double balance, long accountNumber) throws CustomException;
//	
//	String getPasswordQuery(long customerId) throws CustomException;//swami name
//	
//	void withdrawUpdate(double finalAmount, long accountNumber) throws CustomException ;
//	
//	Map<Long, Map<Long, Accounts_pojo>> getAllAccountsOfUser() throws CustomException; //swami map<map>
//	
//	void updateTransactionDetails(TransactionPojo pojo) throws CustomException;
//	
//	void updateSelfTransactionDetails(TransactionPojo pojo) throws CustomException; // combine with previous
//	
//	 Accounts_pojo getAccountPojoQuery(long accountNumber) throws CustomException;//swami
//
//	Map<Long, Map<String, TransactionPojo>> getTransactions(long customerId,Long ...accountNumber) throws CustomException;
//
//	void updateCustomerWithdrawRequestLog(RequestPojo request) throws CustomException;
//
//	List<RequestPojo> getRequestDetails() throws CustomException;
//
//	void updateRequestStatus(RequestPojo request, String referenceId) throws CustomException;
//
//	void updateTransactionAfterVerification(TransactionPojo pojo, String referenceId) throws CustomException;
//
//	Map<Long,Map<String,RequestPojo>> getRequestMap() throws CustomException;
//
//	void declinedRequestInTransaction(double amount, String referenceId,long customerId) throws CustomException;
//
//	void acceptedRequestInTransaction(double amount, String referenceId, long customerId)throws CustomException;
//
//	public Map<Long,Map<String,RequestPojo>> getPendingRequestMap() throws CustomException;
//	
	
	
	
	Map<Long, CustomerPojo> getCustomerMap() throws CustomException;//USERPOJO  //cant replace parent in the place of child
	
	 Map<Long,UserPojo> getUserMap() throws CustomException;
	
	double getCurrentBalance(long accountNumber)throws CustomException;
	
	void depositUpdate(double balance, long accountNumber) throws CustomException;
	
	String getPasswordQuery(long customerId) throws CustomException;//swami name
	
	void withdrawUpdate(double finalAmount, long accountNumber) throws CustomException ;
	
	Map<Long, Map<Long, Accounts_pojo>> getAllAccountsOfUser() throws CustomException; //swami map<map>
	
	void updateTransactionDetails(TransactionPojo pojo) throws CustomException;
	
	void updateSelfTransactionDetails(TransactionPojo pojo) throws CustomException; // combine with previous
	
	 Accounts_pojo getAccountPojoQuery(long accountNumber) throws CustomException;//swami

	Map<Long, Map<String, TransactionPojo>> getTransactions(long customerId,Long ...accountNumber) throws CustomException;

	void updateCustomerWithdrawRequestLog(RequestPojo request) throws CustomException;

//	List<RequestPojo> getRequestDetails() throws CustomException;

	void updateRequestStatus(RequestPojo request, String referenceId) throws CustomException;

	void updateTransactionAfterVerification(TransactionPojo pojo, String referenceId) throws CustomException;

	Map<String, RequestPojo> getRequestMap() throws CustomException;

	void declinedRequestInTransaction(double amount, String referenceId,long customerId) throws CustomException;

	void acceptedRequestInTransaction(double amount, String referenceId, long customerId)throws CustomException;

	Map<String,RequestPojo> getPendingRequestMap() throws CustomException;


}
