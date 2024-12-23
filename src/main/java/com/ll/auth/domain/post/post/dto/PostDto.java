package com.ll.auth.domain.post.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ll.auth.domain.post.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto{
    private long id;
    @JsonProperty("createdDatetime")
    private LocalDateTime createDate;
    @JsonProperty("modifiedDatetime")
    private LocalDateTime modifyDate;

    private long authorId;
    private String authorName;


    private String titile;
    private String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.createDate = post.getCreateDate();
        this.modifyDate = post.getModifiedDate();

        //post.getAuthor()로 접근하는 이유: @ManyToOne 관계를 통해 Post 엔티티는 작성자(Member)와 연관
        this.authorId = post.getAuthor().getId(); //관련 필드명이 author이므로 getAuthor()로 접근
        this.authorName = post.getAuthor().getName();

        this.titile = post.getTitle();
        this.content = post.getContent();
    }



}