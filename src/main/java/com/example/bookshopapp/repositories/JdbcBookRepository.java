package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.Book;
import liquibase.pro.packaged.L;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcBookRepository implements BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long getBooksCount() {
        String q = "SELECT count(*) FROM BOOK";
        return jdbcTemplate.queryForObject(q, Long.class);
    }

    public List<Book> getPopularBooks(Pageable nextPage) {
        String q = "WITH PAID_BOOKS AS ( " +
                "    SELECT B2U.BOOK_ID , COUNT(B2U.USER_ID) AS PAID_COUNT " +
                "    FROM BOOK2USER B2U " +
                "    WHERE B2U.TYPE_ID = (SELECT B2UT.ID FROM BOOK2USER_TYPE AS B2UT WHERE B2UT.NAME LIKE 'PAID')  " +
                "    GROUP BY B2U.BOOK_ID " +
                "  ), " +
                "  CART_BOOKS AS ( " +
                "    SELECT B2U.BOOK_ID , COUNT(B2U.USER_ID) AS CART_COUNT " +
                "    FROM BOOK2USER B2U " +
                "    WHERE B2U.TYPE_ID = (SELECT B2UT.ID FROM BOOK2USER_TYPE AS B2UT WHERE B2UT.NAME LIKE 'CART')  " +
                "    GROUP BY B2U.BOOK_ID " +
                "  ), " +
                "  KEPT_BOOKS AS ( " +
                "    SELECT B2U.BOOK_ID , COUNT(B2U.USER_ID) AS KEPT_COUNT " +
                "    FROM BOOK2USER B2U " +
                "    WHERE B2U.TYPE_ID = (SELECT B2UT.ID FROM BOOK2USER_TYPE AS B2UT WHERE B2UT.NAME LIKE 'KEPT')  " +
                "    GROUP BY B2U.BOOK_ID " +
                "  ) " +
                "SELECT B.*,  " +
                "       COALESCE(PAID_BOOKS.PAID_COUNT, 0) * 1 +  " +
                "       COALESCE(CART_BOOKS.CART_COUNT, 0) * 0.7 +  " +
                "       COALESCE(KEPT_BOOKS.KEPT_COUNT, 0) * 0.4 AS RATING " +
                "FROM BOOK B " +
                "LEFT JOIN PAID_BOOKS ON B.ID = PAID_BOOKS.BOOK_ID " +
                "LEFT JOIN CART_BOOKS ON B.ID = CART_BOOKS.BOOK_ID " +
                "LEFT JOIN KEPT_BOOKS ON B.ID = KEPT_BOOKS.BOOK_ID " +
                "ORDER BY RATING DESC LIMIT " + nextPage.getPageSize() + " offset " + nextPage.getOffset();
        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Book.class));
    }
}
