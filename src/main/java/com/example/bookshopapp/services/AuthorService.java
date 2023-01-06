package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorService {

    private final JdbcTemplate jdbcTemplate;
    private final AbcService abcService;

    @Autowired
    public AuthorService(JdbcTemplate jdbcTemplate, AbcService abcService) {
        this.jdbcTemplate = jdbcTemplate;
        this.abcService = abcService;
    }

    public Map<Character, List<Author>> getAuthorMapByLetter(Language language) {
        Map<Character, List<Author>> map = new HashMap<>();

        List<Character> letters = abcService.getAbcList(language);
        letters.forEach(letter -> {
            String query =
                    "SELECT " +
                            "* " +
                    "FROM " +
                            "books " +
                    "WHERE " +
                            "author LIKE  UPPER('" + letter + "%') OR " +
                            "author LIKE LOWER('" + letter + "%') " +
                    "GROUP BY author";
            List<Author> authors = jdbcTemplate.query(query,
                    (ResultSet rs, int rowNum) -> {
                        Author author = new Author();
                        author.setName(rs.getString("author"));
                        return author;
                    });

            map.put(letter, authors);
        });

        return map;
    }
}
