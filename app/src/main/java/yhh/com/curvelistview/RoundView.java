package yhh.com.curvelistview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Yen-Hsun_Huang on 2015/5/8.
 */
public class RoundView extends ImageView {
    private boolean mIsTop = false, mIsBottom = false;

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    public void setTop(boolean isTop) {
        mIsTop = isTop;
    }

    public void setBottom(boolean isBottom) {
        mIsBottom = isBottom;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (!mIsTop && !mIsBottom) {
            super.onDraw(canvas);
            return;
        }

        final int saveId = canvas.save();
        if (mIsTop) {
            canvas.clipRect(0, 0, 50, 50);
        }
        if (mIsBottom) {
            canvas.clipRect(50, 50, 100, 100);
        }
        super.onDraw(canvas);
        canvas.restoreToCount(saveId);
    }
}
