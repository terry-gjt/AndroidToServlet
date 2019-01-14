package com.terrygjt.androidtoservlet;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ServerActivity extends AppCompatActivity {
    TextView zhou;
    Button btn;
    TextView uname;
    TextView psw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        psw= (TextView) findViewById(R.id.psw);
        uname= (TextView) findViewById(R.id.username);
        btn= (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btn){
                    String username=uname.getText().toString();
                    String pass=  psw.getText().toString();
                    String urlStr="http://192.168.43.43:8080/电子商务/loginServlet?username="+username+"&password="+pass;
                    Log.d("lll","访问"+urlStr);
                    new Async().execute(urlStr);
//                        new getTask().execute(username,pass,"http://192.168.43.43:8080/电子商务/loginServlet");
                }
            }
        });
    }
    class getTask extends AsyncTask {  //网友一get访问        @Override
        protected Object doInBackground(Object[] params) {
            //依次获取用户名，密码与路径
            String name = params[0].toString();
            String pass = params[1].toString();
            String path = params[2].toString();
            try {
                //获取网络上get方式提交的整个路径
                URL url = new URL(path+"?uname="+name +"&upass="+pass);
                //打开网络连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //设置提交方式
                conn.setRequestMethod("GET");
                //设置网络超时时间
                conn.setConnectTimeout(5000);
                //获取结果码
                int code = conn.getResponseCode();
                if (code == 200) {
                    //用io流与web后台进行数据交互
                    InputStream is = conn.getInputStream();
                    //字节流转字符流
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    //读出每一行的数据
                    String s = br.readLine();
                    //返回读出的每一行的数据
                    return s;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }          @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //获取Android studio与web后台数据交互获得的值
            String s= (String) o;
            //吐司Android studio与web后台数据交互获得的值
            Toast.makeText(ServerActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
    class postTask  extends  AsyncTask {//网友一post访问          @Override
        protected Object doInBackground(Object[] params) {
            //依次获取用户名，密码与路径
            String name = params[0].toString();
            String pass = params[1].toString();
            String path = params[2].toString();
            try {
                //获取网络上get方式提交的整个路径
                URL url = new URL(path);
                //打开网络连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //设置提交方式
                conn.setRequestMethod("POST");
                //设置网络超时时间
                conn.setConnectTimeout(5000);
                //界面上所有的参数名加上他的值
                String s = "uname=" + name + "&upass" + pass;
                //获取请求头
                conn.setRequestProperty("Content-Length", s.length() + "");//键是固定的
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//键和值是固定的
                //设置允许对外输出数据
                conn.setDoOutput(true);
                //把界面上的所有数据写出去
                OutputStream os = conn.getOutputStream();
                os.write(s.getBytes());
                if (conn.getResponseCode() == 200) {
                    //用io流与web后台进行数据交互
                    InputStream is = conn.getInputStream();
                    //字节流转字符流
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    //读出每一行的数据
                    String str = br.readLine();
                    //返回读出的每一行的数据
                    return str;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //获取Android  studio与web后台数据交互获得的值
            String s = (String) o;
            //吐司Android  studio与web后台数据交互获得的值
            Toast.makeText(ServerActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
    class Async  extends AsyncTask<String, Object,String>//网友二
    {        @Override
    //后台子线程
    protected String doInBackground(String... params) {
        String result="";
        String path=params[0];
        try {
            URL url=new URL(path);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();

            //文件的读写
            InputStream is=conn.getInputStream();
            byte[] b=new byte[2048];
            int len =0;
            while ((len=is.read(b))!=-1)
            {
                result=new String (b);
                Thread.sleep(200);//进程休眠，便于观看效果
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
        //实时更新的   主线程
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }
        //前台主线程
        protected void onPostExecute(String result) {
            showDialog(result);
            super.onPostExecute(result);
        }
    }
    private void showDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg).setCancelable(false).setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
