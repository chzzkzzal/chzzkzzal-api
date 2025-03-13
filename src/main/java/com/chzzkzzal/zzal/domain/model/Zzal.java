package com.chzzkzzal.zzal.domain.model;

public interface Zzal extends Uploadable, Bookmarkable, Viewable {

	@Override
	Zzal upload();

	@Override
	void bookmark();

	@Override
	void view();

	@Override
	int countTotalView();
}
