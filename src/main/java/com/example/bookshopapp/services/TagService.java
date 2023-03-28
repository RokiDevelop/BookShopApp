package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.book.links.Book2TagEntity;
import com.example.bookshopapp.data.dto.TagDto;
import com.example.bookshopapp.data.tag.TagEntity;
import com.example.bookshopapp.repositories.Book2TagRepository;
import com.example.bookshopapp.repositories.JpaBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final Book2TagRepository book2TagRepository;
    private final JpaBookRepository bookRepository;

    @Autowired
    public TagService(Book2TagRepository book2TagRepository, JpaBookRepository bookRepository) {
        this.book2TagRepository = book2TagRepository;
        this.bookRepository = bookRepository;
    }


    public Page<Book> getBooksByTagId(Long id, Integer offset, Integer limit) {
        int currentOffset = offset == null ? 0 : offset;
        int currentLimit = limit == null ? 20 : limit;

        Pageable nextPage = PageRequest.of(currentOffset,currentLimit);

        return bookRepository.findAllByTagId(id, nextPage);
    }

    public List<TagDto> getTagsWithAmount() {
        List<Book2TagEntity> book2TagList = book2TagRepository.findAll();

        List<TagDto> listTagDto = book2TagList.stream()
                .collect(Collectors.groupingBy(Book2TagEntity::getTag))
                .entrySet()
                .stream()
                .map(entry -> {
                    TagEntity tag = entry.getValue().get(0).getTag();
                    Integer amountBook = (int) entry.getValue()
                            .stream()
                            .map(Book2TagEntity::getBook)
                            .distinct()
                            .count();
                    return new TagDto(tag, amountBook);
                })
                .collect(Collectors.toList());
        return listTagDto;
    }
}
