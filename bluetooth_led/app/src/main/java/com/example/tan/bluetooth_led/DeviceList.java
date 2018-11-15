package com.example.tan.bluetooth_led;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class DeviceList extends AppCompatActivity {

    ListView listView;
    Button btnGet;

    ArrayList<BluetoothDevice> devices;
    BluetoothAdapter bluetoothAdapter;

    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        btnGet=(Button)findViewById(R.id.btnGet);
        listView=(ListView) findViewById(R.id.listView);

        devices=new ArrayList<>();

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDevices();
            }
        });

    }

    private void getDevices(){
        bluetoothAdapter=bluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter!=null){
            Set<BluetoothDevice> deviceSet=bluetoothAdapter.getBondedDevices();
            arrayList=new ArrayList<>();
            if(deviceSet.size()>0){
                for (BluetoothDevice device:deviceSet){
                    devices.add(device);
                    arrayList.add(device.getName()+"\n"+device.getAddress());
                }

                arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,arrayList);

                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(DeviceList.this,MainActivity.class);

                        intent.putExtra("Address",devices.get(position).getAddress());
                        startActivity(intent);
                    }
                });
            }
        }
    }
}

