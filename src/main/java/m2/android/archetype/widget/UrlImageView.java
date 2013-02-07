/*
 * UrlImageView.java $version 2012. 09. 13
 *
 * Copyright 2012 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package m2.android.archetype.widget;

import m2.android.archetype.base.M3Application;
import m2.android.archetype.example.R;
import m2.android.archetype.util.AppInfoUtility;
import m2.android.archetype.util.Logger;
import m2.android.archetype.util.StringUtility;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.nhn.android.archetype.base.image.ImageHelper;
import com.nhn.android.archetype.base.image.ImageHelper.MaskData;
import com.nhn.android.archetype.base.image.listener.ImageLoadListener;
import com.nhn.android.archetype.base.image.listener.ProgressiveImageLoadListener;
import com.nhn.android.archetype.base.object.ApiResponse;
import com.nhn.android.archetype.base.theme.ThemeRelativeLayout;

public class UrlImageView extends ThemeRelativeLayout {
	public static final int SAMPLING_WIDTH_DEFAULT = ImageHelper.SAMPLING_WIDTH_DEFAULT;
	public static final int SAMPLING_WIDTH_NONE = ImageHelper.SAMPLING_WIDTH_NONE;
	public static final int SAMPLING_WIDTH_AUTO = 0;
	
	private static Logger logger = Logger.getLogger(UrlImageView.class);
	
	private String url;
	private String thumbnailType;
	private int requestUrlHash;
	private int requestUrlLen;
	
	private boolean showProgress = false;
	private boolean showFadeAnimation = true; 
	private int samplingWidth = SAMPLING_WIDTH_NONE;
	private boolean progressiveLoad = false;
	private boolean showLoadingCover = false;
	private boolean freeMemoryOnVisibleChange = true;
	
	private Drawable defaultDrawable;
	private Drawable coverDrawable;
	private String maskDrawableKey = null;
	private Drawable maskDrawable;
	private int maskWidth;
	private int maskHeight;
	private Bitmap currentBitmap;
	
	private ImageView imageView;
	private ImageView defaultImageView;
	private ImageView coverImageView;
	private M2Progress progressBar;
	private View progressBarWrap;
	
	private UrlImageViewEventListener listener;
	
	public UrlImageView(Context context) {
		super(context);
		init(null);
	}

	public UrlImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public UrlImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}
	
	private void applyAttributeSet(AttributeSet attrs) {
		try {
			if (attrs != null) {
				TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.UrlImageView);
				
				final int count = a.getIndexCount();
				for (int i=0;i<count;i++) {
					int attr = a.getIndex(i);
					
					switch(attr) {
					case R.styleable.UrlImageView_url:
						setUrl(a.getString(attr));
						break;
						
					case R.styleable.UrlImageView_showProgress:
						showProgress = a.getBoolean(attr, showProgress);
						break;
						
					case R.styleable.UrlImageView_showFadeAnimation:
						showFadeAnimation = a.getBoolean(attr, showFadeAnimation);
						break;
						
					case R.styleable.UrlImageView_defaultResource:
						setDefaultDrawable(a.getDrawable(attr));
						break;
						
					case R.styleable.UrlImageView_coverResource:
						setCoverDrawable(a.getDrawable(attr));
						break;
						
					case R.styleable.UrlImageView_loadingCoverResource:
						setCoverDrawable(a.getDrawable(attr));
						showLoadingCover = true;
						break;
						
					case R.styleable.UrlImageView_maskResource:
						maskDrawableKey = a.getString(attr);
						setMaskDrawable(a.getDrawable(attr));
						break;
						
					case R.styleable.UrlImageView_maskWidth:
						maskWidth = a.getDimensionPixelSize(attr, 0);
						break;
						
					case R.styleable.UrlImageView_maskHeight:
						maskHeight = a.getDimensionPixelSize(attr, 0);
						break;
						
					case R.styleable.UrlImageView_scaleType:
						setScaleType(a.getString(attr));
						break;
						
					case R.styleable.UrlImageView_thumbnailType:
						setThumbnailType(a.getString(attr));
						break;
						
					case R.styleable.UrlImageView_samplingWidth:
						setSamplingWidth(a.getInt(attr, 160));
						break;
						
					case R.styleable.UrlImageView_progressive:
						setProgressiveLoad(a.getBoolean(attr, progressiveLoad));
						break;
					}
				}
				
			}
		} catch (Exception e) {
			logger.e(e);
		}
	}
	
	private void init(AttributeSet attrs) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.urlimageview, null);
		imageView = (ImageView) view.findViewById(R.id.urlimageview_image);
		defaultImageView = (ImageView) view.findViewById(R.id.urlimageview_defaultimage);
		progressBarWrap = view.findViewById(R.id.urlimageview_progress_wrap);
		progressBar = (M2Progress) view.findViewById(R.id.urlimageview_progress);
		coverImageView = (ImageView) view.findViewById(R.id.urlimageview_coverimage);
		
		this.addView(view);
		
		applyAttributeSet(attrs);
	}

	public String getThumbnailType() {
		return thumbnailType;
	}

	public void setThumbnailType(String thumbnailType) {
		this.thumbnailType = thumbnailType;
	}

	public int getSamplingWidth() {
		return samplingWidth;
	}

	public void setSamplingWidth(int samplingWidth) {
		this.samplingWidth = samplingWidth;
	}

	public boolean getFreeMemoryOnVisibleChange() {
		return freeMemoryOnVisibleChange;
	}

	public void setFreeMemoryOnVisibleChange(boolean freeMemoryOnVisibleChange) {
		this.freeMemoryOnVisibleChange = freeMemoryOnVisibleChange;
	}

	public boolean getProgressiveLoad() {
		if (AppInfoUtility.isICSCompatibility()) {
			return progressiveLoad;
		}
		
		return false;
	}

	public void setProgressiveLoad(boolean progressiveLoad) {
		this.progressiveLoad = progressiveLoad;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		setUrl(url, false);
	}
	
	public void setUrl(String url, boolean force) {
		if (StringUtility.isNotNullOrEmpty(url)) {
			if (force == false && this.url != null) {
				String beforeUrl = this.url;
				this.url = ImageHelper.getThumbnailUrl(url, thumbnailType);
				
				if (StringUtility.equals(beforeUrl, this.url)) {
					return;
				}
			} else {
				this.url = ImageHelper.getThumbnailUrl(url, thumbnailType);
			}
			
			loadImage(force);
		} else {
			this.url = null;
			this.setImageBitmap(null);

			this.progressBarWrap.setVisibility(View.GONE);
			this.progressBar.setVisibility(View.GONE);
			this.imageView.setVisibility(View.GONE);
			
			if (getVisibleDefaultResource()) {
				this.defaultImageView.setVisibility(View.VISIBLE);
			}
		}
	}
	
	public Drawable getDefaultDrawable() {
		return defaultDrawable;
	}

	public void setDefaultDrawable(int defaultResource) {
		setDefaultDrawable(getResources().getDrawable(defaultResource));
	}
	
	public void setDefaultDrawable(Drawable drawable) {
		this.defaultDrawable = drawable;
		
		if (drawable != null) {
			defaultImageView.setImageDrawable(drawable);
			defaultImageView.setVisibility(View.VISIBLE);
		} else {
			defaultImageView.setVisibility(View.GONE);
		}
	}
	
	public boolean getVisibleDefaultResource() {
		return defaultDrawable != null;
	}

	public Drawable getCoverDrawable() {
		return coverDrawable;
	}
	
	public boolean getVisibleCoverResource() {
		return coverDrawable != null;
	}

	public void setCoverDrawable(int coverResource) {
		setCoverDrawable(getResources().getDrawable(coverResource));
	}

	public void setCoverDrawable(Drawable drawable) {
		this.coverDrawable = drawable;
		
		if (drawable != null) {
			coverImageView.setImageDrawable(drawable);
			coverImageView.setVisibility(View.VISIBLE);
			coverImageView.bringToFront();
		} else {
			coverImageView.setVisibility(View.GONE);
		}
	}

	public Drawable getMaskDrawable() {
		return maskDrawable;
	}

	public void setMaskDrawable(int maskDrawableId) {
		this.maskDrawableKey = Integer.toString(maskDrawableId);
		Drawable drawable = getResources().getDrawable(maskDrawableId);
		setMaskDrawable(drawable);
	}

	public void setMaskDrawable(Drawable maskDrawable) {
		this.maskDrawable = maskDrawable;
	}

	public int getMaskWidth() {
		return maskWidth;
	}

	public void setMaskWidth(int maskWidth) {
		this.maskWidth = maskWidth;
	}

	public int getMaskHeight() {
		return maskHeight;
	}

	public void setMaskHeight(int maskHeight) {
		this.maskHeight = maskHeight;
	}

	public boolean getShowProgress() {
		return showProgress;
	}

	public void setShowProgress(boolean showProgress) {
		this.showProgress = showProgress;
	}

	public boolean getShowFadeAnimation() {
		return showFadeAnimation;
	}

	public void setShowFadeAnimation(boolean showFadeAnimation) {
		this.showFadeAnimation = showFadeAnimation;
	}

	public UrlImageViewEventListener getListener() {
		return listener;
	}

	public void setListener(UrlImageViewEventListener listener) {
		this.listener = listener;
	}

	private ImageLoadListener getImageLoadListener(final String currentRequestUrl) {
		final long currentRequestHash = requestUrlHash;
		final long currentRequestLen = requestUrlLen;
		
		if (getProgressiveLoad()) {
			return new ProgressiveImageLoadListener() {
				public void onPreload(Bitmap result) {
					if (currentRequestHash == requestUrlHash && currentRequestLen == requestUrlLen) {
						logger.d("onPreload");
						if (getVisibility() != View.VISIBLE) {
							return;
						}
						
						setImageBitmap(result, getShowFadeAnimation(), true);
					}
				}
				
				public void onSuccess(Bitmap result) {
					if (currentRequestHash == requestUrlHash && currentRequestLen == requestUrlLen) {
						if (getVisibility() != View.VISIBLE) {
							return;
						}
						
						setImageBitmap(result, currentBitmap == null && getShowFadeAnimation(), true);
					}
					
					if (getListener() != null) {
						getListener().onLoadCompleted(result);
					}
				}
				
				public void onError(ApiResponse result) {
					logger.d("onError(%s, %s)", currentRequestUrl, result.getCode());
					
					setImageBitmap(null, false, true);
					
					if (getListener() != null) {
						getListener().onError(result);
					}
				}
			};
		}
		
		return new ImageLoadListener() {
			public void onSuccess(Bitmap result) {
				if (currentRequestHash == requestUrlHash && currentRequestLen == requestUrlLen) {
					if (getVisibility() != View.VISIBLE) {
						return;
					}

					logger.d("onSuccess");

					setImageBitmap(result, getShowFadeAnimation(), true);
				}
				
				if (getListener() != null) {
					getListener().onLoadCompleted(result);
				}
			}
			
			public void onError(ApiResponse result) {
				logger.d("onError(%s, %s)", currentRequestUrl, result.getCode());
				
				setImageBitmap(null, false, true);
				
				if (getListener() != null) {
					getListener().onError(result);
				}
			}
		};
	}
	
	public void loadImage() {
		loadImage(false);
	}
	
	private String getCacheKey(String requestUrl) {
		return ImageHelper.getCacheKey(requestUrl, maskDrawableKey, maskWidth, maskHeight);
	}
	
	private Runnable loadImageProgressRunnable = new Runnable() {
		@Override
		public void run() {
			if (getVisibility() == View.VISIBLE && currentBitmap == null && showProgress) {
				progressBarWrap.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.VISIBLE);	
			}
		}
	};
	
	private String oldUrl = null;
	public void freeMemory() {
		oldUrl = url;
		url = null;
		setImageBitmap(null, false);
		
		if (fadeIn != null) {
			fadeIn.reset();
		}
		
		if (fadeOut != null) {
			fadeOut.reset();
		}
	}
	
	public void restoreMemory() {
		if (StringUtility.isNotNullOrEmpty(oldUrl)) {
			this.url = oldUrl;
			loadImage();
		}
	}
	
	@Override
	public void setVisibility(int visibility) {
		if (freeMemoryOnVisibleChange && visibility == View.GONE) {
			freeMemory();
		}
		
		super.setVisibility(visibility);
	}

	public void loadImage(final boolean force) {
		if (StringUtility.isNullOrEmpty(url)) {
			return;
		}
		
		requestUrlHash = url.hashCode();
		requestUrlLen = url.length();

		String cacheKey = getCacheKey(url);
		Bitmap bm = ImageHelper.getFromCache(cacheKey);
		logger.d("loadImage: %s -> cache: %s", cacheKey, bm);
		if (bm != null) {
			setImageBitmap(bm, false, true);
			return;
		}
		
		currentBitmap = null;
		
		imageView.setVisibility(View.GONE);
		progressBarWrap.setVisibility(View.GONE);
		progressBar.setVisibility(View.GONE);
		defaultImageView.setVisibility(View.GONE);
		
		if (coverDrawable != null) {
			coverImageView.setVisibility(View.VISIBLE);
		}
		
		if (getVisibleDefaultResource()) {
			defaultImageView.setVisibility(View.VISIBLE);
		}

		if (maskDrawableKey != null && maskDrawable != null) {
			int w = maskWidth;
			int h = maskHeight;
			
			if (w == 0 || h == 0) {
				w = getWidth();
				h = getHeight();
			}
			
			if (w == 0 || h == 0) {
				// 사이즈 실측 후 마스킹을 하기 위하여
				postDelayed(new Runnable() {
					@Override
					public void run() {	
						int w = getWidth();
						int h = getHeight();
						logger.d("loadImage: %s -> w: %s h: %s", url, w, h);
						MaskData maskData = new MaskData(maskDrawableKey, maskDrawable, getWidth(), getHeight());
						ImageHelper.loadImage(url, force, samplingWidth, maskData, getImageLoadListener(url));
					}
				}, 100);
			} else {
				logger.d("loadImage: %s -> w: %s h: %s", url, w, h);
				MaskData maskData = new MaskData(maskDrawableKey, maskDrawable, w, h);
				ImageHelper.loadImage(url, force, samplingWidth, maskData, getImageLoadListener(url));
			}
		} else {
			ImageHelper.loadImage(url, force, samplingWidth, null, getImageLoadListener(url));
		}
		
		if (showProgress) {
			postDelayed(loadImageProgressRunnable, 500);
		}
	}
	
	public void setImageBitmap(Bitmap bitmap) {
		setImageBitmap(bitmap, showFadeAnimation);
	}

	public void setImageBitmap(Bitmap bitmap, boolean animation) {
		setImageBitmap(bitmap, animation, false);
	}
	
	public void setImageBitmap(Bitmap bitmap, boolean animation, boolean skipMask) {
		if (bitmap == null) {
			animation = false;
		}
		
		Drawable mask = maskDrawable;
		if (skipMask == false && bitmap != null && mask != null) {
			Bitmap maskBitmap = ImageHelper.convertDrawable(mask, bitmap.getWidth(), bitmap.getHeight());
			bitmap = ImageHelper.maskBitmap(bitmap, maskBitmap);
		}
		
		this.currentBitmap = bitmap;
		imageView.setImageBitmap(bitmap);

		if (animation) {
			startFadeAnimation();
		} else {
			progressBarWrap.setVisibility(View.GONE);
			progressBar.setVisibility(View.GONE);
			
			if (bitmap != null) {
				defaultImageView.setVisibility(View.GONE);
				imageView.setVisibility(View.VISIBLE);
			} else {
				defaultImageView.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.GONE);
			}
			
			if (showLoadingCover) {
				// 2012.11.28: 로딩중일때만 커버 나오도록 수정
				// 박수만 센터장님 요청
				coverImageView.setVisibility(View.GONE);
			}
		}
	}
	
	public Bitmap getImageBitmap() {
		return currentBitmap;
	}
	
	private AlphaAnimation fadeIn;
	private AlphaAnimation fadeOut;
	
	private void startFadeAnimation() {
		if (fadeIn == null) {
			fadeIn = new AlphaAnimation(0, 1);
			fadeIn.setAnimationListener(new AnimationListener() {
				public void onAnimationStart(Animation animation) {
					if (showLoadingCover) {
						// 2012.11.28: 로딩중일때만 커버 나오도록 수정
						// 박수만 센터장님 요청
						coverImageView.setVisibility(View.GONE);
					} else {
						if (getVisibleCoverResource()) {
							coverImageView.bringToFront();
						}
					}
				}
				
				public void onAnimationRepeat(Animation animation) {
				}
				
				public void onAnimationEnd(Animation animation) {
					if (showLoadingCover) {
						// 2012.11.28: 로딩중일때만 커버 나오도록 수정
						// 박수만 센터장님 요청
						coverImageView.setVisibility(View.GONE);
					} else {
						if (getVisibleCoverResource()) {
							coverImageView.bringToFront();
						}
					}
				}
			});
			
			fadeIn.setDuration(500);
		} else {
			fadeIn.reset();
		}
		
		imageView.setVisibility(View.VISIBLE);
		imageView.startAnimation(fadeIn);
		
		if (fadeOut == null) {
			fadeOut = new AlphaAnimation(1, 1);
			fadeOut.setAnimationListener(new AnimationListener() {
				public void onAnimationStart(Animation animation) {
				}
				
				public void onAnimationRepeat(Animation animation) {
				}
				
				public void onAnimationEnd(Animation animation) {
					defaultImageView.setVisibility(View.GONE);
				}
			});
			
			fadeOut.setDuration(200);
		} else {
			fadeOut.reset();
		}
		
		if (getVisibleDefaultResource()) {
			defaultImageView.startAnimation(fadeOut);
		}
		
		if (showProgress && progressBar.getVisibility() == View.VISIBLE) {
			progressBarWrap.setVisibility(View.GONE);
			progressBar.setVisibility(View.GONE);
		}
	}
	
	public void setImageResourceId(int resId) {
		setImageDrawable(M3Application.getCurrentApplication().getResources().getDrawable(resId));
	}
	
	public void setImageDrawable(Drawable drawable) {
		progressBarWrap.setVisibility(View.GONE);
		progressBar.setVisibility(View.GONE);
		imageView.setVisibility(View.VISIBLE);
		
		if (showFadeAnimation) {
			startFadeAnimation();
		}
		
		imageView.setImageDrawable(drawable);
	}
	
	private void setScaleType(String scaleType) {
		if (scaleType == null) {
			return;
		}
		
		if (scaleType.equals("matrix")) {
			setScaleType(ScaleType.MATRIX);
		} else if (scaleType.equals("fitXY")) {
			setScaleType(ScaleType.FIT_XY);
		} else if (scaleType.equals("fitStart")) {
			setScaleType(ScaleType.FIT_START);
		} else if (scaleType.equals("fitCenter")) {
			setScaleType(ScaleType.FIT_CENTER);
		} else if (scaleType.equals("fitEnd")) {
			setScaleType(ScaleType.FIT_END);
		} else if (scaleType.equals("center")) {
			setScaleType(ScaleType.CENTER);
		} else if (scaleType.equals("centerCrop")) {
			setScaleType(ScaleType.CENTER_CROP);
		} else if (scaleType.equals("centerInside")) {
			setScaleType(ScaleType.CENTER_INSIDE);
		}
	}
	
	public void setScaleType(ScaleType scaleType) {
		imageView.setScaleType(scaleType);
		
		if (scaleType == ScaleType.MATRIX) {
			scaleType = ScaleType.FIT_XY;
		}
		
		defaultImageView.setScaleType(scaleType);
		coverImageView.setScaleType(scaleType);
	}
	
	public ScaleType getScaleType() {
		return imageView.getScaleType();
	}
	
	public Drawable getDrawable() {
		return imageView.getDrawable();
	}
	
	public static interface UrlImageViewEventListener {
		void onLoadCompleted(Bitmap bitmap);
		void onError(ApiResponse result);
	}
}
