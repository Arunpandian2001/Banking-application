package DBLoadDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import customexception.CustomException;
import interfaces.PersistentLayerPathway;
import pojo.Accounts_pojo;
import pojo.CustomerPojo;
import pojo.RequestPojo;
import pojo.TransactionPojo;
import pojo.UserPojo;
import superclass.Storage;

public class PersistentLayer implements PersistentLayerPathway{

	private final String url="jdbc:mysql://localhost/BANKING_SYSTEM";
	private final String user="root";
	private final String password="Root@123";

	private Connection getConnection() throws CustomException{

		try{
			Connection connection=DriverManager.getConnection(url, user, password);
			return connection;
		}catch(SQLException e) {
			throw new CustomException("Exception occured while connection");
		}
	}
	//	LOGIN POJO
	public Map<Long,CustomerPojo> getCustomerMap() throws CustomException{
		String query="SELECT USER_DETAILS.*,CUSTOMER_DETAILS.* FROM USER_DETAILS INNER JOIN CUSTOMER_DETAILS ON"
				+ " USER_DETAILS.ID=CUSTOMER_DETAILS.CUSTOMER_ID";
		return setCustomerMap(query);
	}

	private Map<Long,CustomerPojo> setCustomerMap(String query) throws CustomException{
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			try(ResultSet result=prepStatement.executeQuery()){
				return getCustomerMapResult(result);
			}
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}

	private Map<Long, CustomerPojo> getCustomerMapResult(ResultSet result) throws CustomException{
		Map<Long,CustomerPojo> map=new LinkedHashMap<>();
		try {
			while(result.next()) {
				CustomerPojo pojo=new CustomerPojo();
				pojo.setId(result.getLong(1));
				pojo.setName(result.getString(2));
				pojo.setDob(result.getString(3));
				pojo.setMobile(result.getString(4));
				pojo.setEmail(result.getString(5));
				pojo.setAddress(result.getString(6));
				pojo.setRole(result.getString(7));
				pojo.setPassword(result.getString(8));
				pojo.setCustomerId(result.getLong(9));
				pojo.setAadhar(result.getLong(10));
				pojo.setPanNumber(result.getString(11));
				pojo.setStatus(result.getString(12));
				map.put(pojo.getCustomerId(),pojo);
			}
			return map;
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting result statement",e);
		}
	}

	public Map<Long,UserPojo> getUserMap() throws CustomException{
		String query="select * from USER_DETAILS;";
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			try(ResultSet result=prepStatement.executeQuery()){
				Map<Long, UserPojo> map=new LinkedHashMap<>();
				while(result.next()) {
					UserPojo pojo=new UserPojo();
					pojo.setId(result.getLong(1));	
					pojo.setName(result.getString(2));
					pojo.setDob(result.getString(3));
					pojo.setMobile(result.getString(4));
					pojo.setEmail(result.getString(5));
					pojo.setAddress(result.getString(6));
					pojo.setRole(result.getString(7));
					pojo.setPassword(result.getString(8));
					map.put(pojo.getId(),pojo);
				}
				return map;
			} catch (SQLException e) {
				throw new CustomException("Exception occured while setting result statement",e);
			}

		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}


	//get every accounts in db


	public Map<Long,Map<Long, Accounts_pojo>> getAllAccountsOfUser() throws CustomException {
		String query="SELECT * FROM ACCOUNTS_DETAILS";
		return allAccounts(query);
	}

	private Map<Long, Map<Long, Accounts_pojo>> allAccounts(String query) throws CustomException{
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			try(ResultSet result=prepStatement.executeQuery()){
				return getAccountsResult(result);
			}
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}

	private Map<Long, Map<Long, Accounts_pojo>> getAccountsResult(ResultSet result) throws CustomException{
		Map<Long, Map<Long, Accounts_pojo>> map=new LinkedHashMap<>();
		int i=0;
		try {
			while(result.next()) {
				Accounts_pojo pojo=new Accounts_pojo();
				pojo.setCustomerId(result.getLong(1));
				pojo.setAccountNumber(result.getLong(2));
				pojo.setAccountType(result.getString(3));
				pojo.setBranch(result.getString(4));
				pojo.setBalance(result.getDouble(5));
				pojo.setStatus(result.getString(6));
				if(i>0 && map.containsKey(pojo.getCustomerId())) {
					map.get(pojo.getCustomerId()).put(pojo.getAccountNumber(), pojo);
				}
				else {
					Map<Long,Accounts_pojo> insideMap=new LinkedHashMap<>();
					insideMap.put(pojo.getAccountNumber(),pojo);
					map.put(pojo.getCustomerId(),insideMap);
				}
				++i;
			}
			return map;
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting result statement",e);
		}
	}

	//	get current balance

	public double getCurrentBalance(long accountNumber) throws CustomException {
		String query="SELECT BALANCE FROM ACCOUNTS_DETAILS WHERE ACCOUNT_NUMBER=?";
		return getBalance(query,accountNumber);
	}
	private double getBalance(String query,long accountNumber) throws CustomException{
		double amount=0;
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1, accountNumber);
			try(ResultSet result=prepStatement.executeQuery()){
				while(result.next()) {
					amount=result.getDouble(1);
				}
				return amount;
			}
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}
	//	update balance
	@Override

	public void depositUpdate(double amount,long accountNumber) throws CustomException {
		String query="UPDATE ACCOUNTS_DETAILS SET BALANCE=BALANCE+? WHERE ACCOUNT_NUMBER=?";
		BalanceUpdated(amount,accountNumber,query);
	}

	public void withdrawUpdate(double amount,long accountNumber) throws CustomException {
		String query="UPDATE ACCOUNTS_DETAILS SET BALANCE=BALANCE-? WHERE ACCOUNT_NUMBER=?";
		BalanceUpdated(amount,accountNumber,query);
	}

	private void BalanceUpdated(double amount, long accountNumber, String query) throws CustomException{
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setDouble(1, amount);
			prepStatement.setLong(2, accountNumber);
			prepStatement.execute();
		}catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}

	@Override
	public void updateTransactionDetails(TransactionPojo pojo) throws CustomException {

		String query="INSERT INTO TRANSACTION_DETAILS(CUSTOMER_ID,ACCOUNT_NUMBER,SECONDARY_ACCOUNT,"
				+ "TRANSACTION_TYPE,MODE_OF_TRANSACTION,AMOUNT,TIME,"
				+ "CLOSING_BALANCE,STATUS,REFERENCE_ID) VALUES(?,?,?,?,?,?,?,?,?,?) ";
		transactionUpdate(pojo,query);
	}
	private void transactionUpdate(TransactionPojo pojo, String query) throws CustomException {
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1, pojo.getCustomerId());
			prepStatement.setLong(2, pojo.getAccountNumber());
			prepStatement.setLong(3, pojo.getSecondary());
			prepStatement.setString(4, pojo.getTransactionTypes());
			prepStatement.setString(5, pojo.getMode());
			prepStatement.setDouble(6, pojo.getAmount());
			prepStatement.setLong(7, pojo.getTimeInMillis());
			prepStatement.setDouble(8, pojo.getClosingBalance());
			prepStatement.setString(9, pojo.getStatus());
			prepStatement.setString(10, pojo.getReferenceId());
			prepStatement.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}
	@Override
	public void updateSelfTransactionDetails(TransactionPojo pojo) throws CustomException {

		String query="INSERT INTO TRANSACTION_DETAILS(CUSTOMER_ID,ACCOUNT_NUMBER,TRANSACTION_TYPE,MODE_OF_TRANSACTION,AMOUNT,TIME,"
				+ "CLOSING_BALANCE,STATUS,REFERENCE_ID) VALUES(?,?,?,?,?,?,?,?,?) ";
		selfTransactionUpdate(pojo,query);
	}
	private void selfTransactionUpdate(TransactionPojo pojo, String query) throws CustomException {
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1, pojo.getCustomerId());
			prepStatement.setLong(2, pojo.getAccountNumber());
			prepStatement.setString(3, pojo.getTransactionTypes());
			prepStatement.setString(4, pojo.getMode());
			prepStatement.setDouble(5, pojo.getAmount());
			prepStatement.setLong(6, pojo.getTimeInMillis());
			prepStatement.setDouble(7, pojo.getClosingBalance());
			prepStatement.setString(8, pojo.getStatus());
			prepStatement.setString(9, pojo.getReferenceId());
			prepStatement.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}
	//get all accounts of a user

	@Override
	public Accounts_pojo getAccountPojoQuery(long accountNumber) throws CustomException {
		String query="SELECT * FROM ACCOUNTS_DETAILS WHERE ACCOUNT_NUMBER=?";
		return getUserMap(accountNumber,query);
	}
	private Accounts_pojo getUserMap(long accountNumber, String query) throws CustomException {

		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1, accountNumber);
			try(ResultSet result=prepStatement.executeQuery()){
				return getMapResult(result,accountNumber);
			}
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}
	private Accounts_pojo getMapResult(ResultSet result,long accountNumber) throws CustomException  {
		Accounts_pojo pojo=new Accounts_pojo();

		try {
			while(result.next()) {
				pojo.setCustomerId(result.getLong(1));
				pojo.setAccountNumber(result.getLong(2));
				pojo.setAccountType(result.getString(3));
				pojo.setBranch(result.getString(4));
				pojo.setBalance(result.getDouble(5));
				pojo.setStatus(result.getString(6));
			}
			return pojo;
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting result statement",e);
		}
	}

	public Map<Long, Map<String, TransactionPojo>> getTransactions(long customerId ,Long ... accountNumber) throws CustomException{

		if(accountNumber.length==0) {
			String query="SELECT * FROM TRANSACTION_DETAILS WHERE CUSTOMER_ID=? ORDER BY TIME DESC LIMIT 10;";
			try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
				prepStatement.setLong(1, customerId);
				try(ResultSet result=prepStatement.executeQuery()){
					return getTransactionResult(result);
				}
			} catch (SQLException e) {
				throw new CustomException("Exception occured while setting prepared statement",e);
			}	
		}
		if(Storage.VALUES.getAccountDetails().get(customerId).containsKey(accountNumber[0])) {
			String query="SELECT * FROM TRANSACTION_DETAILS WHERE CUSTOMER_ID=? AND ACCOUNT_NUMBER=? ORDER BY TIME DESC LIMIT 5;";
			try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
				prepStatement.setLong(1, customerId);
				prepStatement.setLong(2, accountNumber[0]);
				try(ResultSet result=prepStatement.executeQuery()){
					return getTransactionResult(result);
				}
			} catch (SQLException e) {
				throw new CustomException("Exception occured while setting prepared statement",e);
			}
		}else {
			throw new CustomException("Account number is invalid");
		}

	}
	private Map<Long, Map<String, TransactionPojo>> getTransactionResult(ResultSet result) throws CustomException {
		Map<Long, Map<String, TransactionPojo>> map=new LinkedHashMap<>();
		int i=0;

		try {
			while(result.next()) {
				TransactionPojo pojo=new TransactionPojo();
				pojo.setReferenceId(result.getString(1));
				pojo.setCustomerId(result.getLong(2));
				pojo.setAccountNumber(result.getLong(3));
				pojo.setSecondary(result.getLong(4));
				pojo.setTransactionId(result.getLong(5));
				pojo.setTransactionTypes(result.getString(6));
				pojo.setMode(result.getString(7));
				pojo.setAmount(result.getDouble(8));
				pojo.setTimeInMillis(result.getLong(9));
				pojo.setClosingBalance(result.getDouble(10));
				pojo.setStatus(result.getString(11));
				if(i>0 && map.containsKey(pojo.getCustomerId())) {
					map.get(pojo.getCustomerId()).put(pojo.getReferenceId(), pojo);
				}
				else {
					Map<String, TransactionPojo> insideMap=new LinkedHashMap<>();
					insideMap.put(pojo.getReferenceId(),pojo);
					map.put(pojo.getCustomerId(),insideMap);
				}
				++i;
			}
			return map;

		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting result statement",e);
		}
	}
	@Override
	public void updateCustomerWithdrawRequestLog(RequestPojo pojo) throws CustomException {
		String query="INSERT INTO TRANSACTION_REQUESTS (CUSTOMER_ID,ACCOUNT_NUMBER,REFERENCE_ID,AMOUNT,REQUESTED_TIME,STATUS,REQUEST_TYPE) VALUES (?,?,?,?,?,?,?)";
		updateWithdrawLog(pojo,query);
	}
	private void updateWithdrawLog(RequestPojo pojo, String query) throws CustomException {
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1, pojo.getCustomerId());
			prepStatement.setLong(2, pojo.getAccountNumber());
			prepStatement.setString(3, pojo.getReferenceId());
			prepStatement.setDouble(4, pojo.getAmount());
			prepStatement.setLong(5, pojo.getRequestedTime());
			prepStatement.setString(6, pojo.getStatus());
			prepStatement.setString(7, pojo.getType());
			prepStatement.execute();
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}	

	}
	@Override
	public void updateRequestStatus(RequestPojo request,String referenceId) throws CustomException {
		String query="UPDATE TRANSACTION_REQUESTS SET STATUS=? , PROCESSED_TIME=? WHERE REFERENCE_ID=?";
		updateStatus(request,referenceId,query);
	}
	private void updateStatus(RequestPojo request,String referenceId, String query) throws CustomException {
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setString(1,request.getStatus());
			prepStatement.setLong(2, request.getProcessdeTime());
			prepStatement.setString(3, referenceId);
			prepStatement.execute();

		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}
	@Override
	public void updateTransactionAfterVerification(TransactionPojo pojo,String referenceId) throws CustomException {
		String query="UPDATE TRANSACTION_DETAILS SET TIME=? , STATUS =? WHERE REFERENCE_ID=?";
		updateAfterVerification(pojo,referenceId,query);
	}
	private void updateAfterVerification(TransactionPojo pojo, String referenceId,String query) throws CustomException {
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1,pojo.getTimeInMillis());
			prepStatement.setString(2, pojo.getStatus());
			prepStatement.setString(3, referenceId);
			prepStatement.execute();
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}

	//get all request table in map
	public Map<String, RequestPojo> getRequestMap() throws CustomException{
		String query="select * from TRANSACTION_REQUESTS  ";
		return getRequestMapProcess(query);
	}

	//get pending request only in table for user
	public Map<String,RequestPojo> getPendingRequestMap() throws CustomException{
		String query="select * from TRANSACTION_REQUESTS where STATUS='Request pending' ";
		return getRequestMapProcess(query);
	}

	@Override
	public Map<String, RequestPojo> getAcceptedRequestMap() throws CustomException {
		String query="select * from TRANSACTION_REQUESTS where STATUS='Accepted' ";
		return getRequestMapProcess(query);
	}


	private Map<String, RequestPojo> getRequestMapProcess(String query) throws CustomException {
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			try(ResultSet result=prepStatement.executeQuery()){
				Map<String,RequestPojo> map=new LinkedHashMap<>();
				try {
					while(result.next()) {
						RequestPojo pojo=new RequestPojo();
						pojo.setCustomerId(result.getLong(1));
						pojo.setAccountNumber(result.getLong(2));
						pojo.setReferenceId((result.getString(3)));
						pojo.setAmount(result.getDouble(4));
						pojo.setRequestedTime(result.getLong(5));
						pojo.setProcessdeTime(result.getLong(6));
						pojo.setStatus(result.getString(7));
						pojo.setType(result.getString(8));
						map.put(pojo.getReferenceId(), pojo);
					}
					return map;
				} catch (SQLException e) {
					throw new CustomException("Exception occured while setting result statement",e);
				}		
			}
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}

	}

	@Override
	public void declinedRequestInTransaction(double amount, String referenceId,long customerId) throws CustomException {
		String query="update TRANSACTION_DETAILS SET CLOSING_BALANCE=CLOSING_BALANCE+? WHERE REFERENCE_ID=? AND CUSTOMER_ID=?";
		updateRequest(amount,referenceId,customerId,query);
	}
	@Override
	public void acceptedRequestInTransaction(double amount, String referenceId, long customerId)
			throws CustomException {
		String query="update TRANSACTION_DETAILS SET CLOSING_BALANCE=CLOSING_BALANCE-? WHERE REFERENCE_ID=? AND CUSTOMER_ID=?";
		updateRequest(amount,referenceId,customerId,query);

	}

	private void updateRequest(double amount,String referenceId,long customerId,String query) throws CustomException {
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setDouble(1, amount);
			prepStatement.setString(2, referenceId);
			prepStatement.setLong(3, customerId);
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}

	public Map<Long,Map<String,RequestPojo>> getRequestDetailsMap() throws CustomException{
		String query="SELECT * FROM TRANSACTION_REQUESTS ";
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			try(ResultSet result=prepStatement.executeQuery()){
				int i=0;
				Map<Long,Map<String,RequestPojo>> map=new LinkedHashMap<>();
				try {
					while(result.next()) {
						RequestPojo pojo=new RequestPojo();
						pojo.setCustomerId(result.getLong(1));
						pojo.setAccountNumber(result.getLong(2));
						pojo.setReferenceId((result.getString(3)));
						pojo.setAmount(result.getDouble(4));
						pojo.setRequestedTime(result.getLong(5));
						pojo.setProcessdeTime(result.getLong(6));
						pojo.setStatus(result.getString(7));
						pojo.setType(result.getString(8));
						if(i>0 && map.containsKey(pojo.getCustomerId())) {
							map.get(pojo.getCustomerId()).put(pojo.getReferenceId(), pojo);
						}
						else {
							Map<String, RequestPojo> innerMap=new LinkedHashMap<>();
							innerMap.put(pojo.getReferenceId(),pojo);
							map.put(pojo.getCustomerId(),innerMap);
						}
						++i;					}
					return map;
				} catch (SQLException e) {
					throw new CustomException("Exception occured while setting result statement",e);
				}
			} 
		}catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}

	public boolean isUserIdpresent(long customerId) throws CustomException {
		String query="SELECT * FROM TRANSACTION_REQUESTS WHERE CUSTOMER_ID=? AND STATUS='Request pending'";
		int i = 0;
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1, customerId);
			try(ResultSet result=prepStatement.executeQuery()){
				while(result.next())
				{
					i=result.getRow();

				}
				if(i==0) {
					throw new CustomException("Customer id is not present in table");

				}
			} 
			return true;
		}catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		} 
	}

	public boolean isAccountNumberpresent(long accountNumber) throws CustomException {
		String query="SELECT * FROM TRANSACTION_REQUESTS WHERE ACCOUNT_NUMBER=? AND STATUS='Request pending'";
		int i=0;
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1, accountNumber);
			try(ResultSet result=prepStatement.executeQuery()){
				while(result.next()) {
					i=result.getRow();
				}
				if(i==0) {
					throw new CustomException("Account number is not present in table");
				}
			} 
			return true;
		}catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		} 
	}
	@Override
	public CustomerPojo createCustomer(CustomerPojo customerPojo) throws CustomException {
		long customerId=0;
		String userQuery="INSERT INTO USER_DETAILS (NAME,DOB,MOBILE,EMAIL,ADDRESS,ROLE,PASSWORD) VALUES(?,?,?,?,?,?,?)";
		String customerQuery="INSERT INTO CUSTOMER_DETAILS (CUSTOMER_ID,AADHAR_NUMBER,PANCARD,STATUS) VALUES (?,?,?,?)";

		try(PreparedStatement prepStatement=getConnection().prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement prepStatement1=getConnection().prepareStatement(customerQuery)){
			prepStatement.setString(1, customerPojo.getName());
			prepStatement.setString(2, customerPojo.getDob());
			prepStatement.setString(3, customerPojo.getMobile());
			prepStatement.setString(4, customerPojo.getEmail());
			prepStatement.setString(5, customerPojo.getAddress());
			prepStatement.setString(6, "CUSTOMER");
			customerPojo.setPassword(customerPojo.getName()+"@123");
			prepStatement.setString(7,customerPojo.getPassword() );
			prepStatement.execute();
			try(ResultSet result=prepStatement.getGeneratedKeys()){
				while(result.next()) {
					customerId=Long.parseLong(result.getString(1));
				}
			}
			customerPojo.setId(customerId);
			customerPojo.setCustomerId(customerId);//
			prepStatement1.setLong(1, customerId);
			prepStatement1.setLong(2, customerPojo.getAadhar());
			prepStatement1.setString(3, customerPojo.getPanNumber());
			prepStatement1.setString(4, "ACTIVE");
			prepStatement1.execute();
		} catch (SQLException e) {
			throw new CustomException("Error occured while setting connection",e);
		}
		

		return customerPojo;
	}
	@Override
	public Accounts_pojo createAccount(Accounts_pojo pojo) throws CustomException {
		long accountNumber=0;
		String accountQuery="INSERT INTO ACCOUNTS_DETAILS (CUSTOMER_ID,ACCOUNT_TYPE,BRANCH,BALANCE,STATUS) VALUES (?,?,?,?,?)";
		try(PreparedStatement prepStatement=getConnection().prepareStatement(accountQuery, PreparedStatement.RETURN_GENERATED_KEYS)){
			prepStatement.setLong(1, pojo.getCustomerId());
			prepStatement.setString(2, pojo.getAccountType());
			prepStatement.setString(3, pojo.getBranch());
			prepStatement.setDouble(4, pojo.getBalance());
			prepStatement.setString(5, pojo.getStatus());
			prepStatement.execute();

			try(ResultSet result=prepStatement.getGeneratedKeys()){
				while(result.next()) {
					accountNumber=Long.parseLong(result.getString(1));
				}
			}
			pojo.setAccountNumber(accountNumber);
		} catch (SQLException e) {
			throw new CustomException("Error occured while setting connection",e);
		}
		return pojo;
	
	}
}
