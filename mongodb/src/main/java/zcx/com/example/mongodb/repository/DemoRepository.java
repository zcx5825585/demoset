package zcx.com.example.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import zcx.com.example.mongodb.entity.DemoEntity;

public interface DemoRepository extends MongoRepository<DemoEntity,Long> {
}
