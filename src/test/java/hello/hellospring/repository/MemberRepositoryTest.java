package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

public class MemberRepositoryTest {

    MemberRepository repository = new MemberRepositoryImpl();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void testSave() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        final var result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void testFindByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        final var result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
        assertThat(result).isNotEqualTo(member2);
    }

    @Test
    public void testFindAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        final var expect = new ArrayList();
        expect.add(member1);
        expect.add(member2);

        final var result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).isEqualTo(expect);
    }
}
