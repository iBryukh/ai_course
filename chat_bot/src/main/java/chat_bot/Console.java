package chat_bot;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by oleh_kurpiak on 09.09.2016.
 */
public class Console {

    private Scanner scanner;

    private PrintWriter writer;

    public Console(InputStream inputStream, OutputStream outputStream){
        scanner = new Scanner(inputStream);
        writer = new PrintWriter(outputStream);
    }

    public void println(String message){
        writer.println(message);
        writer.flush();
    }

    public void print(String message){
        writer.print(message);
        writer.flush();
    }

    public String read(){
        return scanner.nextLine();
    }

    public void close(){
        try {
            scanner.close();
        } catch (Exception e){ }

        try {
            writer.close();
        } catch (Exception e){ }
    }
}
