package zcx.com.example.mongodb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zcx.com.example.mongodb.entity.DemoEntity;
import zcx.com.example.mongodb.repository.DemoRepository;

import java.util.List;

@RestController
@RequestMapping("/jpa")
public class JPAController {

    @Autowired
    private DemoRepository demoRepository;


    @GetMapping()
    @ResponseBody
    public List<DemoEntity> test() {
        return demoRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public DemoEntity findDemoById(@PathVariable(value = "id") Long id) {
        DemoEntity demoEntity = demoRepository.findById(id).get();
        return demoEntity;
    }

    @PostMapping
    public void saveDemoTest(@RequestBody DemoEntity demoEntity) {
        Long lastId = demoRepository.findAll().stream().mapToLong(DemoEntity::getId).max().getAsLong();
        demoEntity.setId(lastId + 1);
        demoRepository.save(demoEntity);
    }

    @DeleteMapping("/{id}")
    public void removeDemoTest(@PathVariable(value = "id") Long id) {
        demoRepository.deleteById(id);
    }

    @PutMapping
    public void updateDemoTest(@RequestBody DemoEntity demoEntity) {
        demoRepository.save(demoEntity);
    }

}
