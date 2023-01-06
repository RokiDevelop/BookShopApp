package com.example.bookshopapp.data;

import org.springframework.context.annotation.Scope;

import java.util.*;

@Scope(value = "singleton")
public class Abc {

    private static Map<Language, List<Character>> instanceAbcMap;

    private Abc() {

    }

    public static List<Character> getAbcList(Language language) {
        if (instanceAbcMap == null) {
            instanceAbcMap = new HashMap<>();
        } else {
            if (instanceAbcMap.containsKey(language)) {
                return instanceAbcMap.get(language);
            }
        }

        instanceAbcMap.put(language, getLettersList(language));
        return instanceAbcMap.get(language);
    }

    private static List<Character> getLettersList(Language language) {
        List<Character> list = new ArrayList<>();

        if (language.equals(Language.ENG)) {
            for (int i = 'a'; i <= 'z'; i++) {
                list.add((char) i);
            }
        }

        if (language.equals(Language.RUS)) {
            String abc = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
            for(Character ch : abc.toCharArray()) {
                list.add(ch);
            }
        }

        return list;
    }
}
