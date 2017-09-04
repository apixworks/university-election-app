package com.example.apix.unielection;

/**
 * Created by Apix on 28/08/2017.
 */

public class Candidate {
    int id;
    String name;
    String program;
    int yos;
    int position_id;
    String gender;
    String img_url;

    public Candidate(int id,String name,String program,int yos,String gender,String img_url,int position_id) {
        super();
        this.id = id;
        this.name = name;
        this.program = program;
        this.yos = yos;
        this.position_id = position_id;
        this.gender = gender;
        this.img_url = img_url;
    }
}
