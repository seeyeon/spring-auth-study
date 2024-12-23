package com.ll.auth.domain.member.member.entity;

import com.ll.auth.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTime {

    @Column(unique = true, length = 30)
    private String username;

    @Column(length = 50)
    private String password;

    @Column(length = 30)
    private String nickname;

    //회사 정책상, 필요시 결과값을 username으로 바꿔야하는 경우 손쉽게 바꿀 수 있다
    //확장성과 유연성을 위해 getName() 메서드를 추가해봄
    public String getName() {
        return this.nickname;
    }
}
