package elte.rrlg.spark.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elte.rrlg.spark.AuthenticatedUser;
import elte.rrlg.spark.model.User;
import elte.rrlg.spark.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired 
    private UserService userService;

    // teljes listazas
    // @GetMapping("/all")
    // @Secured({ "ROLE_ADMIN" })
    // public ResponseEntity<Iterable<User>> getAll(){
    //     Iterable<User> users = userRepository.findAll();
    //     return ResponseEntity.ok(users);
    // }

    // user id alapján való lekérdezés
    // @GetMapping("/{id}")
    // public ResponseEntity<User> get(@PathVariable Integer id){
    //     Optional<User> user = userRepository.findById(id);
    //     if(!user.isPresent()){
    //         return ResponseEntity.notFound().build();
    //     }
    //     return ResponseEntity.ok(user.get());
    // }

    //Get current user
    @GetMapping("/me") 
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<User> getUser() {
        Optional<User> user = userService.getUser();        
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    // Registration
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        user = userService.registerUser(user);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        user = userService.loginUser();
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    //Logout
    @PostMapping("/logout")
    public ResponseEntity<User> logout(@RequestBody User user) {
        return ResponseEntity.ok(userService.logout(user));
    }

    //Update profile
    @PutMapping("/profile")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<User> profile(@RequestBody User user) {
        user = userService.updateProfile(user);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    //Update account
    @PutMapping("/update")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<User> update(@RequestBody User user) {
        user = userService.updateAccount(user);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    // user id alapjan törlése
    // @DeleteMapping("/delete/{id}")
    // @Secured({ "ROLE_ADMIN" })
    // public ResponseEntity delete(@PathVariable Integer id){
    //     Optional<User> optionalUser = userRepository.findById(id);

    //     if (optionalUser.isPresent()) {
    //         userRepository.delete(optionalUser.get());
    //         return ResponseEntity.ok().build();
    //     }
    //     else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // user modositasa
    // @PutMapping("/edit/{id}")
    // public ResponseEntity<User> edit(@PathVariable Integer id,  @RequestBody User user){
    //     Optional<User> optionalUser = userRepository.findById(id);
    //     if(!optionalUser.isPresent()){
    //         return ResponseEntity.notFound().build();
    //     }
    //     user.setId(id);
    //     return ResponseEntity.ok(userRepository.save(user));
    // }

}