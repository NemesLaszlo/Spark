package elte.rrlg.spark.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elte.rrlg.spark.AuthenticatedUser;
import elte.rrlg.spark.model.Rating;
import elte.rrlg.spark.model.User;
import elte.rrlg.spark.repository.RatingRepository;
import elte.rrlg.spark.repository.UserRepository;

@Service
public class RatingService {

    @Autowired
    private AuthenticatedUser authUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchService matchService;

    @Autowired
    private RatingRepository ratingRepository;

    public void addRating(Integer id, Rating rating) {
        Optional<User> fromUser = userRepository.findById(authUser.getUser().getId());
        Optional<User> toUser = userRepository.findById(id);

        if(fromUser.isPresent() && toUser.isPresent()) {
            rating.setFromUser(fromUser.get());
            rating.setToUser(toUser.get());

            ratingRepository.save(rating);            
        }
    }

    // public float getRating() {
    //     Optional<User> user = userRepository.findById(authUser.getUser().getId());
    //     float t[] = user.get().getRatingPoints();
        
    //     float avg = 0;
    //     for (float v : t) {
    //         avg += v;
    //     }
    //     return avg/3.0f;
    // }

    public List<User> getUsersWithinRating() {

        Optional<User> user = userRepository.findById(authUser.getUser().getId());

        if(!user.isPresent()) {
            return null;
        }

        User curUser = user.get();

        List<User> list = userRepository.findAll();
        list.remove(curUser);
        
        List<User> rated = new LinkedList<>();

        for (Rating v : curUser.getRatedUsers()) {
            rated.add(v.getToUser());
        }

        list.removeAll(rated);

        Iterator<User> p = list.iterator();
        User t;
        while(p.hasNext()) {
            t = p.next();
            if (!curUser.withinRating(t)) {
                p.remove();
            }
        }
        
        return list;
    }

    public User getRandomRatableUser() {
        List<User> list = getUsersWithinRating();
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}