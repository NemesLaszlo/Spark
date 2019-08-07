package elte.rrlg.spark.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Rating implements Serializable {

    public static final float ratingMargin = 1.0f;
    public static final int minRatingCount = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn
    @ManyToOne
    private User fromUser;

    @JoinColumn
    @ManyToOne
    private User toUser;

    @Column
    private int attractive;

    @Column
    private int smart;

    @Column
    private int trustworthy;
}
