package com.huxi.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ProgressHUDImageView extends ImageView {

	private final  Paint paint;  
    private final Context context;
    private SweepGradient gradient;
	
	public ProgressHUDImageView(Context context) {
		this(context,null);
	}
	
	public ProgressHUDImageView(Context context,AttributeSet attrs){
		super(context, attrs);
		this.context = context;  
        this.paint = new Paint();  
        this.paint.setAntiAlias(true); //消除锯齿  
        this.paint.setStyle(Paint.Style.STROKE); //绘制空心圆
	}

	@Override
	public void onDraw(Canvas canvas) {  
		// super.draw(canvas); 
        int centerX = getWidth()/2; 
        int centerY = getHeight()/2;
        int innerCircle = dip2px(context, getWidth()/4.5f); //设置内圆半径

        int ringWidth = dip2px(context, 2); //设置圆环宽度  
        this.paint.setStrokeWidth(ringWidth);
        if (gradient == null) {
        	gradient = new SweepGradient(centerX, centerY, Color.BLACK, Color.WHITE);
		}
        this.paint.setShader(gradient);
        canvas.drawCircle(centerX,centerY, innerCircle+1+ringWidth/2, this.paint);  
    }  
      
      
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }
	
}
