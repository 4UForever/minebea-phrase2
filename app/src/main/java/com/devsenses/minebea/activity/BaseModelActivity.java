package com.devsenses.minebea.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

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
    private TextView lbTextProcessHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        initDataFromBundle(bundle);

        initCreateView(savedInstanceState);
        initModelDataToUI(bundle);
    }

    public abstract void initCreateView(Bundle savedInstanceState);

    protected void initDataFromBundle(Bundle bundle) {
        shiftId = BundleManager.getShiftID(bundle);
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

        setFormattedProcessText(BundleManager.getProcessTitle(bundle), BundleManager.getProcessNumber(bundle),
                lbTextProcessHeader);

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

    protected String getFormattedProcessText(){
        return lbTextProcessHeader.getText().toString();
    }
}
