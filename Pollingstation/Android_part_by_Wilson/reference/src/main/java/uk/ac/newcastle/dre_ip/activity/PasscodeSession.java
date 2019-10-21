package uk.ac.newcastle.dre_ip.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import uk.ac.newcastle.dre_ip.R;
import uk.ac.newcastle.dre_ip.connection.XmlAuthentication;

/**
 * The voter login screen, where voter could input one-time-use-only passcode.
 */
public class PasscodeSession extends AppCompatActivity {

    private TextView iddisplay;
    private EditText passcodeField;
    private Button enterBtn;
    private Activity activity;
    private Context context;
    private String passcode, password, electionID, target, target_server;

    /**
     * Create view for PasscodeSession.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        activity = this;
        context = this;
        electionID = getIntent().getExtras().getString("electionId");
        target = getIntent().getExtras().getString("target");
        target_server = getIntent().getExtras().getString("target_server");
        iddisplay = (TextView) findViewById(R.id.iddisplay);

        SharedPreferences sharedPreferences = getSharedPreferences("selectedconnection", Context.MODE_PRIVATE);
        iddisplay.setText(sharedPreferences.getString("title", ""));


        Log.e("ElectionID", electionID);

        passcodeField = (EditText) findViewById(R.id.passcode);

        enterBtn = (Button) findViewById(R.id.enter);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passcode = passcodeField.getText().toString();
                password = getIntent().getStringExtra("password");

                if (initialValidation(passcode)) {

                    Log.e("Target2", target);
                    new XmlAuthentication(activity, context, target, target_server).execute(electionID, password, passcode);
                    passcodeField.setText("");

                }
            }
        });

    }

    /**
     * To determine the passcode is not blank.
     * @param passcode the one-time-use-only passcode
     * @return false if nothing inputted
     */
    private boolean initialValidation(String passcode) {
        if (passcode.isEmpty()) {
            Toast.makeText(getBaseContext(), "Enter the Passcode", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * Make back button do noting when it is pressed. For preventing user from going back to previous screen which will lead inconsistency of the system.
     */
    @Override
    public void onBackPressed(){

    }
}