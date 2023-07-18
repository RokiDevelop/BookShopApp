package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.book.links.Book2GenreEntity;
import com.example.bookshopapp.data.genre.GenreEntity;
import com.example.bookshopapp.data.genre.genreTree.GenreNode;
import com.example.bookshopapp.repositories.JpaBookRepository;
import com.example.bookshopapp.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final JpaBookRepository bookRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository,
                        JpaBookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    public List<GenreNode> getGenreAmountBookMap() {

        List<Book2GenreEntity> list = getListBook2GenreEntity();

        Map<GenreEntity, List<Book>> map = getMapBooksByGenre(list);

        List<GenreNode> genreNodes = new ArrayList<>();
        map.forEach((genreEntity, books) -> genreNodes.add(new GenreNode(genreEntity, books.size())));

        GenreEntity rootGenre = new GenreEntity(0, null, "", "root");
        GenreNode rootGenreNode = new GenreNode(rootGenre, 0);

        rootGenreNode.addChildren(rootGenreNode, genreNodes, 0);

        return new ArrayList<>(rootGenreNode.getChildren());
    }

    private Map<GenreEntity, List<Book>> getMapBooksByGenre(List<Book2GenreEntity> list) {
        return list.stream().collect(
                Collectors.groupingBy(
                        book2GenreEntity ->
                                genreRepository.getReferenceById(book2GenreEntity.getGenreId()),
                        Collectors.mapping(
                                book2GenreEntity ->
                                        bookRepository.getReferenceById(book2GenreEntity.getBookId()),
                                Collectors
                                        .toList())));
    }

    private List<Book2GenreEntity> getListBook2GenreEntity() {
        return genreRepository.getBookWithGenre();
    }

    public GenreEntity getGenreById(int id) {
        return genreRepository.getReferenceById(id);
    }

    public List<GenreEntity> getAllGenres() {
        return genreRepository.findAll();
    }
}
