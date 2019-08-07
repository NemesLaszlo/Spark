package elte.rrlg.spark.service;

import elte.rrlg.spark.model.User;
import elte.rrlg.spark.model.Match;
import elte.rrlg.spark.model.MatchUnlock;
import elte.rrlg.spark.model.Feed;
import elte.rrlg.spark.model.Message;
import elte.rrlg.spark.repository.UserRepository;
import elte.rrlg.spark.util.TestGlobalMocks;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.spy;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestFeedService {
    
  @Mock
  UserRepository userRepositoryMock;

  @InjectMocks
  UserService userService;
  
  @InjectMocks
  FeedService feedService;
  
  private List<Match> matchUsers;
  private List<Match> newMatchesList;
  private List<Message> messages;
  private List<MatchUnlock> matchUnlocks;
  
  private User userNotEmpty;
  private User userNotEmpty2;
  
  private Match match_one;
  private Match match_two;
  
  private Feed feed;
  
  @Before
  public void setUp() throws Exception {
    
    MockitoAnnotations.initMocks(this);

    matchUsers = new ArrayList<>();
    newMatchesList = new ArrayList<>();
    messages = new ArrayList<>();
    matchUnlocks = new ArrayList<>();
   
    userNotEmpty = TestGlobalMocks.userNotEmpty;
    userNotEmpty2 = TestGlobalMocks.userNotEmpty2;
    
    match_one = spy(new Match(1,userNotEmpty,userNotEmpty2,messages,10,matchUnlocks));
    match_two = spy(new Match(2,userNotEmpty2,userNotEmpty,messages,6,matchUnlocks));
    
    matchUsers.add(match_one);
    matchUsers.add(match_two);
    newMatchesList.add(match_one);
    newMatchesList.add(match_two);
    
    feed = spy(new Feed());
    feed.setNewMessages(newMatchesList);
    feed.setNewMatches(matchUsers);
      
  }
  
  @Test
  public void getRefreshRateMaximumRateTest() {
    float result = feedService.getRefreshRate(5, feed);
    assertEquals(5, result, 0.0f);
  }
  
  @Test
  public void getRefreshRateZeroRateTest() {      
    float result = feedService.getRefreshRate(0, feed);
    assertEquals(1, result, 0.0f);
  }
  
  @Test
  public void getFeedWithExceptionValue() {
      Feed result = feedService.getFeed("word");
      assertEquals(10.0f, result.getRefreshRate(), 0.0f);
  }
  
  @Test
  public void getFeedWithSimpleValue() {
      Feed result = feedService.getFeed("20");
      assertEquals(40.0f, result.getRefreshRate(), 0.0f);
  }
  
  @Test
  public void getFeedWithZeroValue() {
      Feed result = feedService.getFeed("0");
      assertEquals(1.0f, result.getRefreshRate(), 0.0f);
  }
      
}
