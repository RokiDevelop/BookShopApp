package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Map<String, List<Author>> getAuthorMapByLetter() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().collect(Collectors.groupingBy(author -> author.getLastName().substring(0,1)));
    }

    public Author getAuthorById(int id) {
        return authorRepository.getReferenceById(id);
    }
}
