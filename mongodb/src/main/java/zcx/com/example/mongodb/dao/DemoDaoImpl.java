package zcx.com.example.mongodb.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import zcx.com.example.mongodb.entity.DemoEntity;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述: Demo DAO 实现
 *
 * @author yanpenglei
 * @create 2018-02-03 16:57
 **/
@Component
public class DemoDaoImpl implements DemoDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveDemo(DemoEntity demoEntity) {
        Long lastId = mongoTemplate.find(new Query(), DemoEntity.class).stream().mapToLong(DemoEntity::getId).max().getAsLong();
        demoEntity.setId(lastId + 1);
        mongoTemplate.save(demoEntity);
    }

    @Override
    public void removeDemo(Long id) {
        mongoTemplate.remove(id);
    }

    @Override
    public void updateDemo(DemoEntity demoEntity) {
        Query query = new Query(Criteria.where("id").is(demoEntity.getId()));

        Update update = new Update();
        update.set("description", demoEntity.getDescription());

        mongoTemplate.updateFirst(query, update, DemoEntity.class);
    }

    @Override
    public DemoEntity findDemoById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        DemoEntity demoEntity2 = mongoTemplate.findOne(query, DemoEntity.class);
        DemoEntity demoEntity = mongoTemplate.findOne(query, DemoEntity.class);
        return demoEntity;
    }

    @Override
    public List<DemoEntity> list() {
        List<DemoEntity> list = mongoTemplate.find(new Query(), DemoEntity.class);
        return list;
    }
}