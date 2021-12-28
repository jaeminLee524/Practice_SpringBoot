package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service
@Transactional // JPA는 Transaction안에서 이뤄져야 함
public class MemberService {

    private final MemberRepository memberRepository;

    // Service가 Container에 bean으로 관리될때 Autowired를 만나면 Service에 필요한 Memory~Repository를 주입해줌 - DI
//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        // 중복 회원 검증
        validateDuplicateMember(member);

        // 회원 저장
        memberRepository.save(member);

        //return id
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findByAll();
    }

    /**
     * 한명의 회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
