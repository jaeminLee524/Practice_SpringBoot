package hello.hellospring.controller;

import hello.hellospring.doamin.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// Component Anotiation들은 Spring이 객체를 생성해서 Container에 등록
@Controller
public class MemberController {

    // 인스턴스를 매번 새롭게 생성하는건 spring에서 좋은 관리법이 아님
    // spring container에 등록해서 쓰는 방식으로

    // Controller 애노테이션을 통해, Spring이 뜰때 Container에 등록
    private final MemberService memberService;

    // MemberService를 발견하지 못한다고 에러 발생 -> Service가 Controller가 SpringContainer에 등록되는 시점에 존재하지 않기 때문에
    // Controller가 bean으로 관리되면서 Autowired를 만나면 Spring은 필요한 Service를 주입해줌 - DI // Controller는 Service에 의존
    // Container에 등록된 객체들을 Autowired를 통해서 이어줌
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /* 회원가입을 했을때 보여주는 Form */
    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    /* createMemberForm에서 submit 했을때 */
    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        // 회원가입되면 홈으로 redirect
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";

    }
}