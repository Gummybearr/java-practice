package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

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

    @Test
    void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJPARepository.save(member1);
        memberJPARepository.save(member2);

        memberJPARepository.findById(member1.getId());

        List<Member> all = memberJPARepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = memberJPARepository.count();
        assertThat(count).isEqualTo(2);

        memberJPARepository.delete(member1);
        memberJPARepository.delete(member2);
        assertThat(memberJPARepository.count()).isEqualTo(0);
    }

}
