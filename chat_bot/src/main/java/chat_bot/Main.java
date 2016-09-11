package chat_bot;

import java.util.Properties;

/**
 * Created by oleh_kurpiak on 09.09.2016.
 */
public class Main {

    public static void main(String[] args){
        Console console = new Console(System.in, System.out);
        //connecting(console);
        ChatBot bot = new ChatBot();

        while (!bot.isEnd()){
            console.print("[YOU] ");
            String question = console.read();
            console.println("[BOT] " + bot.answer(question));
        }

        console.close();
    }


    private static void connecting(Console console){
        console.print("connecting");
        for(int i = 0; i < 7; ++i){
            try {
                console.print(".");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        console.println("\nSTATUS CONNECTED");
    }
}

