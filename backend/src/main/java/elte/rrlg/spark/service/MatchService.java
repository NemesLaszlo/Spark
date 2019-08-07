package elte.rrlg.spark.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elte.rrlg.spark.AuthenticatedUser;
import elte.rrlg.spark.model.Match;
import elte.rrlg.spark.model.Message;
import elte.rrlg.spark.model.Picture;
import elte.rrlg.spark.model.User;
import elte.rrlg.spark.repository.MatchRepository;
import elte.rrlg.spark.repository.MessageRepository;
import elte.rrlg.spark.repository.UserRepository;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private AuthenticatedUser authUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public List<Match> getMatchesOf(User user) {
        return matchRepository.findByUserAOrUserB(user, user);
    }

    public List<Match> getMatches() {
        Optional<User> user = userRepository.findById(authUser.getUser().getId());
        if (user.isPresent()) {
            List<Match> list = getMatchesOf(user.get());
            for (Match v : list) {
                v.reduceMatch();
            }
            return list;
        }
        return null;        
    }

    public void addMatch(User userA, User userB) {
        Match match = new Match();
        match.setUserA(userA);
        match.setUserB(userB);
        match.setPoints(0);
        matchRepository.save(match);
    }

    public Message sendMessage(Integer id, String message) {
        Optional<Match> match = matchRepository.findById(id);
        Optional<User> user = userRepository.findById(authUser.getUser().getId());

        if (match.isPresent() && user.isPresent()) {
            Message newMessage = new Message();

            newMessage.setMessage(message);
            newMessage.setSender(user.get());
            newMessage.setMatch(match.get());
            
            messageRepository.save(newMessage);

            return newMessage; // teszeknél előjött, hogy ha a save() el tér vissza nullpointer lesz mindig
        }

        return null;
    }

	public List<Picture> unlockPicture(Integer id) {
		return null;
	}
}