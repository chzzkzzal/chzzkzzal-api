package com.chzzkzzal.zzal.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.core.storage.s3.application.S3ServicePort;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberLoader;
import com.chzzkzzal.zzal.domain.dao.SaveZzalPort;
import com.chzzkzzal.zzal.domain.factory.ZzalFactory;
import com.chzzkzzal.zzal.domain.factory.ZzalFactoryProvider;
import com.chzzkzzal.zzal.domain.metadata.support.MetadataProvider;
import com.chzzkzzal.zzal.domain.zzal.Zzal;
import com.chzzkzzal.zzal.domain.zzal.ZzalMetaInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalUploadServiceImpl implements ZzalUploadService {
	private final SaveZzalPort saveZzalPort;
	private final MetadataProvider metadataProvider;
	private final MemberLoader memberLoader;
	private final S3ServicePort s3ServicePort;
	private final ZzalFactoryProvider zzalFactoryProvider;

	@Override
	@Transactional
	public Long upload(String channelId, String title, Long memberId, MultipartFile multipartFile) {
		Member member = memberLoader.loadMember(memberId);
		ZzalMetaInfo metadata = metadataProvider.getMetadata(multipartFile);

		ZzalFactory factory = zzalFactoryProvider.getFactory(metadata);

		String fileName = s3ServicePort.uploadFile(multipartFile);
		String fileUrl = s3ServicePort.getFileUrl(fileName);

		Zzal zzal = factory.createZzal(channelId, member, metadata, title, fileUrl);
		return saveZzalPort.save(zzal).getId();
	}
}
