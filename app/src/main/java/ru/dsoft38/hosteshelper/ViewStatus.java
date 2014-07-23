package ru.dsoft38.hosteshelper;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewStatus extends Activity {
    Typeface helv3thin;
    Typeface helv4light;
    TextView tv;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_status);

        helv3thin = Typeface.createFromAsset(getAssets(), "fonts/helv3thin.otf");
        helv4light = Typeface.createFromAsset(getAssets(), "fonts/helv4light.otf");

        RelativeLayout myRelativeLayout = (RelativeLayout) findViewById(R.id.rl1);

        for (int i = 0; i < myRelativeLayout.getChildCount(); i++) {
            if (myRelativeLayout.getChildAt(i) instanceof TextView)
                ((TextView) myRelativeLayout.getChildAt(i)).setTypeface(helv4light);
        }

        TextView tvRoom = (TextView) findViewById(R.id.tvRoom);
        tvRoom.setTypeface(helv3thin);
        tvRoom.setText(getString(R.string.room) + MyActivity.roomNum);

        TextView tvTable = (TextView) findViewById(R.id.tvTable);
        tvTable.setTypeface(helv3thin);
        tvTable.setText(getString(R.string.table) + MyActivity.tableNum);

        ActionBar actionBar = getActionBar();
//	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.hide();
//	    actionBar.setTitle(getString(R.string.room) + _room);
    }

    public void onClick(View v) {
        finish();
    }

}
