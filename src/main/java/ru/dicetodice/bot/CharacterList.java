package ru.dicetodice.bot;

import java.util.HashMap;

public class CharacterList {
    private HashMap<String, Integer> characters = new HashMap<>();
    String chatID;
    public CharacterList(String chatID, int str, int dex, int con, int inta, int wis, int cha) {
        this.chatID = chatID;
        characters.put("str", str);
        characters.put("dex", dex);
        characters.put("con", con);
        characters.put("int", inta);
        characters.put("wis", wis);
        characters.put("cha", cha);
    }
    public CharacterList(String chatID, String str, String dex, String con, String inta, String wis, String cha) {
        this(chatID, Integer.parseInt(str),
                Integer.parseInt(dex),
                Integer.parseInt(con),
                Integer.parseInt(inta),
                Integer.parseInt(wis),
                Integer.parseInt(cha));
    }

    public CharacterList(String chatID) {
        this(chatID, 10, 10, 10, 10, 10, 10);
    }

    public CharacterList setChar(String chatId, String str, String dex, String con, String inta, String wis, String cha){
        return new CharacterList(chatID, Integer.parseInt(str),
                Integer.parseInt(dex),
                Integer.parseInt(con),
                Integer.parseInt(inta),
                Integer.parseInt(wis),
                Integer.parseInt(cha));
    }

    public CharacterList setChar(String chatId){
        return new CharacterList(chatID, 10, 10, 10, 10, 10, 10);
    }

    public String rollChar(String character) {
        return String.format("%s + %s |%d|", Dice.rollDice("1d20"), character, (characters.get(character)-10)/2);
    }

    @Override
    public String toString() {
        return String.format("Твои характеристики:\nСила: (%d) %d\nЛовкость: (%d) %d\nТелосложение: (%d) %d\nИнтелект: (%d) %d\nМудрость: (%d) %d\nХаризма: (%d) %d",
                (characters.get("str")-10)/2, characters.get("str"),
                (characters.get("dex")-10)/2, characters.get("dex"),
                (characters.get("con")-10)/2, characters.get("con"),
                (characters.get("int")-10)/2, characters.get("int"),
                (characters.get("wis")-10)/2, characters.get("wis"),
                (characters.get("cha")-10)/2, characters.get("cha"));
    }


    public static boolean checkChar(String str, String dex, String con, String inta, String wis, String cha){
        if (Integer.parseInt(str) < 0 || Integer.parseInt(str) > 30) return false;
        if (Integer.parseInt(dex) < 0 || Integer.parseInt(dex) > 30) return false;
        if (Integer.parseInt(con) < 0 || Integer.parseInt(con) > 30) return false;
        if (Integer.parseInt(inta) < 0 || Integer.parseInt(inta) > 30) return false;
        if (Integer.parseInt(wis) < 0 || Integer.parseInt(wis) > 30) return false;
        if (Integer.parseInt(cha) < 0 || Integer.parseInt(cha) > 30) return false;
        return true;
    }


}
