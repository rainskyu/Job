package com.example.myapplication.extras;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Button;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.example.myapplication.R;

public class Print implements ReceiveListener {

    private String printerAddress, content, printCase,extraData;
    private Activity activity;
    private Context context;
    private Printer mPrinter;


    public Print(String printerAddress, String extraData, String content, Activity activity, Context context, String printCase) {
        this.printerAddress = printerAddress;
        this.content = content;
        this.extraData = extraData;
        this.activity = activity;
        this.context = context;
        this.printCase = printCase;
    }

    public boolean runPrinterSequence() {
        //connect to printer
        if (!initializeObject()) {
            return false;
        }
        //create receipt
        switch (printCase){
            case "test":
                if (!createTestPrintData()) {
                    finalizeObject();
                    return false;
                }
                break;
            case "stageOne":
                if (!createStageOneData()) {
                    finalizeObject();
                    return false;
                }
                break;
            case "stageTwo":
                if (!createStageTwoData()) {
                    finalizeObject();
                    return false;
                }
                break;
        }

        /*if (cut) {
            if (!createPrintData()) {
                finalizeObject();
                return false;
            }
        } else {
            if (!createPrintDataNoCut()) {
                finalizeObject();
                return false;
            }
        }*/
        //print
        if (!printData()) {
            finalizeObject();
            return false;
        }

        return true;

    }

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
            } catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        //sending data to printer
        try {
            mPrinter.sendData(Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            ShowMsg.showException(e, "sendData", context);
            try {
                mPrinter.disconnect();
            } catch (Exception ex) {
                // Do nothing
            }
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

    private boolean isPrintable(PrinterStatusInfo status) {
        if (status == null) {
            return false;
        }

        if (status.getConnection() == Printer.FALSE) {
            return false;
        } else if (status.getOnline() == Printer.FALSE) {
            return false;
        }

        return true;
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

        Log.e("PrinterWarning", warningsMsg);
    }

    private boolean connectPrinter() {
        boolean isBeginTransaction = false;

        if (mPrinter == null) {
            return false;
        }

        try {
            mPrinter.connect(printerAddress, Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            ShowMsg.showException(e, "connect", context);
            return false;
        }

        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        } catch (Exception e) {
            ShowMsg.showException(e, "beginTransaction", context);
        }

        if (isBeginTransaction == false) {
            try {
                mPrinter.disconnect();
            } catch (Epos2Exception e) {
                // Do nothing
                return false;
            }
        }

        return true;
    }

    private boolean createStageOneData() {
        String method = "";

        if (mPrinter == null) {
            return false;
        }

        try {
            method = "addTextAlign";
            mPrinter.addTextAlign(Printer.ALIGN_CENTER);
            method = "addTitle";
            mPrinter.addTextSize(1,2);
            mPrinter.addTextStyle(0,0,1,4);
            mPrinter.addText(extraData);
            method = "addFreeLine";
            mPrinter.addFeedLine(2);
            method = "addContent";
            mPrinter.addTextStyle(0,0,0,0);
            mPrinter.addTextSize(1,1);
            mPrinter.addText(content);
            method = "addFreeLine";
            mPrinter.addFeedLine(1);

        } catch (Exception e) {
            ShowMsg.showException(e, method, context);
            return false;
        }

        return true;
    }

    private boolean createStageTwoData(){
        String method = "";

        if (mPrinter == null) {
            return false;
        }

        try {
            method = "addTextAlign";
            mPrinter.addTextAlign(Printer.ALIGN_CENTER);
            method = "addContent";
            mPrinter.addText(content);
            method = "addFreeLine";
            mPrinter.addFeedLine(1);
            method = "addBallotResult";
            mPrinter.addTextSize(2,2);
            mPrinter.addText(extraData);
            method = "addFreeLine";
            mPrinter.addFeedLine(3);
            method = "addCut";
            mPrinter.addCut(Printer.CUT_FEED);

        } catch (Exception e) {
            ShowMsg.showException(e, method, context);
            return false;
        }

        return true;
    }

    private boolean createTestPrintData() {
        String method = "";

        if (mPrinter == null) {
            return false;
        }

        try {
            method = "addTextAlign";
            mPrinter.addTextAlign(Printer.ALIGN_CENTER);
            method = "addContent";
            mPrinter.addText(content);
            method = "addFreeLine";
            mPrinter.addFeedLine(1);
            method = "addCut";
            mPrinter.addCut(Printer.CUT_FEED);

        } catch (Exception e) {
            ShowMsg.showException(e, method, context);
            return false;
        }

        return true;
    }

    private void finalizeObject() {
        if (mPrinter == null) {
            return;
        }

        mPrinter.clearCommandBuffer();

        mPrinter.setReceiveEventListener(null);

        mPrinter = null;
    }

    private boolean initializeObject() {
        try {
            mPrinter = new Printer(Printer.TM_M10, Printer.MODEL_ANK, context);
        } catch (Exception e) {
            ShowMsg.showException(e, "Printer", context);
            return false;
        }

        mPrinter.setReceiveEventListener(this);

        return true;

    }

    private void disconnectPrinter() {
        if (mPrinter == null) {
            return;
        }

        try {
            mPrinter.endTransaction();
        } catch (final Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "endTransaction", context);
                }
            });
        }

        try {
            mPrinter.disconnect();
        } catch (final Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "disconnect", context);
                }
            });
        }

        finalizeObject();
    }


    @Override
    public void onPtrReceive(final Printer printer, final int code, final PrinterStatusInfo printerStatusInfo, final String id) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {
                //ShowMsg.showResult(code, makeErrorMessage(printerStatusInfo), context);

                dispPrinterWarnings(printerStatusInfo);


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
