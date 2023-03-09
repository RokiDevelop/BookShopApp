package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksData() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.getReferenceById(id);
    }

    public Page<Book> getPageBooksByAuthor(Author author, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksByAuthorId(author.getId(), nextPage);
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findAll(nextPage);
//                bookRepository.findBooksRecommended(nextPage) //TODO:need to make query
    }

    public Page<Book> getPageOfPopularBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findAll(nextPage);
//                bookRepository.findBooksPopular(nextPage)//TODO:need to make query
    }

    public Page<Book> getPageOfRecentBooks(Optional<String> optDateFrom, Optional<String> optDateTo, Optional<Integer> offset, Optional<Integer> limit) {

        int currentOffset = offset.orElse(0);
        int currentLimit = limit.orElse(20);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String stringDateFrom = optDateFrom.orElse(
                formatter.format(getDateWithShiftByDayByMonthByYear(0,-1,-10)));
        String stringDateTo = optDateTo.orElse(formatter.format(new Date()));

        LocalDate currentDateFrom = LocalDate.parse(stringDateFrom, formatter1);
        LocalDate currentDateTo = LocalDate.parse(stringDateTo, formatter1);

        Pageable nextPage = PageRequest.of(currentOffset,currentLimit);
        return bookRepository.findBooksByPubDateBetween(currentDateFrom.atStartOfDay(), currentDateTo.atStartOfDay(), nextPage);
    }

    private Date getDateWithShiftByDayByMonthByYear(int day, int month, int year){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DAY_OF_YEAR,day);
        cal.add(Calendar.YEAR,year);
        return cal.getTime();
    }
}
