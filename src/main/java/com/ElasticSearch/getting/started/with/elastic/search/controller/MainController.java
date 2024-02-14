package com.ElasticSearch.getting.started.with.elastic.search.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.ElasticSearch.getting.started.with.elastic.search.entity.Facts;
import com.ElasticSearch.getting.started.with.elastic.search.model.Response;
import com.ElasticSearch.getting.started.with.elastic.search.service.FactsService;
import com.ElasticSearch.getting.started.with.elastic.search.service.GenerativeAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("facts")
public class MainController {

    @Autowired
    FactsService factsService;


    @Autowired
    GenerativeAIService generativeAIService;

    @PostMapping("/save")
    public ResponseEntity<String> saveFacts(@RequestBody Facts facts){
        factsService.saveFacts(facts);
        return new ResponseEntity<>("Facts saved successfully", HttpStatus.OK);
    }
    @PostMapping("/saveMultiple")
    public ResponseEntity<String> saveMultiple(@RequestBody List<Facts> factsList){
        boolean status=factsService.saveMultipleFacts(factsList);
        if(status){
            return new ResponseEntity<>("All facts are saved",HttpStatus.OK);
        }
        return new ResponseEntity<>("Unable to save all",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getAll")
    public Iterable<Facts> getAllFacts(){
        return factsService.getAllFacts();
    }

    @GetMapping("/aiResponse")
    public String aiResponse(
            @RequestParam("prompt") String prompt
    ){
        return generativeAIService.getGenerativeAIResponse(prompt);
    }


    @GetMapping(path="/multiFormPrompt", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String aiResponseWithImage(
            @RequestParam("image") MultipartFile file,
            @RequestParam("prompt") String prompt
            ){
        return generativeAIService.getGenerativeAiResponseWithImage(file,prompt);
    }

    @PostMapping("/fuzzyQuery")
    public Response fuzzySearch(@RequestParam("title") String title,
                                @RequestParam(value = "image",required = false) MultipartFile file
    ){
        return factsService.search(title,file);
    }

    @PostMapping("/imageQuery")
    public Response imageQuery(
            @RequestParam(value = "image") MultipartFile file,
            @RequestParam(value = "text",required = false) String prompt

    ){
        return factsService.imageQuery(prompt,file);
    }

}
