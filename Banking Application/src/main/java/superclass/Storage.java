package superclass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DBLoadDriver.PersistentLayer;
import customexception.CustomException;
import interfaces.PersistentLayerPathway;
import pojo.Accounts_pojo;
import pojo.ActivityPojo;
import pojo.CustomerPojo;
import pojo.RequestPojo;
import pojo.UserPojo;

public enum Storage {

	VALUES;
	private PersistentLayerPathway load=new PersistentLayer();
	private Map<Long,UserPojo> userDetails;
	private Map<Long,CustomerPojo> customerDetails;
	private Map<Long,Map<Long,Accounts_pojo>> accountDetails;
	private Map<Long,Accounts_pojo> accountList;//account number,pojo
	private Map<String,RequestPojo> requestDetails;
	private List<Accounts_pojo> userSpecificAccounts;
	private Map<String,RequestPojo> pendingRequestDetails;
	private Map<String,RequestPojo> acceptedRequestDetails;
	private Map<Long,Map<String,RequestPojo>> requestDetailsMap;
	private Map<Long,ActivityPojo> activityStatus;
	private Map<Long,ActivityPojo> pendingActivityRequest;
	private Map<Long,ActivityPojo> rejectedActivityRequest;
	private Map<Long,ActivityPojo> acceptedActivityRequest;

	
public Map<Long, Accounts_pojo> getAccountList() {
		return accountList;
	}
public void setAccountList() throws CustomException {
		this.accountList = load.getAllAccountsMap();
	}
public Map<Long, ActivityPojo> getPendingActivityRequest() {
		return pendingActivityRequest;
	}
	public void setPendingActivityRequest() throws CustomException {
		this.pendingActivityRequest = load.getPendingActivityRequest();
	}
	public Map<Long, ActivityPojo> getRejectedActivityRequest() {
		return rejectedActivityRequest;
	}
	public void setRejectedActivityRequest() throws CustomException {
		this.rejectedActivityRequest = load.getRejectedActivityRequest();
	}
	public Map<Long, ActivityPojo> getAcceptedActivityRequest() {
		return acceptedActivityRequest;
	}
	public void setAcceptedActivityRequest() throws CustomException {
		this.acceptedActivityRequest = load.getAcceptedActivityRequest();
	}
	public Map<Long, ActivityPojo> getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus() throws CustomException {
		this.activityStatus = load.getActivityRequest();
	}
		public Map<Long, Map<String, RequestPojo>> getRequestDetailsMap() {
		return requestDetailsMap;
	}
	public void setRequestDetailsMap() throws CustomException {
		this.requestDetailsMap = load.getRequestDetailsMap();
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
	
//	public Accounts_pojo getCurrentAccountDetails() {
//		return currentAccountDetails;
//	}
//	public void setCurrentAccountDetails(long accountNumber) throws CustomException {//not in basicdata
//		this.currentAccountDetails = load.getAccountPojoQuery(accountNumber);
//	}
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
		setPendingActivityRequest();
		setAccountList();
		setRejectedActivityRequest();
		setAcceptedActivityRequest();
		setActivityStatus();
		setRequestDetailsMap();
		setAcceptedRequestDetails();
		setUserDetails();
		setPendingRequestDetails();
		setRequestDetails();
		setAccountDetails();
	}
	
	public void setAllActivityStatus() throws CustomException {
		setPendingActivityRequest();
		setRejectedActivityRequest();
		setAcceptedActivityRequest();
		setActivityStatus();
	}
}
