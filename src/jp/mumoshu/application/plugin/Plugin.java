package jp.mumoshu.application.plugin;

public interface Plugin {
	public void onResume();
	public void onPause();
	public void onDestroy();
}
