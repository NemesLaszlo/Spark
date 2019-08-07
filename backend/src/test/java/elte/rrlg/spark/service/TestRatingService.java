package elte.rrlg.spark.service;

import elte.rrlg.spark.AuthenticatedUser;
import elte.rrlg.spark.model.User;
import elte.rrlg.spark.model.Rating;
import elte.rrlg.spark.repository.RatingRepository;
import elte.rrlg.spark.repository.UserRepository;
import elte.rrlg.spark.util.TestGlobalMocks;

import java.util.ArrayList;
import java.util.List;
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
public class TestRatingService {
    
    @Mock
    UserRepository userRepositoryMock;
    
    @Mock
    RatingRepository ratingRepository;

    @InjectMocks
    UserService userService;
    
    @InjectMocks
    RatingService ratingService;

    @Mock
    AuthenticatedUser authUser1;

    @Mock
    BCryptPasswordEncoder encoder;

    private List<Rating> ratedUsers;
    private List<Rating> raterUsers;
    private List<Rating> ratedUsers_forSecoundUser;

    private User userNotEmpty;
    private User userNotEmpty2;

     Rating rating;
     Rating rating2;

    @Before
    public void setUp() throws Exception {

    MockitoAnnotations.initMocks(this);

    ratedUsers = new ArrayList<>();
    ratedUsers_forSecoundUser = new ArrayList<>();
    raterUsers = new ArrayList<>();

    userNotEmpty = TestGlobalMocks.userNotEmpty;
    userNotEmpty2 = TestGlobalMocks.userNotEmpty2;
    
    rating = new Rating(1, userNotEmpty, userNotEmpty2, 3, 3, 3);
    rating2 = new Rating(2, userNotEmpty2, userNotEmpty, 5, 5, 5);
    
    
    }
    
    @Test
    public void getUsersWithinRatingWithCorrectZeroResult() {
        ratedUsers.add(rating);
        raterUsers.add(rating2);

        userNotEmpty.setRatedUsers(ratedUsers);
        userNotEmpty.setRaterUsers(raterUsers);

        ratedUsers_forSecoundUser.add(rating2);
        userNotEmpty2.setRatedUsers(ratedUsers_forSecoundUser);
        
        Mockito.when(authUser1.getUser()).thenReturn(userNotEmpty);
        Optional<User> optUser = Optional.of(authUser1.getUser()); 
        Mockito.when(userRepositoryMock.findById(authUser1.getUser().getId())).thenReturn(optUser);
        List users = new ArrayList<>();
        users.add(userNotEmpty);
        users.add(userNotEmpty2);
        doReturn(users).when(userRepositoryMock).findAll();
        List<User> result = ratingService.getUsersWithinRating();
        assertEquals(0, result.size());
        
    }
    
    @Test
    public void getUsersWithinRatingWithEmptyRatedList() {
        Mockito.when(authUser1.getUser()).thenReturn(userNotEmpty);
        Optional<User> optUser = Optional.of(authUser1.getUser()); 
        Mockito.when(userRepositoryMock.findById(authUser1.getUser().getId())).thenReturn(optUser);
        List users = new ArrayList<>();
        users.add(userNotEmpty);
        users.add(userNotEmpty2);
        doReturn(users).when(userRepositoryMock).findAll();
        ratingService.getUsersWithinRating();
        assertEquals(0, ratingService.getUsersWithinRating().size());
    }
             
}
