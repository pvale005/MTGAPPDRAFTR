package com.example.pablo.draftr2;

import java.util.ArrayList;
//Encapsulates all the attributes of an MTG set
public class Set {
    private String name;
    private String code;
    private String releaseDate;
    private String border;
    private String type;
    private String block;
    private String gathererCode;
    private ArrayList<Card> cards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String getGathererCode() {
        return gathererCode;
    }

    public void setGathererCode(String gathererCode) {
        this.gathererCode = gathererCode;
    }
}
