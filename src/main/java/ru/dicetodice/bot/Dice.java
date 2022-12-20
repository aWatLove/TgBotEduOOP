package ru.dicetodice.bot;

public class Dice {
    static String rollDice(){
        return String.format("Roll 1d20: \n" + (int)(Math.random() * 20 + 1) );
    }

    static String rollDice(String dice){
        String d = String.valueOf(dice.toCharArray()[0]);
        return d;
    }
}
