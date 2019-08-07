package elte.rrlg.spark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import elte.rrlg.spark.model.Feed;
import elte.rrlg.spark.service.FeedService;

@Controller
@RestController
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private FeedService feedService;
    
    @GetMapping("/")
    public ResponseEntity<Feed> getFeed(@RequestParam String strRate) {
        feedService.requestRefresh();
        Feed feed = feedService.getFeed(strRate);
        if(feed == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(feed);
    }
}