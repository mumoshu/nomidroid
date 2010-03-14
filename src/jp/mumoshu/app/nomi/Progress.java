package jp.mumoshu.app.nomi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class Progress implements OnCancelListener {
	ProgressDialog progressDialog;
	boolean canceled = false;
	private Context context;
	
	public Progress(Context c){
		this.context = c;
	}

	public void show() {
		progressDialog = ProgressDialog.show(context, "title", "åüçıíÜ...", true, true, this);
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		canceled = true;
	}

	public void dismiss() {
		progressDialog.dismiss();
	}

	public boolean isNotCanceled() {
		return !canceled;
	}
}
