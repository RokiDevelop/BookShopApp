package com.example.bookshopapp.data.dto;

import com.example.bookshopapp.data.tag.TagEntity;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private TagEntity tag;
    private int amount;
}
