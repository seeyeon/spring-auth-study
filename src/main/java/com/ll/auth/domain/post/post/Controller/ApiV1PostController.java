package com.ll.auth.domain.post.post.Controller;

import com.ll.auth.domain.member.member.entity.Member;
import com.ll.auth.domain.member.member.service.MemberService;
import com.ll.auth.domain.post.post.dto.PostDto;
import com.ll.auth.domain.post.post.entity.Post;
import com.ll.auth.domain.post.post.service.PostService;
import com.ll.auth.global.jpa.entity.BaseTime;
import com.ll.auth.global.rq.Rq;
import com.ll.auth.global.rsData.RsData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class ApiV1PostController extends BaseTime {
    private final PostService postService;
    private final MemberService memberService;
    private final Rq rq;


    @GetMapping
    public List<PostDto> getItems() {
        return postService.findAllByOrderByIdDesc()
                .stream()
                .map(PostDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public PostDto getItems (@PathVariable long id) {
        return  postService.findById(id)
                .map(PostDto::new)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public RsData<Void> deleteItem(@PathVariable long id,
                                   HttpServletRequest req){

        Member actor = rq.checkAuthentication();

        Post post = postService.findById(id).get();

        post.checkActorCanDelete(actor);

        postService.delete(post);

        return new RsData<>("204-1","%d번 글이 삭제되었습니다.".formatted(id));

    }

    record PostModifyReqBody(
            @NotBlank @Length(min = 2) String title,
            @NotBlank @Length(min = 2) String content ){
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData<PostDto> modifyItem(@PathVariable long id, @RequestBody @Valid PostModifyReqBody reqBody){

        Member actor = rq.checkAuthentication();

        Post post = postService.findById(id).get();

        post.checkActorCanModify(actor);

        postService.modify(post, reqBody.title, reqBody.content);

        return new RsData<>("200-1","%d번 글을 수정했습니다.".formatted(id),
                new PostDto(post)
        );
    }

    public record PostWriteReqBody(
            @NotBlank @Length(min = 2) String title,
            @NotBlank @Length(min = 2) String content){
    }


    @PostMapping
    public RsData<PostDto> writeItem(@RequestBody @Valid PostWriteReqBody reqBody){

        Member actor = rq.checkAuthentication();

        Post post = postService.write(actor, reqBody.title, reqBody.content);

        return new RsData<>(
                        "201-1", "%d번 글을 등록했습니다.".formatted(post.getId()),
                                    new PostDto(post)

                );

    }

}