package com.chzzkzzal.zzal.business.domain.zzal.zzal.entity;

import com.chzzkzzal.common.domain.BaseTimeEntity;
import com.chzzkzzal.member.business.domain.Member;
import com.chzzkzzal.zzal.business.domain.zzal.metadata.MediaMeta;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.ValidTitle;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Zzal extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	protected String channelId;

	@ValidTitle
	protected String title;

	abstract public String getUrl();

	abstract public MediaMeta getMetaInfo();

	abstract public Member getMember();

}
