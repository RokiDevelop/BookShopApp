package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.book.rating.BookRating;
import com.example.bookshopapp.data.book.rating.RatingDto;
import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.repositories.IBookRatingRepository;
import com.example.bookshopapp.repositories.IUserRepository;
import com.example.bookshopapp.repositories.JpaBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookRatingService {
    private final IBookRatingRepository ratingRepository;
    private final IUserRepository userRepository;
    private final JpaBookRepository bookRepository;

    @Autowired
    public BookRatingService(IBookRatingRepository ratingRepository, IUserRepository userRepository, JpaBookRepository bookRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public BookRating saveBookRating(Integer userId, Integer bookId, LocalDateTime time, Byte value) {
        UserEntity user = userRepository.getReferenceById(userId);
        Book book = bookRepository.getReferenceById(bookId);

        BookRating bookRating = ratingRepository.findBookRatingByUserAndBook(user, book);

        if (bookRating != null) {
            ratingRepository.updateValueById(bookRating.getId(), value);
            return bookRating;
        }

        return ratingRepository.save(new BookRating(user,book,time,value));
    }

    public RatingDto getRatingByBookId(int id) {
        List<BookRating> bookRatingList = ratingRepository.findBookRatingsByBookId(id);

        RatingDto ratingDto = new RatingDto();

        int ratingSum = 0;
        int amountRating = bookRatingList.size();

        for (BookRating br : bookRatingList) {
            ratingSum += br.getValue();
        }

        Map<Integer, Integer> mapRating = bookRatingList.stream()
                .collect(Collectors.groupingBy(bookRating -> (int) bookRating.getValue(),
                        Collectors.mapping(bookRating -> (int) bookRating.getValue(),
                                Collectors.summingInt(x -> 1))));

        ratingDto.setAmountRatings(amountRating);
        ratingDto.setRatingValue((float) ratingSum / amountRating);
        ratingDto.setIntegerMap(mapRating);

        return ratingDto;
    }
}
