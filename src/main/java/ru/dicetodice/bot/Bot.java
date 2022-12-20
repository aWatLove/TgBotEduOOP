package ru.dicetodice.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.dicetodice.storage.Storage;

public class Bot extends TelegramLongPollingBot {
    private final String BOT_NAME = "dicetodicebot";
    private final String BOT_TOKEN = "5803181116:AAHmg3p9kpXuWgKdyGTvQJ-VMa8j89KCJCA";
    public Storage storage = new Storage();

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
        try{
            if(update.hasMessage() && update.getMessage().hasText())
            {
                //Извлекаем из объекта сообщение пользователя
                Message inMess = update.getMessage();
                //Достаем из inMess id чата пользователя
                String chatId = inMess.getChatId().toString();
                //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                String response = parseMessage(inMess.getText());
                //Создаем объект класса SendMessage - наш будущий ответ пользователю
                SendMessage outMess = new SendMessage();

                //Добавляем в наше сообщение id чата а также наш ответ
                outMess.setChatId(chatId);
                outMess.setText(response);

                //Отправка в чат
                execute(outMess);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public String parseMessage(String textMsg) {
        String response;
        String[] textMessages = textMsg.split(" ");

        //Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        switch (textMessages[0]) {
            case "/start":
                response = "Приветствую, этот бот что то умеет" +
                        "\n /roll - roll d20";
                break;
            case "/get":
                response = storage.getRandQuote();
                break;
            case "/roll":
                if (textMessages.length == 1){
                    response = Dice.rollDice();
                } else if (textMessages.length == 2) {
                    response = Dice.rollDice(textMessages[1]);
                } else response = "Сообщение не распознано";

                break;
            default:
                response = "Сообщение не распознано";
                break;
        }

        return response;
    }
}
