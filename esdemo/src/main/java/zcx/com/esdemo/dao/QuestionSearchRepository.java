package zcx.com.esdemo.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import zcx.com.esdemo.entity.Question;

public interface QuestionSearchRepository extends ElasticsearchRepository<Question, Long> {
}