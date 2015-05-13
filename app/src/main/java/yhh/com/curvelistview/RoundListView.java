package yhh.com.curvelistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by Yen-Hsun_Huang on 2015/5/12.
 */
public class RoundListView extends ListView {
    // method 1
    private Paint mPaint, mMaskPaint;
    private float mCornerRadius;
    private Bitmap mMask;
    private Bitmap mOffScreenBitmap;
    private Canvas mOffScreenCanvas;

    // method 2
    private Path mClipPath;

    public RoundListView(Context context) {
        this(context, null);
    }

    public RoundListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCornerRadius = context.getResources().getDimensionPixelSize(R.dimen.rounded_corner_radius);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        setWillNotDraw(false);
    }

    private void method1(Canvas canvas) {
        if (mOffScreenBitmap == null) {
            mOffScreenBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        }
        if (mOffScreenCanvas == null) {
            mOffScreenCanvas = new Canvas();
            mOffScreenCanvas.setBitmap(mOffScreenBitmap);
        }
        long time = System.currentTimeMillis();
        mOffScreenCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        Log.d("QQQQ", "1: " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        super.dispatchDraw(mOffScreenCanvas);
        Log.d("QQQQ", "2: " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        if (mMask == null) {
            mMask = createMask(canvas.getWidth(), canvas.getHeight());
        }
        mOffScreenCanvas.drawBitmap(mMask, 0f, 0f, mMaskPaint);
        Log.d("QQQQ", "3: " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        canvas.drawBitmap(mOffScreenBitmap, 0f, 0f, mPaint);
        Log.d("QQQQ", "4: " + (System.currentTimeMillis() - time));
        Log.w("QQQQ", "====================================");
//        long time = System.currentTimeMillis();
//        super.dispatchDraw(canvas);
//        Log.i("QQQQ", "time: " + (System.currentTimeMillis() - time));
    }

    private void method2(Canvas canvas) {
        final int width = canvas.getWidth();
        final int height = canvas.getHeight();
        if (mClipPath == null) {
            mClipPath = new Path();
            mClipPath.addRoundRect(new RectF(0, 0, width, height), mCornerRadius, mCornerRadius, Path.Direction.CW);
        }
        canvas.save();
        canvas.clipPath(mClipPath);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        method2(canvas);
    }

    private Bitmap createMask(int width, int height) {
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(mask);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        canvas.drawRect(0, 0, width, height, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRoundRect(new RectF(0, 0, width, height), mCornerRadius, mCornerRadius, paint);

        return mask;
    }
}
