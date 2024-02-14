package com.ElasticSearch.getting.started.with.elastic.search.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.ElasticSearch.getting.started.with.elastic.search.entity.Facts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public interface FactsRepository extends ElasticsearchRepository<Facts,String> {
//
//    @Autowired
//    ElasticsearchClient elasticsearchClient;
//    final String indexName = "facts";


//    public String createOrUpdateDocument(Facts facts) throws IOException {
//        facts.setId(UUID.randomUUID().toString());
//
//        IndexResponse response = elasticsearchClient.index(i -> i
//                .index(indexName)
//                .id(facts.getId())
//                .document(facts)
//        );
//        if(response.result().name().equals("Created")){
//            return "Document has been successfully created.";
//        }else if(response.result().name().equals("Updated")){
//            return new String("Document has been successfully updated.").toString();
//        }
//        return new String("Error while performing the operation.").toString();
//    }
//
//    public Facts getDocumentById(String FactsId) throws IOException{
//        Facts facts = null;
//        GetResponse<Facts> response = elasticsearchClient.get(g -> g
//                        .index(indexName)
//                        .id(FactsId),
//                Facts.class
//        );
//
//        if (response.found()) {
//           facts= response.source();
//            System.out.println("Facts name " +facts.getTitle());
//        } else {
//            System.out.println ("Facts not found");
//        }
//
//        return facts;
//    }
//
//    public String deleteDocumentById(String FactsId) throws IOException {
//
//        DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(FactsId));
//
//        DeleteResponse deleteResponse = elasticsearchClient.delete(request);
//        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
//            return new String("Facts with id " + deleteResponse.id() + " has been deleted.").toString();
//        }
//        System.out.println("Facts not found");
//        return new String("Facts with id " + deleteResponse.id()+" does not exist.").toString();
//
//    }
//
//    public List<Facts> searchAllDocuments() throws IOException {
//
//        SearchRequest searchRequest =  SearchRequest.of(s -> s.index(indexName));
//        SearchResponse searchResponse =  elasticsearchClient.search(searchRequest, Facts.class);
//        List<Hit> hits = searchResponse.hits().hits();
//        List<Facts> factsList = new ArrayList<>();
//        for(Hit object : hits){
//
//            System.out.print(((Facts) object.source()));
//           factsList.add((Facts) object.source());
//
//        }
//        return factsList;
//    }

}
