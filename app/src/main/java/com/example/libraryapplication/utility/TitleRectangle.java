package com.example.libraryapplication.utility;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.example.libraryapplication.R;

import java.io.Serializable;


public class TitleRectangle extends View implements Serializable {

    private static Paint sRectPaint;
    private EmbossMaskFilter filter;
    private float[] mLightSource = new float[] {2, 5, 5};
    private float mAmbientLight = 0.15F;
    private float mSpecularHighlight = 3.75F;
    private float mBlurRadius = 15.6f;
    private Rect boundingbox;
    Context context;

    public TitleRectangle(Context context, Rect boundingbox, int layoutHeight) {
        super(context);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        int left= 0;
        int top=boundingbox.top;
        Log.d("title rectangle", "top bounding box: " + top);
        int right = layoutHeight;
        int bottom=boundingbox.bottom;
        this.boundingbox = new Rect(left,top,right,bottom);
        filter = new EmbossMaskFilter(mLightSource, mAmbientLight, mSpecularHighlight, mBlurRadius);

        if (sRectPaint == null) {
            sRectPaint = new Paint();
            sRectPaint.setColor(getResources().getColor(R.color.accent));
            sRectPaint.setAlpha(200);
            sRectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            sRectPaint.setStrokeWidth(8.0f);
            sRectPaint.setAntiAlias(true);
            sRectPaint.setDither(true);
            sRectPaint.setMaskFilter(filter);
        }
        postInvalidate();
    }

    public void onDraw(Canvas canvas){
        canvas.save();
        canvas.translate(0, getHeight());
        canvas.rotate(-90);
        canvas.drawRect(boundingbox, sRectPaint);
        canvas.restore();
    }


}
