package elte.rrlg.spark.service;

import elte.rrlg.spark.AuthenticatedUser;
import elte.rrlg.spark.model.User;
import elte.rrlg.spark.repository.UserRepository;
import elte.rrlg.spark.util.TestGlobalMocks;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.doReturn;

import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RunWith(MockitoJUnitRunner.class)
public class TestUserService {

  @Mock
  UserRepository userRepositoryMock;

  @InjectMocks
  UserService userService;
  
  @Mock
  AuthenticatedUser authUser1;
  
  @Mock
  BCryptPasswordEncoder encoder;
    
  private User userNull;
  private User userNotEmpty;
  private User userNotEmpyPw;
  
  @Before
  public void setUp() throws Exception {
    
    MockitoAnnotations.initMocks(this);

    userNotEmpty = TestGlobalMocks.userNotEmpty;//spy(new User(1,"Bela","password","user@user.hu","description",Timestamp.from(Instant.now()),User.Gender.MALE,User.Sexuality.HETEROSEXUAL,User.Role.ROLE_USER,picturesEmpty,interestEmpty,interestedEmpty,matchUsers,matchedUsers,messages,matchUnlocks));
    userNotEmpyPw = TestGlobalMocks.userNotEmptyPw;//spy(new User(1,"Bela","$2a$04$YDiv9c./ytEGZQopFfExoOgGlJL6/o0er0K.hiGb5TGKHUL8Ebn..","user@user.hu","description",Timestamp.from(Instant.now()),User.Gender.MALE,User.Sexuality.HETEROSEXUAL,User.Role.ROLE_USER,picturesEmpty,interestEmpty,interestedEmpty,matchUsers,matchedUsers,messages,matchUnlocks));
   
    
    String password = userNotEmpyPw.getPassword();
    Mockito.when(authUser1.getUser()).thenReturn(userNotEmpty);
    Mockito.when(encoder.encode(userNotEmpty.getPassword())).thenReturn(password);
    
  }
  
  @Test(expected = NullPointerException.class)
  public void registerUserNullPtr() {
      userService.registerUser(userNull);
  }
  
  @Test
  public void registerUserReturnUser() {
      doReturn(userNotEmpty).when(userRepositoryMock).save(userNotEmpty);
      assertEquals(userNotEmpty,userService.registerUser(userNotEmpty));
  }
    
  @Test
  public void getUser() {
        Optional<User> optUser = Optional.of(userNotEmpty); 
        Mockito.when(userRepositoryMock.findById(userNotEmpty.getId())).thenReturn(optUser);
        Optional<User> user = userService.getUser();
        Optional<User> optionalNoEmpty = Optional.of(userNotEmpty);
        assertEquals(optionalNoEmpty,user);
  }

  @Test
  public void getUserEmpty() {
      Optional<User> optionalEmpty = Optional.ofNullable(null);
      assertEquals(optionalEmpty,userService.getUser());
  }
  
  @Test
  public void test_updateAccountNull() {
      assertEquals(null,userService.updateAccount(userNull));
  }
  
  @Test
  public void test_updateProfileNull() {
      assertEquals(null,userService.updateProfile(userNull));
  }
  
  @Test
  public void testLogoutNullUser() {
      assertEquals(null,userService.logout(userNull));
  }

  @Test
  public void testLogout() {
      assertEquals(null,userService.logout(userNotEmpty));
  }
  
}
