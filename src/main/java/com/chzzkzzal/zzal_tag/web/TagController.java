package com.chzzkzzal.zzal_tag.web;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.core.common.error.CustomResponse;
import com.chzzkzzal.zzal.domain.zzal.Zzal;
import com.chzzkzzal.zzal_tag.application.TagQueryService;
import com.chzzkzzal.zzal_tag.application.TaggingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "짤 태그 API", description = "")
@RestController
@RequiredArgsConstructor
@RequestMapping("/zzals")
public class TagController {
	private final TaggingService tagCmd;
	private final TagQueryService tagQry;

	@Operation(
		summary = "짤 태그들 생성",
		description =
			"### 짤 태그들 생성 \n" +
				"### TODO\n" +
				"- @Validation"
	)
	@PostMapping("/{id}/tags")
	public ResponseEntity<CustomResponse<Void>> addTags(@PathVariable("id") Long zzalId,
		@Validated @RequestBody TagRequest req) {
		tagCmd.tagZzal(zzalId, req.getTags());
		return ResponseEntity.created(URI.create("/api/zzals/" + zzalId)).build();
	}

	@Operation(
		summary = "짤의 태그 전체 조회", description = ""
	)
	@GetMapping("/{id}/tags")
	public ResponseEntity<CustomResponse<List<String>>> getAllTags(@PathVariable("id") Long zzalId) {
		List<String> allTags = tagQry.getAllTags(zzalId);
		return CustomResponse.okResponseEntity(allTags);
	}

	@Operation(
		summary = "태그로 짤들 검색", description = "### TODO: \n" +
		"- 검색방법 고민필요, 차후 ELK \n" +
		"- 커서 조회"
	)
	@GetMapping("/search")
	public ResponseEntity<CustomResponse<Page<ZzalSummaryResponse>>> search(@RequestParam String keyword,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size) {
		Page<Zzal> zzals = tagQry.search(keyword, PageRequest.of(page, size));
		Page<ZzalSummaryResponse> responses = zzals.map(ZzalSummaryResponse::from);
		return CustomResponse.okResponseEntity(responses);
	}
}
