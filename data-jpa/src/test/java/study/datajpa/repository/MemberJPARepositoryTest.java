package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class MemberJPARepositoryTest {

    @Autowired
    MemberJPARepository memberJPARepository;

    @Test
    void testMember() {
        Member member = new Member("memberA");
        System.out.println(member.getId());
        System.out.println(member);
        Member savedMember = memberJPARepository.save(member);
        System.out.println(member.getId());
        System.out.println(member);
        assertThat(member).isEqualTo(savedMember);
    }

}