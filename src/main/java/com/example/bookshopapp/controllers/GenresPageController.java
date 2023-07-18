package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.genre.GenreEntity;
import com.example.bookshopapp.data.genre.genreTree.GenreNode;
import com.example.bookshopapp.services.GenreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/genres")
@Api(description = "genres data")
public class GenresPageController {
    private final GenreService genreService;

    @Autowired
    public GenresPageController(GenreService genreService) {
        this.genreService = genreService;
    }

    @ModelAttribute(value = "genreData")
    public List<GenreNode> getMapGenre() {
        return genreService.getGenreAmountBookMap();
    }

    @GetMapping("")
    public String genresPage(){
        return "genres/index";
    }

    @GetMapping("/api")
    @ApiOperation("method to get list of genres")
    public List<GenreEntity> genresApi(){
        return genreService.getAllGenres();
    }
}
