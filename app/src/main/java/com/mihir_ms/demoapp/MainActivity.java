package com.mihir_ms.demoapp;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private Button button;
        TextView textView;
        TextView textView1;
        boolean isRunnable=true;
        WifiManager wifiManager;
        WifiInfo connection;
        String display="******* Wifi Information *******";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button1);
        textView=(TextView) findViewById(R.id.text1);
        textView1=(TextView) findViewById(R.id.text2);
        final Thread t=new Thread(){


            @Override
            public void run(){
                while(isRunnable){
                    try {
                        Thread.sleep(1000);  //1000ms = 1 sec so 60000 = 1min
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                                connection = wifiManager.getConnectionInfo();
                                if(connection.getRssi()<-100){
                                    textView.setText("Wifi is not connected");
                                }
                                else {
                                    String tempdisplay = "\nSSID: " + connection.getSSID() + "\n" + "RSSI: " + connection.getRssi() + "\n" + "MAC Address: " + connection.getMacAddress()+"\n\n";
                                    display+=tempdisplay;
                                    textView.setText("******* Wifi Information *******"+tempdisplay);
                                    File textFile= new File(Environment.getExternalStorageDirectory(),"wifi.txt");
                                    try {
                                        FileOutputStream fos = new FileOutputStream(textFile);
                                        fos.write(display.getBytes());
                                        Toast.makeText(MainActivity.this, "Added to file "+getFilesDir()+"/wifi.txt", Toast.LENGTH_SHORT).show();
                                        fos.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        Thread.sleep(4000);  //1000ms = 1 sec so 60000 = 1min

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        };
        t.start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    isRunnable=false;
                    textView1.setText("Stopped Updation");
                    Toast.makeText(MainActivity.this, "Updating Stopped", Toast.LENGTH_SHORT).show();
//                wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                connection = wifiManager.getConnectionInfo();
//                if(connection.getRssi()<-100){
//                    textView.setText("Wifi is not connected");
//                }
//                else {
//                    display += "\nSSID: " + connection.getSSID() + "\n" + "RSSI: " + connection.getRssi() + "\n" + "MAC Address: " + connection.getMacAddress()+"\n\n";
//                    textView.setText(display);
//                    File textFile= new File(Environment.getExternalStorageDirectory(),"wifi.txt");
//                    try {
//                        FileOutputStream fos = new FileOutputStream(textFile);
//                        fos.write(display.getBytes());
//                        Toast.makeText(MainActivity.this, "Added to file "+getFilesDir()+"/wifi.txt", Toast.LENGTH_SHORT).show();
//                        fos.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    }
//                }
            }
        });


    }

}
