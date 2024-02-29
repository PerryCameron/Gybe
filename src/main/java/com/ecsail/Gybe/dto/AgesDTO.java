package com.ecsail.Gybe.dto;

public class AgesDTO {
    int zeroTen;
    int elevenTwenty;
    int twentyOneThirty;
    int thirtyOneForty;
    int fortyOneFifty;
    int fiftyOneSixty;
    int sixtyOneSeventy;
    int seventyOneEighty;
    int eightyOneNinety;
    int notReported;
    int Total;

    public AgesDTO(int zeroTen, int elevenTwenty, int twentyOneThirty, int thirtyOneForty, int fortyOneFifty, int fiftyOneSixty, int sixtyOneSeventy, int seventyOneEighty, int eightyOneNinety, int notReported, int total) {
        this.zeroTen = zeroTen;
        this.elevenTwenty = elevenTwenty;
        this.twentyOneThirty = twentyOneThirty;
        this.thirtyOneForty = thirtyOneForty;
        this.fortyOneFifty = fortyOneFifty;
        this.fiftyOneSixty = fiftyOneSixty;
        this.sixtyOneSeventy = sixtyOneSeventy;
        this.seventyOneEighty = seventyOneEighty;
        this.eightyOneNinety = eightyOneNinety;
        this.notReported = notReported;
        Total = total;
    }

    public int getZeroTen() {
        return zeroTen;
    }

    public int getElevenTwenty() {
        return elevenTwenty;
    }

    public int getTwentyOneThirty() {
        return twentyOneThirty;
    }

    public int getThirtyOneForty() {
        return thirtyOneForty;
    }

    public int getFortyOneFifty() {
        return fortyOneFifty;
    }

    public int getFiftyOneSixty() {
        return fiftyOneSixty;
    }

    public int getSixtyOneSeventy() {
        return sixtyOneSeventy;
    }

    public int getSeventyOneEighty() {
        return seventyOneEighty;
    }

    public int getEightyOneNinety() {
        return eightyOneNinety;
    }

    public int getNotReported() {
        return notReported;
    }

    public int getTotal() {
        return Total;
    }
}
