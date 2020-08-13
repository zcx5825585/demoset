package zcx.com.esdemo.controller;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EsController {
    @Resource
    RestHighLevelClient elasticsearchClient;

    private String index = "spring-data";

    private String type = "elasticsearch";




    @PostMapping
    public void test(@RequestBody String json) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index, type);
        String source = json;
        indexRequest.source(source, XContentType.JSON);
        elasticsearchClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    @GetMapping
    public SearchResponse queryTest() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/search")
    public SearchResponse searchTest(String keyword) {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(10);

//        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("test",keyword);
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("test",keyword);
        sourceBuilder.query(queryBuilder);

        searchRequest.source(sourceBuilder);
        try {
            SearchResponse response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/{id}")
    public GetResponse getTest(@PathVariable(value = "id") String id) {
        GetRequest getRequest=new GetRequest(index,type,id);
        try {
            return elasticsearchClient.get(getRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("/{id}")
    public void updateTest(@PathVariable(value = "id") String id) {
        UpdateRequest updateRequest = new UpdateRequest(index, type, id);
        updateRequest.doc("test", "bbb");
        try {
            elasticsearchClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable(value = "id") String id) {
        DeleteRequest request = new DeleteRequest(index, id);
        try {
            DeleteResponse deleteResponse = elasticsearchClient.delete(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
