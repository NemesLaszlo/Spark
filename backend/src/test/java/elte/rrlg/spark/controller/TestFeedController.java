package elte.rrlg.spark.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import elte.rrlg.spark.model.User;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import elte.rrlg.spark.model.Feed;
import elte.rrlg.spark.model.Match;
import elte.rrlg.spark.model.MatchUnlock;
import elte.rrlg.spark.model.Message;
import elte.rrlg.spark.service.FeedService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.doReturn;
import org.mockito.MockitoAnnotations;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(FeedController.class)
public class TestFeedController {
    
@Autowired
private MockMvc mvc;

@MockBean
private FeedService feedService;

private List<Match> matchUsers;
private List<Message> messages;
private List<Match> newMatchesList;
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
    matchUnlocks = new ArrayList<>();

    match_one = new Match(1,userNotEmpty,userNotEmpty2,messages,10,matchUnlocks);
    match_two = new Match(2,userNotEmpty2,userNotEmpty,messages,6,matchUnlocks);

    matchUsers.add(match_one);
    matchUsers.add(match_two);
    newMatchesList.add(match_one);
    newMatchesList.add(match_two);

    feed = new Feed();
    feed.setNewMessages(newMatchesList);
    feed.setNewMatches(matchUsers);

    }
    
    @Test
    @WithMockUser(username = "user@user.hu", roles = {"USER", "ADMIN"})
    public void getFeedReturnBadRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        String jsonInString = mapper.writeValueAsString(feed);
        mvc.perform(get("/feed/").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
   
    
    @Test
    @WithMockUser(username = "user@user.hu", roles = {"USER", "ADMIN"})
    public void getFeedReturnBadRequestWrongParameter() throws Exception {
        doReturn(feed).when(feedService).getFeed("test");
        mvc.perform(get("/feed/")).andExpect(status().isBadRequest());
    }

}
