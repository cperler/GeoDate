package geodate.client.activities;

import geodate.client.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WebRequestActivity extends GeoDateActivity implements OnClickListener {
	private Button go;
	private EditText text;
	private ImageView image;
	
	private TextView requestText;
	private TextView processText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		
		go = (Button)findViewById(R.id.goButton);
		text = (EditText)findViewById(R.id.httpRequest);
		image = (ImageView)findViewById(R.id.imageView);
		requestText = (TextView)findViewById(R.id.loadText);
		processText = (TextView)findViewById(R.id.processText);
		go.setOnClickListener(this);
	}
	
	@Override
	protected int getContentView() {
		return R.layout.web_request_test;
	}
	
	

	@Override
	public void onClick(View v) {
		long start = 0;
		long stop = 0;
		String uri = text.getText().toString();
		URL url = null;
		InputStream content = null;
		try {
			url = new URL(uri);
		} catch (MalformedURLException e) {
			log(Log.ERROR, "Malformed URL Exception.");
		}	
		
		if (url != null) {
			try {
				start = System.currentTimeMillis();
				content = (InputStream)url.getContent();
				stop = System.currentTimeMillis();
				requestText.setText("Time to retrieve response: " + (stop - start) + "ms");
			} catch (IOException e) {
				log(Log.ERROR, "IO Exception");
			}					
			start = System.currentTimeMillis();
						
			Drawable d = Drawable.createFromStream(content, "src");
			image.setImageDrawable(d);
			stop = System.currentTimeMillis();
			processText.setText("Time to process response: " + (stop - start) + "ms");
		}
	}
}
