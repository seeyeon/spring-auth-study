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


    @Column(unique = true, length = 50)
    private String apikey;

    public String getName() {
        return this.nickname;
    }

    public boolean isAdmin(){
        return "admin".equals(username);  //아이디가 admin인 경우 관리자임
    }
}
