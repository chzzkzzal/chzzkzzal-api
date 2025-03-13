package com.chzzkzzal.zzal.domain.model;

public interface Zzal extends Uploadable, Bookmarkable {

	@Override
	Zzal upload();

	@Override
	void bookmark();

}
