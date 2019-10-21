package uk.ac.newcastle.dre_ip.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;

import java.util.ArrayList;

import uk.ac.newcastle.dre_ip.R;
import uk.ac.newcastle.dre_ip.connection.ServerConnection;
import uk.ac.newcastle.dre_ip.connection.XmlGetQuestion;

import uk.ac.newcastle.dre_ip.extras.ShowMsg;
import uk.ac.newcastle.dre_ip.parsers.XmlParser;
import uk.ac.newcastle.dre_ip.parsers.XmlTrimmer;


/**
 * The Voting activity.
 * <p>
 * This activity has three phases.
 * <p>
 * 1. Display candidates list from the first question, Deselect/Confirm button are disabled.
 * <p>
 * 2. Voter select one of the candidates and the candidate will be highlighted with blue-strip, meanwhile, the candidates list is frozen and Deselect/Confirm button are activated.. The result will be sent to the server and the first part of commitment receipt printed.
 * <p>
 * 3. Voter choose audit the candidate or confirm the selection. The result will be sent to server and the second part of commitment receipt printed.
 */
public class VotingActivity extends AppCompatActivity {

    private ListView questionGroup;
    private String[] lists;
    private Activity activity;
    private Context context;
    private String electionID, passcode, target, target_server, questionTitle, questionID, numberofquestion, electionTitle, candidate, ballotID;
    private Button selectioncancel, selectionconfirm;
    private XmlParser parser;
    private long mLastClickTime =0;

    /**
     * Create view for the election.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        electionID = getIntent().getStringExtra("electionID");
        passcode = getIntent().getStringExtra("passcode");
        target = getIntent().getStringExtra("target");
        target_server = getIntent().getStringExtra("target_server");
        questionTitle = getIntent().getStringExtra("question");
        numberofquestion = getIntent().getStringExtra("numberofquestions");
        questionID = getIntent().getStringExtra("currentquestion");
        this.activity = this;
        this.context = this;
        Log.e("IntentID:", electionID);
        Log.e("IntentPasscode", passcode);

        selectioncancel = (Button) findViewById(R.id.selectcancel);
        selectioncancel.setEnabled(false);
        selectioncancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new StageTwoReceipt(activity, context, target, target_server, numberofquestion).execute(electionID, ballotID, passcode, candidate, questionID, "audit");

            }
        });

        selectionconfirm = (Button) findViewById(R.id.selectconfirm);
        selectionconfirm.setEnabled(false);
        selectionconfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new StageTwoReceipt(activity, context, target, target_server, numberofquestion).execute(electionID, ballotID, passcode, candidate, questionID, "confirm");

            }
        });


        final TextView question = (TextView) findViewById(R.id.question);
        question.setText(questionTitle);
        TextView page = (TextView) findViewById(R.id.page);
        page.setText("Question " + questionID + " of " + numberofquestion);
        lists = getIntent().getExtras().getStringArray("lists");
        electionTitle = getIntent().getStringExtra("title");

        questionGroup = (ListView) findViewById(R.id.questionGroup);

        //Slow down the speed of voter choosing the candidate;
        CountDownTimer timer = new CountDownTimer(1000,500) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                candidateUpdate();
            }
        };
        timer.start();

        TextView titleField = (TextView) findViewById(R.id.title);
        titleField.setText(electionTitle);

        questionGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (SystemClock.elapsedRealtime() -mLastClickTime <1000){return;}
                mLastClickTime = SystemClock.elapsedRealtime();

                candidate = questionGroup.getItemAtPosition(position).toString().trim();

                    Log.e("targetVA", target);
                    new StageOneReceipt(activity, context, electionTitle, target, target_server, numberofquestion).execute(electionID, candidate, passcode, questionID);

            }
        });

    }

    /**
     * Update the state of Deselect/Confirm button according to the different phase of voting.
     * @param state Set enable or disable of the button
     */
    private void stateUpdate(boolean state) {
        selectioncancel.setEnabled(state);
        selectionconfirm.setEnabled(state);

    }

    /**
     * Update the state of candidates list according to the different phase of voting.
     * @param state Set enable or disable of selection of the candidate
     */
    private void clickdisable (boolean state){
        for (int i = 0; i < questionGroup.getChildCount(); i++) {
            questionGroup.getChildAt(i).setClickable(state);
        }
    }

    /**
     * Refresh the display of candidates list.
     */
    private void candidateUpdate() {
        ArrayList<String> questions = new ArrayList<>();
        if (lists != null) {
            for (String i :lists) {
                questions.add(i);
            }
            ArrayAdapter<String> questionAdapter = new ArrayAdapter<>(this, R.layout.questiongroup, R.id.row, questions);
            questionGroup.setAdapter(questionAdapter);
        }
    }

    /**
     * Make back button do noting when it is pressed. For preventing user from going back to previous screen which will lead inconsistency of the system.
     */
    @Override
    public void onBackPressed(){

    }

    /**
     * According to the selection in activity phase 2, StageOneReceipt will send the result to server and print out the first part of the commitment receipt.
     */
    private class StageOneReceipt extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private ProgressDialog dialog;
        private String electionID, candidate, xml, passcode, title, printer, target_server, questionid, numberofquestions;


        private StageOneReceipt(Activity activity, Context context, String title, String printer, String server, String numberofquestions) {

            super();
            this.activity = activity;
            this.context = context;
            this.title = title;
            this.printer = printer;
            target_server = server;
            this.numberofquestions = numberofquestions;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Fetching receipt, please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            electionID = params[0];
            candidate = params[1];
            passcode = params[2];
            questionid = params[3];
            xml = "<dreipvoting>\n" +
                    "<type>stageone</type>\n" +
                    "<electionid>" + electionID + "</electionid>\n" +
                    "<candidate>" + candidate + "</candidate>\n" +
                    "<passcode>" + passcode + "</passcode>\n" +
                    "<questionid>" + questionid + "</questionid>\n" +
                    "</dreipvoting>";
            Log.e("xml", xml);

            try {
                ServerConnection connection = new ServerConnection(target_server, xml);
                String returnStr = connection.retrieveXml();
                return returnStr;

            } catch (Exception e) {
                Log.e("StageOneException", e.toString());
            }

            return "";
        }

        @Override
        protected void onPostExecute(String returnStr) {

            if (dialog != null) dialog.dismiss();

            if (returnStr.isEmpty()) {
                Toast.makeText(context, R.string.connection_error, Toast.LENGTH_LONG).show();
            } else {

                try {
                    parser = new XmlParser(XmlTrimmer.trimXml(returnStr));
                    String receipt = parser.getStageOneReceipt();
                    ballotID = parser.getBallotID();

                    //receipt message
                    final String message = "      -------Voting Stage One-------\n\n"
                            + "      Election ID: " + electionID + "\n"
                            + "      Ballot ID: " + ballotID + "\n"
                            + "      Question ID: " + questionID + "\n"
                            + "      Confirmation Code: \n";
                    final String receiptstr = receipt.substring(0, 5) + " " + receipt.substring(5, 10) + " " + receipt.substring(10, 15) + " " + receipt.substring(15, 20) + " " + receipt.substring(20, 25) +"\n\n" +
                            receipt.substring(25, 30) + " " + receipt.substring(30, 35) + " " + receipt.substring(35, 40)+ " " + receipt.substring(40, 45) + " "+ receipt.substring(45, 50);

                    //Printing
                    if (!printer.equals("<NONE>")) {
                        Log.e("PrinterStageOneReceipt", printer);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Print print = new Print(title, message, receiptstr, null, printer, true, false, activity, context);
                                print.runPrintReceiptSequence();

                            }
                        }).start();
                    }else{VotingActivity.this.stateUpdate(true);
                        selectioncancel.setBackgroundColor(getColor(R.color.colorAccent));
                        selectionconfirm.setBackgroundColor(getColor(R.color.colorAccent));
                    }

                    clickdisable(true);

                } catch (Exception e) {
                    Log.e("StageOneReceiptError", e.toString());
                    e.printStackTrace();
                    Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
    /**
     * According to the selection in activity phase 3, StageTwoReceipt will send the result to server and print out the second part of the commitment receipt.
     */
    private class StageTwoReceipt extends AsyncTask<String, Void, String> {

        private Activity activity;
        private Context context;
        private ProgressDialog dialog;
        private String electionID, ballotID, candidate, xml, passcode, printer, target_server, numberofquestion, questionid;
        private XmlParser parser;

        private StageTwoReceipt(Activity activity, Context context, String printer, String server, String numberofquestion) {
            super();
            this.activity = activity;
            this.context = context;
            this.printer = printer;
            target_server = server;
            this.numberofquestion = numberofquestion;
        }


        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Finishing, please wait...");
            dialog.setCancelable(false);
            dialog.show();
            VotingActivity.this.stateUpdate(false);
            selectioncancel.setBackgroundColor(getColor(R.color.colorGreyLight));
            selectionconfirm.setBackgroundColor(getColor(R.color.colorGreyLight));

        }

        @Override
        protected String doInBackground(String... params) {

            electionID = params[0];
            ballotID = params[1];
            passcode = params[2];
            candidate = params[3];
            questionid = params[4];
            String type = params[5];
            if (type.equals("confirm")) {

                xml = "<dreipvoting>\n" +
                        "<type>stagetwoconfirm</type>\n" +
                        "<electionid>" + electionID + "</electionid>\n" +
                        "<passcode>" + passcode + "</passcode>\n" +
                        "<ballotid>" + ballotID + "</ballotid>\n" +
                        "<questionid>" + questionid + "</questionid>\n" +
                        "</dreipvoting>";
            } else if (type.equals("audit")) {

                xml = "<dreipvoting>\n" +
                        "<type>stagetwoaudit</type>\n" +
                        "<electionid>" + electionID + "</electionid>\n" +
                        "<ballotid>" + ballotID + "</ballotid>\n" +
                        "<candidate>" + candidate + "</candidate>\n" +
                        "<passcode>" + passcode + "</passcode>\n" +
                        "<questionid>" + questionid + "</questionid>\n" +
                        "</dreipvoting>";
            }
            Log.e("xml2", xml);

            try {
                String returnStr = new ServerConnection(target_server, xml).retrieveXml();
                Log.e("returnStr", returnStr);
                return returnStr;

            } catch (Exception e) {
                Log.e("StageTwoException", e.toString());
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            if (dialog != null) dialog.dismiss();

            if (s.isEmpty()) {
                Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show();
            } else {

                Log.e("xmlreturn", s);
                String xml = XmlTrimmer.trimXml(s);
                Log.e("xmlReceipt:", xml);
                try {
                    parser = new XmlParser(xml);
                    String receipt = parser.getStageTwoReceipt();
                    String type = parser.getType();
//                    android.app.DialogFragment dialogFragment;

                    if (type.equals("confirm")) {

                        //printing receipt
                        final String message = "\nBallot Confirmed\n";

                        if (!printer.equals("<NONE>")) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Print print = new Print(message, candidate, printer, false, true, activity, context);
                                    print.runPrintReceiptSequence();
                                }
                            }).start();

                        }else{
                            if (questionID.equals(numberofquestion)) {
                                Intent intent = new Intent(activity, ThanksActivity.class);
                                intent.putExtra("electionId", electionID);
                                intent.putExtra("target_server", target_server);
                                intent.putExtra("target", target);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                activity.finish();
                            } else {
                                int questionid = Integer.parseInt(questionID) + 1;
                                new XmlGetQuestion(activity, context, target, target_server, numberofquestion).execute(electionID, passcode, Integer.toString(questionid));
                            }
                        }
//                        dialogFragment = SecondReceiptDialog.addArgument(electionID, passcode, receipt, candidate, printer, target_server, numberofquestion, questionID);
//                        dialogFragment.show(activity.getFragmentManager(), "");
                    }

                    if (type.equals("audit")) {

                        //Printing receipt
                        final String message = "\nBallot Cancelled\n";

                        if (!printer.equals("<NONE>")) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Print print = new Print(message, candidate, printer, false, false, activity, context);
                                    print.runPrintReceiptSequence();

                                }
                            }).start();

                        }else
                        { VotingActivity.this.candidateUpdate();}

//                        dialogFragment = AuditReceiptDialog.addArgument(candidate);
//                        dialogFragment.show(activity.getFragmentManager(), "");


                    }

                } catch (Exception e) {
                    Log.e("StageTwoReceiptError", e.toString());
                    e.printStackTrace();
                    Toast.makeText(context, "Printer Error", Toast.LENGTH_SHORT).show();

                }
            }


        }
    }

    /**
     * Print the commitment receipt accordingly. Will using the SDK file from Epson in folder jniLibs.
     */
    private class Print implements ReceiveListener {

        private String receipt,message,target,candidate,title;
        private Activity activity;
        private Context context;
        private Printer mPrinter;
        private boolean isfirst,isconfirmed;

        /**
         * Instantiates a new Print.
         *
         * @param title       the title of voting
         * @param message1    the message need to be printed as commitment receipt
         * @param receipt     the conformation code retrieved from server, which need to be printed
         * @param candidate   the candidate name which is null on StageOneReceipt
         * @param target      the printer address
         * @param isfirst     Determine whether this is the first part of the commitment receipt
         * @param isconfirmed Determine whether this second part of the commitment receipt is deselect or confirm
         * @param activity    the activity
         * @param context     the context
         */
        public Print(String title, String message1, String receipt, String candidate, String target, boolean isfirst, boolean isconfirmed, Activity activity, Context context){
            this.activity = activity;
            this.message=message1;
            this.receipt= receipt;
            this.context=context;
            this.target=target;
            this.isfirst=isfirst;
            this.isconfirmed=isconfirmed;
            this.candidate=candidate;
            this.title = title;

        }

        /**
         * Instantiates a new Print.
         *
         * @param message1    the message need to be printed as commitment receipt
         * @param candidate   the candidate name need to be printed on StageTwoReceipt when voter deselect the candidate
         * @param target      the printer address
         * @param isfirst     Determine whether this is the first part of the commitment receipt
         * @param isconfirmed Determine whether this second part of the commitment receipt is deselect or confirm
         * @param activity    the activity
         * @param context     the context
         */
        public Print(String message1, String candidate, String target, boolean isfirst, boolean isconfirmed, Activity activity, Context context){
            this.activity = activity;
            this.message=message1;
            //this.receipt= receipt;
            this.context=context;
            this.target=target;
            this.isfirst=isfirst;
            this.isconfirmed=isconfirmed;
            this.candidate=candidate;

        }

        /**
         * Run print receipt sequence boolean.
         *
         * @return the boolean
         */
        public boolean runPrintReceiptSequence() {
            //connect to printer
            if (!initializeObject()) {
                return false;
            }
            //create receipt
            if (!createReceiptData(isfirst,isconfirmed,candidate)) {
                finalizeObject();
                return false;
            }
            //print
            if (!printData()) {
                finalizeObject();
                return false;
            }

            return true;
        }

        private boolean createReceiptData(boolean isfirst,boolean iscofirmed,String candidate) {
            String method = "";

            if (mPrinter == null) {
                return false;
            }

            try {
                if(isfirst) {
                    mPrinter.addTextAlign(Printer.ALIGN_CENTER);
                    mPrinter.addTextSize(1,2);
                    mPrinter.addTextFont(3);
                    mPrinter.addTextStyle(0,0,1,4);
                    mPrinter.addText(title+"\n");

                    mPrinter.addTextStyle(0,0,0,0);
                    mPrinter.addTextSize(1,1);
                    method = "addTextAlign";
                    mPrinter.addTextAlign(Printer.ALIGN_LEFT);
                    method = "addFeedLine";
                    mPrinter.addFeedLine(1);
                    method = "addText";
                    mPrinter.addText(message);

                    method = "addTextSize";
                    mPrinter.addTextSize(1,1);
                    mPrinter.addTextAlign(Printer.ALIGN_CENTER);
                    method = "addText";
                    mPrinter.addText("\n"+receipt+"\n");
                    mPrinter.addFeedLine(1);


                }else{

                    if(!iscofirmed){
                        mPrinter.addTextSize(1,1);
                        mPrinter.addTextAlign(Printer.ALIGN_LEFT);
                        mPrinter.addText("\n      -------Voting Stage Two-------\n");
                        mPrinter.addText("\n      Cancelled candidate name: \n");
                        mPrinter.addText("\n      "+candidate+"\n");
                    }else{
                        mPrinter.addTextSize(1,1);
                        mPrinter.addText("\n      -------Voting Stage Two-------\n");
                    }
                    method = "addTextSize";
                    mPrinter.addTextSize(2,2);
                    mPrinter.addTextAlign(Printer.ALIGN_CENTER);
                    method = "addText";
                    mPrinter.addText(message+"\n");
                    method = "addTextSize";
                    mPrinter.addTextSize(1,1);
                    mPrinter.addTextAlign(Printer.ALIGN_CENTER);
                    method = "addText";
                    mPrinter.addText("Please visit http://vote.ncl.ac.uk/DRE_IP\n To check your Confirmation Code.");
                    mPrinter.addFeedLine(2);
                    method = "addCut";
                    mPrinter.addCut(Printer.CUT_FEED);
                }
            }
            catch (Exception e) {
                ShowMsg.showException(e, method, context);
                return false;
            }


            return true;
        }


        /**
         * pintData method, to printdata
         * @return
         */
        private boolean printData() {
            if (mPrinter == null) {
                return false;
            }

            //check printer connection
            if (!connectPrinter()) {
                return false;
            }

            PrinterStatusInfo status = mPrinter.getStatus();

            dispPrinterWarnings(status);

            //
            if (!isPrintable(status)) {
                ShowMsg.showMsg(makeErrorMessage(status), context);
                try {
                    mPrinter.disconnect();
                }
                catch (Exception ex) {
                    // Do nothing
                }
                return false;
            }

            //sending data to printer
            try {
                mPrinter.sendData(Printer.PARAM_DEFAULT);
            }
            catch (Exception e) {
                ShowMsg.showException(e, "sendData", context);
                try {
                    mPrinter.disconnect();
                }
                catch (Exception ex) {
                    // Do nothing
                }
                return false;
            }

            return true;
        }

        /**
         * initializeObject method, initialise the printer
         * @return
         */
        private boolean initializeObject() {
            try {
                mPrinter = new Printer(Printer.TM_M30,Printer.MODEL_ANK, context);
            }
            catch (Exception e) {
                ShowMsg.showException(e, "Printer", context);
                return false;
            }

            mPrinter.setReceiveEventListener(this);

            return true;
        }

        /**
         * finalizeObject method
         */
        private void finalizeObject() {
            if (mPrinter == null) {
                return;
            }

            mPrinter.clearCommandBuffer();

            mPrinter.setReceiveEventListener(null);

            mPrinter = null;
        }

        /**
         * connectPrinter method to connect to printer
         *
         * @return
         */
        private boolean connectPrinter() {
            boolean isBeginTransaction = false;

            if (mPrinter == null) {
                return false;
            }

            try {
                mPrinter.connect(target, Printer.PARAM_DEFAULT);
            }
            catch (Exception e) {
                ShowMsg.showException(e, "connect", context);
                return false;
            }

            try {
                mPrinter.beginTransaction();
                isBeginTransaction = true;
            }
            catch (Exception e) {
                ShowMsg.showException(e, "beginTransaction", context);
            }

            if (isBeginTransaction == false) {
                try {
                    mPrinter.disconnect();
                }
                catch (Epos2Exception e) {
                    // Do nothing
                    return false;
                }
            }

            return true;
        }

        private void disconnectPrinter() {
            if (mPrinter == null) {
                return;
            }

            try {
                mPrinter.endTransaction();
            }
            catch (final Exception e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        ShowMsg.showException(e, "endTransaction", context);
                    }
                });
            }

            try {
                mPrinter.disconnect();
            }
            catch (final Exception e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        ShowMsg.showException(e, "disconnect", context);
                    }
                });
            }

            finalizeObject();
        }

        private boolean isPrintable(PrinterStatusInfo status) {
            if (status == null) {
                return false;
            }

            if (status.getConnection() == Printer.FALSE) {
                return false;
            }
            else if (status.getOnline() == Printer.FALSE) {
                return false;
            }

            return true;
        }

        private String makeErrorMessage(PrinterStatusInfo status) {
            String msg = "";

            if (status.getOnline() == Printer.FALSE) {
                msg += activity.getString(R.string.handlingmsg_err_offline);
            }
            if (status.getConnection() == Printer.FALSE) {
                msg += activity.getString(R.string.handlingmsg_err_no_response);
            }
            if (status.getCoverOpen() == Printer.TRUE) {
                msg += activity.getString(R.string.handlingmsg_err_cover_open);
            }
            if (status.getPaper() == Printer.PAPER_EMPTY) {
                msg += activity.getString(R.string.handlingmsg_err_receipt_end);
            }
            if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
                msg += activity.getString(R.string.handlingmsg_err_paper_feed);
            }
            if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
                msg += activity.getString(R.string.handlingmsg_err_autocutter);
                msg += activity.getString(R.string.handlingmsg_err_need_recover);
            }
            if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
                msg += activity.getString(R.string.handlingmsg_err_unrecover);
            }
            if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
                if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                    msg += activity.getString(R.string.handlingmsg_err_overheat);
                    msg += activity.getString(R.string.handlingmsg_err_head);
                }
                if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                    msg += activity.getString(R.string.handlingmsg_err_overheat);
                    msg += activity.getString(R.string.handlingmsg_err_motor);
                }
                if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                    msg += activity.getString(R.string.handlingmsg_err_overheat);
                    msg += activity.getString(R.string.handlingmsg_err_battery);
                }
                if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                    msg += activity.getString(R.string.handlingmsg_err_wrong_paper);
                }
            }
            if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
                msg += activity.getString(R.string.handlingmsg_err_battery_real_end);
            }

            return msg;
        }


        private void dispPrinterWarnings(PrinterStatusInfo status) {
            String warningsMsg = "";

            if (status == null) {
                return;
            }

            if (status.getPaper() == Printer.PAPER_NEAR_END) {
                warningsMsg += activity.getString(R.string.handlingmsg_warn_receipt_near_end);
            }

            if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_1) {
                warningsMsg += activity.getString(R.string.handlingmsg_warn_battery_near_end);
            }

            Log.e("PrinterWarning",warningsMsg);
        }

        @Override
        public void onPtrReceive(Printer printer, final int code, final PrinterStatusInfo printerStatusInfo, String id) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showResult(code, makeErrorMessage(printerStatusInfo), context);

                    dispPrinterWarnings(printerStatusInfo);

                    if(isfirst)
                    {VotingActivity.this.stateUpdate(true);
                    }

                    else if (isconfirmed)
                    {
                        if (questionID.equals(numberofquestion)) {
                            Intent intent = new Intent(activity, ThanksActivity.class);
                            intent.putExtra("electionId", electionID);
                            intent.putExtra("target_server", target_server);
                            intent.putExtra("target", target);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            activity.finish();
                        } else {
                            int questionid = Integer.parseInt(questionID) + 1;
                            new XmlGetQuestion(activity, context, target, target_server, numberofquestion).execute(electionID, passcode, Integer.toString(questionid));

                        }
                    }
                    else
                    { VotingActivity.this.candidateUpdate();}

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            disconnectPrinter();
                        }
                    }).start();
                }
            });
        }

    }
}
