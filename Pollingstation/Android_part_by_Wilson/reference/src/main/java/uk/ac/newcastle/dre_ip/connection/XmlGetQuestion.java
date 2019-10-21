package uk.ac.newcastle.dre_ip.connection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import uk.ac.newcastle.dre_ip.R;
import uk.ac.newcastle.dre_ip.activity.VotingActivity;
import uk.ac.newcastle.dre_ip.parsers.XmlParser;
import uk.ac.newcastle.dre_ip.parsers.XmlTrimmer;

/**
 * Get question title and candidates from server.
 */
public class XmlGetQuestion extends AsyncTask<String, Void, String> {
    private Activity activity;
    private Context context;
    private ProgressDialog dialog;
    private String electionID, passcode, questionID, xml, target, target_server, numberofquestion;

    /**
     * Instantiates a new Xml get question.
     *
     * @param activity         the activity
     * @param context          the context
     * @param target           the printer address
     * @param target_server    the server address
     * @param numberofquestion the number of question
     */
    public XmlGetQuestion(Activity activity, Context context, String target, String target_server, String numberofquestion) {
        super();
        this.activity = activity;
        this.context = context;
        this.target = target;
        this.target_server = target_server;
        this.numberofquestion = numberofquestion;

    }

    @Override
    protected String doInBackground(String... params) {
        electionID = params[0];
        passcode = params[1];
        questionID = params[2];

        xml = "<dreipvoting>\n" +
                "<type>getquestion</type>\n" +
                "<electionid>" + electionID + "</electionid>\n" +
                "<questionid>" + questionID + "</questionid>\n" +
                "</dreipvoting>";
        Log.e("xml-getcan", xml);
        try {
            String returnedString = new ServerConnection(target_server, xml).retrieveXml();
            Log.e("xml-getCan", returnedString);
            //Return the retrieved XML from the server
            return returnedString;
        } catch (Exception e) {

            Log.e("EvotingException2", e.toString());
            e.printStackTrace();
            return "";
        }

    }

    @Override
    protected void onPostExecute(String xmlResult) {
        //Dismiss Loading dialog
        if (dialog != null) dialog.dismiss();

        //If the returned XML is blank, show an error message on-screen
        if (xmlResult.equals("")) {
            Toast.makeText(context, "Connection error", Toast.LENGTH_LONG).show();
        } else {
            //Format XML string, this prevents the app from crashing if error messages are passed from the server in the XML
//            String xml = XmlTrimmer.trimXml(xmlResult);

            //Parse result
            try {
                //Parse the XML into a new XmlParser object
                XmlParser parser = new XmlParser(XmlTrimmer.trimXml(xmlResult));

                //Parse XML to find an error with the question request


                String question = parser.getQuestion();
                ArrayList<String> candidates = parser.getCandidateList();
                String[] candi = candidates.get(0).split(",");
                String title = parser.getTitle();
                Intent intent = new Intent(activity, VotingActivity.class);
                intent.putExtra("electionID", electionID);
                intent.putExtra("passcode", passcode);
                intent.putExtra("lists", candi);
                intent.putExtra("title", title);
                intent.putExtra("question", question);
                intent.putExtra("numberofquestions", numberofquestion);
                intent.putExtra("currentquestion", questionID);
                intent.putExtra("target_server", target_server);
                intent.putExtra("target", target);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
                activity.finish();


            } catch (Exception e) {
                //This catches any possible uncaught exceptions

                Log.e("EVotingError", e.toString());
                e.printStackTrace();
                //Display error message on-screen
                Toast.makeText(context, R.string.connection_error, Toast.LENGTH_LONG).show();
            }
        }
    }

}
