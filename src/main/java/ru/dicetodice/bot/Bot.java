package ru.dicetodice.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    private final String BOT_NAME = "dicetodicebot";
    private final String BOT_TOKEN = "";
    CharacterList characterList;

    @Override
    public String getBotUsername() {
        return this.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return this.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMess = update.getMessage();
                String chatId = inMess.getChatId().toString();
                String response = parseMessage(inMess.getText(), chatId);
                SendMessage outMess = new SendMessage();

                //Добавляем в наше сообщение id чата а также наш ответ
                outMess.setChatId(chatId);
                outMess.setText(response);

                execute(outMess);//Отправка в чат
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String parseMessage(String textMsg, String chatId) {
        String response;
        String[] textMessages = textMsg.split(" ");
        String argument = "create";
        if(textMessages.length > 1) {
            argument = textMessages[1];
        }

        switch (textMessages[0]) {
            case "/start":
                response = "Приветствую, этот бот что то умеет\n" +
                        "\n/roll - Кидает кубик 1d20" +
                        "\n/roll {кол-во}d{Dice} - кидает несколько кубов" +
                        "\n/char - выводит ваш чарлист" +
                        "\n/char create - создает чарлист со стандартными характеристиками" +
                        "\n/char create {6 характеристик} - создаёт чарлист с вашими характеристиками" +
                        "\n/char set - задает стандартные характеристики" +
                        "\n/char set {6 характеристик} - задает характеристики";
                break;


            case "/help":
                response = "\n/roll - Кидает кубик 1d20" +
                        "\n/roll {кол-во}d{Dice} - кидает несколько кубов" +
                        "\n/char - выводит ваш чарлист" +
                        "\n/char create - создает чарлист со стандартными характеристиками" +
                        "\n/char create {6 характеристик} - создаёт чарлист с вашими характеристиками" +
                        "\n/char set - задает стандартные характеристики" +
                        "\n/char set {6 характеристик} - задает характеристики";;
                break;


            case "/roll":
                if (textMessages.length == 1) {
                    response = Dice.rollDice();
                } else if (textMessages.length == 2) {
                    response = Dice.rollDice(textMessages[1]);
                } else response = "Сообщение не распознано";
                break;


            case "/char": //чарлист
                if(textMessages.length > 1) {
                    switch (argument) {    //чарлист создание
                        case "create":
                            if (characterList != null) {
                                response = "Кажется у вас уже есть чарлист.\nЕсли вы хотите поменять характеристики, попробуйте: /char set {характеристики}";
                                break;
                            }

                            if (textMessages.length == 8) {     //все характеристики передали
                                if(!(CharacterList.checkChar(
                                        textMessages[2], textMessages[3], textMessages[4],
                                        textMessages[5], textMessages[6], textMessages[7]))){
                                    response = "Некоректные данные!";
                                    break;
                                }
                                characterList = new CharacterList(chatId,
                                        textMessages[2], textMessages[3], textMessages[4], textMessages[5], textMessages[6], textMessages[7]);
                                response = "Чарник успешно создан. Посмотреть свои характеристики можно: /char";
                                break;
                            } else if (textMessages.length == 2){    //стандартный чарлист, статы все 10
                                characterList = new CharacterList(chatId);
                                response = "Чарник успешно создан. Посмотреть свои характеристики можно: /char";
                                break;
                            }
                        case "set":
                            if(characterList == null){
                                response = "Сначала нужно создать чарник: /char create {6 характеристик}";
                                break;
                            } else if (textMessages.length == 8){
                                if(!CharacterList.checkChar(textMessages[2], textMessages[3], textMessages[4], textMessages[5], textMessages[6], textMessages[7])){
                                    response = "Некоректные данные!";
                                    break;
                                }
                                characterList = characterList.setChar(chatId,
                                        textMessages[2], textMessages[3], textMessages[4], textMessages[5], textMessages[6], textMessages[7]);
                                response = "Новые характеристики установлены";
                                break;
                            }
                            else {
                                characterList = characterList.setChar(chatId);
                                response = "Установлены стандартные характеристики";
                                break;
                            }
                        default:
                            response = "Неверный аргумент!";
                            break;
                    }
                    break;
                } else {
                    if(characterList == null){
                        response = "У тебя еще нет чарника";
                        break;
                    } else {
                        response = characterList.toString();
                        break;
                    }

                }
            default:
                response = "У меня нет такой команды." +
                        "\n/help - для помощи";
                break;
        }

        return response;
    }
}
