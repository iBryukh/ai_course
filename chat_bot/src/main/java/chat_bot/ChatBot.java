package chat_bot;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oleh_kurpiak on 09.09.2016.
 */
public class ChatBot {

    private boolean end = false;

    private boolean likeQuestion = false;
    private boolean hateQuestion = false;

    private static final Pattern HELLO_QUESTION_PATTERN = Pattern.compile("([Пп]ривіт|[Хх]ай|[Дд]ратуті)*");
    private static final String HELLO_ANSWER = "Дратуті. Що любиш чи ненавидиш?";

    private static final Pattern LOVE_QUESTION_PATTERN = Pattern.compile("([Яя]\\s+люблю\\s+|[Мм]ені\\s+подобається\\s+)([а-яА-ЯіІ]+)");
    private static final String LOVE_ANSWER = "Чому ти любиш ";

    private static final Pattern HATE_QUESTION_PATTERN = Pattern.compile("([Яя]\\s+не\\s+люблю\\s+|[Мм]ені\\s+не\\s+подобається\\s+)([а-яА-ЯіІ]+)");
    private static final String HATE_ANSWER = "Чому не любиш ";

    private static final String LIKE_OR_HATE_OK_REASON = "кк";
    private static final String LIKE_OR_HATE_STUPID_REASON = "Дурна причина";

    private static final String WHAT_ELSE = "\nщо ще скажеш? ще щось любиш?";

    private static final Pattern BUE_QUESTION_PATTERN = Pattern.compile("([Пп]апа|[Дд]о зусрічі|[Дд]атвіданіє|[Пп]акеда)*");
    private static final String BUE_ANSWER = "пакеда";

    private static final String DEFAULT_ANSWER = "..\nииии\nНорм скажи що любиш чи ненавидиш.";

    public String answer(String question){
        if(likeQuestion || hateQuestion){
            likeQuestion = false;
            hateQuestion = false;
            return (new Random().nextBoolean() ? LIKE_OR_HATE_OK_REASON : LIKE_OR_HATE_STUPID_REASON) +
                    WHAT_ELSE;
        }
        if(HELLO_QUESTION_PATTERN.matcher(question).matches())
            return HELLO_ANSWER;

        if(BUE_QUESTION_PATTERN.matcher(question).matches()) {
            end = true;
            return BUE_ANSWER;
        }

        Matcher matcher = LOVE_QUESTION_PATTERN.matcher(question);
        if(matcher.matches()) {
            likeQuestion = true;
            return LOVE_ANSWER + matcher.group(2) + "?";
        }

        matcher = HATE_QUESTION_PATTERN.matcher(question);
        if(matcher.matches()){
            hateQuestion = true;
            return HATE_ANSWER + matcher.group(2) + "?";
        }

        return DEFAULT_ANSWER;
    }

    public boolean isEnd(){
        return end;
    }
}
