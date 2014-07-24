package ru.dsoft38.hosteshelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;


public class MyActivity extends Activity implements ActionBar.TabListener{
    ImageView img1, img2, img3, img4, img5, img6, img7, img8 ,img9, img10;
    ImageView[] ImageViewArrayRoom1, ImageViewArrayRoom2, ImageViewArrayRoom3, ImageViewArrayRoom4;

    ViewPager viewPager;
    public static int roomNum;
    public static int tableNum;

    public static String _addressMySQL = "";
    public static String _portMySQL = "3306";
    public static String _userMySQL = "";
    public static String _passwdMySQL = "";

    private ProgressDialog pDialog;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup action bar for tabs
        ActionBar actionBar = getActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();

        View page = inflater.inflate(R.layout.activity_tab, null);

        ImageViewArrayRoom1 = new ImageView[]{
                img1 = (ImageView) page.findViewById(R.id.imageView1),
                img2 = (ImageView) page.findViewById(R.id.imageView2),
                img3 = (ImageView) page.findViewById(R.id.imageView3),
                img4 = (ImageView) page.findViewById(R.id.imageView4),
                img5 = (ImageView) page.findViewById(R.id.imageView5),
                img6 = (ImageView) page.findViewById(R.id.imageView6),
                img7 = (ImageView) page.findViewById(R.id.imageView7),
                img8 = (ImageView) page.findViewById(R.id.imageView8),
                img9 = (ImageView) page.findViewById(R.id.imageView9),
                img10 = (ImageView) page.findViewById(R.id.imageView10)
        };

        for(int i = 0; i < ImageViewArrayRoom1.length; i++){
            ImageViewArrayRoom1[i].setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            //ImageViewArrayRoom1[i].setImageDrawable(new SvgDrawable(CreateSVGImage(i+1, false, 0, "blue"), (getResources().getDisplayMetrics().xdpi + getResources().getDisplayMetrics().ydpi) / 2));
            SVG svg = SVGParser.getSVGFromString(CreateSVGImage(i+1, false, 0, "blue"));
            //Picture picture = svg.getPicture();
            Drawable drawable = svg.createPictureDrawable();
            ImageViewArrayRoom1[i].setImageDrawable(drawable);
            ImageViewArrayRoom1[i].setOnLongClickListener(bu);
        }

        pages.add(page);

        page = inflater.inflate(R.layout.activity_tab, null);

        ImageViewArrayRoom2 = new ImageView[]{
                img1 = (ImageView) page.findViewById(R.id.imageView1),
                img2 = (ImageView) page.findViewById(R.id.imageView2),
                img3 = (ImageView) page.findViewById(R.id.imageView3),
                img4 = (ImageView) page.findViewById(R.id.imageView4),
                img5 = (ImageView) page.findViewById(R.id.imageView5),
                img6 = (ImageView) page.findViewById(R.id.imageView6),
                img7 = (ImageView) page.findViewById(R.id.imageView7),
                img8 = (ImageView) page.findViewById(R.id.imageView8),
                img9 = (ImageView) page.findViewById(R.id.imageView9),
                img10 = (ImageView) page.findViewById(R.id.imageView10)
        };

        for(int i = 0; i < ImageViewArrayRoom2.length; i++){
            ImageViewArrayRoom2[i].setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            SVG svg = SVGParser.getSVGFromString(CreateSVGImage(i+1, false, 0, "yellow"));
            Drawable drawable = svg.createPictureDrawable();
            ImageViewArrayRoom2[i].setImageDrawable(drawable);
            ImageViewArrayRoom2[i].setOnLongClickListener(bu);
        }
        pages.add(page);

        page = inflater.inflate(R.layout.activity_tab, null);
//        textView = (TextView) page.findViewById(R.id.text_view);
//        textView.setText("—Ú‡ÌËˆ‡ 3");

        ImageViewArrayRoom3 = new ImageView[]{
                img1 = (ImageView) page.findViewById(R.id.imageView1),
                img2 = (ImageView) page.findViewById(R.id.imageView2),
                img3 = (ImageView) page.findViewById(R.id.imageView3),
                img4 = (ImageView) page.findViewById(R.id.imageView4),
                img5 = (ImageView) page.findViewById(R.id.imageView5),
                img6 = (ImageView) page.findViewById(R.id.imageView6),
                img7 = (ImageView) page.findViewById(R.id.imageView7),
                img8 = (ImageView) page.findViewById(R.id.imageView8),
                img9 = (ImageView) page.findViewById(R.id.imageView9),
                img10 = (ImageView) page.findViewById(R.id.imageView10)
        };

        for(int i = 0; i < ImageViewArrayRoom3.length; i++){
            ImageViewArrayRoom3[i].setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            SVG svg = SVGParser.getSVGFromString(CreateSVGImage(i+1, false, 0, "green"));
            Drawable drawable = svg.createPictureDrawable();
            ImageViewArrayRoom3[i].setImageDrawable(drawable);
            ImageViewArrayRoom3[i].setOnLongClickListener(bu);
        }
        pages.add(page);

        page = inflater.inflate(R.layout.activity_tab, null);
//        textView = (TextView) page.findViewById(R.id.text_view);
//        textView.setText("—Ú‡ÌËˆ‡ 4");

        ImageViewArrayRoom4 = new ImageView[]{
                img1 = (ImageView) page.findViewById(R.id.imageView1),
                img2 = (ImageView) page.findViewById(R.id.imageView2),
                img3 = (ImageView) page.findViewById(R.id.imageView3),
                img4 = (ImageView) page.findViewById(R.id.imageView4),
                img5 = (ImageView) page.findViewById(R.id.imageView5),
                img6 = (ImageView) page.findViewById(R.id.imageView6),
                img7 = (ImageView) page.findViewById(R.id.imageView7),
                img8 = (ImageView) page.findViewById(R.id.imageView8),
                img9 = (ImageView) page.findViewById(R.id.imageView9),
                img10 = (ImageView) page.findViewById(R.id.imageView10)
        };

        for(int i = 0; i < ImageViewArrayRoom4.length; i++){
            ImageViewArrayRoom4[i].setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            SVG svg = SVGParser.getSVGFromString(CreateSVGImage(i+1, false, 0, "magenta"));
            Drawable drawable = svg.createPictureDrawable();
            ImageViewArrayRoom4[i].setImageDrawable(drawable);
            ImageViewArrayRoom4[i].setOnLongClickListener(bu);
        }
        pages.add(page);

        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
        // ViewPager viewPager = new ViewPager(this);
        viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);

        setContentView(viewPager);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        _addressMySQL = sp.getString("ServerAddress","");
        _portMySQL = sp.getString("ServerPort","");
        _userMySQL = sp.getString("ServerUser","");
        _passwdMySQL = sp.getString("ServerPasswd","");

        if(_addressMySQL == "" || _userMySQL == "" || _passwdMySQL == "") {
            Toast.makeText(getBaseContext(), "Не заполнены параметры подключения к серверу.", Toast.LENGTH_SHORT).show();
        }
        else if (isOnline()) {
            new ConnectorMySQL().execute(_addressMySQL, _portMySQL, "HostesHelper", _userMySQL, _passwdMySQL, "SELECT  `room` ,  `table` ,  `guests` ,  `reserved`,  `busy` FROM  `table_status`;");
        }
        else {
            Toast.makeText(getBaseContext(), "Вы не подключены к сети.", Toast.LENGTH_SHORT).show();
        }

    }

    // Долгое нажатие на картинку
    ImageView.OnLongClickListener bu = new ImageView.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            // Toast.makeText(getBaseContext(), "Long Clicked Button1.", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(MainActivity.this, EditStatus.class);
            //startActivity(intent);
            return true;
        }
    };


    // Обработка нажатия
    public void onClick(View v){
        Toast.makeText(getApplicationContext(), "Click view id " + v.getId() + " " + viewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
        roomNum = viewPager.getCurrentItem() + 1;
        //tableNum = (int) v.getTag();
        tableNum = Integer.valueOf((String) v.getTag());

        //sendData("|ReadTableData|" + roomNum + "-"+ tableNum + "|\n");

        Intent intent = new Intent(MyActivity.this, ViewStatus.class);
        startActivity(intent);
    }

    // Генерация svg файла
    public String CreateSVGImage(int bigNum, boolean Reserved, int smallNum, String Color){
        String svg = getString(R.string.svg_begin);

        svg += getString(R.string.svg_rectangle);
        // Резервирование
        if(Reserved)
            svg += getString(R.string.svg_reserved);

        // Комнаты
        switch (bigNum) {
            case 1:
                svg += getString(R.string.svg_big1);
                break;
            case 2:
                svg += getString(R.string.svg_big2);
                break;
            case 3:
                svg += getString(R.string.svg_big3);
                break;
            case 4:
                svg += getString(R.string.svg_big4);
                break;
            case 5:
                svg += getString(R.string.svg_big5);
                break;
            case 6:
                svg += getString(R.string.svg_big6);
                break;
            case 7:
                svg += getString(R.string.svg_big7);
                break;
            case 8:
                svg += getString(R.string.svg_big8);
                break;
            case 9:
                svg += getString(R.string.svg_big9);
                break;
            case 10:
                svg += getString(R.string.svg_big10);
                break;
        }

        // гости
        switch (smallNum) {
            case 1:
                svg += getString(R.string.svg_small_1);
                break;
            case 2:
                svg += getString(R.string.svg_small_2);
                break;
            case 3:
                svg += getString(R.string.svg_small_3);
                break;
            case 4:
                svg += getString(R.string.svg_small_4);
                break;
            case 5:
                svg += getString(R.string.svg_small_5);
                break;
            case 6:
                svg += getString(R.string.svg_small_6);
                break;
            case 7:
                svg += getString(R.string.svg_small_7);
                break;
            case 8:
                svg += getString(R.string.svg_small_8);
                break;
            case 9:
                svg += getString(R.string.svg_small_9);
                break;
            case 10:
                svg += getString(R.string.svg_small_10);
                break;
        }

        svg += getString(R.string.svg_end);

        // красный цвет
        if (Color.compareToIgnoreCase("red") == 0){
            Pattern pattern1  = Pattern.compile("color1");
            Matcher matcher1 = pattern1.matcher(svg);
            svg = matcher1.replaceAll(getString(R.string.svg_color_red1));

            Pattern pattern2  = Pattern.compile("color2");
            Matcher matcher2 = pattern2.matcher(svg);
            svg = matcher2.replaceAll(getString(R.string.svg_color_red2));
        }

        if (Color.compareToIgnoreCase("blue") == 0){
            Pattern pattern1  = Pattern.compile("color1");
            Matcher matcher1 = pattern1.matcher(svg);
            svg = matcher1.replaceAll(getString(R.string.svg_color_blue1));

            Pattern pattern2  = Pattern.compile("color2");
            Matcher matcher2 = pattern2.matcher(svg);
            svg = matcher2.replaceAll(getString(R.string.svg_color_blue2));
        }

        if (Color.compareToIgnoreCase("yellow") == 0){
            Pattern pattern1  = Pattern.compile("color1");
            Matcher matcher1 = pattern1.matcher(svg);
            svg = matcher1.replaceAll(getString(R.string.svg_color_yellow1));

            Pattern pattern2  = Pattern.compile("color2");
            Matcher matcher2 = pattern2.matcher(svg);
            svg = matcher2.replaceAll(getString(R.string.svg_color_yellow2));
        }

        if (Color.compareToIgnoreCase("green") == 0){
            Pattern pattern1  = Pattern.compile("color1");
            Matcher matcher1 = pattern1.matcher(svg);
            svg = matcher1.replaceAll(getString(R.string.svg_color_green1));

            Pattern pattern2  = Pattern.compile("color2");
            Matcher matcher2 = pattern2.matcher(svg);
            svg = matcher2.replaceAll(getString(R.string.svg_color_green2));
        }

        if (Color.compareToIgnoreCase("magenta") == 0){
            Pattern pattern1  = Pattern.compile("color1");
            Matcher matcher1 = pattern1.matcher(svg);
            svg = matcher1.replaceAll(getString(R.string.svg_color_magenta1));

            Pattern pattern2  = Pattern.compile("color2");
            Matcher matcher2 = pattern2.matcher(svg);
            svg = matcher2.replaceAll(getString(R.string.svg_color_magenta2));
        }
        //Drawable img = SVGParser.getSVGFromString(svg).createPictureDrawable();
        return svg;
    }

    // меню
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    // Обрабатывает нажатия на пунктах меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
//	        Log.i("LOG", "Settings");
                Intent intent = new Intent(MyActivity.this, PrefActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_update:
                if(_addressMySQL == "" || _userMySQL == "" || _passwdMySQL == "") {
                    Toast.makeText(getBaseContext(), "Не заполнены параметры подключения к серверу.", Toast.LENGTH_SHORT).show();
                }
                else if (isOnline()) {
                    new ConnectorMySQL().execute(_addressMySQL, _portMySQL, "HostesHelper", _userMySQL, _passwdMySQL, "SELECT  `room` ,  `table` ,  `guests` ,  `reserved`,  `busy` FROM  `table_status`;");
                }
                else {
                    Toast.makeText(getBaseContext(), "Вы не подключены к сети.", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {

    }

    public void setResultSQL(ResultSet res){
        try {
            while (res.next())
            {
                //System.out.println(str_res);
                String _color = "red";

                int roomID = res.getInt(1);
                int _tableID = res.getInt(2);
                int _guests = res.getInt(3);
                boolean _reserved = res.getBoolean(4);
                boolean _busy = res.getBoolean(5);

                // Если столик занят, то красим его
                if(_busy){
                    switch (roomID) {
                        case 1:
                            //ImageViewArrayRoom1[_tableID-1].setImageDrawable(new SvgDrawable(CreateSVGImage(_tableID, _reserved, _guests, _color), (getResources().getDisplayMetrics().xdpi + getResources().getDisplayMetrics().ydpi) / 2));
                            SVG svg1 = SVGParser.getSVGFromString(CreateSVGImage(_tableID, _reserved, _guests, _color));
                            Drawable drawable1 = svg1.createPictureDrawable();
                            ImageViewArrayRoom1[_tableID-1].setImageDrawable(drawable1);
                            break;
                        case 2:
                            SVG svg2 = SVGParser.getSVGFromString(CreateSVGImage(_tableID, _reserved, _guests, _color));
                            Drawable drawable2 = svg2.createPictureDrawable();
                            ImageViewArrayRoom2[_tableID-1].setImageDrawable(drawable2);
                            break;
                        case 3:
                            SVG svg3 = SVGParser.getSVGFromString(CreateSVGImage(_tableID, _reserved, _guests, _color));
                            Drawable drawable3 = svg3.createPictureDrawable();
                            ImageViewArrayRoom3[_tableID-1].setImageDrawable(drawable3);
                            break;
                        case 4:
                            SVG svg4 = SVGParser.getSVGFromString(CreateSVGImage(_tableID, _reserved, _guests, _color));
                            Drawable drawable4 = svg4.createPictureDrawable();
                            ImageViewArrayRoom4[_tableID-1].setImageDrawable(drawable4);
                            break;
                    }
                }
                // Если столик не занят и зарезервирова, рисуем резерв
                else if (!_busy && _reserved) {

	    			switch (roomID) {
					case 1:
						SVG svg1 = SVGParser.getSVGFromString(CreateSVGImage(_tableID, _reserved, _guests, "blue"));
                        Drawable drawable1 = svg1.createPictureDrawable();
                        ImageViewArrayRoom1[_tableID-1].setImageDrawable(drawable1);
                        break;
					 case 2:
					 	SVG svg2 = SVGParser.getSVGFromString(CreateSVGImage(_tableID, _reserved, _guests, "yellow"));
                        Drawable drawable2 = svg2.createPictureDrawable();
                        ImageViewArrayRoom2[_tableID-1].setImageDrawable(drawable2);
                        break;
					 case 3:
					 	SVG svg3 = SVGParser.getSVGFromString(CreateSVGImage(_tableID, _reserved, _guests, "green"));
                        Drawable drawable3 = svg3.createPictureDrawable();
                        ImageViewArrayRoom3[_tableID-1].setImageDrawable(drawable3);
                        break;
					 case 4:
					 	SVG svg4 = SVGParser.getSVGFromString(CreateSVGImage(_tableID, _reserved, _guests, "magenta"));
                        Drawable drawable4 = svg4.createPictureDrawable();
                        ImageViewArrayRoom4[_tableID-1].setImageDrawable(drawable4);
                        break;
		    		}

                }
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

            pDialog = new ProgressDialog(MyActivity.this);
            pDialog.setMessage("Загрузка продуктов. Подождите...");
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
