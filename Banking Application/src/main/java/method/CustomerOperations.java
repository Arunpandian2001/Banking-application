package method;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import superclass.User;

public class CustomerOperations extends User{
	
	private PersistentLayerPathway load=new PersistentLayer();
	private InputChecks inputCheck=new InputChecks();
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

	public double balance(long userId,long accountNumber) throws CustomException {
		return Storage.VALUES.getuserSpecificAccounts(userId).get(accountNumber).getBalance();
	}

	public void toDeposit(long userId,long accountNumber, double amount) throws CustomException {
		Accounts_pojo currentUser=Storage.VALUES.getAccountList().get(accountNumber);
		Map<Long, Map<Long, Accounts_pojo>> map=Storage.VALUES.getAccountDetails();
		double balance;

		if(isSenderValid(userId,accountNumber,amount)) {
			load.depositUpdate(amount,accountNumber);
			balance=load.getCurrentBalance(accountNumber);
			map.get(userId).get(accountNumber).setBalance(balance);
			Storage.VALUES.getAccountList().get(accountNumber).setBalance(balance);
			currentUser.setBalance(balance);
			updateDeposit(amount,getTime(),currentUser);
		}

	}
	public void toWithdraw(long userId,long accountNumber,double amount) throws CustomException {
		Accounts_pojo currentUser=Storage.VALUES.getAccountList().get(accountNumber);
		Map<Long, Map<Long, Accounts_pojo>> map=Storage.VALUES.getAccountDetails();
		double balance;
		if(isSenderValid(userId,accountNumber,amount)) {
			load.withdrawUpdate(amount,accountNumber);
			balance=load.getCurrentBalance(accountNumber);
			map.get(userId).get(accountNumber).setBalance(balance);
			Storage.VALUES.getAccountList().get(accountNumber).setBalance(balance);
			currentUser.setBalance(balance);
			updateWithdraw(amount,getTime(),currentUser);
		}
	}
	


	private void updateWithdraw(double amount, long time,Accounts_pojo userPojo) throws CustomException {
		TransactionPojo pojo = new TransactionPojo();	
		String referenceId="AP"+time;
		pojo.setAmount(amount);
		pojo.setTransactionTypes("DEBIT");
		pojo.setTimeInMillis(time);
		pojo.setClosingBalance(userPojo.getBalance());
		pojo.setMode("Withdraw");
		pojo.setAccountNumber(userPojo.getAccountNumber());
		pojo.setCustomerId(userPojo.getCustomerId());
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
	
	private void updateDeposit( double amount, long time,Accounts_pojo userPojo) throws CustomException {
		TransactionPojo pojo = new TransactionPojo();
		String referenceId="AP"+time;
		pojo.setAmount(amount);
		pojo.setMode("Deposit");
		pojo.setTransactionTypes("CREDIT");
		pojo.setTimeInMillis(time);
		pojo.setClosingBalance(userPojo.getBalance());
		pojo.setAccountNumber(userPojo.getAccountNumber());
		pojo.setCustomerId(userPojo.getCustomerId());
		pojo.setStatus("Success");
		pojo.setReferenceId(referenceId);

		load.updateSelfTransactionDetails(pojo);

		Storage.VALUES.setBasicData();
	}
	
	public void transferAmount(long currentUserAccountNumber,long receiverAccountNumber, double amount, String password) throws CustomException {
		
		Accounts_pojo currentUser=Storage.VALUES.getAccountList().get(currentUserAccountNumber);
		Accounts_pojo receiverPojo=Storage.VALUES.getAccountList().get(receiverAccountNumber);
		long receiverUserId=receiverPojo.getCustomerId();
		long userId=receiverPojo.getCustomerId();
		double balance;
		Map<Long, Map<Long, Accounts_pojo>> map=Storage.VALUES.getAccountDetails();
		
		if((currentUserAccountNumber!=receiverAccountNumber)) {
			if(isSenderValid(userId,currentUserAccountNumber,amount) && isReceiverValid(receiverUserId,receiverAccountNumber)) {
				if(password.equals(Storage.VALUES.getUserDetails().get(userId).getPassword()) ){

					load.depositUpdate(amount,receiverAccountNumber);
					balance=load.getCurrentBalance(receiverAccountNumber);
					map.get(receiverUserId).get(receiverAccountNumber).setBalance(balance);
					Storage.VALUES.getAccountList().get(receiverAccountNumber).setBalance(balance);
					receiverPojo.setBalance(balance);

					load.withdrawUpdate(amount,currentUserAccountNumber);
					balance=load.getCurrentBalance(currentUserAccountNumber);
					map.get(userId).get(currentUserAccountNumber).setBalance(balance);
					Storage.VALUES.getAccountList().get(currentUserAccountNumber).setBalance(balance);;
					currentUser.setBalance(balance);
					updateTransaction(amount,getTime(),receiverPojo,currentUser);
				}else {
					throw new CustomException("The password entered is invalid");
				}
			}
		}else {
			throw new CustomException("The entered account number is invalid");
		}
		
		
	}
	
	private void updateTransaction(double amount, long time,Accounts_pojo receiverPojo,Accounts_pojo userPojo) throws CustomException {
		
		String referenceId="AP"+time;

		TransactionPojo pojo = new TransactionPojo();	
		pojo.setSecondary(receiverPojo.getAccountNumber());
		pojo.setAmount(amount);
		pojo.setTransactionTypes("DEBIT");
		pojo.setMode("Transfer");
		pojo.setTimeInMillis(time);
		pojo.setClosingBalance(userPojo.getBalance());
		pojo.setAccountNumber(userPojo.getAccountNumber());
		pojo.setCustomerId(userPojo.getCustomerId());
		pojo.setReferenceId(referenceId);
		pojo.setStatus("success");

		load.updateTransactionDetails(pojo);
		
		TransactionPojo pojo1 = new TransactionPojo();	
		pojo1.setSecondary(userPojo.getCustomerId());
		pojo1.setAmount(amount);
		pojo1.setMode("Transfer");
		pojo1.setTransactionTypes("CREDIT");
		pojo1.setTimeInMillis(time);
		pojo1.setClosingBalance(receiverPojo.getBalance());
		pojo1.setAccountNumber(receiverPojo.getAccountNumber());
		pojo1.setCustomerId(receiverPojo.getCustomerId());    
		pojo1.setReferenceId(referenceId);
		pojo1.setStatus("success");

		load.updateTransactionDetails(pojo1);
		
		Storage.VALUES.setBasicData();
		
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

	public void accountActiveRequest(String accountNumber, StringBuilder message) throws CustomException {
		if(accountNumber.isEmpty()) {
			throw new CustomException("Please enter the account number");
		}
		long time=System.currentTimeMillis();
		ActivityPojo pojo=new ActivityPojo();	
		pojo.setAccountNumber(Long.parseLong(accountNumber));
		pojo.setMessage(message.toString());
		pojo.setRequestedTime(time);
		pojo.setStatus("PENDING");
		load.postAccountActiveRequest(pojo);
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

	public void changePassword(long userId, String oldPassword,String newPassword, String reEnteredPassword) throws CustomException {
		UserPojo pojo=Storage.VALUES.getUserDetails().get(userId);
		if(pojo.getPassword().matches(oldPassword)) {
			if(newPassword.matches(reEnteredPassword)) {
				pojo.setPassword(reEnteredPassword);
				load.updatePassword(pojo);
			}else {
				throw new CustomException("The re entered password mismatches");
			}
		}else {
			throw new CustomException("The entered old password is incorrect");
		}
	}
}
