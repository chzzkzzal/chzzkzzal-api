// package com.chzzkzzal.core.api.controller.v1;
//
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
//
// import com.chzzkzzal.core.api.controller.v1.request.ExampleRequestDto;
// import com.chzzkzzal.core.api.controller.v1.response.ExampleResponseDto;
// import com.chzzkzzal.core.support.response.ApiResponse;
// import com.chzzkzzal.zzal.domain.dto.ExampleData;
// import com.chzzkzzal.zzal.domain.dto.ExampleResult;
//
// @RestController
// public class ExampleController {
//
// 	private final ExampleService exampleExampleService;
//
// 	public ExampleController(ExampleService exampleExampleService) {
// 		this.exampleExampleService = exampleExampleService;
// 	}
//
// 	@GetMapping("/get/{exampleValue}")
// 	public ApiResponse<ExampleResponseDto> exampleGet(@PathVariable String exampleValue,
// 		@RequestParam String exampleParam) {
// 		ExampleResult result = exampleExampleService.processExample(new ExampleData(exampleValue, exampleParam));
// 		return ApiResponse.success(new ExampleResponseDto(result.data()));
// 	}
//
// 	@PostMapping("/post")
// 	public ApiResponse<ExampleResponseDto> examplePost(@RequestBody ExampleRequestDto request) {
// 		ExampleResult result = exampleExampleService.processExample(request.toExampleData());
// 		return ApiResponse.success(new ExampleResponseDto(result.data()));
// 	}
//
// }
