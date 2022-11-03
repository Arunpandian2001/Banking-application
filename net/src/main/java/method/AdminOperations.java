package method;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import DBLoadDriver.PersistentLayer;
import customexception.CustomException;
import interfaces.PersistentLayerPathway;
import pojo.Accounts_pojo;
import pojo.CustomerPojo;
import pojo.RequestPojo;
import pojo.TransactionPojo;
import superclass.Storage;

public class AdminOperations {

	private PersistentLayerPathway load=new PersistentLayer();

	public List<CustomerPojo> getCustomerDetails(Long ... customerIds) throws CustomException {
		int length=customerIds.length;
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


	public Map<Long,List<Accounts_pojo>> getAccountDetails(long userId,Long... accountNumbers) throws CustomException {
		int length = accountNumbers.length;
		Map<Long, Accounts_pojo> map = Storage.VALUES.getAccountDetails().get(userId);
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

		return load.getTransactions(customerId,accountNumber).get(customerId);
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
		requestPojo.setStatus("Accepted");
		requestPojo.setProcessdeTime(System.currentTimeMillis());
		load.updateRequestStatus(requestPojo,requestPojo.getReferenceId());
		load.withdrawUpdate(requestPojo.getAmount(),requestPojo.getAccountNumber());
		load.acceptedRequestInTransaction(requestPojo.getAmount(),requestPojo.getReferenceId(),requestPojo.getCustomerId());

		TransactionPojo pojo=new TransactionPojo();
		pojo.setTimeInMillis(requestPojo.getProcessdeTime());
		pojo.setStatus("Success");
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

	public void processRequest(long customerId,String status,Long ...accountNumbers) throws CustomException {
		int length=accountNumbers.length;
		if(length==0) {
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
		else {
			for(int i=0;i<length;i++) {//accepted
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
