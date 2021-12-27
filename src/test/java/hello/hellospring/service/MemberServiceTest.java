package hello.hellospring.service;

import hello.hellospring.doamin.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    // 선언    
    MemoryMemberRepository memoryMemberRepository;
    MemberService memberService;

    // clearStore을 사용하기 위해 memoryMemberRepository 인스턴스를 생성해야됨 - Service에서 repo를 DI로 받음
    @BeforeEach
    void beforeEach() {
        // 각 테스트 메서드 실행전에 초기화
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    void afterEach() {
        // 각 테스트 메서드 실행 후 map clear
        memoryMemberRepository.clearStore();
    }

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when - 회원가입
        Long memberId = memberService.join(member);

        //then - 회원가입한 회원의 id로 일치하는지 검증
        Member findMember = memberService.findOne(memberId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when - 동일 회원을 가입했을 때
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        // message가 동일한지 검증
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}