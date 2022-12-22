package ru.dicetodice.bot;

public class Dice {
    static String rollDice() {
//        return String.format("Roll 1d20: \n" + (int) (Math.random() * 20 + 1));
        return rollDice("1d20");
    }

    static String rollDice(String stringDice) {
        if (stringDice.split("d").length != 2) return "Неверный аргумент. Попробуйте /roll 1d6";
        String[] splitDice = stringDice.split("d", 0);
        int count = Integer.parseInt(splitDice[0]);
        int dice = Integer.parseInt(splitDice[1]);
        String resString = "Roll " + stringDice + "\n";
        int resInt = 0;

        if (count < 1) return "Кубов не может быть меньше 1го";
        else if (count == 1) {
            resInt = (int) (Math.random() * dice + 1);
            return String.format("%s|%d|",resString, resInt);
        } else {
            for (int i = 0; i < count; i++) {
                int tmp = (int) (Math.random() * dice + 1);
                resInt += tmp;
                resString += String.format("|%d|", tmp);
                if (i != count - 1) resString += " + ";
            }
            return String.format("%s = |%d|", resString, resInt);
        }
    }

    static String rollDice(String ... slicedDices){
        return "f";
    }
}
