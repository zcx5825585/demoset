package zcx.com.example.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import zcx.com.example.mongodb.entity.DemoEntity;

import java.util.List;

public interface DemoRepository extends MongoRepository<DemoEntity,Long> {

    @Query("{'description' : /?0/ }")
    List<DemoEntity> testSearch(String description);
}
