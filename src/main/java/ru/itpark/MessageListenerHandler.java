package ru.itpark;

import lombok.SneakyThrows;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Random;

public class MessageListenerHandler implements LongPollingSingleThreadUpdateConsumer {
    TelegramClient client = new OkHttpTelegramClient("6543777887:AAFgXq6i6IM8okrRT8057UMlOBFtZBlyUes");
    Random randomGenerator = new Random();
    int myNumber;

    @SneakyThrows
    @Override
    public void consume(Update update) {
       if(update.hasMessage()) {
           Message messageFromUser = update.getMessage();

           if(messageFromUser.hasText()) {
               String messageText = messageFromUser.getText();

               if (messageText.equals("/start")) {
                   myNumber = randomGenerator.nextInt(0,100);

                   SendMessage messageToSend = new SendMessage(messageFromUser.getChatId().toString(), "Привет! Я загадал число от 0 до 100! Попробуй угадать");

                   client.execute(messageToSend);
               } else {
                   int quessedNumber  = Integer.parseInt(messageText);

                   if(quessedNumber == myNumber) {
                       SendMessage messageToSend = new SendMessage(messageFromUser.getChatId().toString(), String.format("Ты угадал! Моё число %d.", myNumber));

                       client.execute(messageToSend);
                   }else if (myNumber > quessedNumber){
                       SendMessage messageToSend = new SendMessage(messageFromUser.getChatId().toString(), "Моё число больше твоего.");

                       client.execute(messageToSend);
                   } else if (myNumber < quessedNumber){
                       SendMessage messageToSend = new SendMessage(messageFromUser.getChatId().toString(), "Моё число меньше твоего.");

                       client.execute(messageToSend);
                   }
               }
           }
       }
    }
}
