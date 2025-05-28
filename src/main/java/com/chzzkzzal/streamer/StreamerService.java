package com.chzzkzzal.streamer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.chzzk.adapter.in.facade.ChzzkDevelopersFacade;
import com.chzzkzzal.core.chzzk.common.response.ChzzkChannelInfoResponse;
import com.chzzkzzal.member.business.domain.MemberRepository;
import com.chzzkzzal.zzal.business.application.port.in.query.result.ZzalDetailResponse;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.ZzalJpaRepository;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StreamerService {
	private final StreamerRepository streamerRepository;
	private final ZzalJpaRepository zzalJpaRepository;
	private final MemberRepository memberRepository;
	private final ChzzkDevelopersFacade chzzkDevelopersFacade;

	public String register(RegisterStreamerCommand command) {
		Streamer streamer = Streamer.register(
			command.channelId(),
			command.channelName(),
			command.channelImageUrl(),
			command.followerCount()
		);
		streamer = streamerRepository.save(streamer);
		return streamer.getChannelId();
	}

	public String registerByChzzkClient(String[] channelIds) {
		ChzzkChannelInfoResponse response = chzzkDevelopersFacade.getChannelInfoByIds(channelIds);
		Streamer streamer = new Streamer(response.channelId(), response.channelName(), response.channelImageUrl(),
			response.followerCount());
		streamer = streamerRepository.save(streamer);
		return streamer.getChannelId();

	}

	public List<GetStreamerResponse> findAll() {
		return streamerRepository.findAll().stream().map(streamer -> new GetStreamerResponse(
			streamer.getId(),
			streamer.getChannelId(),
			streamer.getChannelName(),
			streamer.getChannelImageUrl(),
			streamer.getFollowerCount(),
			streamer.getCreatedAt(),
			streamer.getUpdatedAt()
		)).collect(Collectors.toList());
	}

	public GetStreamerResponse findByChannelId(String channelId) {
		Streamer streamer = streamerRepository.findByChannelId(channelId)
			.orElseThrow(() -> new IllegalArgumentException());
		return new GetStreamerResponse(
			streamer.getId(),
			streamer.getChannelId(),
			streamer.getChannelName(),
			streamer.getChannelImageUrl(),
			streamer.getFollowerCount(),
			streamer.getCreatedAt(),
			streamer.getUpdatedAt()
		);

	}

	public List<ZzalDetailResponse> getStreamerZzals(String channelId) {
		List<Zzal> zzals = zzalJpaRepository.findAllByChannelId(channelId);
		List<ZzalDetailResponse> response = zzals.stream()
			.map(zzal -> ZzalDetailResponse.toResponse(zzal, zzal.getMember()))
			.collect(Collectors.toList());

		return response;
	}
}
