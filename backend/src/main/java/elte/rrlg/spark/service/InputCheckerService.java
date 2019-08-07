package elte.rrlg.spark.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher; 
import java.util.regex.Pattern; 

@Service
public class InputCheckerService {

    // Email validation
    public boolean isValidMail(String email) {
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 

    }
    
    // password validation
    public boolean isValidPw(String password) {
        //String result = "";
        boolean result = false;
        boolean upper = false;
        boolean lower = false;
        boolean number = false;
        if (password.length() < 8) {
            //result += "must be at least 8 characters long \n";
            return result;
        } else {
            for (char c : password.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    upper = true;
                } else if (Character.isLowerCase(c)) {
                    lower = true;
                } else if (Character.isDigit(c)) {
                    number = true;
                }
            }
            if (!upper) {
                //result += "must contain at least one uppercase character \n";
                return result;
            } else if (!lower) {
                //result += "must contain at least one lowercase character \n";
                return result;
            } else if (!number) {
                //result += "must contain at least one number \n";
                return result;
            } else if( upper && lower && number) {
                result = true; 
            } 
        }
        return result;       
    }

    // Username validation
    public boolean isValidUser(String username) {
        
        if(username != null && username.length() > 4 ) {
            if(username.matches("(\\b[A-Z]{1}[a-z]+)(( )([A-Z]{1}[a-z]+\\b))+")) {
                return true;
            }
        }
        return false;
    }




}