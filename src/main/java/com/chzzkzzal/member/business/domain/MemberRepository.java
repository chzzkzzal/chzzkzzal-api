package com.chzzkzzal.member.business.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByChannelId(String channelId);

	Optional<Member> findById(Long memberId);

	List<Member> findAllByIdIn(List<Long> id);

	Member save(Member member);

}
