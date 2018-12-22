package com.example.tan.bluetooth_led;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button btnOn;
    Button btnOff;
    ProgressBar progressBar;
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;

    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOn=(Button) findViewById(R.id.btnOn);
        btnOff=(Button) findViewById(R.id.btnOff);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        Intent intent=getIntent();
        address=intent.getStringExtra("Address");

        new BluetoothConnect().execute();

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetoothSocket!=null){
                    try {
                        bluetoothSocket.getOutputStream().write('1');//gửi dữ liệu đi
                    }catch(IOException exception){

                    }

                }
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetoothSocket!=null){
                    try {
                        bluetoothSocket.getOutputStream().write('0');//gửi dữ liệu đi
                    }catch(IOException exception){

                    }

                }
            }
        });

    }

    private class BluetoothConnect extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {
            bluetoothAdapter=bluetoothAdapter.getDefaultAdapter();

            BluetoothDevice device=bluetoothAdapter.getRemoteDevice(address);

            UUID myUUID=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID);
                bluetoothAdapter.cancelDiscovery(); //Dừng tìm kiếm
                bluetoothSocket.connect();

            }catch(IOException exception){

            }

            return null;
        }
    }
}
