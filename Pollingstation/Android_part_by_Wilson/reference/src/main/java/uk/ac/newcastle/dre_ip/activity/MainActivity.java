package uk.ac.newcastle.dre_ip.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import uk.ac.newcastle.dre_ip.R;
import uk.ac.newcastle.dre_ip.fragment.MainPage_Fragment;
import uk.ac.newcastle.dre_ip.fragment.Printer_Fragment;
import uk.ac.newcastle.dre_ip.fragment.Server_Fragment;

import static uk.ac.newcastle.dre_ip.R.id.navigation_home;

/**
 * The administration and login screen of the App, which is using for administrators of the election ONLY, that should not be seen by the end users.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Create view for MainActivity. It will be refreshed every time when different sub-fragment on-top-bar is selected.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        checkBTPermissions();



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(navigation_home);

        navigation.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_server:
                                selectedFragment = Server_Fragment.newInstance();
                                break;
                            case R.id.navigation_home:
                                selectedFragment = MainPage_Fragment.newInstance();
                                break;
                            case R.id.navigation_printer:
                                selectedFragment = Printer_Fragment.newInstance();
                                break;
                        }

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, MainPage_Fragment.newInstance());
        transaction.commit();


    }

    /**
     * Check bluetooth permissions. It will pop-up the window the first time App runs, to obtain permission for bluetooth printer searching.
     */
    public void checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        } else {
            Log.d("error", "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    /**
     * Make back button do noting when it is pressed. For preventing user from going back to previous screen which will lead inconsistency of the system.
     */
    @Override
    public void onBackPressed(){

    }

}
