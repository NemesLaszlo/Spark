package elte.rrlg.spark.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @JoinColumn
    @ManyToOne
    private Match match;

    // @JsonIgnore
    @JoinColumn
    @ManyToOne
    private User sender;

    @Column
    // @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Timestamp sendTime;

    @Column
    private String message;

    @JsonIgnore
    public void reduceSender() {
        sender = sender.reduceForMessage();
    }

}