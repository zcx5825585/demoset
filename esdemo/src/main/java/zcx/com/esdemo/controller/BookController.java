package zcx.com.esdemo.controller;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
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
    public Object testSaveArticleIndex(@RequestBody Book book) {
        return bookSearchRepository.save(book);
    }

    @DeleteMapping
    public void clear() {
        bookSearchRepository.deleteAll();
    }

    @GetMapping("/all")
    public List<Book> findAll() {
        Iterable<Book> searchResult = bookSearchRepository.findAll();
        Iterator<Book> iterator = searchResult.iterator();
        List<Book> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    @GetMapping
    public List<Book> testSearch(String keyword) {
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("name", keyword);

        Iterable<Book> searchResult = bookSearchRepository.searchBook(keyword);
        Iterator<Book> iterator = searchResult.iterator();
        List<Book> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }
}
