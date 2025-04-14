package com.chzzkzzal.member.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByChannelId(String channelId);

	List<Member> findAllByIdIn(List<Long> id);

}
