package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // jpa는 EntityManager로 동작, 라이브러리를 받았으니 스프링부트가 관리해줌
    private final EntityManager em;
    
    // injection 받으면 됨
    public JpaMemberRepository(EntityManager em) {
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

    // CRUD는 쿼리를 만들 필요가 없음, PK기반이 아닌 나머지 조회들은 JPQL을 이용해서 쿼리문을 만들어줘야 함
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name=:name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        //jpql 문법 객체 자체를 select
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
