package cn.nevercode.module.main.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.nevercode.base.BaseActivity;
import cn.nevercode.cola.R;
import cn.nevercode.module.main.adapter.AlbumAdapter;
import cn.nevercode.module.main.model.PapersModel;
import cn.nevercode.widget.MyLogUtil;
import cn.nevercode.xlistview.IXListViewListener;
import cn.nevercode.xlistview.XListView;

public class MainActivity extends BaseActivity implements IXListViewListener {

    XListView listView;
    AlbumAdapter AlbumAdapter;
    List<PapersModel.DataBean> list;
    View header;
    TextView tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        list = new ArrayList<>();
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(false);
        listView.setXListViewListener(this, 4);
        SharedPreferences setting = getSharedPreferences("cola", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {//第一次
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            setting.edit().putBoolean("FIRST", false).commit();
        }
        header = View.inflate(MainActivity.this, R.layout.header, null);
        tip = header.findViewById(R.id.tips);
        listView.startHeaderRefresh();
    }


    @Override
    public void onRefresh(int id) {
        getWallpapers();
    }

    @Override
    public void onLoadMore(int id) {

    }

    public void getWallpapers() {
        new Thread(() -> {
            try {
                URL url = new URL("https://api.klbz.club/db.json");
                //得到connection对象。
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //设置请求方式
                connection.setRequestMethod("GET");
                //连接
                connection.connect();
                //得到响应码
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    //得到响应流
                    InputStream inputStream = connection.getInputStream();
                    //将响应流转换成字符串
                    String result = is2String(inputStream);//将流转换为字符串。

                    MyLogUtil.d(result);
                    MainActivity.this.runOnUiThread(() -> {
                        try {
                            listView.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
                            PapersModel papersModel = new Gson().fromJson(result, PapersModel.class);
                            AlbumAdapter = new AlbumAdapter(this, papersModel.getData());
                            if (listView.getHeaderViewsCount()>1){
                                listView.removeHeaderView(header);
                            }
                            if (!TextUtils.isEmpty(papersModel.getTips())) {
                                tip.setText(papersModel.getTips());
                            } else {
                                tip.setText("自然很小，山川，大海，河流，湖泊，森林；\n自然很大，大到无数个镜头都装不下。关于自然的部分，我只想给你看最好的。\n因为：\n\n这是计划的一部分。--罗辑");
                            }
                            listView.addHeaderView(header);
                        } catch (Exception e) {
                            MyLogUtil.e(e.getMessage() + "====" + e.getCause());
                            AlbumAdapter = new AlbumAdapter(this, null);
                            tip.setText("自然很小，山川，大海，河流，湖泊，森林；\n自然很大，大到无数个镜头都装不下。关于自然的部分，我只想给你看最好的。\n因为：\n\n这是计划的一部分。--罗辑");
                            listView.addHeaderView(header);
                        }
                        listView.setAdapter(AlbumAdapter);
                    });
                }
            } catch (Exception e) {
                MyLogUtil.e(e.getMessage() + "====" + e.getCause());
                e.printStackTrace();
            }
        }).start();
    }


    public String is2String(InputStream is) throws Exception {

        //连接后，创建一个输入流来读取response
        BufferedReader bufferedReader = new BufferedReader(new
                InputStreamReader(is, "utf-8"));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        String response = "";
        //每次读取一行，若非空则添加至 stringBuilder
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        //读取所有的数据后，赋值给 response
        response = stringBuilder.toString().trim();
        return response;

    }
}