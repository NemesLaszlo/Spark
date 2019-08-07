package elte.rrlg.spark.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elte.rrlg.spark.AuthenticatedUser;
import elte.rrlg.spark.model.Feed;
import elte.rrlg.spark.model.Match;
import elte.rrlg.spark.model.User;
import elte.rrlg.spark.repository.UserRepository;

@Service
public class FeedService {

    private final float MIN = 1;
    private final float MAX = 10;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedUser authUser;

    public void requestRefresh() {
        Optional<User> user = userRepository.findById(authUser.getUser().getId());
        if (user.isPresent()) {
            user.get().setLastOnline(Timestamp.from(Instant.now()));
        }
    }

    public Feed getFeed(String strRate) {
        float refreshRate;
        try {
            refreshRate = Float.parseFloat(strRate);
        } catch(Exception e) {
            refreshRate = 5.0f;
        }
        

        Feed feed = new Feed();
        feed.setNewMatches(new ArrayList<>());
        feed.setNewMessages(new ArrayList<>());

        refreshRate = getRefreshRate(refreshRate, feed);
        feed.setRefreshRate(refreshRate);
        return feed;
    }

    public float getRefreshRate(float lastRate, Feed feed) {
        int matchCount = feed.getNewMatches().size();
        int messageCount = 0;

        for (Match v : feed.getNewMessages()) {
            messageCount += v.getMessages().size();
        }

        // feed.getNewMessages().forEach(x -> messageCount += x.getMessages().size());

        float newRate = Math.max((float)(matchCount + messageCount) * (lastRate / MAX),0.5f);
        return Math.max(lastRate / newRate, MIN);
    }
}