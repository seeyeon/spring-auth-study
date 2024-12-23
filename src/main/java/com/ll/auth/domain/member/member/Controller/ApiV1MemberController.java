package com.ll.auth.domain.member.member.Controller;

import com.ll.auth.domain.member.member.dto.MemberDto;
import com.ll.auth.domain.member.member.entity.Member;
import com.ll.auth.domain.member.member.service.MemberService;
import com.ll.auth.global.jpa.entity.BaseTime;
import com.ll.auth.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@AllArgsConstructor
public class ApiV1MemberController extends BaseTime {
    private final MemberService memberService;

    public record MemberWriteReqBody(
            @NotBlank @Length(min = 4) String username,
            @NotBlank @Length(min = 4) String password,
            @NotBlank @Length(min = 3) String nickname){
    }



    @PostMapping("/join")
    public RsData<MemberDto> join(@RequestBody @Valid MemberWriteReqBody reqBody){
        Member member = memberService.join(reqBody.username, reqBody.password, reqBody.nickname);

        return new RsData<>(
                        "201-1", "%s님 환영합니다.".formatted(member.getUsername()),
                        new MemberDto(member)
                );

    }


//    public record MemberLoginReqBody(
//            @NotBlank @Length(min = 4) String username,
//            @NotBlank @Length(min = 4) String password,
//            @NotBlank @Length(min = 3) String nickname){
//    }
//
//    @PostMapping("/login")
//    public RsData<MemberDto> join(@RequestBody @Valid MemberLoginReqBody reqBody){
//        Member member = memberService.findByUsername(reqBody.username)
//                .orElse(() -> new ServiceException("401-1","해당 회원은 존재하지 않습니다."));
//
//        if(!member.getPassword().equals(reqBody.password))
//            throw new ServiceException("401-2","비밀번호가 일치하지 않습니다.");
//
//        return new RsData<>(
//                "201-1", "%s님 환영합니다.".formatted(member.getUsername()),
//                new MemberDto(member)
//        );
//
//    }

}