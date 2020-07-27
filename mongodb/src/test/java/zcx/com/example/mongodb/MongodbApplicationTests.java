package zcx.com.example.mongodb;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zcx.com.example.mongodb.dao.DemoDao;
import zcx.com.example.mongodb.entity.DemoEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongodbApplication.class)
class MongodbApplicationTests {

    @Autowired
    private DemoDao demoDao;

    @Test
    public void saveDemoTest() {

        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setId(1L);
        demoEntity.setDescription("entity_1");

        demoDao.saveDemo(demoEntity);

        demoEntity = new DemoEntity();
        demoEntity.setId(2L);
        demoEntity.setDescription("entity_2");

        demoDao.saveDemo(demoEntity);
    }

    @Test
    public void removeDemoTest() {
        demoDao.removeDemo(2L);
    }

    @Test
    public void updateDemoTest() {
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setId(1L);
        demoEntity.setDescription("update_entity_1");

        demoDao.updateDemo(demoEntity);
    }

    @Test
    public void findDemoByIdTest() {
        DemoEntity demoEntity = demoDao.findDemoById(1L);
        System.out.println(JSONObject.toJSONString(demoEntity));
    }

}
