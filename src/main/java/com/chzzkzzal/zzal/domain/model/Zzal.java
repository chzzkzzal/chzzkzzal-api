package com.chzzkzzal.zzal.domain.model;

import com.chzzkzzal.core.BaseTimeEntity;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Zzal extends BaseTimeEntity implements Uploadable, Bookmarkable, Viewable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

}
