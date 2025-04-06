package com.chzzkzzal.streamer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Streamer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "streamer_id")
	private Long id;

	private String name;

}
