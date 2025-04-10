package com.chzzkzzal.zzal_tag.web;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.chzzkzzal.zzal.domain.model.zzal.Zzal;
import com.chzzkzzal.zzal_tag.application.TagQueryService;
import com.chzzkzzal.zzal_tag.application.TaggingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/zzals")
public class TagController {
	private final TaggingService tagCmd;
	private final TagQueryService tagQry;

	@PostMapping("/{id}/tags")
	public ResponseEntity<Void> addTags(@PathVariable("id") Long zzalId,
		@Validated @RequestBody TagRequest req) {
		tagCmd.tagZzal(zzalId, req.getTags());
		return ResponseEntity.created(URI.create("/api/zzals/" + zzalId)).build();
	}

	@GetMapping("/{id}/tags")
	public ResponseEntity<List<String>> getAllTags(@PathVariable("id") Long zzalId) {
		List<String> allTags = tagQry.getAllTags(zzalId);
		return ResponseEntity.of(Optional.ofNullable(allTags));
	}

	@GetMapping("/search")
	public ResponseEntity<Page<ZzalSummaryResponse>> search(@RequestParam String keyword,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size) {
		Page<Zzal> zzals = tagQry.search(keyword, PageRequest.of(page, size));
		Page<ZzalSummaryResponse> dto = zzals.map(ZzalSummaryResponse::from);
		return ResponseEntity.ok(dto);
	}
}
