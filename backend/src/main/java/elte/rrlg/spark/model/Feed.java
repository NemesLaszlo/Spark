package elte.rrlg.spark.model;

import java.util.List;

import lombok.Data;

@Data
public class Feed {
    private float refreshRate;

    private List<Match> newMatches;

    private List<Match> newMessages;
}