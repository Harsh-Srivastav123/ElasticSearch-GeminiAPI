package com.ElasticSearch.getting.started.with.elastic.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.ElasticSearch.getting.started.with.elastic.search.configuration.ElasticConfiguration;
import com.ElasticSearch.getting.started.with.elastic.search.entity.Facts;
import com.ElasticSearch.getting.started.with.elastic.search.model.Response;
import com.ElasticSearch.getting.started.with.elastic.search.repository.FactsRepository;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
@Slf4j
public class FactsService {
    @Autowired
    FactsRepository factsDAO;
    @Autowired
    GenerativeModel generativeModel;
    @Autowired
    ElasticConfiguration elasticConfiguration;
    @Autowired
    GenerativeAIService generativeAIService;
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Autowired

    public Iterable<Facts> getAllFacts() {
        return factsDAO.findAll();
    }

    public Facts getFactsById(String id) {
        return factsDAO.findById(id).get();
    }

    public void saveFacts(Facts facts) {
        factsDAO.save(facts);
    }

    public boolean saveMultipleFacts(List<Facts> factsList) {
        try {
            for (Facts facts : factsList) {
                factsDAO.save(facts);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


//
//    public String getGenerativeAIResponse(String prompt){
//        StringBuilder response=new StringBuilder();
//        try {
//
//            for(Candidate candidate:generativeModel.generateContent("what is prompt").getCandidatesList()){
//                System.out.println(candidate.getContent());
//                response.append(candidate.getContent());
//            }
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return response.toString();
//    }
//
//    public String getGenerativeAiResponseWithImage(MultipartFile file, String prompt) {
//    }


    public Response search(String approximateTitle, MultipartFile file) {
        Response response = new Response();
        if ( file!=null && !file.isEmpty()) {
            response.setAiResponse(generativeAIService.getGenerativeAiResponseWithImage(file, approximateTitle));
        } else {
            response.setAiResponse(generativeAIService.getGenerativeAIResponse(approximateTitle));
        }

        List<Facts> factsList = new ArrayList<>();
        Supplier<Query> supplier = elasticConfiguration.createSupplierMatchQuery(approximateTitle);

        SearchResponse<Facts> responseMatch = null;
        try {
            responseMatch = elasticsearchClient.search(s -> s.index("facts").query(supplier.get()), Facts.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (responseMatch != null && responseMatch.hits() != null && responseMatch.hits().hits().size() > 0) {
            List<Hit<Facts>> hitList = responseMatch.hits().hits();
            System.out.println(hitList);

            for (Hit<Facts> hit : hitList) {
                factsList.add(hit.source());
            }
            response.setFactsList(factsList);
            return response;
        }
        Supplier<Query> newSupplier = elasticConfiguration.createSupplierFuzzyQuery(approximateTitle);
        SearchResponse<Facts> responseFuzzy = null;
        try {
            responseFuzzy = elasticsearchClient.search(s -> s.index("facts").query(newSupplier.get()), Facts.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("elasticsearch supplier fuzzy query " + supplier.get().toString());
        List<Hit<Facts>> hitList = responseFuzzy.hits().hits();
        System.out.println(hitList);

        for (Hit<Facts> hit : hitList) {
            factsList.add(hit.source());
        }
        response.setFactsList(factsList);
        return response;
    }

    public Response imageQuery(String prompt, MultipartFile file) {



        Response response = new Response();



        if(prompt==null){
            prompt="Describe the image";
        }
        response.setAiResponse(generativeAIService.getGenerativeAiResponseWithImage(file,prompt));
        Supplier<Query> supplier=elasticConfiguration.supplierQueryForMultiMatchQuery(response.getAiResponse());
        SearchResponse<Facts> searchResponse=null;
        try{
            searchResponse=elasticsearchClient.search(s -> s.index("facts").query(supplier.get()), Facts.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        List<Facts> factsList=new ArrayList<>();
        List<Hit<Facts>> hitList = searchResponse.hits().hits();
        System.out.println(hitList);

        for (Hit<Facts> hit : hitList) {
            factsList.add(hit.source());
        }
        response.setFactsList(factsList);
        return response;
    }
}
