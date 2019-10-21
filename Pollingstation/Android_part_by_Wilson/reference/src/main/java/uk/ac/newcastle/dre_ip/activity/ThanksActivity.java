package uk.ac.newcastle.dre_ip.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import uk.ac.newcastle.dre_ip.R;


/**
 * Show Thanks screen. It will pass all parameters to the new PasscodeSession for the next voter using.
 */
public class ThanksActivity extends Activity {

    private Activity activity;
    private Context context;
    private String electionID, target, target_server;

    /**
     * Create view for Thanks screen..
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanks);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        electionID = getIntent().getExtras().getString("electionId");
        target = getIntent().getExtras().getString("target");
        target_server = getIntent().getExtras().getString("target_server");

        Button clean = (Button) findViewById(R.id.clean);

        clean.setOnClickListener(new View.OnClickListener() {

                                     @Override
                                     public void onClick(View view) {

                                         Intent intent = new Intent(getApplicationContext(), PasscodeSession.class);
                                         intent.putExtra("electionId", electionID);
                                         intent.putExtra("target_server", target_server);
                                         intent.putExtra("target", target);
                                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                         view.getContext().startActivity(intent);
                                     }
                                 }
        );

    }

    /**
     * Make back button do noting when it is pressed. For preventing user from going back to previous screen which will lead inconsistency of the system.
     */
    @Override
    public void onBackPressed(){

    }
}
