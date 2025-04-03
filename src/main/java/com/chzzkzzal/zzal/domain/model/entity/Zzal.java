package com.chzzkzzal.zzal.domain.model.entity;

import com.chzzkzzal.core.domain.BaseTimeEntity;
import com.chzzkzzal.member.domain.Member;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Zzal extends BaseTimeEntity implements Uploadable, Bookmarkable, Viewable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	protected String title;

	abstract public String getUrl();
	abstract public ZzalMetaInfo getMetaInfo();
	abstract public Member getMember();

}
