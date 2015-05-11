package yhh.com.curvelistview;

/**
 * Created by Yen-Hsun_Huang on 2015/5/11.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.FrameLayout;

public class RoundedCornerLayout extends FrameLayout {
    private final static float CORNER_RADIUS = 40.0f;
    private boolean mIsTop = false, mIsBottom = false;
    private Bitmap mTopMask, mBottomMask, mTopAndBottomMask;
    private Paint paint, maskPaint;
    private float cornerRadius;

    public RoundedCornerLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RoundedCornerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RoundedCornerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CORNER_RADIUS, metrics);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        setWillNotDraw(false);
    }

    public void setTop(boolean isTop) {
        mIsTop = isTop;
    }

    public void setBottom(boolean isBottom) {
        mIsBottom = isBottom;
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        if (!mIsTop && !mIsBottom) {
            super.dispatchDraw(canvas);
            return;
        }
        Bitmap mask = null;
        if (mIsTop && mIsBottom) {
            if (mTopAndBottomMask == null)
                mTopAndBottomMask = createMask(canvas.getWidth(), canvas.getHeight());
            mask = mTopAndBottomMask;
        } else if (mIsTop) {
            if (mTopMask == null)
                mTopMask = createMask(canvas.getWidth(), canvas.getHeight());
            mask = mTopMask;
        } else {
            if (mBottomMask == null)
                mBottomMask = createMask(canvas.getWidth(), canvas.getHeight());
            mask = mBottomMask;
        }

        Bitmap offscreenBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas offscreenCanvas = new Canvas(offscreenBitmap);

        super.dispatchDraw(offscreenCanvas);

        offscreenCanvas.drawBitmap(mask, 0f, 0f, maskPaint);
        canvas.drawBitmap(offscreenBitmap, 0f, 0f, paint);
    }

    private Bitmap createMask(int width, int height) {
        int startY, endY;
        if (mIsTop && mIsBottom) {
            startY = 0;
            endY = height;
        } else if (mIsTop) {
            startY = 0;
            endY = height + (int) cornerRadius * 2;
        } else {
            startY = -(int) cornerRadius * 2;
            endY = height;
        }
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(mask);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);

        canvas.drawRect(0, 0, width, height, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRoundRect(new RectF(0, startY, width, endY), cornerRadius, cornerRadius, paint);

        return mask;
    }
}