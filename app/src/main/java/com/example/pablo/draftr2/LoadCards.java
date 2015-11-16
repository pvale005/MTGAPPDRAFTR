package com.example.pablo.draftr2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadCards {
    //private List<Card> allCards;
    //This method accepts a json as a byte array and return a list of cards
    public List<Card> getCards(byte[] json) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //Return a list of cards              //get all cards accepts one parameter
        List<Card> allCards = getAllCards((Map<String, Set>) mapper.readValue(json, new TypeReference<Map<String, Set>>() {
        }));
        return allCards;
    }

    public static List<Card> getAllCards(Map<String, Set> sets) {
        List<Card> allCards = new ArrayList<Card>();

        for (Set set : sets.values()) {
            for (Card card : set.getCards()) {
                card.setSetCode(set.getCode());
                card.setSetName(set.getName());

                //Here we modify the set name
                if ("Magic Origins".equals(card.getSetName())) {
                    //We don't want basic lands in our list of cards, so lets make sure they don't make it in
                    if (card.getName().equals("Forest") || card.getName().equals("Plains") || card.getName().equals("Mountain") || card.getName().equals("Swamp") || card.getName().equals("Island")) {
                    } else
                        //System.out.println(card.getName() + ": " + card.getManaCost());
                        allCards.add(card);

                }

            }
        }
        return allCards;
    }
}
