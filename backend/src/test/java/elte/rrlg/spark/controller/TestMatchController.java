package elte.rrlg.spark.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import elte.rrlg.spark.AuthenticatedUser;
import elte.rrlg.spark.model.User;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import elte.rrlg.spark.model.Match;
import elte.rrlg.spark.model.MatchUnlock;
import elte.rrlg.spark.model.Message;
import elte.rrlg.spark.service.MatchService;
import elte.rrlg.spark.util.TestGlobalMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.doReturn;
import org.mockito.Mock;
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
@WebMvcTest(value = MatchController.class, secure = false)
public class TestMatchController {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MatchService matchService;
    
    @Mock
    AuthenticatedUser authUser1;

    @Mock
    BCryptPasswordEncoder encoder;
    
    private List<Match> matchUsers;
    private List<Match> matchedUsers;
    private List<Match> EmptyMatch;
    private List<Message> messages;
    private List<MatchUnlock> matchUnlocks;
    
    private User userNotEmpty;
    private User userNotEmpty2;
    
    private Match match_one;
    private Match match_two;

    private Message message_one;
    private Message message_two;
    private Message message_three;
    
    @Before
    public void setUp() throws Exception {

      MockitoAnnotations.initMocks(this);

      matchUsers = new ArrayList<>();
      matchedUsers = new ArrayList<>();
      EmptyMatch = new ArrayList<>();
      messages = new ArrayList<>();
      matchUnlocks = new ArrayList<>();

      userNotEmpty = TestGlobalMocks.userNotEmpty;
      userNotEmpty2 = TestGlobalMocks.userNotEmpty2;
      
      match_one = new Match(1,userNotEmpty,userNotEmpty2,messages,10,matchUnlocks);
      
      message_one = new Message(1,match_one,userNotEmpty,null,"message");
      message_two = new Message(2,match_one,userNotEmpty,null,"testing");
      message_three = null;

      matchUsers.add(match_one);
      matchUsers.add(match_two);
      matchedUsers.add(match_one);
      matchedUsers.add(match_two);
      messages.add(message_one);
      
    }
    
    @Test
    @WithMockUser(username = "user@user.hu", roles = {"USER", "ADMIN"})
    public void getMatchesReturnOkWithMatches() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        String jsonInString = mapper.writeValueAsString(matchUsers);
        mvc.perform(get("/match/me").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "user2@user.hu", roles = {"USER", "ADMIN"})
    public void getMatchesReturnEmptyMatch() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        String jsonInString = mapper.writeValueAsString(EmptyMatch);
        mvc.perform(get("/match/me").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "user2@user.hu", roles = {"USER", "ADMIN"})
    public void getMatchesReturnNotFound() throws Exception {
        doReturn(null).when(matchService).getMatches();
        mvc.perform(get("/match/me")).andExpect(status().isNotFound());
    }
    
    @Test
    @WithMockUser(username = "user@user.hu", roles = {"USER", "ADMIN"})
    public void sendMessageReturnOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        String jsonInString = mapper.writeValueAsString(message_one);
        doReturn(message_one).when(matchService).sendMessage(2, "testing");
        mvc.perform(MockMvcRequestBuilders.post("/match/2/message").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "user@user.hu", roles = {"USER", "ADMIN"})
    public void sendMessageReturnNull() throws Exception {
        doReturn(null).when(matchService).sendMessage(2, "testing");
        mvc.perform(MockMvcRequestBuilders.post("/match/2/message")).andExpect(status().isBadRequest());
    }
    
}
