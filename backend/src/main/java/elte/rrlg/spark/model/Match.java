package elte.rrlg.spark.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
public class Match implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // @JsonIgnore
    @JoinColumn
    @ManyToOne
    private User userA;

    // @JsonIgnore
    @JoinColumn
    @ManyToOne
    private User userB;

    // @JsonIgnore
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<Message> messages;

    @Column
    private Integer points;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<MatchUnlock> unlocks;

    @JsonIgnore
    public void reduceMatch() {
        userA.reduceForProfile();
        userB.reduceForProfile();
        for (Message msg : messages) {
            msg.reduceSender();
        }
    }
}