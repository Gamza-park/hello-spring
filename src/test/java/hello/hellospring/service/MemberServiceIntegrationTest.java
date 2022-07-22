package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void testJoin() {
        // given
        Member member = new Member();
        member.setName("hello1");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void testJoin_duplicateException() {
        // given
        Member member1 = new Member();
        member1.setName("hello");

        Member member2 = new Member();
        member2.setName("hello");

        // when
        memberService.join(member1);

        // then
        IllegalStateException e =
                Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("Duplicate Name Error");
    }

    @Test
    void testFindMembers() {
        // given
        Member member = new Member();
        member.setName("Test");

        memberService.join(member);

        // when
        final var result = memberService.findMembers();

        // then
        assertThat(result.get(0).getName()).isEqualTo(member.getName());

    }

    @Test
    void testFindOne() {
        // given
        Member member = new Member();
        member.setName("Test");

        memberService.join(member);
        // when
        final var result = memberService.findOne(member.getId()).get();

        // then
        assertThat(member.getName()).isEqualTo(result.getName());

    }
}