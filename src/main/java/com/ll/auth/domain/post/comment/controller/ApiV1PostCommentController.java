package com.ll.auth.domain.post.comment.controller;

import com.ll.auth.domain.member.member.entity.Member;
import com.ll.auth.domain.post.comment.dto.PostCommentDto;
import com.ll.auth.domain.post.comment.entity.PostComment;
import com.ll.auth.domain.post.post.entity.Post;
import com.ll.auth.domain.post.post.service.PostService;
import com.ll.auth.global.exceptions.ServiceException;
import com.ll.auth.global.rq.Rq;
import com.ll.auth.global.rsData.RsData;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class ApiV1PostCommentController {

    @Autowired
    @Lazy
    private ApiV1PostCommentController self;

    private final PostService postService;
    private final Rq rq;

    private final EntityManager em;

    @GetMapping
    public List<PostCommentDto> getItems(
            @PathVariable long postId
    ){

        Post post = postService.findById(postId)
                .orElseThrow(() -> new ServiceException("401-1","%d번 글은 존재하지 않습니다.".formatted(postId)));

        return post
                .getCommentsByOrderByIdDesc()
                .stream()
                .map(PostCommentDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public PostCommentDto getItem(
            @PathVariable long postId,
            @PathVariable long id
    ){

        Post post = postService.findById(postId)
                .orElseThrow(() -> new ServiceException("401-1","%d번 글은 존재하지 않습니다.".formatted(postId)));

        return post
                .getCommentById(id)
                .map(PostCommentDto::new)
                .orElseThrow(
                        () -> new ServiceException("401-2","%d번 댓글은 존재하지 않습니다.".formatted(id))
                );
    }

    public record PostCommentWriteReqBody(
            @NotBlank @Length(min = 2) String content){
    }


    @PostMapping
    public RsData<Void> writeItem(
            @PathVariable long postId,
            @RequestBody @Valid PostCommentWriteReqBody reqBody){

        PostComment postComment = self._writeItem(postId, reqBody);

        em.flush();

        return new RsData<>(
                "201-1", "%d번 글을 등록했습니다.".formatted(postComment.getId())
        );

    }

    //트랜잭션의 insert시기에 따른 자료값 출력을 위해 일반 매서드를 만듦
    @Transactional
    public PostComment _writeItem(
            @PathVariable long postId,
            @RequestBody @Valid PostCommentWriteReqBody reqBody){

        Member actor = rq.checkAuthentication();

        Post post = postService.findById(postId).orElseThrow(
                () -> new ServiceException("401-1","%d번 글은 존재하지 않습니다.".formatted(postId))
        );

      return  post.addComment(
                actor,
                reqBody.content
        );

    }


}
