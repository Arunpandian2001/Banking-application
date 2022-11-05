package method;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import DBLoadDriver.PersistentLayer;
import customexception.CustomException;
import interfaces.PersistentLayerPathway;
import pojo.Accounts_pojo;
import pojo.ActivityPojo;
import pojo.CustomerPojo;
import pojo.RequestPojo;
import pojo.TransactionPojo;
import superclass.Storage;
import superclass.User;

public class CustomerOperations extends User{
	
	private PersistentLayerPathway load=new PersistentLayer();
	
	private long getTime() {
		return System.currentTimeMillis();
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
	
	public List<Long> getInActiveAccountList(long id) throws CustomException{
		Map<Long, Accounts_pojo> map=Storage.VALUES.getAccountDetails().get(id);
		List<Long> accountsList=new ArrayList<>();
		for( Map.Entry<Long, Accounts_pojo> element:map.entrySet()) {
			if(!element.getValue().getStatus().equalsIgnoreCase("active")) {
				accountsList.add(element.getValue().getAccountNumber());
			}
		}
		
		return accountsList;
	}

	@Override
	public double balance() throws CustomException {
		long userId=getUserId();
		long accountNumber=getAccountNumber();
		Map<Long,Accounts_pojo> map=Storage.VALUES.getuserSpecificAccounts(userId);
		return map.get(accountNumber).getBalance();
	}

	@Override
	public void toDeposit( double amount) throws CustomException {
		long userId=getUserId();
		long accountNumber=getAccountNumber();
		Map<Long, Map<Long, Accounts_pojo>> map=Storage.VALUES.getAccountDetails();
	
		if(isSenderValid(userId,accountNumber,amount)) {
			load.depositUpdate(amount,accountNumber);
			map.get(userId).get(accountNumber).setBalance(load.getCurrentBalance(accountNumber));
			updateDeposit(accountNumber,amount,getTime(),map.get(userId).get(accountNumber).getBalance());
		}

	}
	@Override
	public void toWithdraw(double amount) throws CustomException {
		
		long userId=getUserId();
		long accountNumber=getAccountNumber();
		Map<Long, Map<Long, Accounts_pojo>> map=Storage.VALUES.getAccountDetails();
	
		if(isSenderValid(userId,accountNumber,amount)) {
			load.withdrawUpdate(amount,accountNumber);
			map.get(userId).get(accountNumber).setBalance(load.getCurrentBalance(accountNumber));
			updateWithdraw(accountNumber,amount,getTime(),map.get(userId).get(accountNumber).getBalance());
		}
	}
	


	private void updateWithdraw(long userAccount, double amount, long time, double balance) throws CustomException {
		TransactionPojo pojo = new TransactionPojo();	
		String referenceId="AP"+time;
		pojo.setAmount(amount);
		pojo.setTransactionTypes("DEBIT");
		pojo.setTimeInMillis(time);
		pojo.setClosingBalance(balance);
		pojo.setMode("Withdraw");
		pojo.setAccountNumber(userAccount);
		pojo.setCustomerId(Storage.VALUES.getUserId());
		pojo.setStatus("Request pending");
		pojo.setReferenceId(referenceId);
		load.updateSelfTransactionDetails(pojo);
		
		RequestPojo request=new RequestPojo();
		request.setCustomerId(pojo.getCustomerId());
		request.setAccountNumber(pojo.getAccountNumber());
		request.setReferenceId(pojo.getReferenceId());
		request.setAmount(pojo.getAmount());
		request.setRequestedTime(pojo.getTimeInMillis());
		request.setStatus(pojo.getStatus());
		request.setType("Withdraw");
		load.updateCustomerWithdrawRequestLog(request);
		
		Storage.VALUES.setBasicData();
	}
	
	private void updateDeposit(long userAccount, double amount, long time, double balance) throws CustomException {
		TransactionPojo pojo = new TransactionPojo();
		String referenceId="AP"+time;
		pojo.setAmount(amount);
		pojo.setMode("Deposit");
		pojo.setTransactionTypes("CREDIT");
		pojo.setTimeInMillis(time);
		pojo.setClosingBalance(balance);
		pojo.setAccountNumber(userAccount);
		pojo.setCustomerId(Storage.VALUES.getUserId());
		pojo.setStatus("Success");
		pojo.setReferenceId(referenceId);

		load.updateSelfTransactionDetails(pojo);

		Storage.VALUES.setBasicData();
	}
	
	@Override
	public void transferAmount(long receiverAccountNumber, double amount, String password) throws CustomException {
		
		long userId=getUserId();
		long currentUserAccountNumber=getAccountNumber();
		long receiverUserId=load.getAccountPojoQuery(receiverAccountNumber).getCustomerId();
		Map<Long, Map<Long, Accounts_pojo>> map=Storage.VALUES.getAccountDetails();
		
		if((currentUserAccountNumber!=receiverAccountNumber)) {
			if(isSenderValid(userId,currentUserAccountNumber,amount) && isReceiverValid(receiverUserId,receiverAccountNumber))  {
				if(password.equals(Storage.VALUES.getUserDetails().get(userId).getPassword()) ){

					load.depositUpdate(amount,receiverAccountNumber);
					map.get(receiverUserId).get(receiverAccountNumber).setBalance(load.getCurrentBalance(receiverAccountNumber));

					load.withdrawUpdate(amount,currentUserAccountNumber);
					map.get(userId).get(currentUserAccountNumber).setBalance(load.getCurrentBalance(currentUserAccountNumber));

					updateTransaction(currentUserAccountNumber,receiverAccountNumber,amount,getTime(),map.get(userId).get(currentUserAccountNumber).getBalance(),map.get(receiverUserId).get(receiverAccountNumber).getBalance(),receiverUserId);
				}else {
					throw new CustomException("The password entered is invalid");
				}
			}
		}else {
			throw new CustomException("The entered account number is invalid");
		}
		
		
	}
	
	private void updateTransaction(long curentUser, long receiver, double amount, long time, double currentUserBalance, double receiverBalance,long receiverId) throws CustomException {
		
		String referenceId="AP"+time;

		TransactionPojo pojo = new TransactionPojo();	
		pojo.setSecondary(receiver);
		pojo.setAmount(amount);
		pojo.setTransactionTypes("DEBIT");
		pojo.setMode("Transfer");
		pojo.setTimeInMillis(time);
		pojo.setClosingBalance(currentUserBalance);
		pojo.setAccountNumber(curentUser);
		pojo.setCustomerId(Storage.VALUES.getUserId());
		pojo.setReferenceId(referenceId);
		pojo.setStatus("success");

		load.updateTransactionDetails(pojo);
		
		TransactionPojo pojo1 = new TransactionPojo();	
		pojo1.setSecondary(curentUser);
		pojo1.setAmount(amount);
		pojo1.setMode("Transfer");
		pojo1.setTransactionTypes("CREDIT");
		pojo1.setTimeInMillis(time);
		pojo1.setClosingBalance(receiverBalance);
		pojo1.setAccountNumber(receiver);
		pojo1.setCustomerId(receiverId);    
		pojo1.setReferenceId(referenceId);
		pojo1.setStatus("success");

		load.updateTransactionDetails(pojo1);
		
		Storage.VALUES.setBasicData();
		
	}
	
	
	private long getUserId() {
		return Storage.VALUES.getUserId();
	}
	
	private long getAccountNumber() {
		return Storage.VALUES.getAccountNumber();
	}
	
	private boolean isSenderValid(long userId,long accountNumber,double amount) throws CustomException{

		Accounts_pojo userAccountPojo=Storage.VALUES.getAccountDetails().get(userId).get(accountNumber);
		CustomerPojo userCustomerDetails=Storage.VALUES.getCustomerDetails().get(userId);

		if(userAccountPojo.getStatus().equalsIgnoreCase("active")) {
			if(userAccountPojo.getBalance()>amount) {
				if(userCustomerDetails.getStatus().equalsIgnoreCase("active")) {
					if(amount>0) {
						return true;
					}else {
						throw new CustomException("The amount enterd is in correct");
					}
				}else {
					throw new CustomException("Your User account is inactive");
				}
			}else {
				throw new CustomException("Your balance is insufficient to make deposit");
			}
		}else {
			throw new CustomException("Your account is inactive");
		}
	}
	
	private boolean isReceiverValid(long receiverUserId,long receiverAccountNumber) throws CustomException{
		Accounts_pojo receiverAccountPojo=Storage.VALUES.getAccountDetails().get(receiverUserId).get(receiverAccountNumber);
		CustomerPojo receiverCustomerDetails=Storage.VALUES.getCustomerDetails().get(receiverUserId);

		if(receiverAccountPojo.getStatus().equalsIgnoreCase("active")) {
			if(receiverCustomerDetails.getStatus().equalsIgnoreCase("active")) {
				return true;
			}else {
				throw new CustomException("Reciever Customer account is inactive");
			}
		} else {
			throw new CustomException("Reciever Bank account is inactive");
		}
	}

	public Map<Long,Map<String,TransactionPojo>> getRecentTransaction(long customerId,Long...accountNumber) throws CustomException {
		return load.getTransactions(customerId,accountNumber);
	}
	
	public Map<String, RequestPojo> getRequestMap(long userId) throws CustomException {
		Storage.VALUES.setPendingRequestDetails();
		Map<String, RequestPojo> map=Storage.VALUES.getPendingRequestDetails();
		Map<String, RequestPojo> returnMap=new LinkedHashMap<>();
		for(Map.Entry<String, RequestPojo> element:map.entrySet()) {
			if(element.getValue().getCustomerId()==userId) {
				returnMap.put(element.getKey(), element.getValue());
			}
		}
		return returnMap;
	}
	
	public String millisToString(long time) {
		ZoneId zoneId=ZoneId.systemDefault();
		return Instant.ofEpochMilli(time).atZone(zoneId).toString();
		
	}
	
	public boolean isAccountValidWithId(long userId,long accountNumber) {
		if(Storage.VALUES.getuserSpecificAccounts(userId).get(accountNumber).getStatus().equalsIgnoreCase("active")) {
			return true;
		}
		return false;
	}
	
	public boolean isAccountValid(long accountNumber) throws CustomException {

		if(load.isAccountNumberpresent(accountNumber)) {
			return true;
		}
		return false;		
	}

	public void accountActiveRequest(long accountNumber, StringBuilder message) throws CustomException {
		long time=System.currentTimeMillis();
		ActivityPojo pojo=new ActivityPojo();	
		pojo.setAccountNumber(accountNumber);
		pojo.setMessage(message.toString());
		pojo.setRequestedTime(time);
		pojo.setStatus("PENDING");
		load.postAccountActiveRequest(pojo);
	}
}
