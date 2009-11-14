package com.mumoshu.patterns;

public abstract class Callbackable<Data> {
	/* public�ɂ��Ȃ���inner class��override�ł��Ȃ��̂ŁB */
	public Callbackable(){
		onInit();
	};
	/* public�ɂ��Ȃ���inner class��override�ł��Ȃ��̂ŁB */
	public void onInit() {};
	/* public�ɂ��Ȃ���inner class��override�ł��Ȃ��̂ŁBinterface�̏ꍇ�͂����Ė������Ȃ��Ă������炵��  */
	public abstract void callback(Data data);
}
