package ru.dsoft38.hosteshelper;

import java.net.DatagramPacket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditStatus extends Activity {
	
	TextView tvDateTime;
    Typeface helv3thin;
    Typeface helv4light;

	int DIALOG_TIME = 1;
	int DIALOG_DATA_TIME = 2;
	int myHour = 14;
	int myMinute = 35;

    public  String _addressMySQL = "";
    public  String _portMySQL = "3306";
    public  String _userMySQL = "";
    public  String _passwdMySQL = "";

    public String[] names = {};

    public Switch swBusy;
    public CheckBox chbReserved;
    public ListView lv;
	
	static final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

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
		setContentView(R.layout.edit_status);

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

		// используем адаптер данных
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1, new String[] {"pen","price","color"},);
		//SimpleAdapter adapter = new SimpleAdapter(this,list,android.R.layout.simple_list_item_1,new String[] {"pen","price"},new int[] {R.id.tvListMenu,R.id.tvListComment});

        // получаем экземпляр элемента ListView
        lv = (ListView)findViewById(R.id.listEdit);
		// создаем адаптер    
		adapter = new ArrayAdapter<String>(this,  R.layout.edit_list_item, R.id.TopText, names);
		
		lv.setAdapter(adapter);

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
                    "WHERE table_status.room = " + MyActivity.roomNum + " AND table_status.table = " + MyActivity.tableNum + " AND table_status.garcon_id = garcon.id;", "select");
        }
        else {
            Toast.makeText(getBaseContext(), "Вы не подключены к сети.", Toast.LENGTH_SHORT).show();
        }
	}
	
  	@SuppressWarnings("deprecation")
	public void onClick(View v){
  		
  		switch (v.getId()) {
//		case R.id.tvDateTime:
//			showDialog(DIALOG_TIME);
//			break;

		default:
			break;
		}
  		showDialog(DIALOG_DATA_TIME);
  	}
  	
	// Диалог просмотра статуса столика
    @SuppressWarnings("deprecation")
	protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
            return tpd;
          }
        
        // Создаем диалог ввода даты и времени
        if(id == DIALOG_DATA_TIME){
            AlertDialog.Builder adb = new AlertDialog.Builder(this); 
            
            adb.setTitle("Дата и время");   
            // создаем view из dialog.xml    
            LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.date_time, null);   
            // устанавливаем ее, как содержимое тела диалога    
            adb.setView(view);    
            // находим TexView для отображения кол-ва    
            DatePicker dpData = (DatePicker) view.findViewById(R.id.datePicker1);   
            dpData.setCalendarViewShown(false);
            
            TimePicker tpTime = (TimePicker) view.findViewById(R.id.timePicker1);
            tpTime.setIs24HourView(true);
            
            adb.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
	    		@SuppressWarnings("deprecation")
				@SuppressLint("SimpleDateFormat")
				public void onClick(DialogInterface dialog, int id) {
		    		//MainActivity.this.finish();
//	    			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//	    			sendData("|AddData|" + spnTableName.getSelectedItem().toString() + 
//	    					"|" + spnTableStatus.getSelectedItem().toString() + 
//	    					"|" + sdf.format(System.currentTimeMillis()) +
//	    					"|" + timeOrder.getCurrentHour() + ":" + timeOrder.getCurrentMinute() +
//	    					"|" + edtGuests.getText().toString() + 
//	    					"|" + spnGarcon.getSelectedItem().toString() + "|\n");	    			
//		    		// Обновление состояния столиков
//		    		sendData("|ReadTableStatus|\n");
                    if( _addressMySQL == "" || _userMySQL == "" || _passwdMySQL == "") {
                        Toast.makeText(getBaseContext(), "Не заполнены параметры подключения к серверу.", Toast.LENGTH_SHORT).show();
                    }
                    else if (isOnline()) {
                        String _busy = "1";
                        String _reserved = "0";

                        if(swBusy.isChecked())
                            _busy = "0";

                        if(chbReserved.isChecked())
                            _reserved = "1";

                        String str =  "INSERT INTO `table_status`(`room`, `table`, `garcon_id`, `guests`, `reserved`, `busy`, `comment`) VALUES ('" + MyActivity.roomNum + "','" + MyActivity.tableNum + "','3','4','" + _reserved + "','" + _busy + "','value-7');";
                        new ConnectorMySQL().execute(_addressMySQL, _portMySQL, "HostesHelper", _userMySQL, _passwdMySQL, str, "insert");
                    }
                    else {
                        Toast.makeText(getBaseContext(), "Вы не подключены к сети.", Toast.LENGTH_SHORT).show();
                    }

		    		removeDialog(DIALOG_DATA_TIME);
	    		}
    		});

            adb.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
	    		@SuppressWarnings("deprecation")
				public void onClick(DialogInterface dialog, int id) {
	    			//dialog.cancel();
	    			removeDialog(DIALOG_DATA_TIME);
	    		}
    		});
    		
            return adb.create();
        }
          return super.onCreateDialog(id);
    }
	
    OnTimeSetListener myCallBack = new OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	          myHour = hourOfDay;
	          myMinute = minute; 
	          //tvTime.setText("Time is " + myHour + " hours " + myMinute + " minutes");
		}
      };
      
      @SuppressWarnings("deprecation")
	  @Override  
      protected void onPrepareDialog(int id, Dialog dialog) {    
    	  super.onPrepareDialog(id, dialog);    
    	  if (id == DIALOG_DATA_TIME) {
    		  
    	  }
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
                adapter = new ArrayAdapter<String>(this, R.layout.edit_list_item, R.id.TopText, names);
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

                if(arg[6] == "select") {
                    rs = st.executeQuery(sql);
                } else if (arg[6] == "insert"){
                    st.execute(sql);
                }

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

            pDialog = new ProgressDialog(EditStatus.this);
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
