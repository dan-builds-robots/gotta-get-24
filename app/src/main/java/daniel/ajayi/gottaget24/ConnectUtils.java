package daniel.ajayi.gottaget24;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.math.BigInteger;
import java.util.UUID;

import daniel.BluetoothConnect;

import static android.bluetooth.BluetoothProfile.A2DP;
import static android.content.ContentValues.TAG;

public class ConnectUtils {

    private static final int REQUEST_READ_PHONE_STATE = 30;

    private Context context;

    private Handler handler;

    public static final int STATE_NONE = 0;

    public static final int STATE_LISTEN = 1;

    public static final int STATE_CONNECTING = 2;

    public static final int STATE_CONNECTED = 3;

    public static final int STATE_FAILED = 4;

    TelephonyManager tManager;

    String uuid;

    private UUID APP_UUID;

    private final String APP_NAME = "Gotta Get 24";

    private BluetoothAdapter bluetoothAdapter;

    private ConnectThread connectThread;

    private AcceptThread acceptThread;

    private int state;

    public ConnectUtils(Context context, Handler handler) {

        this.context = context;

        this.handler = handler;

        tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);

        } else {
//
//            APP_UUID = UUID.fromString("88b00fcd-9789-4ba2-9bde-9ec390218d6c");
//
//            APP_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
//
//            APP_UUID = UUID.fromString("f954aa85-ac77-4211-9158-b478ed3a1743");

            APP_UUID = UUID.fromString("95b5e394-ef82-4bc1-b652-15324c0ff99e");

        }
        
        state = STATE_NONE;

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    //@Override

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {

            case REQUEST_READ_PHONE_STATE:

                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    uuid = tManager.getDeviceId();

                    APP_UUID = UUID.fromString(uuid);

                    APP_UUID = UUID.fromString("95b5e394-ef82-4bc1-b652-15324c0ff99e");

                }

                break;

            default:

                break;
        }
    }

    public int getState() {

        return state;

    }

    public synchronized void setState(int state) {

        this.state = state;

        handler.obtainMessage();

    }

    public synchronized void start() {

        if (connectThread != null) {

            connectThread.cancel();

            connectThread = null;

        }

        if (acceptThread == null) {

            acceptThread = new AcceptThread();

        }

        setState(STATE_LISTEN);

    }

    public synchronized void stop() {

        if (connectThread != null) {

            connectThread.cancel();

            connectThread = null;

        }

        if (acceptThread != null) {

            acceptThread.cancel();

            acceptThread = null;

        }

        state = STATE_NONE;

    }

    public void connect (BluetoothDevice device) {

        Log.i("asdfasdfasdf","asdfasdfasdf");

        if (state == STATE_CONNECTING && connectThread != null) {

            connectThread.cancel();

            connectThread = null;

        }
//
//        setState(STATE_CONNECTING);
//
//        handler.obtainMessage(STATE_CONNECTING).sendToTarget();

        connectThread = new ConnectThread(device);

        connectThread.start();

    }

    private class AcceptThread extends Thread {

        private BluetoothServerSocket serverSocket;

        public AcceptThread() {

            BluetoothServerSocket tmp = null;

            try {

                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME,APP_UUID);


            } catch (IOException e) {

                Log.e(TAG, "Socket's listen() method failed", e);

            }

            serverSocket = tmp;

        }

        public void run() {

            BluetoothSocket socket = null;

            try {

                socket = serverSocket.accept();

            } catch (IOException e) {

                Log.e(TAG, "Socket's accept() method failed", e);

                try {

                    serverSocket.close();

                } catch (IOException e1) {

                    Log.e(TAG, "Could not close the connect socket", e1);

                }

            }

            if (socket != null) {

                BluetoothConnect.numConnectedPlayers++;

                Log.i("numConnectedPlayers",BluetoothConnect.numConnectedPlayers+"");

                switch (state) {

                    case STATE_LISTEN:

                    case STATE_CONNECTING:

                        connect(socket.getRemoteDevice());

                        break;

                    case STATE_NONE:



                    case STATE_CONNECTED:

                        try {

                            socket.close();

                        } catch (IOException e) {

                            // log sumn here

                        }

                        break;

                }

            }

        }

        public void cancel() {

            try {

                serverSocket.close();

            } catch (IOException e) {

                //log

            }

        }

    }


    private class ConnectThread extends Thread {

        private BluetoothSocket socket;

        private final BluetoothDevice device;

        public ConnectThread(BluetoothDevice device) {

            this.device = device;

            BluetoothSocket tmp = null;

            try{

                tmp = device.createRfcommSocketToServiceRecord(APP_UUID);

                Log.i("Attempting"," to connect asdf");

            } catch(IOException e) {

                Log.i("The connection","failed asdf");

            }

            socket = tmp;

        }

        public void run() {

            bluetoothAdapter.cancelDiscovery();

            try {

                socket.connect();

                Log.i("Socket","trying to connect");

            } catch (IOException e) {

                Log.i("Socket","connection failed");

                Log.e(TAG,"This is what's happening",e);

                try {

                    Log.d(TAG,"2nd Attempt: trying fallback...");

                    socket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);

                    socket.connect();

                    Log.d(TAG,"2nd Attempt: Connected");

                    BluetoothConnect.numConnectedPlayers++;

                    Log.i("numConnectedPlayers",BluetoothConnect.numConnectedPlayers+"");

                    if (BluetoothConnect.numConnectedPlayers == MainActivity.numPlayers - 1) {

                        Intent intent = new Intent(context, QuestionGeneration.class);

                        context.startActivity(intent);

                    }

                } catch (Exception e1) {

                    if (bluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP) == STATE_CONNECTED) {

                        Log.d(TAG,"2nd Attempt: Connected");

                        BluetoothConnect.numConnectedPlayers++;

                        Log.i("numConnectedPlayers",BluetoothConnect.numConnectedPlayers+"");

                        if (BluetoothConnect.numConnectedPlayers == MainActivity.numPlayers - 1) {

                            Intent intent = new Intent(context, QuestionGeneration.class);

                            context.startActivity(intent);

                        }

                    } else {

                        Log.d(TAG, "2nd Attempt: Couldn't establish Bluetooth connection!");

                        Log.e("asdf","asdf",e1);

                        try {

                            Log.i("Socket","trying to close");

                            socket.close();

                        } catch (IOException e2) {

                            Log.i("Socket","closing failed");

                        }

                        connectionFailed();

                        return;

                    }

                }

            }

            if (socket != null) {

                connected(device);

                Log.i("Device connected","I made this");

            } else {

                Log.i("Device not connected","I made this");

            }

            synchronized(ConnectUtils.this) {

                connectThread = null;

                Log.i("This is apparently","getting run");

            }

            connect(device);

        }

        public void cancel() {

            try {

                socket.close();

            } catch (IOException e) {

                //log this crap

            }

        }

    }



    private synchronized void connectionFailed() {

        Log.i("This is runing","running");

        setState(STATE_FAILED);

        handler.obtainMessage(STATE_FAILED).sendToTarget();

        ConnectUtils.this.start();

        //Message message = handler.obtainMessage(MainActivity.

        //basically pass something to the main activity to let it know that the connection failed

    }

    private synchronized void connected (BluetoothDevice device) {

        if (connectThread != null) {

            connectThread.cancel();

            connectThread = null;

        }

        setState(STATE_CONNECTED);

        handler.obtainMessage(STATE_CONNECTED).sendToTarget();

    }

}
