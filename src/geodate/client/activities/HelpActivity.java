package geodate.client.activities;

import geodate.client.R;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

public class HelpActivity extends GeoDateActivity {

	@Override
	protected int getContentView() {
		return R.layout.mock_help;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
        
        try {
        	ImageButton ib = (ImageButton)this.findViewById(R.id.imageLogo);
        
        	Animation circAnim = new RotateAnimation(0f, 360f,  Animation.RELATIVE_TO_SELF, 0.5f, 
                    Animation.RELATIVE_TO_SELF, 0.5f); 
 
        	circAnim.setRepeatMode(Animation.RESTART);
        	circAnim.setRepeatCount(Animation.INFINITE);
            circAnim.setDuration(1000L);
            circAnim.setInterpolator(new LinearInterpolator());
            circAnim.setFillAfter(true);
            circAnim.setFillBefore(true);
    		ib.startAnimation(circAnim);
        } catch(Exception e) { }        
	}
}