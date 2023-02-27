package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.genre.GenreEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/genres")
@Api(description = "genres data")
public class GenresPageController {

    @GetMapping("")
    public String genresPage(){
        return "genres/index";
    }

    @GetMapping("/{id}")
    public String genresById(@PathVariable(value = "id") int id, Model model){
        return "genres/slug";
    }

    @GetMapping("/api")
    @ApiOperation("method to get list of genres")
    public List<GenreEntity> genresApi(){
        //TODO
        return new ArrayList<>();
    }
}
