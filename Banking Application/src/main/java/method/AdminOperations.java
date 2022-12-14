package method;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.util.getvalues.InputChecks;

import DBLoadDriver.PersistentLayer;
import customexception.CustomException;
import interfaces.PersistentLayerPathway;
import pojo.Accounts_pojo;
import pojo.ActivityPojo;
import pojo.CustomerPojo;
import pojo.RequestPojo;
import pojo.TransactionPojo;
import pojo.UserPojo;
import superclass.Storage;

public class AdminOperations {

	private PersistentLayerPathway load=new PersistentLayer();
	private InputChecks inputCheck=new InputChecks();

	public List<CustomerPojo> getCustomerDetails(Long ... customerIds) throws CustomException {
		int length=customerIds.length;
		Storage.VALUES.setCustomerDetails();
		Map<Long,CustomerPojo> map=Storage.VALUES.getCustomerDetails();
		List<CustomerPojo> list=new ArrayList<>();
		if(length==0) {
			for(Map.Entry<Long, CustomerPojo> element:map.entrySet()) {
				if(map.containsKey(element.getKey())) {
					list.add(element.getValue());
				}
				else {
					throw new CustomException("User id not found ");
				}
			}
		}
		else if(length>=1) {
			for(int i=0;i<length;i++) {
				if(map.containsKey(customerIds[i])) {
					list.add(map.get(customerIds[i]));
				}
				else {
					throw new CustomException("User id not found ");
				}
			}
		}

		return list;
	}


	public List<Long> getList(long id) throws CustomException{
		Map<Long, Accounts_pojo> map=Storage.VALUES.getAccountDetails().get(id);
		List<Long> accountsList=new ArrayList<>();
		for( Map.Entry<Long, Accounts_pojo> element:map.entrySet()) {
			if(element.getValue().getStatus().equalsIgnoreCase("active")) {
				accountsList.add(element.getValue().getAccountNumber());
			}
		}
		
		return accountsList;
	}
	
	public Map<Long,List<Accounts_pojo>> getAccountDetails(long userId,Long... accountNumbers) throws CustomException {
		int length = accountNumbers.length;
		Map<Long, Accounts_pojo> map = Storage.VALUES.getAccountDetails().get(userId);
		if(map==null){
			throw new CustomException("Customer id not found ");
		}
		Map<Long,List<Accounts_pojo>> accountMap=new LinkedHashMap<>();
		List<Accounts_pojo> list=new ArrayList<>();
		if (length == 0) {
			for (Map.Entry<Long, Accounts_pojo> element : map.entrySet()) {
				list.add(element.getValue());
			}
			accountMap.put(userId, list);
		} else if (length >= 1) {
			for (int i = 0; i < length; i++) {
				if(map.containsKey(accountNumbers[i])){
					list.add(map.get(accountNumbers[i]));
				}else{
					throw new CustomException("Account number not found ");
				}
			}
			accountMap.put(userId, list);
		}
		return accountMap;

	}
	public Map<String, TransactionPojo> getTransactionDetails(long customerId,Long ...accountNumber) throws CustomException{
		if(Storage.VALUES.getUserDetails().containsKey(customerId)) {
			return load.getTransactions(customerId,accountNumber).get(customerId);
		
		}else {
			throw new CustomException("Customer id is invalid");
		}
	}
	
	
	public Map<String, RequestPojo> getRequestDetails() throws CustomException  {
		Storage.VALUES.setPendingRequestDetails();
		return Storage.VALUES.getPendingRequestDetails();
	}
	
	public Map<String, RequestPojo> getAllRequests() throws CustomException{
		Storage.VALUES.setRequestDetails();
		return Storage.VALUES.getRequestDetails();
	}

	public void acceptRequest(RequestPojo requestPojo) throws CustomException {
		double amount=requestPojo.getAmount();
		long id=requestPojo.getCustomerId();
		long acc_No=requestPojo.getAccountNumber();
		double balance=Storage.VALUES.getAccountDetails().get(id).get(acc_No).getBalance();
		requestPojo.setStatus("Accepted");
		requestPojo.setProcessdeTime(System.currentTimeMillis());
		load.updateRequestStatus(requestPojo,requestPojo.getReferenceId());
		load.withdrawUpdate(requestPojo.getAmount(),requestPojo.getAccountNumber());
		load.acceptedRequestInTransaction(requestPojo.getAmount(),requestPojo.getReferenceId(),requestPojo.getCustomerId());

		TransactionPojo pojo=new TransactionPojo();
		pojo.setTimeInMillis(requestPojo.getProcessdeTime());
		pojo.setStatus("Success");
		pojo.setClosingBalance(balance-amount);
		load.updateTransactionAfterVerification(pojo,requestPojo.getReferenceId());
		
		Storage.VALUES.setBasicData();

	}

	public void declineRequest(RequestPojo requestPojo) throws CustomException {
		requestPojo.setStatus("Rejected");
		requestPojo.setProcessdeTime(System.currentTimeMillis());
		load.updateRequestStatus(requestPojo,requestPojo.getReferenceId());
		load.declinedRequestInTransaction(requestPojo.getAmount(),requestPojo.getReferenceId(),requestPojo.getCustomerId());
		
		TransactionPojo pojo=new TransactionPojo();
		load.depositUpdate(requestPojo.getAmount(),requestPojo.getAccountNumber());
		pojo.setTimeInMillis(requestPojo.getProcessdeTime());
		pojo.setStatus(requestPojo.getStatus());
		load.updateTransactionAfterVerification(pojo,requestPojo.getReferenceId());
		
		Storage.VALUES.setBasicData();
	}

	public void processButtonInTable(String referenceId,String status) throws CustomException {
		if(status.equalsIgnoreCase("accepted")) {//accepted
			Map<String,RequestPojo> map=Storage.VALUES.getPendingRequestDetails();
				RequestPojo pojo=map.get(referenceId);
				String ref=pojo.getReferenceId();
				if(ref.equals(referenceId)) {
					acceptRequest(pojo);
				}
			
		}else {//declined
			Map<String,RequestPojo> map=Storage.VALUES.getPendingRequestDetails();
			RequestPojo pojo=map.get(referenceId);
			if(pojo.getReferenceId().equals(referenceId)) {
				declineRequest(pojo);
			}
		}
	}
	
	public void processAllRequests(long customerId,String status,Long ...accountNumbers) throws CustomException {
		if(load.isUserIdpresentInTransaction(customerId)) {
			if(status.equalsIgnoreCase("accepted")) {//accepted
				Map<String,RequestPojo> map=Storage.VALUES.getPendingRequestDetails();
				for(Entry<String, RequestPojo> element:map.entrySet()) {
					RequestPojo pojo=element.getValue();
					if(pojo.getCustomerId()==customerId) {
						acceptRequest(pojo);
					}
				}
			}else {//declined
				Map<String,RequestPojo> map=Storage.VALUES.getPendingRequestDetails();
				for(Entry<String, RequestPojo> element:map.entrySet()) {
					RequestPojo pojo=element.getValue();
					if(pojo.getCustomerId()==customerId) {
						declineRequest(pojo);
					}
				}
			}
		}
	}
	
	public void processSpecificRequests(long customerId, String status, Long... accountNumbers) throws CustomException {
		int length=accountNumbers.length;
		for(int i=0;i<length;i++) {//accepted
			if(load.isUserIdpresentInTransaction(customerId)) {
				if(load.isAccountNumberpresent(accountNumbers[i])) {
					if(status.equalsIgnoreCase("accepted")) {
						Map<String,RequestPojo> map=Storage.VALUES.getPendingRequestDetails();
						for(Entry<String, RequestPojo> element:map.entrySet()) {
							RequestPojo pojo=element.getValue();
							if(pojo.getCustomerId()==customerId && pojo.getAccountNumber()==accountNumbers[i]) {
								acceptRequest(pojo);
							}
						}
					}else {//declined
						Map<String,RequestPojo> map=Storage.VALUES.getPendingRequestDetails();
						for(Entry<String, RequestPojo> element:map.entrySet()) {
							RequestPojo pojo=element.getValue();
							if(pojo.getCustomerId()==customerId && pojo.getAccountNumber()==accountNumbers[i]) {
								declineRequest(pojo);
							}
						}
					}
				}
			}
			
		}
	}
	
	public void processRequest(long customerId,String referenceId,String status,Long ...accountNumbers) throws CustomException {

		if(referenceId!=null) {
			processButtonInTable(referenceId,status);
		}
		else {
			if(Storage.VALUES.getCustomerDetails().containsKey(customerId)) {
				int length=accountNumbers.length;
				if(length==0) {
					processAllRequests(customerId, status, accountNumbers);
				}
				else {
					processSpecificRequests(customerId,status,accountNumbers);
				}
			}else {
				throw new CustomException("Customer id is invalid");
			}
		}
	}
	
	
	public boolean checkCredentials(String mobile, String email, String aadhar, String pan) throws CustomException {
		if(inputCheck.checkMobile(mobile) && inputCheck.checkEmail(email) && inputCheck.checkAadhar(aadhar) && inputCheck.checkPanNumber(pan)) {
			return true;
		}
		return false;
	}
	public CustomerPojo createCustomer(CustomerPojo customerPojo)throws CustomException  {
		customerPojo=load.createCustomer(customerPojo);
		Storage.VALUES.setBasicData();
		return customerPojo;
	}
	
	public Accounts_pojo createAccount(Accounts_pojo pojo)throws CustomException  {
		if(Storage.VALUES.getCustomerDetails().containsKey(pojo.getCustomerId())) {
			pojo=load.createAccount(pojo);
			Storage.VALUES.setBasicData();
			return pojo;
		}
		else {
			throw new CustomException("Customer id is invaild");
		}
		
	}
	
	public Map<Long,ActivityPojo> getPendingActivityRequest() throws CustomException{
		Storage.VALUES.setPendingActivityRequest();
		return Storage.VALUES.getPendingActivityRequest();
	}
	
	public  void activateAccount(long requestId) throws CustomException {
		ActivityPojo pojo=new ActivityPojo();
		pojo.setProcessedTime(System.currentTimeMillis());
		pojo.setStatus("ACTIVE");
		pojo.setRequestId(requestId);
		pojo.setAccountNumber(Storage.VALUES.getPendingActivityRequest().get(requestId).getAccountNumber());
		load.processActivityStatus(pojo);
		Storage.VALUES.setAllActivityStatus();
	
	}
	
	public void deactivateAccount(long requestId) throws CustomException {
		ActivityPojo pojo=new ActivityPojo();
		pojo.setProcessedTime(System.currentTimeMillis());
		pojo.setStatus("INACTIVE");
		pojo.setRequestId(requestId);
		pojo.setAccountNumber(Storage.VALUES.getPendingActivityRequest().get(requestId).getAccountNumber());
		load.processActivityStatus(pojo);
		Storage.VALUES.setAllActivityStatus();
	}


	public void activateAccountWithId(long requestId) throws CustomException {
		Storage.VALUES.setPendingActivityRequest();
		Map<Long,ActivityPojo> map= Storage.VALUES.getPendingActivityRequest();
		for(Map.Entry<Long, ActivityPojo> element:map.entrySet()) {
			if(element.getValue().getRequestId()==requestId) {
				activateAccount(element.getValue().getAccountNumber());
			}
		}
		
	}
	
	public void deactivateAccountWithId(long requestId) throws CustomException {
		Storage.VALUES.setPendingActivityRequest();
		Map<Long,ActivityPojo> map= Storage.VALUES.getPendingActivityRequest();
		for(Map.Entry<Long, ActivityPojo> element:map.entrySet()) {
			if(element.getValue().getRequestId()==requestId) {
				deactivateAccount(element.getValue().getAccountNumber());
			}
		}
		
	}
	
	public void activateAllActivityRequest() throws CustomException {
		Storage.VALUES.setPendingActivityRequest();
		Map<Long,ActivityPojo> map= Storage.VALUES.getPendingActivityRequest();
		for(Map.Entry<Long, ActivityPojo> element:map.entrySet()) {
			activateAccount(element.getKey());
		}
	}
	
	public void deactivateAllActivityRequest() throws CustomException {
		Storage.VALUES.setPendingActivityRequest();
		Map<Long,ActivityPojo> map= Storage.VALUES.getPendingActivityRequest();
		for(Map.Entry<Long, ActivityPojo> element:map.entrySet()) {
			deactivateAccount(element.getKey());
		}
	}
	
	public void updateProfile(UserPojo userPojo) throws CustomException {
		if(userPojo.getDob()!=null) {
			load.updateProfile(userPojo);
		}
		else if(userPojo.getEmail()!=null) {
			if(inputCheck.checkEmail(userPojo.getEmail())) {
				load.updateProfile(userPojo);
			}
		}
		else if(userPojo.getMobile()!=null) {
			if(inputCheck.checkMobile(userPojo.getMobile())) {
				load.updateProfile(userPojo);
			}
		}
		else if(userPojo.getAddress()!=null) {
			if(inputCheck.checkAddress(userPojo.getAddress())) {
				load.updateProfile(userPojo);
			}
		}
	}
}
