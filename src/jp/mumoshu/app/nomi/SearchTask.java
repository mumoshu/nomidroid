package jp.mumoshu.app.nomi;

import java.io.IOException;

import jp.mumoshu.webapi.hotpepper.HotpepperSearch;

import org.json.JSONException;

import android.os.AsyncTask;

public class SearchTask extends AsyncTask<HotpepperSearch, Integer, SearchResult>{
	private SearchResultHandler handler;

	public SearchTask(SearchResultHandler handler){
		this.handler = handler;
	}

	@Override
	protected SearchResult doInBackground(HotpepperSearch... params) {
		HotpepperSearch s = params[0];
		try {
			return SearchResult.fromHotpepperData(s.execute());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(SearchResult result) {
		handler.process(result);
	}
}
