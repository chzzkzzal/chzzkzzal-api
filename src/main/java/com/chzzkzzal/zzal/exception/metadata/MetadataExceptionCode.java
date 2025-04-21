package com.chzzkzzal.zzal.exception.metadata;

import org.springframework.http.HttpStatus;

import com.chzzkzzal.core.common.error.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MetadataExceptionCode implements ErrorCode {

	METADATA_UNSUPPORTED_FORMAT(HttpStatus.BAD_REQUEST, "M001", "지원하지 않는 이미지 포맷입니다."),
	METADATA_EXTRACTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "M002", "메타데이터 추출에 실패했습니다."),
	METADATA_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "M003", "파일 입출력 중 오류가 발생했습니다."),
	METADATA_CONTENT_TYPE_NULL(HttpStatus.BAD_REQUEST, "M004", "파일 형식 정보가 없습니다.");

	private final HttpStatus status;
	private final String message;
	private final String code;
}
