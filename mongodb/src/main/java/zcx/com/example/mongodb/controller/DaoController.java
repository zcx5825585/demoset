package zcx.com.example.mongodb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zcx.com.example.mongodb.dao.DemoDao;
import zcx.com.example.mongodb.entity.DemoEntity;

import java.util.List;


@RestController
@RequestMapping("/dao")
public class DaoController {
    @Autowired
    private DemoDao demoDao;


    @PostMapping
    public void saveDemoTest(@RequestBody DemoEntity demoEntity) {
        demoDao.saveDemo(demoEntity);
    }

    @DeleteMapping("/{id}")
    public void removeDemoTest(@PathVariable(value = "id") Long id) {
        demoDao.removeDemo(id);
    }

    @PutMapping
    public void updateDemoTest(@RequestBody DemoEntity demoEntity) {
        demoDao.updateDemo(demoEntity);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public DemoEntity findDemoByIdTest(@PathVariable(value = "id") Long id) {
        DemoEntity demoEntity = demoDao.findDemoById(id);
        return demoEntity;
    }

    @GetMapping
    @ResponseBody
    public List<DemoEntity> list() {
        return demoDao.list();
    }
}
