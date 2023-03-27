package com.example.bookshopapp.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BookWithGenreDto {
    Integer book_id;
    Integer genreId;
}
