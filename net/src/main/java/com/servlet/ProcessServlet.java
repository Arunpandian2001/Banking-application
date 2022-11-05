package com.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.servletenum.URLEnum;

import customexception.CustomException;
import login.LoginLayer;
import method.AdminOperations;
import method.CustomerOperations;
import pojo.Accounts_pojo;
import pojo.CustomerPojo;
import pojo.RequestPojo;
import pojo.TransactionPojo;
import pojo.UserPojo;
import superclass.Storage;

/**
 * Servlet implementation class ProcessServlet
 */
@WebServlet("/ProcessServlet")

public class ProcessServlet extends HttpServlet {
	private static CustomerPojo customerPojo;
	private static UserPojo userPojo;
	private CustomerOperations customerMethod=new CustomerOperations();
	private AdminOperations adminOperation=new AdminOperations();
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. 
	 */
	public ProcessServlet() {
		// TODO Auto-generated constructor stub
		try {
			Storage.VALUES.setBasicData();
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);	
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);	
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}


	private List<Long> displayAccounts(HttpServletRequest request, HttpServletResponse response) throws CustomException {
		HttpSession session=request.getSession(false);
		long userId=(long)session.getAttribute("userid");
		return customerMethod.getList(userId);
	}
	
	private void forwardRequest(HttpServletRequest request, HttpServletResponse response,String url) throws ServletException, IOException {
		RequestDispatcher req = request.getRequestDispatcher(url);
		req.forward(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("action");
		switch(action) {
		case "logout":{

			response.sendRedirect(URLEnum.LOGOUT.getURL());
			break;
		}
		case "Login":{
			long userId=Long.parseLong(request.getParameter("userId"));
			String password=request.getParameter("password");
			LoginLayer login=new LoginLayer();
			HttpSession session=request.getSession(true);
			session.setAttribute("userid", userId);

			try {
				if(login.isAccountAvailable(userId, password)) {
					Storage.VALUES.setUserId(userId);
						if(login.isCustomer(userId)) {
							if(login.isActive(userId)) {
							customerPojo=Storage.VALUES.getCustomerDetails().get(userId);
							session.setAttribute("name",customerPojo.getName());
							forwardRequest(request, response, URLEnum.CUSTOMERLOGIN.getURL());
							break;

						}
						else {
							forwardRequest(request,response,URLEnum.ACTIVITYREQUEST.getURL());
							break;
						}
					}
						else {
							userPojo=Storage.VALUES.getUserDetails().get(userId);
							session.setAttribute("name", userPojo.getName());
							forwardRequest(request, response, URLEnum.ADMINLOGIN.getURL());
							break;

						}
				}

			} catch (CustomException e) {
				request.setAttribute("wrongPassword",e.getMessage());
				forwardRequest(request, response, URLEnum.LOGOUT.getURL());
				e.printStackTrace();
			}
			break;
		}

		case "CustomerProfile":{
			request.setAttribute("userDetails", customerPojo);
			forwardRequest(request, response, URLEnum.CUSTOMERPROFILE.getURL());
			break;
		}
		case "AdminProfile":{
			request.setAttribute("userDetails", userPojo);
			forwardRequest(request, response, URLEnum.ADMINPROFILE.getURL());
			break;
		}
		case "customer":{
			forwardRequest(request, response, URLEnum.CUSTOMER.getURL());
			break;
		}
		case "admin":{
			forwardRequest(request, response, URLEnum.ADMIN.getURL());
			break;
		}
		case"CustomerAccounts": {
			HttpSession session=request.getSession(false);
			long userId=(long)session.getAttribute("userid");
			Map<Long,Accounts_pojo> map=Storage.VALUES.getuserSpecificAccounts(userId);
			request.setAttribute("accountmap", map);
			forwardRequest(request, response, URLEnum.CUSTOMERACCOUNTSDETAILS.getURL());
			break;
		}
		case "ToDeposit":{
			
			try {
				request.setAttribute("accountlist", displayAccounts(request,response));
			} catch (CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardRequest(request, response, URLEnum.TODEPOSIT.getURL());
			break;
		}
		case "deposit":{
			if(!request.getParameter("Accounts").equals("")) {
				long accountNumber=Long.parseLong(request.getParameter("Accounts"));
				Storage.VALUES.setAccountNumber(accountNumber);
				double amount=Double.parseDouble(request.getParameter("amount"));
				try {
					customerMethod.toDeposit(amount);
					request.setAttribute("message", "Deposit successfull");
					request.setAttribute("accountlist", displayAccounts(request,response));
					forwardRequest(request, response, URLEnum.TODEPOSIT.getURL());
				} catch (CustomException e) {
					request.setAttribute("message", e.getMessage());
					forwardRequest(request, response, URLEnum.TODEPOSIT.getURL());

					e.printStackTrace();
				}
			}else {
				request.setAttribute("message","Please select an account");
				forwardRequest(request, response, URLEnum.TODEPOSIT.getURL());
			}
			
			break;
		}
		case "ToWithdraw":{
			try {
				request.setAttribute("accountlist", displayAccounts(request,response));
			} catch (CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardRequest(request, response, URLEnum.TOWITHDRAW.getURL());

			break;
		}
		case "withdraw":{
			if(!request.getParameter("Accounts").equals("")) {
				long accountNumber=Long.parseLong(request.getParameter("Accounts"));
				Storage.VALUES.setAccountNumber(accountNumber);
				double amount=Double.parseDouble(request.getParameter("amount"));
				try {
					customerMethod.toWithdraw(amount);
					request.setAttribute("message", "Withdraw requested");
					request.setAttribute("accountlist", displayAccounts(request,response));
					forwardRequest(request, response, URLEnum.TOWITHDRAW.getURL());

				} catch (CustomException e) {
					request.setAttribute("message", e.getMessage());
					forwardRequest(request, response, URLEnum.TOWITHDRAW.getURL());

					e.printStackTrace();
				}
			}else {
				request.setAttribute("message","Please select an account");
				forwardRequest(request, response, URLEnum.TOWITHDRAW.getURL());
			}
			
			break;
		}
		case "ToTransfer":{
			try {
				request.setAttribute("accountlist", displayAccounts(request,response));
			} catch (CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardRequest(request, response, URLEnum.TOTRANSFER.getURL());

			break;
		}
		case "transfer":{
			if(!request.getParameter("Accounts").equals("")) {
				long userAccountNumber=Long.parseLong(request.getParameter("Accounts"));
				Storage.VALUES.setAccountNumber(userAccountNumber);
				long receiverAccountNumber=Long.parseLong(request.getParameter("receiver"));
				double amount=Double.parseDouble(request.getParameter("amount"));
				String password=(String)request.getParameter("password");
				try {
					customerMethod.transferAmount(receiverAccountNumber, amount, password);
					request.setAttribute("message", "Transfer success");
					request.setAttribute("accountlist", displayAccounts(request,response));
					forwardRequest(request, response, URLEnum.TOTRANSFER.getURL());

				} catch (CustomException e) {
					try {
						request.setAttribute("accountlist", displayAccounts(request,response));
					} catch (CustomException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					request.setAttribute("message", e.getMessage());
					forwardRequest(request, response, URLEnum.TOTRANSFER.getURL());

					e.printStackTrace();
				}
			}else {
				request.setAttribute("message","Please select an account");
				forwardRequest(request, response, URLEnum.TOTRANSFER.getURL());
			}
			
			break;
		}
		case "ToTransactionDetails":{
			HttpSession session=request.getSession(false);
			long userId=(long)session.getAttribute("userid");
			try {
				List<Long> accountNumbers=customerMethod.getList(userId);
				session.setAttribute("accountlist", accountNumbers);
			} catch (CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardRequest(request, response, URLEnum.TRANSACTIONDETAILS.getURL());
			break;
		}
		case "transactiondetails":{
			if(!request.getParameter("Accounts").equals("")) {
				HttpSession session=request.getSession(false);
				long userId=(long)session.getAttribute("userid");
				long accountNumber=Long.parseLong(request.getParameter("Accounts"));
				Map<String,TransactionPojo> map=new LinkedHashMap<>();
				try {
					map=customerMethod.getRecentTransaction(userId,accountNumber).get(userId);
					if(map==null) {
						request.setAttribute("message", "No transaction available");
						forwardRequest(request, response, URLEnum.TRANSACTIONDETAILS.getURL());
						break;

					}
					request.setAttribute("transaction", map);
					forwardRequest(request, response, URLEnum.TRANSACTIONDETAILS.getURL());
					break;
				} catch (CustomException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				request.setAttribute("message","Please select an account");
				forwardRequest(request, response, URLEnum.TRANSACTIONDETAILS.getURL());
			}
				break;

		}
		case "CustomerTransactionRequests":{
			HttpSession session=request.getSession(false);
			long userId=(long)session.getAttribute("userid");
			Map<String, RequestPojo> map=null;
			try {
				map = customerMethod.getRequestMap(userId);
				if(map==null) {
					request.setAttribute("message", "No requests pending");
					forwardRequest(request, response, URLEnum.CUSTOMERTRANSACTIONREQUESTS.getURL());
					break;
				}
				request.setAttribute("requestmap", map);
				forwardRequest(request, response, URLEnum.CUSTOMERTRANSACTIONREQUESTS.getURL());
				break;
			} catch (CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		case "UserInformation":{
			List<CustomerPojo> list=new ArrayList<>();
			try {
				list=adminOperation.getCustomerDetails();
				request.setAttribute("customerdetails", list);
				forwardRequest(request, response, URLEnum.USERINFORMATION.getURL());

			} catch (CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		case "searchuser":{
				long searchId=Long.parseLong(request.getParameter("customerid"));
				List<CustomerPojo> list=new ArrayList<>();
				try {
					list=adminOperation.getCustomerDetails(searchId);
					request.setAttribute("customerdetails", list);
					forwardRequest(request, response, URLEnum.USERINFORMATION.getURL());

				} catch (CustomException e) {
					// TODO Auto-generated catch block
					request.setAttribute("message", e.getMessage());
					forwardRequest(request, response, URLEnum.USERINFORMATION.getURL());
					e.printStackTrace();
				}
				break;
			}

		case "AccountInformation":{
			forwardRequest(request, response, URLEnum.ACCOUNTINFORMATION.getURL());

			break;
		}
		case "accountsearch":{
			if(request.getParameter("accountnumber").isEmpty()) {
				long searchId=Long.parseLong(request.getParameter("customerid"));
				Map<Long,List<Accounts_pojo>> map=new LinkedHashMap<>();

				try {
					map=adminOperation.getAccountDetails(searchId);
					List<Accounts_pojo> list=map.get(searchId);
					request.setAttribute("accountdetails", list);
					forwardRequest(request, response, URLEnum.ACCOUNTINFORMATION.getURL());

				} catch (CustomException e) {
					// TODO Auto-generated catch block
					request.setAttribute("message", e.getMessage());
					forwardRequest(request, response, URLEnum.ACCOUNTINFORMATION.getURL());
					e.printStackTrace();
				}
				break;
			}else {
				long searchId=Long.parseLong(request.getParameter("customerid"));
				long accountNumber=Long.parseLong(request.getParameter("accountnumber"));
				Map<Long,List<Accounts_pojo>> map=new LinkedHashMap<>();

				try {
					map=adminOperation.getAccountDetails(searchId,accountNumber);
					List<Accounts_pojo> list=map.get(searchId);
					request.setAttribute("accountdetails", list);
					forwardRequest(request, response, URLEnum.ACCOUNTINFORMATION.getURL());

				} catch (CustomException e) {
					request.setAttribute("message", e.getMessage());
					forwardRequest(request, response, URLEnum.ACCOUNTINFORMATION.getURL());
					e.printStackTrace();
				
				}
				break;
			}
		}

		case"AdminTransaction":{
			forwardRequest(request, response, URLEnum.ADMINTRANSACTION.getURL());
			break;
		}
		case "searchtransaction":{
			Map<String,TransactionPojo> map=new LinkedHashMap<>();

			if(request.getParameter("accountnumber").isEmpty()) {
				long searchId=Long.parseLong(request.getParameter("customerid"));

				try {
					map=adminOperation.getTransactionDetails(searchId);
					request.setAttribute("transactiondetails", map);
					forwardRequest(request, response, URLEnum.ADMINTRANSACTION.getURL());

				} catch (CustomException e) {
					// TODO Auto-generated catch block
					request.setAttribute("message", e.getMessage());
					forwardRequest(request, response, URLEnum.ADMINTRANSACTION.getURL());
					e.printStackTrace();
				}
				break;
			}else {
				long searchId=Long.parseLong(request.getParameter("customerid"));
				long accountNumber=Long.parseLong(request.getParameter("accountnumber"));

				try {
					map=adminOperation.getTransactionDetails(searchId,accountNumber);
					request.setAttribute("transactiondetails", map);
					forwardRequest(request, response, URLEnum.ADMINTRANSACTION.getURL());

				} catch (CustomException e) {
					request.setAttribute("message", e.getMessage());
					forwardRequest(request, response, URLEnum.ADMINTRANSACTION.getURL());
					e.printStackTrace();
				
				}
				break;
			}
		}
		case "PendingWithdrawRequests":{
			Map<String,RequestPojo>map=null;
			try {
				map=adminOperation.getRequestDetails();
				if(map==null) {
					request.setAttribute("message", "No requests pending");
					forwardRequest(request, response, URLEnum.PENDINGWITHDRAWREQUESTS.getURL());
					break;
				}
				else {
					request.setAttribute("pendingrequestmap", map);
					forwardRequest(request, response, URLEnum.PENDINGWITHDRAWREQUESTS.getURL());
					break;
				}
			} catch (CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		
		
		case "accepttransaction":{
			Map<String,RequestPojo>map=null;
			if(request.getParameter("accountnumber").isEmpty()) {
				long customerId=Long.parseLong(request.getParameter("customerid"));
				String status="Accepted";
				try {
					map=adminOperation.getRequestDetails();
					adminOperation.processRequest(customerId,null, status);
					request.setAttribute("message", "Accepted requests");
					map=adminOperation.getRequestDetails();
					request.setAttribute("pendingrequestmap", map);
					forwardRequest(request, response, URLEnum.PENDINGWITHDRAWREQUESTS.getURL());
					break;
				} catch (CustomException e) {
					try {
						map=adminOperation.getRequestDetails();
					} catch (CustomException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					request.setAttribute("message", e.getMessage());
					request.setAttribute("pendingrequestmap", map);
					forwardRequest(request, response, URLEnum.PENDINGWITHDRAWREQUESTS.getURL());
					
				}
			}else {
				long customerId=Long.parseLong(request.getParameter("customerid"));
				long accountNumber=Long.parseLong(request.getParameter("accountnumber"));
				String status="Accepted";
				try {
					
					adminOperation.processRequest(customerId,null, status,accountNumber);
					request.setAttribute("message", "Accepted requests");
					map=adminOperation.getRequestDetails();
					request.setAttribute("pendingrequestmap", map);
					forwardRequest(request, response, URLEnum.PENDINGWITHDRAWREQUESTS.getURL());

					break;
				} catch (CustomException e) {
					try {
						map=adminOperation.getRequestDetails();
					} catch (CustomException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					request.setAttribute("message", e.getMessage());
					request.setAttribute("pendingrequestmap", map);
					forwardRequest(request, response, URLEnum.PENDINGWITHDRAWREQUESTS.getURL());
					
				}
			}
			break;

		}

		case "rejecttransaction":{
			Map<String,RequestPojo>map=null;
			if(request.getParameter("accountnumber").isEmpty()) {
				long customerId=Long.parseLong(request.getParameter("customerid"));
				String status="Rejected";
				try {
					map=adminOperation.getRequestDetails();
					adminOperation.processRequest(customerId,null, status);
					request.setAttribute("message", "Rejected requests");
					map=adminOperation.getRequestDetails();
					request.setAttribute("pendingrequestmap", map);
					forwardRequest(request, response, URLEnum.PENDINGWITHDRAWREQUESTS.getURL());

					break;
				} catch (CustomException e) {
					try {
						map=adminOperation.getRequestDetails();
					} catch (CustomException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					request.setAttribute("message",e.getMessage());
					request.setAttribute("pendingrequestmap", map);
					forwardRequest(request, response, URLEnum.PENDINGWITHDRAWREQUESTS.getURL());

				}
				break;
			}else {
				long customerId=Long.parseLong(request.getParameter("customerid"));
				long accountNumber=Long.parseLong(request.getParameter("accountnumber"));
				String status="Rejected";
				try {
					map=adminOperation.getRequestDetails();
					adminOperation.processRequest(customerId, null,status,accountNumber);
					request.setAttribute("message", "Rejected requests");
					map=adminOperation.getRequestDetails();
					request.setAttribute("pendingrequestmap", map);
					forwardRequest(request, response, URLEnum.PENDINGWITHDRAWREQUESTS.getURL());
					break;
				} catch (CustomException e) {
					try {
						map=adminOperation.getRequestDetails();
					} catch (CustomException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					request.setAttribute("message",e.getMessage());
					request.setAttribute("pendingrequestmap", map);
					forwardRequest(request, response, URLEnum.PENDINGWITHDRAWREQUESTS.getURL());

				}
			}
			break;
		}

		case "acceptintable":{
				Map<String,RequestPojo>map=null;
	
				String referenceId=request.getParameter("referenceid");
				String status="Accepted";
				try {
					map=adminOperation.getRequestDetails();
					adminOperation.processRequest(0,referenceId, status);
					request.setAttribute("message", "Accepted requests");
					map=adminOperation.getRequestDetails();
					request.setAttribute("pendingrequestmap", map);
					forwardRequest(request, response, URLEnum.PENDINGWITHDRAWREQUESTS.getURL());
	
					break;
				} catch (CustomException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

		}

		case "rejectintable":{
				Map<String,RequestPojo>map=null;
	
				String referenceId=request.getParameter("referenceid");
				String status="Rejected";
				try {
					map=adminOperation.getRequestDetails();
					adminOperation.processRequest(0,referenceId, status);
					request.setAttribute("message", "Rejected requests");
					map=adminOperation.getRequestDetails();
					request.setAttribute("pendingrequestmap", map);
					forwardRequest(request, response, URLEnum.PENDINGWITHDRAWREQUESTS.getURL());
	
					break;
				} catch (CustomException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
	
			}
		case"ToCreateCustomer":{
			
			forwardRequest(request,response,URLEnum.CREATECUSTOMER.getURL());
				
				break;
			}
		case"CreateCustomer":{
			
			String name=request.getParameter("name");
			String dob=request.getParameter("dob");
			String mobile=request.getParameter("mobile");
			String email=request.getParameter("email");
			String address=request.getParameter("address");
			String aadhar=request.getParameter("aadhar");
			String pan=request.getParameter("pan");
			String accountType=request.getParameter("account type");
			String branch=request.getParameter("branch");
			double balance=Double.parseDouble(request.getParameter("minimum balance"));
			CustomerPojo customerPojo=new CustomerPojo();
			Accounts_pojo accountPojo =new Accounts_pojo();
			
			try {
				if(adminOperation.checkCredentials(mobile,email,aadhar,pan)) {
					customerPojo.setName(name);
					customerPojo.setDob(dob);
					customerPojo.setMobile(mobile);
					customerPojo.setEmail(email);
					customerPojo.setAddress(address);
					customerPojo.setAadhar(Long.parseLong(aadhar));
					customerPojo.setPanNumber(pan);
					accountPojo.setAccountType(accountType);
					accountPojo.setBranch(branch);
					accountPojo.setBalance(balance);
					accountPojo.setStatus("ACTIVE");
					customerPojo=adminOperation.createCustomer(customerPojo);
					accountPojo.setCustomerId(customerPojo.getCustomerId());
					accountPojo=adminOperation.createAccount(accountPojo);
					Storage.VALUES.setBasicData();
					request.setAttribute("message","Customer created successfully \n User id: "+customerPojo.getCustomerId()+
							"\n  Password :"+customerPojo.getPassword()+ "\n Account number : "+accountPojo.getAccountNumber());
					forwardRequest(request,response,URLEnum.CREATECUSTOMER.getURL());
					
				}
			} catch (CustomException e) {
				request.setAttribute("errormessage", e.getMessage());
				forwardRequest(request,response,URLEnum.CREATECUSTOMER.getURL());
				e.printStackTrace();
			}
			
				break;
			}
		case"ToCreateAccount":{
				forwardRequest(request,response,URLEnum.CREATEACCOUNT.getURL());
	
				break;
			}
		case "CreateAccount":{
				String customerId=request.getParameter("customerid");
				String accountType=request.getParameter("account type");
				String branch=request.getParameter("branch");
				double balance=Double.parseDouble(request.getParameter("minimum balance"));
				Accounts_pojo accountPojo =new Accounts_pojo();
				try {
						accountPojo.setCustomerId(Long.parseLong(customerId));
						accountPojo.setAccountType(accountType);
						accountPojo.setBranch(branch);
						accountPojo.setBalance(balance);
						accountPojo.setStatus("ACTIVE");
						accountPojo=adminOperation.createAccount(accountPojo);
						Storage.VALUES.setBasicData();
						request.setAttribute("message","Account created successfully \n Account number : "+accountPojo.getAccountNumber());
						forwardRequest(request,response,URLEnum.CREATEACCOUNT.getURL());
				} catch (CustomException e) {
					request.setAttribute("errormessage", e.getMessage());
					forwardRequest(request,response,URLEnum.CREATEACCOUNT.getURL());
					e.printStackTrace();
				}
				break;	
			}
		
		case"":{
			
				break;
			}
		}

	}

}
