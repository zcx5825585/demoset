package zcx.com.esdemo.dao;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import zcx.com.esdemo.entity.Book;

public interface BookSearchRepository extends ElasticsearchRepository<Book, Long> {

    @Query("{\"match\": {\"name\": {\"query\": \"?0\"}}}")
    Iterable<Book> searchBook(String keyword);
}