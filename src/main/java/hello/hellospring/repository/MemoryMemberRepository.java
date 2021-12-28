package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//@Repository
public class MemoryMemberRepository implements MemberRepository {
    private static HashMap<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        // id 저장(seq+1)
        member.setId(++sequence);
        // map에 넘어온 member저장
        store.put(member.getId(), member);
        // return
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // test를 위한 메서드
    public void clearStore() {
        store.clear();
    }
}
