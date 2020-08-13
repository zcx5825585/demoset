package zcx.com.esdemo.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import zcx.com.esdemo.entity.Book;

public interface BookSearchRepository extends ElasticsearchRepository<Book, Long> {
}