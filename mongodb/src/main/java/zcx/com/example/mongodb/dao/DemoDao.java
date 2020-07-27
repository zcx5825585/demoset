package zcx.com.example.mongodb.dao;

import zcx.com.example.mongodb.entity.DemoEntity;

import java.util.List;

public interface DemoDao {

    void saveDemo(DemoEntity demoEntity);

    void removeDemo(Long id);

    void updateDemo(DemoEntity demoEntity);

    DemoEntity findDemoById(Long id);

    List<DemoEntity> list();
}