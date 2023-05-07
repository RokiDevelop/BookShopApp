package com.example.bookshopapp.data.dto;

import com.example.bookshopapp.data.book.review.BookReviewEntity;
import com.example.bookshopapp.data.book.review.BookReviewLikeEntity;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookReviewDto {

    private BookReviewEntity bookReviewEntity;

    private Set<BookReviewLikeEntity> positiveBookReviewLikeEntityList = new HashSet<>();

    private Set<BookReviewLikeEntity> negativeBookReviewLikeEntityList = new HashSet<>();

    private Integer rating;

    public Integer getPositiveReviewLikeValue() {
        return positiveBookReviewLikeEntityList.size();
    }

    public Integer getNegativeReviewLikeValue() {
        return negativeBookReviewLikeEntityList.size();
    }

    public Integer getRating() {
        return positiveBookReviewLikeEntityList.size() - negativeBookReviewLikeEntityList.size();
    }

    public void addPositiveReviewLike(BookReviewLikeEntity reviewLike) {
        positiveBookReviewLikeEntityList.add(reviewLike);
    }

    public void addNegativeReviewLike(BookReviewLikeEntity reviewLike) {
        negativeBookReviewLikeEntityList.add(reviewLike);
    }

    public void removePositiveReviewLike(BookReviewLikeEntity reviewLike) {
        positiveBookReviewLikeEntityList.remove(reviewLike);
    }

    public void removeNegativeReviewLike(BookReviewLikeEntity reviewLike) {
        negativeBookReviewLikeEntityList.remove(reviewLike);
    }
}
