package elte.rrlg.spark.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elte.rrlg.spark.AuthenticatedUser;
import elte.rrlg.spark.model.Interest;
import elte.rrlg.spark.model.User;
import elte.rrlg.spark.repository.InterestRepository;
import elte.rrlg.spark.repository.UserRepository;

@Service
public class InterestService {

    @Autowired
    private AuthenticatedUser authUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchService matchService;

    @Autowired
    private InterestRepository interestRepository;

    public void addInterest(Integer id) {
        Optional<User> fromUser = userRepository.findById(authUser.getUser().getId());
        Optional<User> toUser = userRepository.findById(id);

        if(fromUser.isPresent() && toUser.isPresent()) {
            //Matchmaking if the interest is mutual
            Optional<Interest> mutual = interestRepository.findByFromUserAndToUser(toUser.get(), fromUser.get());
            if(mutual.isPresent()) {
                matchService.addMatch(fromUser.get(), toUser.get());
            }
            Interest interest = new Interest();
            interest.setFromUser(fromUser.get());
            interest.setToUser(toUser.get());

            interestRepository.save(interest);            
        }
    }
}