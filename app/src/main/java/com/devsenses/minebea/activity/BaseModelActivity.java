package com.devsenses.minebea.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.devsenses.minebea.MockData;
import com.devsenses.minebea.R;
import com.devsenses.minebea.manager.BundleManager;
import com.devsenses.minebea.utils.Utils;

/**
 * Created by pong.p on 2/9/2016.
 */
public abstract class BaseModelActivity extends FragmentActivity {
    protected long lineId, modelId, processId, shiftId;
    protected String employeeNo = "";
    protected Bundle bundle;
    protected String workingDate, shiftTime;
    private TextView textDateShift, lbTextProcessHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        if (bundle == null) {
            bundle = BundleManager.putUserDataToBundle(new Bundle(), "0001", false, false);
            bundle = BundleManager.putLoginModelDataToBundle(bundle, MockData.getMockModel(),
                    MockData.getMockLine(), MockData.getMockProcess());
            bundle = BundleManager.putSelectedModelDataToBundle(bundle, MockData.getMockSelectedModel());
            bundle = BundleManager.putLotNo(bundle, "mock lot no naja");
            bundle = BundleManager.putPartData(bundle, MockData.getMockPartData());
        }
        initDataFromBundle(bundle);

        initCreateView(savedInstanceState);
        initModelDataToUI(bundle);
    }

    public abstract void initCreateView(Bundle savedInstanceState);

    protected void initDataFromBundle(Bundle bundle) {
        workingDate = BundleManager.getWorkingDate(bundle);
        shiftId = BundleManager.getShiftID(bundle);
        shiftTime = BundleManager.getShiftTime(bundle);
        processId = BundleManager.getProcessID(bundle);
        lineId = BundleManager.getLineID(bundle);
        modelId = BundleManager.getModelID(bundle);
        employeeNo = BundleManager.getEmployeeNo(bundle);
    }

    private void initModelDataToUI(Bundle bundle) {
        lbTextProcessHeader = (TextView) findViewById(R.id.expand_head_text);
        TextView lbTextModel = (TextView) findViewById(R.id.lbTextModel);
        TextView lbTextLine = (TextView) findViewById(R.id.lbTextLine);
        TextView lbTextEmpNo = (TextView) findViewById(R.id.lbTextEmpNo);
        TextView lbLotNoTextView = (TextView) findViewById(R.id.lbLotNoTextView);
        textDateShift = (TextView) findViewById(R.id.textDateShift);

        setFormattedProcessText(BundleManager.getProcessTitle(bundle), BundleManager.getProcessNumber(bundle),
                lbTextProcessHeader);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        String black = getResources().getString(R.string.login_date_and_shift);
        SpannableString blackSpannable = new SpannableString(black);
        blackSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, black.length(), 0);
        builder.append(blackSpannable);

        String blue = " " + BundleManager.getWorkingDate(bundle) + " / " + BundleManager.getShiftTime(bundle);
        SpannableString blueSpannable = new SpannableString(blue);
        blueSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blueText)), 0, blue.length(), 0);
        builder.append(blueSpannable);
        textDateShift.setText(builder, TextView.BufferType.SPANNABLE);

        lbTextModel.setText(BundleManager.getModelTitle(bundle));
        lbTextLine.setText(BundleManager.getLineTitle(bundle));
        lbTextEmpNo.setText(BundleManager.getEmployeeNo(bundle));
        lbLotNoTextView.setText(BundleManager.getLotNo(bundle));

        TextView textDate = (TextView) findViewById(R.id.textDate);
        textDate.setText(Utils.getDateCurrent());
    }

    @SuppressLint("SetTextI18n")
    private void setFormattedProcessText(String title, String number, TextView textView) {
        textView.setText(title + " : " + number);   //text Process default
    }

    protected String getFormattedProcessText() {
        return lbTextProcessHeader.getText().toString();
    }
}
