package tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Task01StringStreams {

    /**
     * Функция должна вернуть число строчных символов в строке.
     *
     * Пример:
     *  "abcDE" -> 3
     *  "ABC" -> 0
     */
    static long countLowercaseLetters(String str)
    {
        char[] charArray = str.toCharArray();
        ArrayList<String> list = new ArrayList<>();
        for (Character item : charArray)
        {
            list.add(String.valueOf(item));
        }
        return list.stream().filter((x) -> x.matches("[a-z,а-я]")).count();
    }


    /**
     * Функция должна заменить каждое слово в строке его длинной.
     *
     * Слова разделяются одним или более пробелами.
     *
     * Пример:
     *   "a b cd" -> "1 1 2"
     *   "one two   three" -> "3 3 5"
     *
     * Тут подойдут эти методы:
     *    - String::split
     *    - Stream::map
     *    - Stream::collect
     *    - Collectors.joining
     */
    static String replaceWordsOnLength(String str)
    {
        return Arrays.stream(str.split(" {1,}")).map(x -> x = Integer.toString(x.length())).collect(Collectors.joining(" "));
    }
}