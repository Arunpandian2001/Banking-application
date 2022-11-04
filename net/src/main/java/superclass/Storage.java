package superclass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DBLoadDriver.PersistentLayer;
import customexception.CustomException;
import interfaces.PersistentLayerPathway;
import pojo.Accounts_pojo;
import pojo.CustomerPojo;
import pojo.RequestPojo;
import pojo.UserPojo;

public enum Storage {

	VALUES;
	private long userId;
	private long accountNumber;
	private PersistentLayerPathway load=new PersistentLayer();
	private Map<Long,UserPojo> userDetails;
	private Map<Long,CustomerPojo> customerDetails;
	private Map<Long,Map<Long,Accounts_pojo>> accountDetails;
	private Map<String,RequestPojo> requestDetails;
	private List<Accounts_pojo> userSpecificAccounts;
	private Accounts_pojo currentAccountDetails;
	private Map<String,RequestPojo> pendingRequestDetails;
	private Map<String,RequestPojo> acceptedRequestDetails;
	private Map<Long,Map<String,RequestPojo>> requestDetailsMap;
	
	public Map<Long, Map<String, RequestPojo>> getRequestDetailsMap() {
		return requestDetailsMap;
	}
	public void setRequestDetailsMap() {
		this.requestDetailsMap = requestDetailsMap;
	}
	public Map<String, RequestPojo> getAcceptedRequestDetails() {
		return acceptedRequestDetails;
	}
	public void setAcceptedRequestDetails() throws CustomException {
		this.acceptedRequestDetails = load.getAcceptedRequestMap();
	}
	public Map<Long, UserPojo> getUserDetails() {
		return userDetails;
	}
	public void setUserDetails() throws CustomException {
		this.userDetails = load.getUserMap();
	}
	public Map<String, RequestPojo> getPendingRequestDetails() {
		return pendingRequestDetails;
	}
	public void setPendingRequestDetails() throws CustomException {
		this.pendingRequestDetails = load.getPendingRequestMap();
	}
	
	
	public Map<String, RequestPojo> getRequestDetails() {
		return requestDetails;
	}
	public void setRequestDetails() throws CustomException {
		this.requestDetails = load.getRequestMap();
	}	
	
	public Accounts_pojo getCurrentAccountDetails() {
		return currentAccountDetails;
	}
	public void setCurrentAccountDetails(long accountNumber) throws CustomException {
		this.currentAccountDetails = load.getAccountPojoQuery(accountNumber);
	}
	public Map<Long, Accounts_pojo> getuserSpecificAccounts(long userId) {
		return accountDetails.get(userId);
	}
	public Map<Long, Map<Long, Accounts_pojo>> getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails() throws CustomException {
		accountDetails = load.getAllAccountsOfUser();
	}

	public void updateAccountsDetails(Map<Long,Map<Long,Accounts_pojo>> accounts) {
		accountDetails=accounts;
	}
	

	public Map<Long, CustomerPojo> getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails() throws CustomException {
		this.customerDetails = load.getCustomerMap();
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getAccountNumber() {
//		Thread.dumpStack();        Find the mistake trace in intialization
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public void setAccounts(long userId){
		Map<Long,Accounts_pojo>map=getuserSpecificAccounts(userId);
		List<Accounts_pojo> list=new ArrayList<>();
		for(Map.Entry<Long, Accounts_pojo> element : map.entrySet()) {
			list.add(element.getValue());
		}
		this.userSpecificAccounts=list;
	}
	
	public List<Accounts_pojo> getAccounts(){
		return this.userSpecificAccounts;
	}
	
	public void setBasicData() throws CustomException {
		setUserDetails();
		setPendingRequestDetails();
		setRequestDetails();
		setAccountDetails();
		setCustomerDetails();
		setAcceptedRequestDetails();
		setRequestDetailsMap();
	}
}
