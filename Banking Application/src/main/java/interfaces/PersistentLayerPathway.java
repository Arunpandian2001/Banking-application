package interfaces;

import java.util.Map;

import customexception.CustomException;
import pojo.Accounts_pojo;
import pojo.ActivityPojo;
import pojo.CustomerPojo;
import pojo.RequestPojo;
import pojo.TransactionPojo;
import pojo.UserPojo;

public interface PersistentLayerPathway {
	
	Map<Long, CustomerPojo> getCustomerMap() throws CustomException;
	
	 Map<Long,UserPojo> getUserMap() throws CustomException;
	
	double getCurrentBalance(long accountNumber)throws CustomException;
	
	void depositUpdate(double balance, long accountNumber) throws CustomException;
		
	void withdrawUpdate(double finalAmount, long accountNumber) throws CustomException ;
	
	Map<Long, Map<Long, Accounts_pojo>> getAllAccountsOfUser() throws CustomException; //swami map<map>
	
	void updateTransactionDetails(TransactionPojo pojo) throws CustomException;
	
	void updateSelfTransactionDetails(TransactionPojo pojo) throws CustomException; // combine with previous
	
	 Accounts_pojo getAccountPojoQuery(long accountNumber) throws CustomException;//swami

	Map<Long, Map<String, TransactionPojo>> getTransactions(long customerId,Long ...accountNumber) throws CustomException;

	void updateCustomerWithdrawRequestLog(RequestPojo request) throws CustomException;

	void updateRequestStatus(RequestPojo request, String referenceId) throws CustomException;

	void updateTransactionAfterVerification(TransactionPojo pojo, String referenceId) throws CustomException;

	void declinedRequestInTransaction(double amount, String referenceId,long customerId) throws CustomException;

	void acceptedRequestInTransaction(double amount, String referenceId, long customerId)throws CustomException;
	
	Map<String, RequestPojo> getRequestMap() throws CustomException;

	Map<String,RequestPojo> getPendingRequestMap() throws CustomException;

	Map<String,RequestPojo> getAcceptedRequestMap() throws CustomException;
	
	boolean isUserIdpresentInTransaction(long customerId) throws CustomException;
	
	boolean isAccountNumberpresent(long accountNumber) throws CustomException;
	
	boolean isAccountNumberpresentInTransaction(long accountNumber) throws CustomException;

	CustomerPojo createCustomer(CustomerPojo customerPojo) throws CustomException;

	Accounts_pojo createAccount(Accounts_pojo pojo)throws CustomException;
	
	Map<Long,ActivityPojo>getActivityRequest()throws CustomException;
	
	public Map<Long, ActivityPojo> getPendingActivityRequest()throws CustomException;
	
	public Map<Long, ActivityPojo> getAcceptedActivityRequest()throws CustomException;
	
	public Map<Long, ActivityPojo> getRejectedActivityRequest()throws CustomException;

	void postAccountActiveRequest(ActivityPojo pojo) throws CustomException;
	
	Map<Long,Map<String,RequestPojo>> getRequestDetailsMap() throws CustomException;
	
	void processActivityStatus (ActivityPojo pojo) throws CustomException;
	
	void updateProfile(UserPojo pojo) throws CustomException;
}
