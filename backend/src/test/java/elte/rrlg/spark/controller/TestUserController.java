package elte.rrlg.spark.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import elte.rrlg.spark.AuthenticatedUser;
import elte.rrlg.spark.model.User;
import elte.rrlg.spark.service.UserService;
import elte.rrlg.spark.util.TestGlobalMocks;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.doReturn;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class TestUserController{

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    
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
   
    userNotEmpty = TestGlobalMocks.userNotEmpty;
    userNotEmpyPw = TestGlobalMocks.userNotEmptyPw;
    
    String password = userNotEmpyPw.getPassword();
    Mockito.when(authUser1.getUser()).thenReturn(userNotEmpty);
    Mockito.when(encoder.encode(userNotEmpty.getPassword())).thenReturn(password);
    
  }
    
    @Test
    @WithMockUser(username = "user@user.hu", roles = {"USER", "ADMIN"})
    public void testGetUserReturnOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        String jsonInString = mapper.writeValueAsString(userNotEmpty);
        Optional<User> optUser = Optional.of(userNotEmpty); 
        doReturn(optUser).when(userService).getUser();
        mvc.perform(get("/user/me").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    
    @Test(expected = NullPointerException.class)
    @WithMockUser(username = "user@user.hu", roles = {"USER", "ADMIN"})
    public void testGetUserReturnBadRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        String jsonInString = mapper.writeValueAsString(userNotEmpty);
        Optional<User> optUser = Optional.of(userNull); 
        doReturn(optUser).when(userService).getUser();
        mvc.perform(get("/user/me").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
  
    @Test
    public void registerUserReturnOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        String jsonInString = mapper.writeValueAsString(userNotEmpty);
        doReturn(userNotEmpty).when(userService).registerUser(Mockito.any(User.class));
        mvc.perform(MockMvcRequestBuilders.post("/user/register").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void registerUserReturnBadRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        String jsonInString = mapper.writeValueAsString(userNotEmpty);
        mvc.perform(MockMvcRequestBuilders.post("/user/register").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user@user.hu", roles = {"USER", "ADMIN"})
    public void testLogoutReturnBadRequest() throws Exception {
          ObjectMapper mapper = new ObjectMapper();
          mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
          String jsonInString = mapper.writeValueAsString(userNull);
          mvc.perform(MockMvcRequestBuilders.post("/user/logout").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    
    @Test
    public void testLogin() throws Exception {
          ObjectMapper mapper = new ObjectMapper();
          mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
          String jsonInString = mapper.writeValueAsString(userNotEmpty);
          doReturn(userNotEmpty).when(userService).loginUser();
          mvc.perform(MockMvcRequestBuilders.post("/user/login").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    
    @Test
    public void testLoginReturnBadRequest() throws Exception {
          ObjectMapper mapper = new ObjectMapper();
          mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
          String jsonInString = mapper.writeValueAsString(null);
          mvc.perform(MockMvcRequestBuilders.post("/user/login").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
   
}