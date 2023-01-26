package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.Language;
import com.example.bookshopapp.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
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
        Iterator<Author> iterator = authorRepository.findAll().iterator();
        List<Author> authors = new ArrayList<>();
        iterator.forEachRemaining(authors::add);

        return authors.stream().collect(Collectors.groupingBy(author -> author.getLastName().substring(0,1)));
    }

    public Author getAuthorById(int id) throws Exception {
        Optional<Author> optional = authorRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }

        throw new Exception();
    }
}
