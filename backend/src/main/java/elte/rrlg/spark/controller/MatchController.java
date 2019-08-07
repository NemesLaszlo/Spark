package elte.rrlg.spark.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import elte.rrlg.spark.model.Match;
import elte.rrlg.spark.model.Message;
import elte.rrlg.spark.model.Picture;
import elte.rrlg.spark.service.MatchService;

@CrossOrigin
@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/me")
    public ResponseEntity<List<Match>> getMatches() {
        List<Match> matches = matchService.getMatches();        
        if(matches == null){
            return ResponseEntity.notFound().build();
        }
        // System.out.println(matches);
        return ResponseEntity.ok(matches);
    }

    @PostMapping("/{id}/message")
    public void sendMessage(@PathVariable Integer id, @RequestBody Message message) {
        matchService.sendMessage(id, message.getMessage());
    }

    @PutMapping("/{id}/unlock")
    public ResponseEntity<List<Picture>> unlockPicture(@PathVariable Integer id) {
        List<Picture> pictures = matchService.unlockPicture(id);
        if (pictures == null) {
            return ResponseEntity.notFound().build();
        } 
        return ResponseEntity.ok(pictures);
    }
}