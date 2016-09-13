package borsch.knowledge_db;

import java.io.*;

/**
 * Created by oleh_kurpiak on 13.09.2016.
 */
public class FileUtils {

    private FileUtils(){}

    public static String read(String pathToFile){
        StringBuilder builder = new StringBuilder();
        try {
            FileReader reader = new FileReader(new File(pathToFile));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                builder.append(line).append("\n");
            }

            try {
                bufferedReader.close();
            } catch (Exception e){ }
            try {
                reader.close();
            } catch (Exception e){ }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static void write(String pathToFile, String text){
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(pathToFile));
            PrintWriter writer = new PrintWriter(outputStream);

            writer.write(text);
            writer.flush();

            try {
                writer.close();
            } catch (Exception e){ }
            try {
                outputStream.close();
            } catch (Exception e){ }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
