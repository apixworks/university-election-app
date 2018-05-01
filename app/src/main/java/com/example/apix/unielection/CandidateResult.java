package com.example.apix.unielection;

/**
 * Created by Apix on 04/09/2017.
 */

public class CandidateResult {
    int position_id;
    String name;
    String program;
    int yos;
    int votes;
    String gender;
    String img_url;
    int id;

    public CandidateResult(int position_id, String name, String program, int yos, int votes, String gender, String img_url,int id) {
        this.position_id = position_id;
        this.name = name;
        this.program = program;
        this.yos = yos;
        this.votes = votes;
        this.gender = gender;
        this.img_url = img_url;
        this.id = id;
    }

}
