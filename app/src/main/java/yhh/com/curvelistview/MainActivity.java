package yhh.com.curvelistview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

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
