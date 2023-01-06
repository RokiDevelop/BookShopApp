package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Abc;
import com.example.bookshopapp.data.Language;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbcService {

    public List<Character> getAbcList(Language language){
        return Abc.getAbcList(language);
    }
}
