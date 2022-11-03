package com.servletenum;

public enum URLEnum {
	ADMIN("JSP/Admin.jsp"),
	CUSTOMER("JSP/Customer.jsp"),
	ADMINLOGIN("JSP/AdminFrame.jsp"),
	CUSTOMERLOGIN("JSP/CustomerFrame.jsp"),
	ADMINPROFILE("JSP/ViewAdminProfile.jsp"),
	CUSTOMERPROFILE("JSP/ViewCustomerProfile.jsp"),
	CUSTOMERACCOUNTSDETAILS("JSP/CustomerAccountDetails.jsp"),
	USERINFORMATION("JSP/UserInformation.jsp"),
	ACCOUNTINFORMATION("JSP/AccountInformation.jsp"),
	ADMINTRANSACTION("JSP/AdminTransaction.jsp"),
	PENDINGWITHDRAWREQUESTS("JSP/AdminRequests.jsp"),
	TODEPOSIT("JSP/DepositFrame.jsp"),
	TOWITHDRAW("JSP/Withdraw.jsp"),
	TOTRANSFER("JSP/Transfer.jsp"),
	TRANSACTIONDETAILS("JSP/Transaction.jsp"),
	CUSTOMERTRANSACTIONREQUESTS("JSP/CustomerTransactionRequest.jsp"),	
	LOGOUT("JSP/Login.jsp");
	String url;
	
	
	URLEnum(String string) {
		this.url=string;
	}


	public String getURL() {
		return this.url;
	}
	
}
