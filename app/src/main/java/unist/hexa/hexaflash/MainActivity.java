package unist.hexa.hexaflash;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Camera cam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(this, FlashActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        Switch switch_flash = (Switch) findViewById(R.id.switch_flash);
        switch_flash.setOnCheckedChangeListener(new MyCheckedChangeListener());
    }

    private void flashOn() {
        Log.d(TAG, "flashOn");
        if (cam == null) {
            cam = Camera.open();
        }
        Camera.Parameters p = cam.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(p);
    }

    private void flashOff() {
        Log.d(TAG, "flashOff");
        if (cam == null)
            return;
        cam.stopPreview();
        cam.release();
        cam = null;
    }

    /**
     * switch listener
     */
    class MyCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                flashOn();
            } else {
                flashOff();
            }
        }
    }
}
