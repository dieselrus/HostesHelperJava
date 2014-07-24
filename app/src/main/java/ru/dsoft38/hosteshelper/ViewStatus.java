package ru.dsoft38.hosteshelper;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewStatus extends Activity {
    Typeface helv3thin;
    Typeface helv4light;
    TextView tv;

    private ProgressDialog pDialog;

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

    public void setResultSQL(ResultSet res){
        try {
            while (res.next())
            {

            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public class ConnectorMySQL extends AsyncTask<String, Void, ResultSet> {

        @Override
        protected ResultSet doInBackground(String... arg) {

            ResultSet rs = null;

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                Connection conexionMySQL = DriverManager.getConnection("jdbc:mysql://" + arg[0] + ":" + arg[1] + "/" + arg[2], arg[3], arg[4]);

                Statement st = conexionMySQL.createStatement();
                String sql = arg[5];
                rs = st.executeQuery(sql);
                //rs.next();
                //str_res = rs.getString(1);
                //System.out.print(str_res);

            } catch (Exception e) {
                System.out.println("Error" + e.getMessage());
            }

            return rs;
        }

        /**
         * Перед началом фонового потока Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(ViewStatus.this);
            pDialog.setMessage("Получение данных.\n Подождите...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected void onPostExecute(ResultSet result) {
            // закрываем прогресс диалог после получение все продуктов
            pDialog.dismiss();
            if(result == null){
                Toast.makeText(getBaseContext(), "Cервер вернул пустые данные. Возможно он не доступен.", Toast.LENGTH_SHORT).show();
            } else {
                setResultSQL(result);
            }
        }
    }
}
