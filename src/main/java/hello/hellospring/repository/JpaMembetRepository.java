package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMembetRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMembetRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> member = em.createQuery("SELECT m from Member m　WHERE m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return member.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("SELECT m from Member m", Member.class)
                .getResultList();
    }

    @Override
    public void clearStore() {

    }
}
