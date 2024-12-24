package com.ll.auth.domain.post.comment.dto;

import com.ll.auth.domain.post.comment.entity.PostComment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCommentDto {

    private long id;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private long postId;
    private long authorId;
    private String authorName;
    private String content;

    public PostCommentDto(PostComment postComment) {
        this.id = postComment.getId();
        this.createDate = postComment.getCreateDate();
        this.modifyDate = postComment.getModifiedDate();
        this.postId = postComment.getPost().getId();

        //post.getAuthor()로 접근하는 이유: @ManyToOne 관계를 통해 Post 엔티티는 작성자(Member)와 연관
        this.authorId = postComment.getAuthor().getId(); //관련 필드명이 author이므로 getAuthor()로 접근
        this.authorName = postComment.getAuthor().getName();
        this.content = postComment.getContent();
    }



}