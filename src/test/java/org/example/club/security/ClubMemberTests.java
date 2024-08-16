package org.example.club.security;


import org.example.club.entity.ClubMember;
import org.example.club.entity.ClubMemberRole;
import org.example.club.repository.ClubMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberTests {

@Autowired
    private ClubMemberRepository repository;

@Autowired
    private PasswordEncoder passwordEncoder;

@Test
    public void inserDummies(){

    IntStream.rangeClosed(1,100).forEach(i -> {
        ClubMember member = ClubMember.builder()
                .email("user"+i+"@zerock.org")
                .name("사용자"+i)
                .fromSocial(false)
                .password(passwordEncoder.encode("1111"))
                .build();

        member.addMemberRole(ClubMemberRole.USER);

        if (i >80){
            member.addMemberRole(ClubMemberRole.MANAGER);

        }
        if (i >90){
            member.addMemberRole(ClubMemberRole.ADMIN);

        }

        repository.save(member);
    });
}
    @Test
    public void testRead(){

        Optional<ClubMember> result = repository.findbyEmail("user1@zerock.org", false);

        ClubMember clubMember = result.get();

        System.out.println(clubMember);
    }
}
