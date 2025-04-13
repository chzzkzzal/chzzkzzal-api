package com.chzzkzzal.streamer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamerRepository extends JpaRepository<Streamer, Long> {
	Optional<Streamer> findByChannelId(String channelId);
}
