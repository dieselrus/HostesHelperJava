package ru.dsoft38.hosteshelper;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
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

    public  String _addressMySQL = "";
    public  String _portMySQL = "3306";
    public  String _userMySQL = "";
    public  String _passwdMySQL = "";

    public String[] names = {};

    public Switch swBusy;
    public CheckBox chbReserved;
    public ListView lv;

    public ArrayAdapter<String> adapter;

    private ProgressDialog pDialog;

    // Определяем подключены ли к интернету
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            // Log.i(LOG_TAG, "ONLINE");
            return true;
        }
        else {
            // Log.i(LOG_TAG, "OFFLINE");
            return false;
        }
    }

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

        swBusy = (Switch) findViewById(R.id.swStatus);
        chbReserved = (CheckBox) findViewById(R.id.chbReserved);

        // получаем экземпляр элемента ListView
        lv = (ListView)findViewById(R.id.listEdit);
        // создаем адаптер
        adapter = new ArrayAdapter<String>(this, R.layout.my_list_item, names);

        lv.setAdapter(adapter);

        ActionBar actionBar = getActionBar();
//	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.hide();
//	    actionBar.setTitle(getString(R.string.room) + _room);

        _addressMySQL = MyActivity.getAddressMySQL();
        _portMySQL = MyActivity.getPortMySQL();
        _userMySQL = MyActivity.getUserMySQL();
        _passwdMySQL = MyActivity.getpasswdMySQL();

        if( _addressMySQL == "" || _userMySQL == "" || _passwdMySQL == "") {
            Toast.makeText(getBaseContext(), "Не заполнены параметры подключения к серверу.", Toast.LENGTH_SHORT).show();
        }
        else if (isOnline()) {
            new ConnectorMySQL().execute(_addressMySQL, _portMySQL, "HostesHelper", _userMySQL, _passwdMySQL, "SELECT table_status.guests, table_status.reserved, table_status.busy, garcon.name \n" +
                    "FROM table_status, garcon\n" +
                    "WHERE table_status.room = " + MyActivity.roomNum + " AND table_status.table = " + MyActivity.tableNum + " AND table_status.garcon_id = garcon.id;");
        }
        else {
            Toast.makeText(getBaseContext(), "Вы не подключены к сети.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v) {
        finish();
    }

    public void setResultSQL(ResultSet res){
        try {
            while (res.next())
            {
                //int _guests = res.getInt(1);
                chbReserved.setChecked(res.getBoolean(2));
                swBusy.setChecked(!res.getBoolean(3));

                names = new String[1];
                names[0] = res.getString(4);
                adapter = new ArrayAdapter<String>(this, R.layout.my_list_item, names);
                lv.setAdapter(adapter);
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
                conexionMySQL.close();

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
