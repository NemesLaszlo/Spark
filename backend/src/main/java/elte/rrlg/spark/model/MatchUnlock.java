package elte.rrlg.spark.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MatchUnlock {

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Match match;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToOne @MapsId
    private Picture picture;
}