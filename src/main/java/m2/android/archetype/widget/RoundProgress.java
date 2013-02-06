package m2.android.archetype.widget;

import m2.android.archetype.example.R;
import m2.android.archetype.widget.ProgressManager.ProgressAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ClipDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RoundProgress extends RelativeLayout {
	private ImageView progressDrawableImageView;
	private ImageView trackDrawableImageView;
	
	private int max = 100;
	private int progress = 0;
	private String progressKey;
	
	public RoundProgress(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public RoundProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public String getProgressKey() {
		return progressKey;
	}

	public void setProgressKey(String progressKey) {
		this.progressKey = progressKey;
	}
	
	public ImageView getProgressDrawableImageView() {
		return progressDrawableImageView;
	}

	public void setProgressDrawableImageView(ImageView progressDrawableImageView) {
		this.progressDrawableImageView = progressDrawableImageView;
	}

	public ImageView getTrackDrawableImageView() {
		return trackDrawableImageView;
	}

	public void setTrackDrawableImageView(ImageView trackDrawableImageView) {
		this.trackDrawableImageView = trackDrawableImageView;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
		
		ClipDrawable drawable = (ClipDrawable) progressDrawableImageView.getBackground();
		double percent = (double) progress / (double) max;
		int level = (int) Math.floor(percent * 10000);
		
		drawable.setLevel(level);
	}
	
	private void init(Context context, AttributeSet attrs) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.round_progress, this);
		
		setup(context, attrs);
	}
	
	private void setup(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
		
		final String xmlns = "http://schemas.android.com/apk/res/com.nhn.android.band";
		int bgResource = attrs.getAttributeResourceValue(xmlns, "progressDrawable", 0);
		
		progressDrawableImageView = (ImageView) findViewById(R.id.progress_drawable_image_view);
		progressDrawableImageView.setBackgroundResource(bgResource);
		
		int trackResource = attrs.getAttributeResourceValue(xmlns, "track", 0);
		trackDrawableImageView = (ImageView) findViewById(R.id.track_image_view);
		trackDrawableImageView.setBackgroundResource(trackResource);
		
		int progress = attrs.getAttributeIntValue(xmlns, "progress", 0);
		setProgress(progress);
		
		int max = attrs.getAttributeIntValue(xmlns, "max", 100);
		setMax(max);
		
		a.recycle();
		
		ProgressBarOutline outline = new ProgressBarOutline(context);
		addView(outline);
	}

	public void start() {
		updateProgress();
	}
	
	public void stop() {
		progressKey = null;
	}

	private void updateProgress() {
		ProgressAdapter adapter = ProgressManager.get(progressKey);
		if (adapter == null) {
			setVisibility(View.GONE);
		} else {
			setMax(adapter.getMax());
			setProgress(adapter.getProgress());
			
			postDelayed(new Runnable() {
				@Override
				public void run() {
					updateProgress();
				}
				
			}, 400);
		}
	}
	
	@Override
	public void setVisibility(int v) {
		if (v == View.VISIBLE) {
			start();
		} else {
			stop();
		}
		
		super.setVisibility(v);
	}
	
	public static class ProgressBarOutline extends View {
	    private Paint paint;

	    public ProgressBarOutline(Context context) {
	        super(context);
	        paint = new Paint();
	    }

	    @Override
	    public void onDraw(Canvas canvas) {
	        paint.setColor(Color.WHITE);
	        paint.setStrokeWidth(2);
	        paint.setAntiAlias(true);
	        paint.setStyle(Paint.Style.STROKE);
	        paint.setARGB(255, 230, 230, 230);
	        RectF r = new RectF(1,1,getWidth()-2,getHeight()-2);
	        canvas.drawRoundRect(r,getHeight()/2,getHeight()/2, paint);
	    }
	}
}
