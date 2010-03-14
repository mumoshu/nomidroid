/**
 * 
 */
package jp.mumoshu.app.nomi;

import jp.mumoshu.webapi.hotpepper.Spot;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author mumoshu
 *
 */
public class SpotDialog extends Dialog implements View.OnClickListener {
	private ImageView imageView;
	private TextView nameView;
	private TextView genreView;
	private TextView descView;
	private Button openUrlButton;
	private Button closeButton;
	private Context context;
	private Uri uri;

	/**
	 * @param context
	 */
	public SpotDialog(Context context) {
		super(context);
		initViews(context);
	}

	/**
	 * @param context
	 * @param theme
	 */
	public SpotDialog(Context context, int theme) {
		super(context, theme);
		initViews(context);
	}

	/**
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	public SpotDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initViews(context);
	}
	
	private void initViews(Context context) {
		this.context = context;
		setContentView(R.layout.shop_details);
		nameView = (TextView)findViewById(R.id.name);
		genreView = (TextView)findViewById(R.id.genre);
		descView = (TextView)findViewById(R.id.description);
		imageView = (ImageView)findViewById(R.id.image);
		openUrlButton = (Button)findViewById(R.id.openUrlButton);
		closeButton = (Button)findViewById(R.id.closeButton);
	}
	
	public void set(String name, String genre, String desc, Drawable image, String url) {
		nameView.setText(name);
		genreView.setText(genre);
		descView.setText(desc);
		if(image != null) {
			imageView.setImageDrawable(image);
		}
		closeButton.setOnClickListener(this);
		openUrlButton.setOnClickListener(this);
		uri = Uri.parse(url);
	}

	public void setSpot(Spot spot, Drawable image){
		set(spot.getName(), spot.getGenre().getDescription(), spot.getOpen(), image, spot.getURL().toExternalForm());
	}
	
	@Override
	public void onClick(View v) {
		if(v == closeButton) {
			dismiss();
		} else {
			Intent i = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(i);
		}
	}
}
