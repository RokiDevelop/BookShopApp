package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.repositories.JpaBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService {
    private final JpaBookRepository bookRepository;

    @Autowired
    public BookService(JpaBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksData() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.getReferenceById(id);
    }

    public Page<Book> getPageBooksByAuthor(Author author,
                                           Integer offset,
                                           Integer limit) {
        int currentOffset = offset == null ? 0 : offset;
        int currentLimit = limit == null ? 20 : limit;
        Pageable nextPage = PageRequest.of(currentOffset, currentLimit);

        return bookRepository.findByAuthor(author.getId(), nextPage);
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset,
                                                Integer limit) {
        int currentOffset = offset == null ? 0 : offset;
        int currentLimit = limit == null ? 20 : limit;

        Pageable nextPage = PageRequest.of(currentOffset, currentLimit);

        return bookRepository.findAll(nextPage);
        //TODO: need to make query

        // bookRepository.findBooksRecommended(nextPage)
    }

    public Page<Book> getPageOfRecentBooks(String optDateFrom,
                                           String optDateTo,
                                           Integer offset,
                                           Integer limit) {
        int currentOffset = offset == null ? 0 : offset;
        int currentLimit = limit == null ? 20 : limit;

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String stringDateFrom = optDateFrom == null ? (LocalDateTime.now().minusMonths(1).format(formatter1)) : optDateFrom;
        String stringDateTo = optDateTo == null ? (LocalDateTime.now().format(formatter1)) : optDateTo;

        LocalDate currentDateFrom = LocalDate.parse(stringDateFrom, formatter1);
        LocalDate currentDateTo = LocalDate.parse(stringDateTo, formatter1);

        Pageable nextPage = PageRequest.of(currentOffset, currentLimit);
        return bookRepository.findBooksByPubDateBetweenOrdOrderByPubDate(
                currentDateFrom.atStartOfDay(),
                currentDateTo.atStartOfDay(),
                nextPage
        );
    }

    public Book getBookBySlug(String slug) {
        return bookRepository.findBooksBySlug(slug);
    }

    public Page<Book> getBooksByGenreId(int id,
                                        Integer offset,
                                        Integer limit) {
        int currentOffset = offset == null ? 0 : offset;
        int currentLimit = limit == null ? 20 : limit;

        Pageable nextPage = PageRequest.of(currentOffset, currentLimit);
        return bookRepository.getBookByGenreId(id, nextPage);
    }

    public void save(Book bookToUpdate) {
        bookRepository.save(bookToUpdate);
    }

    public List<Book> findBooksBySlugIn(String[] slugs) {
        return bookRepository.findBooksBySlugIn(Arrays.asList(slugs));
    }
}
