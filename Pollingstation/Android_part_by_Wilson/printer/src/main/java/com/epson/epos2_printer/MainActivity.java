package com.epson.epos2_printer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.Log;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, ReceiveListener {

    private static final int REQUEST_PERMISSION = 100;

    private Context mContext = null;
    private EditText mEditTarget = null;
    private Spinner mSpnSeries = null;
    private Spinner mSpnLang = null;
    private Printer  mPrinter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestRuntimePermission();

        mContext = this;

        int[] target = {
            R.id.btnDiscovery,
            R.id.btnSampleReceipt,
            R.id.btnSampleCoupon
        };

        for (int i = 0; i < target.length; i++) {
            Button button = (Button)findViewById(target[i]);
            button.setOnClickListener(this);
        }

        mSpnSeries = (Spinner)findViewById(R.id.spnModel);
        ArrayAdapter<SpnModelsItem> seriesAdapter = new ArrayAdapter<SpnModelsItem>(this, android.R.layout.simple_spinner_item);
        seriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_m10), Printer.TM_M10));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_m30), Printer.TM_M30));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_p20), Printer.TM_P20));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_p60), Printer.TM_P60));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_p60ii), Printer.TM_P60II));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_p80), Printer.TM_P80));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t20), Printer.TM_T20));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t60), Printer.TM_T60));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t70), Printer.TM_T70));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t81), Printer.TM_T81));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t82), Printer.TM_T82));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t83), Printer.TM_T83));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t83iii), Printer.TM_T83III));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t88), Printer.TM_T88));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t90), Printer.TM_T90));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t90kp), Printer.TM_T90KP));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t100), Printer.TM_T100));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_u220), Printer.TM_U220));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_u330), Printer.TM_U330));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_l90), Printer.TM_L90));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_h6000), Printer.TM_H6000));
        mSpnSeries.setAdapter(seriesAdapter);
        mSpnSeries.setSelection(0);

        mSpnLang = (Spinner)findViewById(R.id.spnLang);
        ArrayAdapter<SpnModelsItem> langAdapter = new ArrayAdapter<SpnModelsItem>(this, android.R.layout.simple_spinner_item);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langAdapter.add(new SpnModelsItem(getString(R.string.lang_ank), Printer.MODEL_ANK));
        langAdapter.add(new SpnModelsItem(getString(R.string.lang_japanese), Printer.MODEL_JAPANESE));
        langAdapter.add(new SpnModelsItem(getString(R.string.lang_chinese), Printer.MODEL_CHINESE));
        langAdapter.add(new SpnModelsItem(getString(R.string.lang_taiwan), Printer.MODEL_TAIWAN));
        langAdapter.add(new SpnModelsItem(getString(R.string.lang_korean), Printer.MODEL_KOREAN));
        langAdapter.add(new SpnModelsItem(getString(R.string.lang_thai), Printer.MODEL_THAI));
        langAdapter.add(new SpnModelsItem(getString(R.string.lang_southasia), Printer.MODEL_SOUTHASIA));
        mSpnLang.setAdapter(langAdapter);
        mSpnLang.setSelection(0);

        mEditTarget = (EditText)findViewById(R.id.edtTarget);

        try {
            Log.setLogSettings(mContext, Log.PERIOD_TEMPORARY, Log.OUTPUT_STORAGE, null, 0, 1, Log.LOGLEVEL_LOW);
        }
        catch (Exception e) {
            ShowMsg.showException(e, "setLogSettings", mContext);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        if (data != null && resultCode == RESULT_OK) {
            String target = data.getStringExtra(getString(R.string.title_target));
            if (target != null) {
                EditText mEdtTarget = (EditText)findViewById(R.id.edtTarget);
                mEdtTarget.setText(target);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btnDiscovery:
                intent = new Intent(this, DiscoveryActivity.class);
                startActivityForResult(intent, 0);
                break;

            case R.id.btnSampleReceipt:
                updateButtonState(false);
                if (!runPrintReceiptSequence()) {
                    updateButtonState(true);
                }
                break;

            case R.id.btnSampleCoupon:
                updateButtonState(false);
                if (!runPrintCouponSequence()) {
                    updateButtonState(true);
                }
                break;

            default:
                // Do nothing
                break;
        }
    }

    private boolean runPrintReceiptSequence() {
        if (!initializeObject()) {
            return false;
        }

        if (!createReceiptData()) {
            finalizeObject();
            return false;
        }

        if (!printData()) {
            finalizeObject();
            return false;
        }

        return true;
    }

    private boolean createReceiptData() {
        String method = "";
        Bitmap logoData = BitmapFactory.decodeResource(getResources(), R.drawable.store);
        StringBuilder textData = new StringBuilder();
        final int barcodeWidth = 2;
        final int barcodeHeight = 100;

        if (mPrinter == null) {
            return false;
        }

        try {
            method = "addTextAlign";
            mPrinter.addTextAlign(Printer.ALIGN_CENTER);

            method = "addImage";
            mPrinter.addImage(logoData, 0, 0,
                              logoData.getWidth(),
                              logoData.getHeight(),
                              Printer.COLOR_1,
                              Printer.MODE_MONO,
                              Printer.HALFTONE_DITHER,
                              Printer.PARAM_DEFAULT,
                              Printer.COMPRESS_AUTO);

            method = "addFeedLine";
            mPrinter.addFeedLine(1);
            textData.append("THE STORE 123 (555) 555 – 5555\n");
            textData.append("STORE DIRECTOR – John Smith\n");
            textData.append("\n");
            textData.append("7/01/07 16:58 6153 05 0191 134\n");
            textData.append("ST# 21 OP# 001 TE# 01 TR# 747\n");
            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("400 OHEIDA 3PK SPRINGF  9.99 R\n");
            textData.append("410 3 CUP BLK TEAPOT    9.99 R\n");
            textData.append("445 EMERIL GRIDDLE/PAN 17.99 R\n");
            textData.append("438 CANDYMAKER ASSORT   4.99 R\n");
            textData.append("474 TRIPOD              8.99 R\n");
            textData.append("433 BLK LOGO PRNTED ZO  7.99 R\n");
            textData.append("458 AQUA MICROTERRY SC  6.99 R\n");
            textData.append("493 30L BLK FF DRESS   16.99 R\n");
            textData.append("407 LEVITATING DESKTOP  7.99 R\n");
            textData.append("441 **Blue Overprint P  2.99 R\n");
            textData.append("476 REPOSE 4PCPM CHOC   5.49 R\n");
            textData.append("461 WESTGATE BLACK 25  59.99 R\n");
            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("SUBTOTAL                160.38\n");
            textData.append("TAX                      14.43\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            method = "addTextSize";
            mPrinter.addTextSize(2, 2);
            method = "addText";
            mPrinter.addText("TOTAL    174.81\n");
            method = "addTextSize";
            mPrinter.addTextSize(1, 1);
            method = "addFeedLine";
            mPrinter.addFeedLine(1);

            textData.append("CASH                    200.00\n");
            textData.append("CHANGE                   25.19\n");
            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("Purchased item total number\n");
            textData.append("Sign Up and Save !\n");
            textData.append("With Preferred Saving Card\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());
            method = "addFeedLine";
            mPrinter.addFeedLine(2);

            method = "addBarcode";
            mPrinter.addBarcode("01209457",
                                Printer.BARCODE_CODE39,
                                Printer.HRI_BELOW,
                                Printer.FONT_A,
                                barcodeWidth,
                                barcodeHeight);

            method = "addCut";
            mPrinter.addCut(Printer.CUT_FEED);
        }
        catch (Exception e) {
            ShowMsg.showException(e, method, mContext);
            return false;
        }

        textData = null;

        return true;
    }

    private boolean runPrintCouponSequence() {
        if (!initializeObject()) {
            return false;
        }

        if (!createCouponData()) {
            finalizeObject();
            return false;
        }

        if (!printData()) {
            finalizeObject();
            return false;
        }

        return true;
    }

    private boolean createCouponData() {
        String method = "";
        Bitmap coffeeData = BitmapFactory.decodeResource(getResources(), R.drawable.coffee);
        Bitmap wmarkData = BitmapFactory.decodeResource(getResources(), R.drawable.wmark);

        final int barcodeWidth = 2;
        final int barcodeHeight = 64;
        final int pageAreaHeight = 500;
        final int pageAreaWidth = 500;
        final int fontAHeight = 24;
        final int fontAWidth = 12;
        final int barcodeWidthPos = 110;
        final int barcodeHeightPos = 70;

        if (mPrinter == null) {
            return false;
        }

        try {
            method = "addPageBegin";
            mPrinter.addPageBegin();

            method = "addPageArea";
            mPrinter.addPageArea(0, 0, pageAreaWidth, pageAreaHeight);

            method = "addPageDirection";
            mPrinter.addPageDirection(Printer.DIRECTION_TOP_TO_BOTTOM);

            method = "addPagePosition";
            mPrinter.addPagePosition(0, coffeeData.getHeight());

            method = "addImage";
            mPrinter.addImage(coffeeData, 0, 0, coffeeData.getWidth(), coffeeData.getHeight(), Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, 3, Printer.PARAM_DEFAULT);

            method = "addPagePosition";
            mPrinter.addPagePosition(0, wmarkData.getHeight());

            method = "addImage";
            mPrinter.addImage(wmarkData, 0, 0, wmarkData.getWidth(), wmarkData.getHeight(), Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT);

            method = "addPagePosition";
            mPrinter.addPagePosition(fontAWidth * 4, (pageAreaHeight / 2) - (fontAHeight * 2));

            method = "addTextSize";
            mPrinter.addTextSize(3, 3);

            method = "addTextStyle";
            mPrinter.addTextStyle(Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, Printer.TRUE, Printer.PARAM_DEFAULT);

            method = "addTextSmooth";
            mPrinter.addTextSmooth(Printer.TRUE);

            method = "addText";
            mPrinter.addText("FREE Coffee\n");

            method = "addPagePosition";
            mPrinter.addPagePosition((pageAreaWidth / barcodeWidth) - barcodeWidthPos, coffeeData.getHeight() + barcodeHeightPos);

            method = "addBarcode";
            mPrinter.addBarcode("01234567890", Printer.BARCODE_UPC_A, Printer.PARAM_DEFAULT, Printer.PARAM_DEFAULT, barcodeWidth, barcodeHeight);

            method = "addPageEnd";
            mPrinter.addPageEnd();

            method = "addCut";
            mPrinter.addCut(Printer.CUT_FEED);
        }
        catch (Exception e) {
            ShowMsg.showException(e, method, mContext);
            return false;
        }

        return true;
    }

    private boolean printData() {
        if (mPrinter == null) {
            return false;
        }

        if (!connectPrinter()) {
            return false;
        }

        PrinterStatusInfo status = mPrinter.getStatus();

        dispPrinterWarnings(status);

        if (!isPrintable(status)) {
            ShowMsg.showMsg(makeErrorMessage(status), mContext);
            try {
                mPrinter.disconnect();
            }
            catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        try {
            mPrinter.sendData(Printer.PARAM_DEFAULT);
        }
        catch (Exception e) {
            ShowMsg.showException(e, "sendData", mContext);
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

    private boolean initializeObject() {
        try {
            mPrinter = new Printer(((SpnModelsItem) mSpnSeries.getSelectedItem()).getModelConstant(),
                                   ((SpnModelsItem) mSpnLang.getSelectedItem()).getModelConstant(),
                                   mContext);
        }
        catch (Exception e) {
            ShowMsg.showException(e, "Printer", mContext);
            return false;
        }

        mPrinter.setReceiveEventListener(this);

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

    private boolean connectPrinter() {
        boolean isBeginTransaction = false;

        if (mPrinter == null) {
            return false;
        }

        try {
            mPrinter.connect(mEditTarget.getText().toString(), Printer.PARAM_DEFAULT);
        }
        catch (Exception e) {
            ShowMsg.showException(e, "connect", mContext);
            return false;
        }

        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        }
        catch (Exception e) {
            ShowMsg.showException(e, "beginTransaction", mContext);
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
            runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "endTransaction", mContext);
                }
            });
        }

        try {
            mPrinter.disconnect();
        }
        catch (final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "disconnect", mContext);
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
        else {
            ;//print available
        }

        return true;
    }

    private String makeErrorMessage(PrinterStatusInfo status) {
        String msg = "";

        if (status.getOnline() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_offline);
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_no_response);
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += getString(R.string.handlingmsg_err_cover_open);
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += getString(R.string.handlingmsg_err_receipt_end);
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += getString(R.string.handlingmsg_err_paper_feed);
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += getString(R.string.handlingmsg_err_autocutter);
            msg += getString(R.string.handlingmsg_err_need_recover);
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += getString(R.string.handlingmsg_err_unrecover);
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_head);
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_motor);
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_battery);
            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += getString(R.string.handlingmsg_err_wrong_paper);
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += getString(R.string.handlingmsg_err_battery_real_end);
        }

        return msg;
    }

    private void dispPrinterWarnings(PrinterStatusInfo status) {
        EditText edtWarnings = (EditText)findViewById(R.id.edtWarnings);
        String warningsMsg = "";

        if (status == null) {
            return;
        }

        if (status.getPaper() == Printer.PAPER_NEAR_END) {
            warningsMsg += getString(R.string.handlingmsg_warn_receipt_near_end);
        }

        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_1) {
            warningsMsg += getString(R.string.handlingmsg_warn_battery_near_end);
        }

        edtWarnings.setText(warningsMsg);
    }

    private void updateButtonState(boolean state) {
        Button btnReceipt = (Button)findViewById(R.id.btnSampleReceipt);
        Button btnCoupon = (Button)findViewById(R.id.btnSampleCoupon);
        btnReceipt.setEnabled(state);
        btnCoupon.setEnabled(state);
    }

    @Override
    public void onPtrReceive(final Printer printerObj, final int code, final PrinterStatusInfo status, final String printJobId) {
        runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {
                ShowMsg.showResult(code, makeErrorMessage(status), mContext);

                dispPrinterWarnings(status);

                updateButtonState(true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disconnectPrinter();
                    }
                }).start();
            }
        });
    }

    private void requestRuntimePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        int permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> requestPermissions = new ArrayList<>();

        if (permissionStorage == PackageManager.PERMISSION_DENIED) {
            requestPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionLocation == PackageManager.PERMISSION_DENIED) {
            requestPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!requestPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, requestPermissions.toArray(new String[requestPermissions.size()]), REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode != REQUEST_PERMISSION || grantResults.length == 0) {
            return;
        }

        List<String> requestPermissions = new ArrayList<>();

        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                requestPermissions.add(permissions[i]);
            }
            if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                    && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                requestPermissions.add(permissions[i]);
            }
        }

        if (!requestPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, requestPermissions.toArray(new String[requestPermissions.size()]), REQUEST_PERMISSION);
        }
    }
}
