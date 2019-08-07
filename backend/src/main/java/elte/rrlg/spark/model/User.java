package elte.rrlg.spark.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotNull
    private String fullName;

    @Column
    @NotNull
    private String password;

    @Column
    @NotNull
    private String email;

    @Column
    private String description;

    @Column
    // @Temporal(TemporalType.TIMESTAMP)
    private Timestamp lastOnline;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    @Enumerated(EnumType.STRING)
    private Sexuality sexuality;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Picture> pictures;

    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    // private List<Match> matches;

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Interest> interestUsers;

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Interest> interestedUsers;

    @OneToMany(mappedBy = "userA", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Match> matchUsers;

    @OneToMany(mappedBy = "userB", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Match> matchedUsers;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Message> messages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MatchUnlock> unlocks;

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rating> ratedUsers;

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rating> raterUsers;

    public enum Sexuality {
        HETEROSEXUAL, HOMOSEXUAL, BISEXUAL
    }

    public enum Gender {
        MALE, FEMALE
    }

    public enum Role {
        ROLE_ADMIN, ROLE_USER
    }

    @JsonIgnore
    public User reduceForMessage() {
        User user = new User();        
        user.setId(this.getId());
        user.setFullName(this.getFullName());

        return user;
    }

    @JsonIgnore
    public User reduceForProfile() {
        this.setPassword(null);
        return this;
    }

    @JsonIgnore
    public User reduceForRating() {
        User user = new User();
        user.setId(user.getId());
        user.setPictures(this.getPictures());
        return user;
    }

    @JsonIgnore
    public float[] getRatingPoints() {

        float[] avg = {0.0f,0.0f,0.0f};

        if (raterUsers.size() > Rating.minRatingCount) {
            for (Rating rate : raterUsers) {
                avg[0] += rate.getAttractive();
                avg[1] += rate.getSmart();
                avg[2] += rate.getTrustworthy();
            }
    
            for (int i=0;i<avg.length;i++) {
                avg[i] /= (float)raterUsers.size();
            }
        } else {
            for (int i=0;i<avg.length;i++) {
                avg[i] = 3;
            }
        }

        

        return avg;
    }

    @JsonIgnore
    public float getAveragePoints() {
        float t[] = getRatingPoints();
        float avg = 0;
        for (float v : t) {
            avg += v;
        }
        return avg/3.0f;
    }

    @JsonIgnore
    public float getSumPoints() {
        float t[] = getRatingPoints();
        float sum = 0;
        for (float v : t) {
            sum += v;
        }
        return sum;
    }

    @JsonIgnore
    public float getMinPoints() {
        float t[] = getRatingPoints();
        float min = Float.MAX_VALUE;
        for (float v : t) {
            min = v < min ? v : min;
        }
        return min;
    }

    @JsonIgnore
    public float getMaxPoints() {
        float t[] = getRatingPoints();
        float max = Float.MIN_VALUE;
        for (float v : t) {
            max = v < max ? v : max;
        }
        return max;
    }

    @JsonIgnore
    public float getPoints() {
        return getAveragePoints();
    }
    
    @JsonIgnore
    public boolean withinRating(User u) {
        return Math.abs(getPoints() - u.getPoints()) <= Rating.ratingMargin;
    }
    
}