package elte.rrlg.spark.service;

import elte.rrlg.spark.AuthenticatedUser;
import elte.rrlg.spark.model.User;
import elte.rrlg.spark.model.Match;
import elte.rrlg.spark.model.MatchUnlock;
import elte.rrlg.spark.model.Message;
import elte.rrlg.spark.repository.MatchRepository;
import elte.rrlg.spark.repository.MessageRepository;
import elte.rrlg.spark.repository.UserRepository;
import elte.rrlg.spark.util.TestGlobalMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.spy;

import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class TestMatchService {
    
  @Mock
  UserRepository userRepositoryMock;
  
  @Mock
  MatchRepository matchRepositoryMock;
  
  @Mock
  MessageRepository messageRepository;

  @InjectMocks
  UserService userService;
  
  @InjectMocks
  MatchService matchService;
  
  @Mock
  AuthenticatedUser authUser1;
  
  @Mock
  BCryptPasswordEncoder encoder;
  
  private List<Match> matchUsers;
  private List<Match> matchedUsers;
  private List<Message> messages;
  private List<MatchUnlock> matchUnlocks;
  

  private User userNull;
  private User userNotEmpty;
  private User userNotEmpty2;
  
  private Match match_one;
  private Match match_two;
  
  private Message message_one;
  private Message message_two;
  
    @Before
    public void setUp() throws Exception {

      MockitoAnnotations.initMocks(this);

      matchUsers = new ArrayList<>();
      matchedUsers = new ArrayList<>();
      messages = new ArrayList<>();
      matchUnlocks = new ArrayList<>();

      userNotEmpty = TestGlobalMocks.userNotEmpty;
      userNotEmpty2 = TestGlobalMocks.userNotEmpty2;
      
      userNotEmpty.setMatchUsers(matchUsers);
      userNotEmpty.setMatchedUsers(matchUsers);
      userNotEmpty.setMessages(messages);
      userNotEmpty2.setMessages(messages);

      match_one = spy(new Match(1,userNotEmpty,userNotEmpty2,messages,10,matchUnlocks));
      match_two = spy(new Match(2,userNotEmpty2,userNotEmpty,messages,10,matchUnlocks));
      
      message_one = spy(new Message(1,match_one,userNotEmpty,null,"message"));
      message_two = spy(new Message(2,match_one,userNotEmpty,null,"testing"));

      matchUsers.add(match_one);
      matchUsers.add(match_two);
      matchedUsers.add(match_one);
      matchedUsers.add(match_two);
      messages.add(message_one);
      
    }
    
    @Test
    public void getMatchesOfSimpleTestWithGoodResult() {
        Mockito.when(authUser1.getUser()).thenReturn(userNotEmpty);
        Mockito.when(matchRepositoryMock.findByUserAOrUserB(authUser1.getUser(),authUser1.getUser())).thenReturn(matchUsers);
        List<Match> result = matchService.getMatchesOf(userNotEmpty);
        assertEquals(matchUsers,result );
    }
    
    @Test
    public void getMatchesOfSimpleTestWithEmptyResult() {
        List<Match> result = matchService.getMatchesOf(userNull);
        assertEquals(new ArrayList<>(),result);
    }
    
    @Test
    public void getMatchesWithCorrenctAuthUserResult() {
        Mockito.when(authUser1.getUser()).thenReturn(userNotEmpty);
        Optional<User> optUser = Optional.of(authUser1.getUser()); 
        Mockito.when(userRepositoryMock.findById(authUser1.getUser().getId())).thenReturn(optUser);
        Mockito.when(matchService.getMatchesOf(authUser1.getUser())).thenReturn(matchUsers);
        List<Match> result = matchService.getMatches();
        assertEquals(matchUsers,result);
    }
    
    @Test
    public void getMatchesWithNullResult() {
        Mockito.when(authUser1.getUser()).thenReturn(userNotEmpty);
        List<Match> result = matchService.getMatches();
        assertEquals(null,result);
    }
    
    @Test(expected = NullPointerException.class)
    public void getMatchesWithNullPointerExceptionResult() {
        matchService.getMatches();
    }
    
    @Test
    public void sendMessageTestWithSucces() {
        Mockito.when(authUser1.getUser()).thenReturn(userNotEmpty);
        Optional<User> optUser = Optional.of(authUser1.getUser()); 
        Optional<Match> optMatch = Optional.of(match_one);
        Mockito.when(userRepositoryMock.findById(authUser1.getUser().getId())).thenReturn(optUser);
        Mockito.when(matchRepositoryMock.findById(match_one.getId())).thenReturn(optMatch);
        Message result = matchService.sendMessage(match_one.getId(), "testing");
        assertEquals(match_one,result.getMatch());
    }
    
    @Test(expected = NullPointerException.class)
    public void sendMessageTestWithNllPointerMatch() {
        matchService.sendMessage(match_two.getId(), "testing");
    }
    
}
