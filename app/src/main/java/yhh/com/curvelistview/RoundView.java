package yhh.com.curvelistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Yen-Hsun_Huang on 2015/5/8.
 */
public class RoundView extends FrameLayout {
    private boolean mIsTop = false, mIsBottom = false;

    private final Path mClipPath = new Path();

    private final Paint mDebugPaint = new Paint();

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mDebugPaint.setColor(Color.MAGENTA);
        mDebugPaint.setStrokeWidth(3);
        mDebugPaint.setStyle(Paint.Style.STROKE);
        mDebugPaint.setAntiAlias(true);
    }

    public void setTop(boolean isTop) {
        mIsTop = isTop;
    }

    public void setBottom(boolean isBottom) {
        mIsBottom = isBottom;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (!mIsTop && !mIsBottom) {
            super.dispatchDraw(canvas);
            return;
        }
        mClipPath.reset();
        final int saveId = canvas.save();
        if (mIsTop) {
            mClipPath.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), 50, 50, Path.Direction.CW);
        }
        if (mIsBottom) {
        }
        canvas.clipPath(mClipPath);

        super.dispatchDraw(canvas);
        canvas.restoreToCount(saveId);
        canvas.drawPath(mClipPath, mDebugPaint);
    }
}
