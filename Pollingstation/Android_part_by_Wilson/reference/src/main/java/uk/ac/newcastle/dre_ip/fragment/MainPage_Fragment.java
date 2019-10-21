package uk.ac.newcastle.dre_ip.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import uk.ac.newcastle.dre_ip.R;
import uk.ac.newcastle.dre_ip.connection.XmlAuthentication;

/**
 * The Mainpage fragment, where administrator could input the electionID and password.
 */
public class MainPage_Fragment extends Fragment {

    private Button enter, noprinterValue;
    private TextView serverAddress, printerAddress,warning;
    private EditText passwordField, electionIDField;
    private Context context;
    private Activity activity;
    private String target,target_server;


    /**
     * New instance main page fragment.
     *
     * @return the main page fragment
     */
    public static MainPage_Fragment newInstance() {
        MainPage_Fragment fragment = new MainPage_Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainpage, container, false);

        target = null;
        activity = getActivity();
        context = getContext();
        serverAddress = (TextView) view.findViewById(R.id.serverInfo);
        serverAddress.setText(R.string.serverInfo);
        printerAddress = (TextView) view.findViewById(R.id.printerInfo);
        printerAddress.setText(R.string.printerInfo);
        electionIDField = (EditText) view.findViewById(R.id.electionID);
        passwordField = (EditText) view.findViewById(R.id.password);
        enter = (Button) view.findViewById(R.id.enter);
        warning = (TextView)view.findViewById(R.id.warning);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("selectedconnection", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("serverAddress", "").equals(""))
        {serverAddress.setText(sharedPreferences.getString("serverAddress", ""));}
        if (!sharedPreferences.getString("printerAddress", "").equals(""))
        {printerAddress.setText(sharedPreferences.getString("printerAddress", ""));}

        target = printerAddress.getText().toString();
        target_server = serverAddress.getText().toString();

        if(!target.equals("<NONE>")){warning.setVisibility(View.GONE);}

        enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Get text from session and password text fields
                String electionIDText, passwordText;
                electionIDText = electionIDField.getText().toString();
                passwordText = passwordField.getText().toString();


                //Initial validation
                if (initialValidation(electionIDText, passwordText)) {

                    Log.e("target3", target);
                    new XmlAuthentication(activity, context, target,target_server).execute(electionIDText, passwordText);

                }
            }
        });

        return view;
    }


    /**
     * Determine whether the electionid/password is valid.
     * @param electionIDText ElectionID
     * @param passwordText Password for election
     * @return
     */
    private boolean initialValidation(String electionIDText, String passwordText) {
        if (electionIDText.isEmpty() && passwordText.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in the election id and password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (electionIDText.equals("")) {
            //Display on-screen message if session ID is not completed
            Toast.makeText(getActivity(), "Please enter Election ID", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordText.equals("")) {
            Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


}
