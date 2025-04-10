package com.chzzkzzal.zzal_tag.web;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagRequest {
	@NotEmpty
	private List<String> tags;
}
