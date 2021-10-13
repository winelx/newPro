package com.example.administrator.yanghu.pzgc.bean;

/**
 * Created by Administrator on 2018/8/15 0015.
 */

public class chekitemList {
    String id;
    String score;
    String sequence;
    String standardScore;
    String number;
    String s_type;
    Integer pos;
    boolean generate;
    boolean noSuch;
    boolean penalty;
    boolean gray;

    public chekitemList(String id, String score, String sequence, String standardScore, String number, String s_type,Integer pos,boolean generate, boolean noSuch, boolean penalty, boolean gray) {
        this.id = id;
        this.score = score;
        this.sequence = sequence;
        this.standardScore = standardScore;
        this.number = number;
        this.s_type = s_type;
        this.pos = pos;
        this.generate = generate;
        this.noSuch = noSuch;
        this.penalty = penalty;
        this.gray = gray;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getStandardScore() {
        return standardScore;
    }

    public void setStandardScore(String standardScore) {
        this.standardScore = standardScore;
    }

    public boolean isNoSuch() {
        return noSuch;
    }

    public void setNoSuch(boolean noSuch) {
        this.noSuch = noSuch;
    }

    public boolean isPenalty() {
        return penalty;
    }

    public void setPenalty(boolean penalty) {
        this.penalty = penalty;
    }

    public boolean isGenerate() {
        return generate;
    }

    public void setGenerate(boolean generate) {
        this.generate = generate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isGray() {
        return gray;
    }

    public void setGray(boolean gray) {
        this.gray = gray;
    }

    public String getS_type() {
        return s_type;
    }

    public void setS_type(String s_type) {
        this.s_type = s_type;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }
}
