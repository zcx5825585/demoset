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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    BookSearchRepository bookSearchRepository;

    @PostMapping("all")
    public Object init() {
        Book book1 = new Book();
        book1.setId(1);
        book1.setName("论语");
        book1.setDescribe(null);
        book1.setTags(Arrays.asList("中国", "名著"));
        Book book2 = new Book();
        book2.setId(2);
        book2.setName("三体");
        book2.setDescribe(null);
        book2.setTags(Arrays.asList("中国", "科幻", "小说"));
        Book book3 = new Book();
        book3.setId(3);
        book3.setName("三体 黑暗森林");
        book3.setDescribe(null);
        book3.setTags(Arrays.asList("中国", "科幻", "小说"));
        Book book4 = new Book();
        book4.setId(4);
        book4.setName("沙丘");
        book4.setDescribe(null);
        book4.setTags(Arrays.asList("美国", "科幻", "小说"));
        bookSearchRepository.save(book1);
        bookSearchRepository.save(book2);
        bookSearchRepository.save(book3);
        bookSearchRepository.save(book4);

        return "true";
    }

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
