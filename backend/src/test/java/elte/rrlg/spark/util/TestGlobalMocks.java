package elte.rrlg.spark.util;

import java.time.Instant;
import java.util.ArrayList;

import elte.rrlg.spark.model.User;

import java.sql.Timestamp;

/**
 * TestGlobalMocks
 */
public class TestGlobalMocks {

    public static User userNull;
    public static User userNotEmpty;
    public static User userNotEmpty2;
    public static User userNotEmpty3;
    public static User userNotEmptyPw;
    public static User userNotEmptyNullPw;

    static {
        userNotEmpty = makeTestUser(1,"Bela","password","user@user.hu","description",User.Gender.MALE,User.Sexuality.HETEROSEXUAL,User.Role.ROLE_USER);
        userNotEmpty2 = makeTestUser(2,"Zoey","password","user2@user.hu","description",User.Gender.FEMALE,User.Sexuality.HETEROSEXUAL,User.Role.ROLE_USER);
        userNotEmpty3 = makeTestUser(3,"Jade","password","user3@user.hu","description",User.Gender.FEMALE,User.Sexuality.HETEROSEXUAL,User.Role.ROLE_USER);
        userNotEmptyNullPw = makeTestUser(1,"Bela",null,"user@user.hu","description",User.Gender.MALE,User.Sexuality.HETEROSEXUAL,User.Role.ROLE_USER);
        userNotEmptyPw = makeTestUser(1,"Bela","$2a$04$YDiv9c./ytEGZQopFfExoOgGlJL6/o0er0K.hiGb5TGKHUL8Ebn..","user@user.hu","description",User.Gender.MALE,User.Sexuality.HETEROSEXUAL,User.Role.ROLE_USER);  
    }

    public static User makeTestUser(int id, String fullName, String password, String email, String desc, User.Gender gender, User.Sexuality sexuality, User.Role role) {
        User user = new User();
        user.setId(id);
        user.setFullName(fullName);
        user.setPassword(password);
        user.setEmail(email);
        user.setDescription(desc);
        user.setLastOnline(Timestamp.from(Instant.now()));
        user.setGender(gender);
        user.setSexuality(sexuality);
        user.setRole(role);
        user.setPictures(makeEmptyList());
        return user;
    }

    public static User makeTestUser(int id, String fullName, String password, String email, String desc) {
        return makeTestUser(id,fullName,password,email,desc,User.Gender.MALE,User.Sexuality.HETEROSEXUAL,User.Role.ROLE_USER);
    }
    
    public static <T> ArrayList<T> makeEmptyList() {
        return new ArrayList<T>();
    }
}