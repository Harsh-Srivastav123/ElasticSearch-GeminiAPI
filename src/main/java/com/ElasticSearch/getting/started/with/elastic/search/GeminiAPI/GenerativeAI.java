package com.ElasticSearch.getting.started.with.elastic.search.GeminiAPI;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class GenerativeAI {
    String projectId="fast-web-414119";
    String location="us-central1";
    String modelName="gemini-pro-vision";

    @Bean
    public GenerativeModel generativeModel(){
        VertexAI vertexAI = null;
        try {
            vertexAI = new VertexAI(projectId, location);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GenerativeModel model = new GenerativeModel(modelName, vertexAI);
        return model;
    }
}
