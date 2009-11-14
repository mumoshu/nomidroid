package com.mumoshu.patterns;

public abstract class Callbackable<Data> {
	/* public‚É‚µ‚È‚¢‚Æinner class‚Åoverride‚Å‚«‚È‚¢‚Ì‚ÅB */
	public Callbackable(){
		onInit();
	};
	/* public‚É‚µ‚È‚¢‚Æinner class‚Åoverride‚Å‚«‚È‚¢‚Ì‚ÅB */
	public void onInit() {};
	/* public‚É‚µ‚È‚¢‚Æinner class‚Åoverride‚Å‚«‚È‚¢‚Ì‚ÅBinterface‚Ìê‡‚Í‚ ‚¦‚Ä–¾¦‚µ‚È‚­‚Ä‚à‚¢‚¢‚ç‚µ‚¢  */
	public abstract void callback(Data data);
}
