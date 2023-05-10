package com.example.bookshopapp.data.book.rating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RatingDto {
    private float ratingValue;
    private Integer amountRatings;
    private Map<Integer, Integer> integerMap;

    public float getRatingValue() {
        return Math.round(ratingValue);
    }
}
