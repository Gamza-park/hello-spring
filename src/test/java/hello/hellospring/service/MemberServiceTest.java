package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemberRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemberRepository memberRepository;

    @BeforeEach
    public void beforEach() {
        memberRepository = new MemberRepositoryImpl();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void testJoin() {
        // given
        Member member = new Member();
        member.setName("hello");

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

        final var expect = new ArrayList();
        expect.add(member);

        memberService.join(member);

        // when
        final var result = memberService.findMembers();

        // then
        assertThat(result).isEqualTo(expect);

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
        assertThat(member).isEqualTo(result);

    }
}