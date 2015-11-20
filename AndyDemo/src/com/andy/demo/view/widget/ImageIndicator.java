package com.andy.demo.view.widget;

import com.andy.demo.BuildConfig;
import com.andy.demo.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ImageIndicator extends View {
	private int count;
	private float space, radius;
	private int point_normal_color, point_seleted_color;

	// 选中
	private int seleted = 0;

	public ImageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ImageIndicator);

		count = a.getInteger(R.styleable.ImageIndicator_count, 4);
		space = a.getDimension(R.styleable.ImageIndicator_space, 9);
		radius = a.getDimension(R.styleable.ImageIndicator_point_radius, 9);

		point_normal_color = a.getColor(
				R.styleable.ImageIndicator_point_normal_color, 0x000000);
		point_seleted_color = a.getColor(
				R.styleable.ImageIndicator_point_seleted_color, 0xffff07);

		int sum = attrs.getAttributeCount();
		if (BuildConfig.DEBUG) {
			String str = "";
			for (int i = 0; i < sum; i++) {
				String name = attrs.getAttributeName(i);
				String value = attrs.getAttributeValue(i);
				str += "attr_name :" + name + ": " + value + "\n";
			}
			Log.i("attribute", str);
		}
		a.recycle();
	}

	public void setSeletion(int index) {
		this.seleted = index;
		invalidate();
	}

	public void setCount(int count) {
		this.count = count;
		invalidate();
	}

	public void next() {
		if (seleted < count - 1)
			seleted++;
		else
			seleted = 0;
		invalidate();
	}

	public void previous() {
		if (seleted > 0)
			seleted--;
		else
			seleted = count - 1;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setAntiAlias(true);

		float width = (getWidth() - ((radius * 2 * count) + (space * (count - 1)))) / 2.f;

		for (int i = 0; i < count; i++) {
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			if (i == seleted)
				paint.setColor(point_seleted_color);
			else
				paint.setColor(point_normal_color);
			
			canvas.drawCircle(width + getPaddingLeft() + radius + i
					* (space + radius + radius), getHeight() / 2, radius, paint);
			if (i != seleted) {
				paint.setColor(0xffb7b7b7);
				paint.setStyle(Paint.Style.STROKE);
				paint.setStrokeWidth(1);
				canvas.drawCircle(width + getPaddingLeft() + radius + i
						* (space + radius + radius), getHeight() / 2, radius, paint);
			}

		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = (int) (getPaddingLeft() + getPaddingRight()
					+ (count * 2 * radius) + (count - 1) * radius + 1);
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = (int) (2 * radius + getPaddingTop() + getPaddingBottom() + 1);
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}
}