package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    void testMember() {
        Member member = new Member("memberName");

        Member saveMember = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.find(saveMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void findByUsernameAndAgeGreaterThan() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        //List<Member> findMembers = memberJpaRepository.findByUsernameAndAgeGreaterThen("AAA", 9);
        List<Member> findMembers = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 9);

        assertThat(findMembers).containsExactly(member1, member2);
    }

    @Test
    void findUser() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        //List<Member> findMembers = memberJpaRepository.findByUsernameAndAgeGreaterThen("AAA", 9);
        List<Member> findMembers = memberRepository.findUser("AAA", 9);

        assertThat(findMembers).containsExactly(member1, member2);
    }

    @Test
    void findMemberDto() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);

        Team teamA = new Team("TeamA");
        teamA.addMember(member1);
        teamA.addMember(member2);

        teamRepository.save(teamA);

        List<MemberDto> findMembers = memberRepository.findMemberDto();
        MemberDto memberDto1 = new MemberDto(member1.getId(), member1.getUsername(), teamA.getName());
        MemberDto memberDto2 = new MemberDto(member2.getId(), member2.getUsername(), teamA.getName());

        assertThat(findMembers).containsExactly(memberDto1, memberDto2);
    }
}
