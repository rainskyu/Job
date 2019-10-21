package uk.ac.newcastle.dre_ip.connection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import uk.ac.newcastle.dre_ip.R;
import uk.ac.newcastle.dre_ip.activity.PasscodeSession;
import uk.ac.newcastle.dre_ip.extras.CdataConverter;
import uk.ac.newcastle.dre_ip.parsers.XmlParser;
import uk.ac.newcastle.dre_ip.parsers.XmlTrimmer;


/**
 * XML Join server connection
 *
 * @author Carlton Shepherd
 */
public class XmlAuthentication extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;
    private Activity activity;
    private Context context;
    private Bundle bundle;
    private String password, electionId, xml, passcode, target, target_server, iddisplay;
    private Boolean noErrors, authenticationPhase;
    private XmlParser parser;

    /**
     * Constructor
     *
     * @param activity      the activity
     * @param context       the context
     * @param target        the target
     * @param target_server the target server
     */
    public XmlAuthentication(Activity activity, Context context, String target, String target_server) {
        super();
        this.activity = activity;
        this.context = context;
        this.target = target;
        this.target_server = target_server;
        authenticationPhase = true;

    }

    /**
     * Display progress dialog prior to connection taking place
     */
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Logging in, please wait...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... details) {
        electionId = details[0];
        password = details[1];
        if (details.length == 2) {

            Log.e("id", electionId + "" + password);
            xml =
                    "<dreipvoting>\n" +
                            "<type>join</type>\n" +
                            "<electionid>" + electionId + "</electionid>\n" +
                            //Convert to CDATA
                            "<password>" + CdataConverter.getCdata(password) + "</password>\n" +
                            "</dreipvoting>";

        } else if (details.length == 3) {
            passcode = details[2];
            authenticationPhase = false;
            Log.e("Passcode", passcode);
            xml = "<dreipvoting>\n" +
                    "<type>validate</type>\n" +
                    "<electionid>" + electionId + "</electionid>\n" +
                    "<passcode>" + passcode + "</passcode>\n" +
                    "</dreipvoting>";

        }
        Log.e("xmlI", xml);
        try {
            //Connect to the server using the above XML
            Log.e("gethere", "here");
            String returnedString = new ServerConnection(target_server, xml).retrieveXml();
            Log.e("xml", returnedString);
            //Return the retrieved XML from the server
            return returnedString;
        } catch (Exception e) {
            Log.e("EvotingException", e.toString());
        }

        //If "" is returned, an error has occurred
        return "";
    }

    protected void onPostExecute(String xmlResult) {
        //Dismiss dialog
        if (dialog != null) dialog.dismiss();

        if (xmlResult.equals("")) {
            //Display an appropriate error message
            Toast.makeText(context, R.string.connection_error, Toast.LENGTH_LONG).show();
        } else {
            Log.e("xmlR", xmlResult);

            try {
                //Parse returned XML
                parser = new XmlParser(XmlTrimmer.trimXml(xmlResult));
                Log.e("trim", xml);
                //Find any errors that are within the parsed XML
                int i = parser.findError();
                noErrors = false;
                String error = "";

                switch (i) {
                    //Case 0: no errors
                    case 0:
                        noErrors = true;
                        break;

                    //Case 1: invalid election ID
                    case 1:
                        error = "Invalid Election ID!";
                        break;

                    //Case 2: invalid password
                    case 2:
                        error = "Invalid Password!";
                        break;
                    //Case 3: invalid passcode
                    case 3:
                        error = "Invalid Passcode!";
                        break;
                    case 4:
                        error = "Used Passcode!";
                        break;
                    case 5:
                        error = "Election Finished";
                        break;

                }

                //If no errors have been found
                if (noErrors && authenticationPhase) {
                    iddisplay = parser.getType();

                    SharedPreferences sharedPref = activity.getSharedPreferences("selectedconnection", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("title", iddisplay);
                    editor.commit();


                    Intent intent = new Intent(activity, PasscodeSession.class);
                    intent.putExtra("electionId", electionId);
                    intent.putExtra("target_server", target_server);
//                        intent.putExtra("iddisplay", iddisplay);
//                    if (target != null) {
                        intent.putExtra("target", target);
//                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                    activity.finish();
                } else if (noErrors) {

                    String numberofquestion = parser.getNumberOfQuestion();
                    String questionfinished = parser.getQuestionsFinished();

                    int Qnumber = Integer.parseInt(questionfinished) + 1;
                    String questionnumber = Qnumber + "";
                    new XmlGetQuestion(activity, context, target, target_server, numberofquestion).execute(electionId, passcode, questionnumber);
                } else {
                    //Display a message on-screen depending on the error
                    Toast toast = Toast.makeText(context, error, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } catch (Exception e) {
                //Catch any uncaught exceptions!
                Log.e("EvotingError", e.toString());
                e.printStackTrace();
                Toast.makeText(context, R.string.connection_error, Toast.LENGTH_LONG).show();
            }

        }
    }
}