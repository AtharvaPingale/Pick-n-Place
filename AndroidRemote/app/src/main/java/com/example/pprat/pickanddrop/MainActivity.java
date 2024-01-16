package com.example.pprat.pickanddrop;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import static com.example.pprat.pickanddrop.R.drawable.btn_gradient_right;

public class MainActivity extends AppCompatActivity {
    private final String DEVICE_ADDRESS = "00:18:E4:00:34:35"; //MAC Address of Bluetooth Module
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;

    private static final String TAG = "MainActivity";
    
    Button openJawBtn, closeJawBtn, liftUpBtn, liftDownBtn;
    Button forward_btn, left_btn, right_btn, reverse_btn,bluetooth_connect_btn;
    String command; //string variable that will store value to be transmitted to the bluetooth module

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declaration of button variables
        forward_btn =  findViewById(R.id.forward_btn);
        left_btn =  findViewById(R.id.left_btn);
        right_btn =  findViewById(R.id.right_btn);
        reverse_btn =  findViewById(R.id.reverse_btn);
        bluetooth_connect_btn =  findViewById(R.id.bluetooth_connect_btn);
        openJawBtn = findViewById(R.id.btnOpenJaw);
        closeJawBtn = findViewById(R.id.btnCloseJaw);
        liftUpBtn = findViewById(R.id.btnLiftUp);
        liftDownBtn = findViewById(R.id.btnLiftDown);


        //OnTouchListener code for the forward button (button long press)
        forward_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        forward_btn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_gradient_up));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        forward_btn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_gradient_up));
                    }
                    command = "1";
                    Log.d(TAG, "onTouch: Forward");
                    try
                    {

                        outputStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                else if(event.getAction() == MotionEvent.ACTION_UP) //MotionEvent.ACTION_UP is when you release a button
                {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        forward_btn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_default));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        forward_btn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_default));
                    }
                    command = "10";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }

                return false;
            }

        });

        //OnTouchListener code for the reverse button (button long press)
        reverse_btn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        reverse_btn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_gradient_down));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        reverse_btn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_gradient_down));
                    }
                    command = "2";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        reverse_btn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_default));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        reverse_btn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_default));
                    }
                    command = "10";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(Exception e)
                    {

                    }


                }
                return false;
            }
        });

        //OnTouchListener code for the forward left button (button long press)
        right_btn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        right_btn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_gradient_right));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        right_btn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_gradient_right));
                    }
                    command = "3";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        right_btn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_default));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        right_btn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_default));
                    }
                    command = "10";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }


                }
                return false;
            }
        });

        //OnTouchListener code for the forward right button (button long press)
        left_btn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        left_btn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_gradient_left));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        left_btn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_gradient_left));
                    }
                    command = "4";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        left_btn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_default));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        left_btn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_default));
                    }
                    command = "10";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });

        openJawBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    command = "5";
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        openJawBtn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth_connected));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        openJawBtn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth_connected));
                    }

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        openJawBtn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        openJawBtn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth));
                    }
                    command = "10";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });

        closeJawBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    command = "6";
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        closeJawBtn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth_connected));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        closeJawBtn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth_connected));
                    }
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        closeJawBtn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        closeJawBtn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth));
                    }
                    command = "10";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });

        liftUpBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    command = "7";
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        liftUpBtn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth_connected));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        liftUpBtn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth_connected));
                    }
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    command = "10";
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        liftUpBtn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        liftUpBtn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth));
                    }
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });

        liftDownBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    command = "8";
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        liftDownBtn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth_connected));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        liftDownBtn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth_connected));
                    }
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    command = "10";
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                        liftDownBtn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth));
                    }
                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        liftDownBtn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth));
                    }
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }

                return false;
            }
        });

        //Button that connects the device to the bluetooth module when pressed
        bluetooth_connect_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(BTinit())
                {
                    BTconnect();
                }

            }
        });

    }

    //Initializes bluetooth module
    public boolean BTinit()
    {
        boolean found = false;

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null) //Checks if the device supports bluetooth
        {
            Toast.makeText(getApplicationContext(), "Device doesn't support bluetooth", Toast.LENGTH_SHORT).show();
        }

        if(!bluetoothAdapter.isEnabled()) //Checks if bluetooth is enabled. If not, the program will ask permission from the user to enable it
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter,0);

            try
            {
                Thread.sleep(1000);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if(bondedDevices.isEmpty()) //Checks for paired bluetooth devices
        {
            Toast.makeText(getApplicationContext(), "Please pair the device first", Toast.LENGTH_SHORT).show();
        }
        else
        {
            for(BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    device = iterator;
                    found = true;
                    Log.d(TAG, "BTinit: Connected");
                    break;
                }
            }
        }
        Log.d(TAG, "BTinit: Found " + found);
        return found;
    }

    public boolean BTconnect()
    {
        boolean connected = true;
        Log.d(TAG, "BTconnect: Connecting....");
        try
        {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID); //Creates a socket to handle the outgoing connection
            socket.connect();

            if(socket.isConnected()){
                Toast.makeText(getApplicationContext(),
                        "Connection to bluetooth device successful", Toast.LENGTH_LONG).show();

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                    bluetooth_connect_btn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth_connected));
                }
                else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bluetooth_connect_btn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth_connected));
                }
            }
            else{
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                    bluetooth_connect_btn.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth));
                }
                else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bluetooth_connect_btn.setBackground(ContextCompat.getDrawable(MainActivity.this,R.drawable.btn_bluetooth));
                }
            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
            connected = false;
        }

        if(connected)
        {
            try
            {
                outputStream = socket.getOutputStream(); //gets the output stream of the socket
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        return connected;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }





}
