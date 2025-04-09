package com.chzzkzzal.zzal_tag.domain.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ZzalHashtagId implements Serializable {
	private Long zzalId;
	private Long hashtagId;
}
