package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/books")
public class BookCartKeptController {

    private final BookService bookService;

    @Autowired
    public BookCartKeptController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }
    @ModelAttribute(name = "bookKept")
    public List<Book> bookKept() {
        return new ArrayList<>();
    }

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model) {
        if (cartContents == null || cartContents.isEmpty()) {
            model.addAttribute("isCartEmpty", true);
        } else {
            model.addAttribute("isCartEmpty", false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents =
                    cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
            String[] cookieSlugs = cartContents.split("/");
            List<Book> booksFromCookieSlug = bookService.findBooksBySlugIn(cookieSlugs);
            model.addAttribute("bookCart", booksFromCookieSlug);
        }
        return "cart";
    }


    @GetMapping("/postponed")
    public String handlePostponedRequest(@CookieValue(value = "keptContents", required = false) String keptContents,
    Model model) {
        if (keptContents == null || keptContents.isEmpty()) {
            model.addAttribute("isKeptEmpty", true);
        } else {
            model.addAttribute("isKeptEmpty", false);
            keptContents = keptContents.startsWith("/") ? keptContents.substring(1) : keptContents;
            keptContents =
                    keptContents.endsWith("/") ? keptContents.substring(0, keptContents.length() - 1) : keptContents;
            String[] cookieSlugs = keptContents.split("/");
            List<Book> booksFromCookieSlug = bookService.findBooksBySlugIn(cookieSlugs);
            model.addAttribute("bookKept", booksFromCookieSlug);
        }
        return "postponed";
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String removeCart(@PathVariable("slug") String slug,
            @CookieValue(name = "cartContents", required = false) String cartContents,
            HttpServletResponse response, Model model) {
        if (cartContents != null && !cartContents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty",false);
        }else {
            model.addAttribute("isCartEmpty",true);
        }

        return "redirect:/books/cart";
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug,
                                         @CookieValue(name = "cartContents", required = false) String cartContents,
                                         @CookieValue(name = "keptContents", required = false) String keptContents,
                                         @RequestParam(value = "bookids", required = false) String[] bookids,
                                         @RequestParam(value = "status", required = false) String status,
                                         HttpServletResponse response,
                                         Model model) {
        if (status.equals("CART")) {
            changeBooksStatus(slug, cartContents, "cartContents", "isCartEmpty", response, model);
        } else if (status.equals("KEPT")) {
            changeBooksStatus(slug, keptContents, "keptContents", "isKeptEmpty", response, model);
        } else if (status.equals("UNLINK")) {

        }


        return "redirect:/books/" + slug;
    }

    private void changeBooksStatus(String slug,
                                         String contents,
                                         String cookieName,
                                         String attrName,
                                         HttpServletResponse response,
                                         Model model) {
        if (contents == null || contents.isEmpty()) {
            Cookie cookie = new Cookie(cookieName, slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute(attrName, false);
        } else if (!contents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(contents).add(slug);
            Cookie cookie = new Cookie(cookieName, stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
        }
    }
}
