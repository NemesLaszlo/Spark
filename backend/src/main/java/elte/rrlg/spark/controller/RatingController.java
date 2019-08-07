package elte.rrlg.spark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elte.rrlg.spark.model.Rating;
import elte.rrlg.spark.model.User;
import elte.rrlg.spark.service.RatingService;

@CrossOrigin
@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/add/{id}")
    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    public void add(@PathVariable Integer id, @RequestBody Rating rating) {
        ratingService.addRating(id, rating);
    }

    @GetMapping("/next")
    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    public ResponseEntity<User> getNextRateable() {
        User t = ratingService.getRandomRatableUser();
        if(t == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(t.reduceForRating());
    }

    // Get points
    // @GetMapping("/me")
    // @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    // public void get() {
    //     System.out.println(ratingService.getRating());
    // }
}