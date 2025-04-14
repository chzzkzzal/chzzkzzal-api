package com.chzzkzzal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.chzzkzzal.streamer.RegisterStreamerCommand;
import com.chzzkzzal.streamer.StreamerService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

	private final StreamerService streamerService;

	@Override
	public void run(String... args) throws Exception {
		// 애플리케이션 시작 시 실행할 코드
		도파();
		랄로();
		파카();
		괴물쥐();
	}

	private void 괴물쥐() {
		RegisterStreamerCommand command = new RegisterStreamerCommand(
			"c7ded8ea6b0605d3c78e18650d2df83b",
			"괴물쥐",
			"http://localhost:8080/zzal/괴물쥐.jpg",
			290000
		);
		streamerService.register(command);
	}

	private void 도파() {
		RegisterStreamerCommand command = new RegisterStreamerCommand(
			"c847a58a1599988f6154446c75366523",
			"도파99",
			"http://localhost:8080/zzal/도파.jpg",
			114000
		);
		streamerService.register(command);
	}

	private void 랄로() {
		RegisterStreamerCommand command = new RegisterStreamerCommand(
			"3497a9a7221cc3ee5d3f95991d9f95e9",
			"랄로",
			"http://localhost:8080/zzal/랄로.png",
			315000
		);
		streamerService.register(command);
	}

	private void 파카() {
		RegisterStreamerCommand command = new RegisterStreamerCommand(
			"42597020c1a79fb151bd9b9beaa9779b",
			"파카",
			"http://localhost:8080/zzal/파카.jpg",
			224000
		);
		streamerService.register(command);
	}

}
