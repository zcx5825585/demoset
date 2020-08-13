package zcx.com.esdemo.controller;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zcx.com.esdemo.dao.BookSearchRepository;
import zcx.com.esdemo.entity.Book;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    BookSearchRepository bookSearchRepository;


    @PostMapping
    public void testSaveArticleIndex() {
        Book book = new Book();
        book.setId(1);
        book.setName("three body");
        bookSearchRepository.save(book);
    }

    @GetMapping
    public List<Book> testSearch(String keyword) {
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("name", keyword);

        Iterable<Book> searchResult = bookSearchRepository.search(queryBuilder);
        Iterator<Book> iterator = searchResult.iterator();
        List<Book> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }
}
