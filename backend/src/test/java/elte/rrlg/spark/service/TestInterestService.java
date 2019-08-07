package elte.rrlg.spark.service;

import elte.rrlg.spark.AuthenticatedUser;
import elte.rrlg.spark.model.User;
import elte.rrlg.spark.model.Interest;
import elte.rrlg.spark.repository.UserRepository;
import elte.rrlg.spark.util.TestGlobalMocks;
import elte.rrlg.spark.repository.InterestRepository;
import elte.rrlg.spark.repository.MatchRepository;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class TestInterestService {

  @Mock
  UserRepository userRepositoryMock;
   
  @Mock
  InterestRepository interestRepository;
  
  @Mock
  MatchRepository matchRepository;
  
  @InjectMocks
  InterestService interestService;
  
  @InjectMocks
  MatchService matchService;
  
  @Mock
  AuthenticatedUser authUser1;
  
  private User userNotEmpty;
  private User userNotEmpty2;
  private User userNotEmpyPw;

  
  @Before
  public void setUp() throws Exception {
    
    MockitoAnnotations.initMocks(this);
   
    userNotEmpty = TestGlobalMocks.userNotEmpty;
    userNotEmpty2 = TestGlobalMocks.userNotEmpty2;
    userNotEmpyPw = TestGlobalMocks.userNotEmptyPw;
    
    Mockito.when(authUser1.getUser()).thenReturn(userNotEmpty);
    //Mockito.when(interestRepository.findByFromUserAndToUser(userNotEmpty2, userNotEmpty)).thenReturn(optInterest2);
    
  }
  
  @Test
  public void addInterestNotMatch() {     
      Optional<User> optUser = Optional.of(userNotEmpty2); 
      Mockito.when(userRepositoryMock.findById(userNotEmpty2.getId())).thenReturn(optUser);
      interestService.addInterest(userNotEmpty2.getId());
      //Mockito.when(matchRepository.findByUserAOrUserB(userNotEmpty, userNotEmpty2)).thenReturn(matchUsers);
      Optional<Interest> result = interestRepository.findByFromUserAndToUser(userNotEmpyPw, userNotEmpty2);
      assertEquals(Optional.empty(), result);
  }
  
  @Test
  public void addInterestNoMatchSucces() {   
      Optional<User> optUser = Optional.of(userNotEmpty2); 
      Mockito.when(userRepositoryMock.findById(userNotEmpty2.getId())).thenReturn(optUser);
            
      interestService.addInterest(userNotEmpty2.getId());
      //Mockito.when(matchRepository.findByUserAOrUserB(userNotEmpty, userNotEmpty2)).thenReturn(matchUsers);
      Optional<Interest> result = interestRepository.findByFromUserAndToUser(userNotEmpty, userNotEmpty2);
      assertEquals(Optional.empty(), result);
  }
  
}