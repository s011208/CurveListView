package yhh.com.curvelistview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;


public class MainActivity extends Activity {

    private ListView mRoundListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRoundListView = (ListView) findViewById(R.id.round_listview);
        RoundListAdapter adapter = new RoundListAdapter(this);
        mRoundListView.setAdapter(adapter);
    }
}
