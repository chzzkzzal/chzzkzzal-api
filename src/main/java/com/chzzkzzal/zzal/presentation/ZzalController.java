package com.chzzkzzal.zzal.presentation;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.chzzkzzal.common.error.CustomResponse;
import com.chzzkzzal.core.auth.adapter.in.web.security.MemberUserDetails;
import com.chzzkzzal.zzal.business.application.dto.ClientInfo;
import com.chzzkzzal.zzal.business.application.port.in.GetZzalAllUseCase;
import com.chzzkzzal.zzal.business.application.port.in.GetZzalDetailUseCase;
import com.chzzkzzal.zzal.business.application.port.in.UploadZzalUseCase;
import com.chzzkzzal.zzal.business.application.port.in.command.UploadCommand;
import com.chzzkzzal.zzal.business.application.port.in.query.GetZzalDetailQuery;
import com.chzzkzzal.zzal.business.application.port.in.query.result.ZzalDetailResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "짤 API", description = "")
@RestController
@RequiredArgsConstructor
@RequestMapping("/zzals")
public class ZzalController {

	private final UploadZzalUseCase uploadZzalUseCase;
	private final GetZzalDetailUseCase getZzalDetailUseCase;
	private final GetZzalAllUseCase getZzalAllUseCase;

	@Operation(
		summary = "짤 업로드(파일1개)",
		description =
			"### 짤 업로드\n" +
				"- 파일은 1개만 가능(용량 제한 있음) \n" +
				"### TODO\n" +
				"- 파일업로드는 별도 api로 분리 \n" +
				"- Body @Validation 추가"
	)
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<CustomResponse<Long>> upload(
		@RequestPart(value = "file") MultipartFile multipartFile,
		@RequestPart(value = "zzalCreateRequest") ZzalCreateRequest zzalCreateRequest,
		@AuthenticationPrincipal MemberUserDetails memberUserDetails) throws IOException {

		Long memberId = memberUserDetails.getMember().getId();

		validateUser(memberUserDetails);

		UploadCommand command = new UploadCommand(
			zzalCreateRequest.channelId(),
			zzalCreateRequest.title(),
			memberId,
			multipartFile.getName(),
			multipartFile.getOriginalFilename(),
			multipartFile.getContentType(),
			multipartFile.getSize(),
			multipartFile.getBytes(),
			multipartFile.getInputStream()
		);

		Long response = uploadZzalUseCase.upload(command);
		return CustomResponse.okResponseEntity(response);
	}

	private void validateUser(MemberUserDetails userDetails) {
		if (userDetails == null || userDetails.getMember() == null) {
			// 필요하면 커스텀 예외 사용 가능
			// throw new UnAuthorizedException("인증 정보가 유효하지 않습니다.");

			// 혹은 401/403을 직접 반환
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "인증 정보가 유효하지 않습니다.");
		}
	}

	@Operation(
		summary = "짤 단건 조회",
		description =
			"### 짤 단건 조회\n" +
				"- 짤 메타정보 포함 \n"
	)
	@GetMapping("{zzalId}")
	public ResponseEntity<CustomResponse<ZzalDetailResponse>> viewDetail(
		@PathVariable("zzalId") Long zzalId,
		HttpServletRequest request
	) {

		GetZzalDetailQuery query = new GetZzalDetailQuery(zzalId, ClientInfo.from(request));
		ZzalDetailResponse response = getZzalDetailUseCase.execute(query);
		return CustomResponse.okResponseEntity(response);
	}

	@Operation(
		summary = "짤 전체 조회",
		description =
			"### 짤 전체 조회\n" +
				"- findAll \n" +
				"### TODO \n" +
				"- 커서기반 리팩토링 \n"
	)
	@GetMapping
	public ResponseEntity<CustomResponse<List<ZzalDetailResponse>>> getAll() {
		List<ZzalDetailResponse> responses = getZzalAllUseCase.getAll();
		return CustomResponse.okResponseEntity(responses);
	}
}
