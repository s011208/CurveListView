package yhh.com.curvelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Yen-Hsun_Huang on 2015/5/8.
 */
public class RoundListAdapter extends BaseAdapter {
    private final Context mContext;
    private final LayoutInflater mInflater;
    private static final int SIZE = 20;
    private static final ArrayList<Integer> mItems = new ArrayList<Integer>();

    static {
        for (int i = 0; i < SIZE; i++) {
            mItems.add(i);
        }
    }

    public RoundListAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return SIZE;
    }

    @Override
    public Integer getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item, null);
            holder.mBg = (ImageView) convertView.findViewById(R.id.bg);
            holder.mFg = (ImageView) convertView.findViewById(R.id.fg);
            holder.mRoundView = (RoundedCornerLayout) convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mBg.setImageResource(R.drawable.bg);
        holder.mFg.setImageResource(R.drawable.fg);
        holder.mRoundView.setTop(position == 0);
        holder.mRoundView.setBottom(position == getCount() - 1);
        return convertView;
    }

    private static class ViewHolder {
        ImageView mBg, mFg;
        RoundedCornerLayout mRoundView;
    }
}
