
package com.ElasticSearch.getting.started.with.elastic.search.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "facts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Facts {

    @Id
    String id;
    @Field
    private String category;
    @Field
    private String consequence;
    @Field
    private String description;
    @Field
    private String time_field;
    @Field
    private String title;



}
