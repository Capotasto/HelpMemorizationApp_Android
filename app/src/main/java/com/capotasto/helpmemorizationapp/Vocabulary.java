package com.capotasto.helpmemorizationapp;

/**
 * Created by Capotasto on 11/22/15.
 */
public class Vocabulary {

    private int id;
    private String word;
    private String meaning;
    private String example;
    private String p_symbol;

    public Vocabulary(){

    }

    public Vocabulary(int id, String word, String meaning, String example, String p_symbol) {
        this.id = id;
        this.word = word;
        this.meaning = meaning;
        this.example = example;
        this.p_symbol = p_symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getP_symbol() {
        return p_symbol;
    }

    public void setP_symbol(String p_symbol) {
        this.p_symbol = p_symbol;
    }
}
