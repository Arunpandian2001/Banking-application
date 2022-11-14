package login;

import DBLoadDriver.PersistentLayer;
import customexception.CustomException;
import interfaces.PersistentLayerPathway;
import superclass.Storage;

public class LoginLayer {
	
	PersistentLayerPathway load=new PersistentLayer();
	
	
	
	public boolean isAccountAvailable(long id,String password) throws CustomException {
		if(Storage.VALUES.getUserDetails()==null) {
			Storage.VALUES.setUserDetails();
		}

		if(Storage.VALUES.getUserDetails().containsKey(id)) {
			if(( Storage.VALUES.getUserDetails().get(id).getPassword().equals(password))) {
				return true;
			}else {
				throw new CustomException("Entered password id is invalid");
			}
		}else {
			throw new CustomException("Entered User id is invalid");
		}
	}
	
	public boolean isCustomer(long userId) {
		if(Storage.VALUES.getUserDetails().get(userId).getRole().equalsIgnoreCase("customer")) {
			return true;
		}
		return false;
	}
	
	public boolean isActive(long userId) throws CustomException {
		Storage.VALUES.setCustomerDetails();
		if(Storage.VALUES.getCustomerDetails().get(userId).getStatus().equalsIgnoreCase("active")) {
			return true;
		}
		return false;
	}
	
}