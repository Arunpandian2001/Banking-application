package com.util.getvalues;

import java.util.regex.Pattern;

import customexception.CustomException;

public class InputChecks {

	public boolean checkMobile(String mobile) throws CustomException {
		if(Pattern.matches("[\\d]{10}", mobile)) {
			return true;
		}else {
			throw new CustomException("The entered mobile number is invalid");
		}
	}
	
	public boolean checkEmail(String email) throws CustomException {
		if(Pattern.compile("^[^.](.+)@[^.]{1}(.+)[^.@]$").matcher(email).find()) {
			return true;
		}else {
			throw new CustomException("The entered email is invalid");
		}
	}
	
	public boolean checkAadhar(String aadhar) throws CustomException {
		if(Pattern.matches("^\\d{4}\\d{4}\\d{4}$", aadhar)) {
			return true;
		}else {
			throw new CustomException("The entered Aadhar number is invalid");
		}
	}
	
	public boolean checkPanNumber(String pan) throws CustomException {
		if(Pattern.matches("[A-Z]{5}[0-9]{4}[A-Z]{1}", pan)) {
			return true;
		}else {
			throw new CustomException("The entered PAN number is invalid");
		}
	}
	public boolean checkCredentials(String mobile, String email, String aadhar, String pan) throws CustomException {
		if(checkMobile(mobile) && checkEmail(email) && checkAadhar(aadhar) && checkPanNumber(pan)) {
			return true;
		}
		return false;
	}
}
