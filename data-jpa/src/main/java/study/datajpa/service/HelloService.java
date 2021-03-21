package study.datajpa.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@Service
@AllArgsConstructor
@Transactional
public class HelloService {

    private final MemberRepository memberRepository;

    public void hello() {
        Member member = new Member("name");
        memberRepository.save(member);

    }
}
