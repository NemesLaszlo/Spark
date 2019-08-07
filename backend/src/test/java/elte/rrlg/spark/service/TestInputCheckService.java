package elte.rrlg.spark.service;

import elte.rrlg.spark.model.User;
import elte.rrlg.spark.util.TestGlobalMocks;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class TestInputCheckService {

  @InjectMocks
  InputCheckerService inputCheckService;

  private User userNotEmpty;

  
  @Before
  public void setUp() throws Exception {
    
    MockitoAnnotations.initMocks(this);
   
    userNotEmpty = TestGlobalMocks.userNotEmpty;
  }
  
  @Test
  public void isvalidMailEmptyString() {
       boolean result = inputCheckService.isValidMail("");
       assertEquals(false,result);
  }
  
  @Test
  public void isvalidMailWrongPeriods() {
       boolean result = inputCheckService.isValidMail("test.test.hu");
       assertEquals(false,result);
  }
  
  @Test
  public void isvalidMailWrongRegion() {
       boolean result = inputCheckService.isValidMail("test@test.c");
       assertEquals(false,result);
  }
  
  @Test
  public void isvalidMailCorrectFormation() {
       boolean result = inputCheckService.isValidMail("test@test.com");
       assertEquals(true,result);
  }
  
  @Test
  public void isvalidMailCorrectFormationFromUser() {
       boolean result = inputCheckService.isValidMail(userNotEmpty.getEmail());
       assertEquals(true,result);
  }
  
  @Test
  public void isvaliPwEmptyString() {
       boolean result = inputCheckService.isValidPw("");
       assertEquals(false,result);
  }
  
  @Test
  public void isvaliPwWrongLength() {
       boolean result = inputCheckService.isValidPw("Test2");
       assertEquals(false,result);
  }
  
  @Test
  public void isvaliPwNoUpperCharacter() {
       boolean result = inputCheckService.isValidPw("testpassword123");
       assertEquals(false,result);
  }
  
  @Test
  public void isvaliPwNoLowerCharacter() {
       boolean result = inputCheckService.isValidPw("TESTPASSWORD123");
       assertEquals(false,result);
  }
  
  @Test
  public void isvaliPwNoNumber() {
       boolean result = inputCheckService.isValidPw("Testpassword");
       assertEquals(false,result);
  }
  
  @Test
  public void isvaliPwCorrenct() {
       boolean result = inputCheckService.isValidPw("TestPassword1234");
       assertEquals(true,result);
  }
  
  @Test
  public void isValidUserEmptyString() {
       boolean result = inputCheckService.isValidUser("");
       assertEquals(false,result);
  }
  
  @Test
  public void isValidUserWrongLength() {
       boolean result = inputCheckService.isValidUser("Edd");
       assertEquals(false,result);
  }
  
  @Test
  public void isValidUserNonUpperStartChar() {
       boolean result = inputCheckService.isValidUser("adam Smith");
       assertEquals(false,result);
  }
  
  @Test
  public void isValidUserUpperStartChar() {
       boolean result = inputCheckService.isValidUser("Adam Smith");
       assertEquals(true,result);
  }
  
  @Test
  public void isValidUserTooMuchSeparationChar() {
       boolean result = inputCheckService.isValidUser("Adam  Smith");
       assertEquals(false,result);
  }
  
}