package com.ElasticSearch.getting.started.with.elastic.search.model;

import com.ElasticSearch.getting.started.with.elastic.search.entity.Facts;
import lombok.Data;

import java.util.List;
@Data
public class Response {
    List<Facts> factsList;
    String aiResponse;
}
