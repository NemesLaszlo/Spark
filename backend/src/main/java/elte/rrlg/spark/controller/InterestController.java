package elte.rrlg.spark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elte.rrlg.spark.service.InterestService;

@CrossOrigin
@RestController
@RequestMapping("/interest")
public class InterestController {

    @Autowired
    private InterestService interestService;

    @PostMapping("/add/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void add(@PathVariable Integer id) {
        interestService.addInterest(id);
    }
}