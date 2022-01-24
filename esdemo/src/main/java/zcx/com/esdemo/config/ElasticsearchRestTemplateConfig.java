//package zcx.com.esdemo.config;
//
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
//
//@Configuration
//public class ElasticsearchRestTemplateConfig extends AbstractElasticsearchConfiguration {
//    @Value("${spring.elasticsearch.rest.uris}")
//    private String uris;

//    @Override
//    public RestHighLevelClient elasticsearchClient() {
//        ClientConfiguration configuration = ClientConfiguration.builder()
//                .connectedTo(uris)
//                .withConnectTimeout(10000)
//                .withSocketTimeout(30000)
//                .build();
//        return RestClients.create(configuration).rest();
//    }
//}
