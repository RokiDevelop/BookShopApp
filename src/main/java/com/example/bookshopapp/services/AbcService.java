package com.example.bookshopapp.data;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbcService {

    public List<Character> getAbcList(Language language){
        return Abc.getAbcList(language);
    };
}
