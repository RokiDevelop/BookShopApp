package com.example.bookshopapp.data.dto;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookWithGenreDto {
    Integer book_id;
    Integer genreId;
}
