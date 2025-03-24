package com.chzzkzzal.zzal.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.core.s3.service.S3ServicePort;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberLoader;
import com.chzzkzzal.zzal.domain.dao.SaveZzalPort;
import com.chzzkzzal.zzal.domain.model.entity.Zzal;
import com.chzzkzzal.zzal.domain.model.entity.ZzalMetaInfo;
import com.chzzkzzal.zzal.domain.model.factory.ZzalFactory;
import com.chzzkzzal.zzal.domain.model.metadata.MetadataProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalUploadServiceImpl implements ZzalUploadService {
	private final SaveZzalPort saveZzalPort;
	private final MetadataProvider metadataProvider;
	private final MemberLoader memberLoader;
	private final S3ServicePort s3ServicePort;
	private final List<ZzalFactory> zzalFactories;

	@Override
	@Transactional
	public void upload(Long memberId, MultipartFile multipartFile) {
		Member member = memberLoader.loadMember(memberId);
		ZzalMetaInfo metadata = metadataProvider.getMetadata(multipartFile);

		ZzalFactory factory = zzalFactories.stream()
			.filter(f -> f.supports(metadata))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("지원되지 않는 Zzal 유형입니다."));
		String fileName = s3ServicePort.uploadFile(multipartFile);

		String fileUrl = s3ServicePort.getFileUrl(fileName);
		Zzal zzal = factory.createZzal(member, metadata, multipartFile.getOriginalFilename(),fileUrl);
		saveZzalPort.save(zzal);

	}
}
