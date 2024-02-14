package com.ElasticSearch.getting.started.with.elastic.search.service;

import com.google.cloud.vertexai.api.Candidate;
import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.generativeai.preview.ContentMaker;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import com.google.cloud.vertexai.generativeai.preview.PartMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public class GenerativeAIService {


    @Autowired
    GenerativeModel generativeModel;


    public String getGenerativeAIResponse(String prompt){
        StringBuilder response=new StringBuilder();
        try {

            for(Candidate candidate:generativeModel.generateContent(prompt).getCandidatesList()){
                System.out.println(candidate.getContent());
                response.append(candidate.getContent());
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.toString();
    }

    public String getGenerativeAiResponseWithImage(MultipartFile file, String prompt) {
        StringBuilder response =new StringBuilder();
        try{
            Content content= ContentMaker.fromMultiModalData(
                    PartMaker.fromMimeTypeAndData("image/png",file.getBytes()),prompt);
            for(Candidate candidate:generativeModel.generateContent(content).getCandidatesList()){
                response.append(candidate.getContent());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response.toString();
    }
}
