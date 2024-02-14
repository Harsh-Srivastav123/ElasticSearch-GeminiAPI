package com.ElasticSearch.getting.started.with.elastic.search.configuration;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Component
public class ElasticConfiguration {

//    @Bean
//    public RestClient getRestClient() {
//        RestClient restClient = RestClient.builder(
//                new HttpHost("localhost", 9200)).build();
//        return restClient;
//    }
//
//    @Bean
//    public ElasticsearchTransport getElasticsearchTransport() {
//        return new RestClientTransport(
//                getRestClient(), new JacksonJsonpMapper());
//    }
//
//
//    @Bean
//    public ElasticsearchClient getElasticsearchClient(){
//        ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
//        return client;
//    }
    List<String> fields=new ArrayList<>();


    public Supplier<Query> createSupplierFuzzyQuery(String approximateTitle){
        Supplier<Query> supplier = ()->Query.of(q->q.fuzzy(createFuzzyQuery(approximateTitle)));
        return  supplier;
    }

    public   FuzzyQuery createFuzzyQuery(String approximateTitle) {

        val fuzzyQuery  = new FuzzyQuery.Builder();
        return  fuzzyQuery.field("title").value(approximateTitle).build();
    }


    public Supplier<Query> createSupplierMatchQuery(String approximateTitle){
        Supplier<Query> supplier = ()->Query.of(q->q.match(createMatchQuery(approximateTitle)));
        return  supplier;
    }

    public MatchQuery createMatchQuery(String approximateTitle) {

        val matchQuery=new MatchQuery.Builder();
        return  matchQuery.field("title").query(approximateTitle).build();
    }



    public Supplier<Query>  supplierQueryForMultiMatchQuery(String key ){
        fields.add("title");
        fields.add("description");

        Supplier<Query> supplier = ()->Query.of(q->q.multiMatch(multiMatchQuery(key, fields)));
        return supplier;
    }
    public MultiMatchQuery multiMatchQuery(String key , List<String> fields ){

        val multiMatch = new MultiMatchQuery.Builder();
        return   multiMatch.query(key).fields(fields).build();
    }

}
