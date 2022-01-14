package zcx.com.esdemo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Similarity;

import java.io.Serializable;
import java.util.List;

@Document(indexName="spring-data",type="book",indexStoreType="fs",shards=5,replicas=1,refreshInterval="-1")
public class Book implements Serializable {
    @Id
    private Integer id;
        @Field(index = false,similarity = Similarity.BM25)
    private String describe;
//    @Field(index = false)
    private String name;

    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
