package daniel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.net.ServerSocket;
import java.util.ArrayList;

import daniel.ajayi.gottaget24.ConnectUtils;
import daniel.ajayi.gottaget24.MainActivity;
import daniel.ajayi.gottaget24.R;

public class BluetoothConnect extends AppCompatActivity {

    ListView listView;

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    //private final BluetoothServerSocket mmServerSocket;

    ImageView user1LoadingSign;

    boolean deviceHasBluetooth = true;

    ImageView user2LoadingSign;

    ImageView user3LoadingSign;

    ArrayList<ImageView> loadingSigns;

    ArrayList<ImageView> presentBox;

    ArrayList<Integer> selectedDevices;

    public static int posOfSelectedDevice = 0;

    IntentFilter intentFilter;

    public static ArrayList<BluetoothDevice> detectedDevices;

    View lastTouchedDevice;

    ImageView p1Status;

    ImageView p2Status;

    ImageView p3Status;

    ImageView p1Present;

    ImageView p2Present;

    ImageView p3Present;

    TextView u1QM;

    TextView u2QM;

    TextView u3QM;

    TextView slot1Name;

    TextView slot2Name;

    TextView slot3Name;

    ArrayList<TextView> questionMarks;

    TextView usernameTxt;

    TextView searchingForDevices;

    Typeface mTypeface;

    LocationManager locationManager;

    ArrayList<View> views;

    public static int numConnectedPlayers;

    ArrayList<String> deviceTags;

    public static final int STATE_NONE = 0;

    public static final int STATE_LISTEN = 1;

    public static final int STATE_CONNECTING = 2;

    public static final int STATE_CONNECTED = 3;

    public static final int STATE_FAILED = 4;

    private final int LOCATION_PERMISSION_REQUEST = 101;

    private final int CONNECT_DEVICE = 102;

    private final int REQUEST_ENABLE_BT = 99; // Any positive integer should work.

    ArrayList<String> bluetoothDevices = new ArrayList<>();

    ArrayAdapter arrayAdapter;

    BluetoothAdapter bluetoothAdapter;

    LocationRequest locationRequest = LocationRequest.create();

    ConnectUtils connectUtils;

    ArrayList<Boolean> statusOfPlayers;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override

        public boolean handleMessage(@NonNull Message message) {

            switch (message.what) {

                case ConnectUtils.STATE_CONNECTED:

                    Log.i("numConnectedPlayers",BluetoothConnect.numConnectedPlayers+"");

                    loadingSigns.get(numConnectedPlayers).clearAnimation();

                    loadingSigns.get(numConnectedPlayers).setVisibility(View.GONE);

                    presentBox.get(numConnectedPlayers).setVisibility(View.VISIBLE);

                    break;

                case ConnectUtils.STATE_CONNECTING:

                    Toast.makeText(BluetoothConnect.this, "Connecting...", Toast.LENGTH_SHORT).show();


                case ConnectUtils.STATE_FAILED:

                    loadingSigns.get(numConnectedPlayers).clearAnimation();

                    loadingSigns.get(numConnectedPlayers).setVisibility(View.INVISIBLE);

                    questionMarks.get(numConnectedPlayers).setVisibility(View.VISIBLE);

                    Toast.makeText(BluetoothConnect.this, "Connection Failed", Toast.LENGTH_SHORT).show();

                    break;

            }

            return false;

        }

    });

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {


        @Override

        public void onReceive(Context context, Intent intent) {

            registerReceiver(broadcastReceiver,intentFilter);

            String action = intent.getAction();

            //Log.i("Action",action);

            if(bluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

                bluetoothAdapter.startDiscovery();

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                searchingForDevices.setVisibility(View.INVISIBLE);

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                String rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE) + "";

                String name = device.getName();

                String address = device.getAddress();

                //Log.i("Device found","Name: " + name + "Address: " + address + "RSSI: " + rssi);

                if (name == null) {

                    if (!bluetoothDevices.contains(address)) {

                        bluetoothDevices.add(address);

                        detectedDevices.add(device);

                        deviceTags.add(address);

                    }

                } else if (name.equals("")) {

                    if (!bluetoothDevices.contains(address+"")) {

                        bluetoothDevices.add(address + "");

                        detectedDevices.add(device);

                        deviceTags.add(address);

                    }

                } else {

                    if (!bluetoothDevices.contains(name+"")) {

                        bluetoothDevices.add(name + "");

                        detectedDevices.add(device);

                        deviceTags.add(address);

                    }

                }

                arrayAdapter.notifyDataSetChanged();

                if (bluetoothDevices.isEmpty()) {

                    searchingForDevices.setVisibility(View.VISIBLE);

                } else {

                    searchingForDevices.setVisibility(View.INVISIBLE);

                }


            }

            //Check if no more devices are showing

            else if (action.equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
                    && BluetoothAdapter.EXTRA_CONNECTION_STATE.equals("STATE_CONNECTED")
                    && BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE.equals("STATE_DISCONNECTED")) {

                Log.i("numConnectedPlayers",BluetoothConnect.numConnectedPlayers+"");

                loadingSigns.get(numConnectedPlayers).clearAnimation();

                loadingSigns.get(numConnectedPlayers).setVisibility(View.GONE);

                presentBox.get(numConnectedPlayers).setVisibility(View.VISIBLE);

                numConnectedPlayers++;

            }

        }

    };

//    public BluetoothConnect(BluetoothServerSocket mmServerSocket) {
//
//        this.mmServerSocket = mmServerSocket;
//
//    }
//
//    public BluetoothConnect() {
//
//        mmServerSocket = new BluetoothServerSocket();
//
//    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bluetooth_connect);

        connectUtils = new ConnectUtils(this,handler);

        deviceTags = new ArrayList<>();

        numConnectedPlayers = 0;

        detectedDevices = new ArrayList<>();

        p1Status = findViewById(R.id.user1Box);

        p2Status = findViewById(R.id.user2Box);

        p3Status = findViewById(R.id.user3Box);

        u1QM = findViewById(R.id.user1Unknown);

        u2QM = findViewById(R.id.user2Unknown);

        u3QM = findViewById(R.id.user3Unknown);

        slot1Name = findViewById(R.id.slot1Name);

        slot2Name = findViewById(R.id.slot2Name);

        slot3Name = findViewById(R.id.slot3Name);

        questionMarks = new ArrayList<>();

        questionMarks.add(u1QM);

        user1LoadingSign = findViewById(R.id.user1Loading);

        user2LoadingSign = findViewById(R.id.user2Loading);

        user3LoadingSign = findViewById(R.id.user3Loading);

        p1Present = findViewById(R.id.user1Present);

        p2Present = findViewById(R.id.user2Present);

        p3Present = findViewById(R.id.user3Present);

        presentBox = new ArrayList<>();

        presentBox.add(p1Present);

        statusOfPlayers = new ArrayList<>();

        loadingSigns = new ArrayList<>();

        loadingSigns.add(user1LoadingSign);

        if (MainActivity.numPlayers == 2) {

            slot2Name.setVisibility(View.GONE);

            slot3Name.setVisibility(View.GONE);

            u2QM.setVisibility(View.GONE);

            u3QM.setVisibility(View.GONE);

            user2LoadingSign.setVisibility(View.GONE);

            user3LoadingSign.setVisibility(View.GONE);

            p2Status.setVisibility(View.GONE);

            p3Status.setVisibility(View.GONE);

        } else if (MainActivity.numPlayers == 3) {

            slot3Name.setVisibility(View.GONE);

            u3QM.setVisibility(View.GONE);

            p3Status.setVisibility(View.GONE);

            loadingSigns.add(user2LoadingSign);

            presentBox.add(p2Present);

            questionMarks.add(u2QM);

        } else if (MainActivity.numPlayers == 4) {

            loadingSigns.add(user3LoadingSign);

            presentBox.add(p3Present);

            questionMarks.add(u3QM);

        }

        selectedDevices = new ArrayList<>();

        searchingForDevices = findViewById(R.id.searchingForDevice);

        usernameTxt = findViewById(R.id.username);

        usernameTxt.setText("You are: " + MainActivity.username);

        listView = findViewById(R.id.listView);

        views = new ArrayList<>();
//
//        mTypeface = Typeface.createFromAsset(getAssets(),"font/gill_sans_ultra_bold_condensed.ttf");

        mTypeface = ResourcesCompat.getFont(this, R.font.gill_sans_ultra_bold_condensed);

        //ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.textcenter, R.id.textItem, functions);

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, bluetoothDevices) {

            @Override

            public View getView(int position, View convertView, ViewGroup parent){

                // Cast the list view each item as text view

                TextView item = (TextView) super.getView(position,convertView,parent);

                item.setTag(deviceTags.get(position));

                // Set the typeface/font for the current item

                item.setTypeface(mTypeface);

                // Set the list view item's text color

                item.setTextColor(Color.parseColor("#FFFFFF"));
//
//                // Set the item text style to bold

//                item.setTypeface(item.getTypeface(), Typeface.BOLD);

                // Change the item text size

                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);

                // return the view

                return item;
            }
        };


        listView.setAdapter(arrayAdapter);

        initBluetooth();

        intentFilter = new IntentFilter();

        intentFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);

        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);

        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);

        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(broadcastReceiver,intentFilter);

        checkPermissions();

        CheckGpsStatus();

        if (bluetoothAdapter != null) {

            bluetoothAdapter.startDiscovery();

        } else {

            Toast.makeText(this, "We apologize, bluetooth is not supported on this device", Toast.LENGTH_SHORT).show();

            Log.i("Device does not have","bluetooth");

            deviceHasBluetooth = false;

            Log.i("deviceHasBluetooth","" + deviceHasBluetooth);

            goHome(null);

        }

        if (bluetoothDevices.isEmpty()) {

            searchingForDevices.setVisibility(View.VISIBLE);

        } else {

            searchingForDevices.setVisibility(View.INVISIBLE);

        }

        checkForClick();



    }

    public void initBluetooth() {

        //Check to see if device supports bluetooth or if bluetooth is even on

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {

            // Device doesn't support Bluetooth

            Toast.makeText(this, "We apologize, bluetooth is not supported on this device", Toast.LENGTH_SHORT).show();

            Log.i("asfd","asfd");

            deviceHasBluetooth = false;

            goHome(null);

            return;

        } else if (!bluetoothAdapter.isEnabled()) {

            bluetoothAdapter.enable();

            Log.i("fdsa","fdsa");

        }

        if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {

            Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

            discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);

            startActivity(discoveryIntent);

        }

    }

    public void goHome(View view) {

        Log.i("deviceHasBluetooth","" + deviceHasBluetooth);

        if (!deviceHasBluetooth) {

            Intent intent = new Intent(this,  MainActivity.class);

            startActivity(intent);

            return;

        } else {

            unregisterReceiver(broadcastReceiver);

            if (bluetoothAdapter != null) {

                bluetoothAdapter.cancelDiscovery();

                bluetoothAdapter.disable();

            }

            if (connectUtils != null) {

                connectUtils.stop();

            }

        }

        Intent intent = new Intent(this,  MainActivity.class);

        startActivity(intent);

    }


    @Override

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {

            if (resultCode == 0) {

                // If the resultCode is 0, the user selected “No” when prompt to allow the app to enable bluetooth.

                Toast.makeText(this, "Please turn on bluetooth to find devices", Toast.LENGTH_SHORT).show();

            } else {

                Log.i("Result", "User enabled bluetooth access");

            }

        }

    }

    public void resizeDevices (View view) {

        //when one device is clicked on, others should shrink and that shoud expand

    }

    public void checkPermissions () {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(BluetoothConnect.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);

        }

//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

    }

    public void checkForClick() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                posOfSelectedDevice = i;

                lastTouchedDevice = view;

                views.add(view);

                for(View v : views) {

                    v.setBackgroundColor(Color.TRANSPARENT);

                }

                view.setBackgroundColor(getResources().getColor(R.color.lightblue));

            }

        });

    }

    public void CheckGpsStatus() {

        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        assert locationManager != null;

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            Log.i("GPS","Is Enabled");

        } else {

            Log.i("GPS","Is Not Enabled");

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            SettingsClient client = LocationServices.getSettingsClient(this);

            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

            task.addOnFailureListener(this, new OnFailureListener() {

                @Override

                public void onFailure(@NonNull Exception e) {

                    if (e instanceof ResolvableApiException) {

                        // Location settings are not satisfied, but this can be fixed

                        // by showing the user a dialog.

                        try {

                            // Show the dialog by calling startResolutionForResult(),

                            // and check the result in onActivityResult().

                            ResolvableApiException resolvable = (ResolvableApiException) e;

                            resolvable.startResolutionForResult(BluetoothConnect.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException sendEx) {

                            // Ignore the error.

                        }

                    }

                }

            });

        }

    }

    public void connect(View view) {

        if (lastTouchedDevice == null) {

            return;

        }

        String address = lastTouchedDevice.getTag().toString();

        Toast.makeText(this, "Address:" + address, Toast.LENGTH_SHORT).show();

        questionMarks.get(numConnectedPlayers).setVisibility(View.INVISIBLE);

        loadingSigns.get(numConnectedPlayers).setVisibility(View.VISIBLE);
//
        ///set the laoding sign to rotate until the connection is secured

        //loadingSigns.get(numConnectedPlayers).animate().rotation(3f).setListener();

        RotateAnimation rotate = new RotateAnimation (

                0, 360,

                Animation.RELATIVE_TO_SELF, 0.5f,

                Animation.RELATIVE_TO_SELF, 0.5f

        );

        rotate.setDuration(1100);

        rotate.setRepeatCount(Animation.INFINITE);

        if (loadingSigns.get(numConnectedPlayers).getAnimation() == null) {

            loadingSigns.get(numConnectedPlayers).startAnimation(rotate);

            Log.i("number of loading signs","" + loadingSigns.size());

        }

//        if (/*device is already paired*/) {
//
//            //connected
//
//        } else {
//
//            connectUtils.connect(detectedDevices.get(posOfSelectedDevice));
//
//        }


        if (numConnectedPlayers == MainActivity.numPlayers) {

            connectUtils.stop();

        }




    }



}