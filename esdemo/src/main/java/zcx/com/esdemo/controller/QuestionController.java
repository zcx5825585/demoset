package zcx.com.esdemo.controller;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import zcx.com.esdemo.dao.QuestionSearchRepository;
import zcx.com.esdemo.entity.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionSearchRepository questionSearchRepository;

    @DeleteMapping
    public void clear() {
        questionSearchRepository.deleteAll();
    }

    @GetMapping
    public List<Question> testSearch(String keyword) {
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", keyword);

        Iterable<Question> searchResult = questionSearchRepository.search(queryBuilder);
        List<Question> result = new ArrayList<>();
        searchResult.forEach(result::add);
//        Iterator<Question> iterator = searchResult.iterator();
//        while (iterator.hasNext()) {
//            result.add(iterator.next());
//        }
        return result;
    }

    @PostMapping("/input")
    public void input() throws IOException {
        int count = 0;
        for (Question question : getSavedQuestionsList()) {
            System.out.println(++count + " " + question.getTitle());
            questionSearchRepository.save(question);
        }
    }

    public List<Question> getSavedQuestionsList() {
        List<Question> savedQuestionsList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("C:\\Users\\DELL\\Desktop\\questions");
             InputStreamReader isr = new InputStreamReader(fis, "utf-8");
             BufferedReader reader = new BufferedReader(isr)) {
            while (reader.ready()) {
                String line = reader.readLine();
                if (!StringUtils.isEmpty(line))
                    savedQuestionsList.add(new Question(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedQuestionsList;
    }
}
