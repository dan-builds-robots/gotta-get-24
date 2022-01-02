package daniel.ajayi.gottaget24;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import daniel.ajayi.gottaget24.MainActivity;
import daniel.ajayi.gottaget24.R;

public class SplashScreen extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

    }

}
