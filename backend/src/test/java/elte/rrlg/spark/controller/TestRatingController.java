package elte.rrlg.spark.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import elte.rrlg.spark.AuthenticatedUser;
import elte.rrlg.spark.model.User;
import elte.rrlg.spark.model.Picture;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import elte.rrlg.spark.service.RatingService;
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

@RunWith(SpringRunner.class)
@WebMvcTest(value = RatingController.class, secure = false)
public class TestRatingController {
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private RatingService ratingService;
    
    @Mock
    AuthenticatedUser authUser1;

    @Mock
    BCryptPasswordEncoder encoder;
    
    private List<Picture> picturesEmpty;

    private User userNotEmpty;
    private User result;
    
    @Before
    public void setUp() throws Exception {

      MockitoAnnotations.initMocks(this);

      picturesEmpty = new ArrayList<>();
      userNotEmpty = TestGlobalMocks.userNotEmpty;
      
      result = new User();
      result.setId(userNotEmpty.getId());
      result.setPictures(picturesEmpty);
      
    }
    
    @Test
    @WithMockUser(username = "user@user.hu", roles = {"USER", "ADMIN"})
    public void getNextRateableReturnWithOK() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        String jsonInString = mapper.writeValueAsString(result);
        doReturn(result).when(ratingService).getRandomRatableUser();
        mvc.perform(get("/rating/next").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "user@user.hu", roles = {"USER", "ADMIN"})
    public void getNextRateableReturnWithNull() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        String jsonInString = mapper.writeValueAsString(result);
        doReturn(null).when(ratingService).getRandomRatableUser();
        mvc.perform(get("/rating/next").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
    
    
}
