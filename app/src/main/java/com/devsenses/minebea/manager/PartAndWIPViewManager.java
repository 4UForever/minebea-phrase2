package com.devsenses.minebea.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.model.partmodel.Part;
import com.devsenses.minebea.model.partmodel.LotNo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pong.p on 12/15/2015.
 */
public class PartAndWIPViewManager {

    private static final String TAG = "PartAndWIPViewManager";
    private static final int BASE_LOT_COUNT = 3;

    private LinearLayout partNoListLayout;
    private LinearLayout wipNoListLayout;

    private List<PartView> partItemViewList;
    private List<PartLotView> wipItemViewList;

    private LayoutInflater inflater;
    private List<Part> partList;
    private View customView;

    public PartAndWIPViewManager(@NonNull Context context, View customView, List<Part> partList) {
        this.customView = customView;
        this.partList = partList;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        initUIDialog();
        addPartAndWIPList(partList);
    }

    private void initUIDialog() {
        partNoListLayout = (LinearLayout) customView.findViewById(R.id.partNoListLayout);
        wipNoListLayout = (LinearLayout) customView.findViewById(R.id.wipNoListLayout);
    }

    private void addPartAndWIPList(List<Part> partList) {
        partItemViewList = createPartNoViewList(partList, partNoListLayout);
        wipItemViewList = createPartLotViewList(wipNoListLayout, BASE_LOT_COUNT, "WIP Lot no.");
    }

    private List<PartView> createPartNoViewList(List<Part> list, LinearLayout layout) {
        List<PartView> partViewList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            View view = inflater.inflate(R.layout.item_part_header, layout, false);

            PartView partView = new PartView(view);

            partView.partHeaderTextView.setText(list.get(i).getNumber()+"("+list.get(i).getName()+")");
            partView.setIqcViewList(createPartLotViewList(partView.partListLayout, BASE_LOT_COUNT, "IQC Lot no."));

            partViewList.add(partView);
            layout.addView(view);
        }
        return partViewList;
    }

    private List<PartLotView> createPartLotViewList(LinearLayout layout, int size, String title) {
        List<PartLotView> viewList = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            View view = inflater.inflate(R.layout.item_part_body, layout, false);
            PartLotView partLotView = new PartLotView(view);
            partLotView.partLotNoText.setText(title);

            viewList.add(partLotView);
            layout.addView(view);
        }
        return viewList;
    }

    public void setOnEachScanButtonClick(OnQRCodeRead listener) {
        if (partItemViewList != null) {
            for (int i = 0; i < partItemViewList.size(); i++) {
                partItemViewList.get(i).setEachIQCScanButtonListener(listener);
            }
        }
        if (wipItemViewList != null) {
            for (int i = 0; i < wipItemViewList.size(); i++) {
                wipItemViewList.get(i).setScanButtonListener(listener);
            }
        }
    }

    public void getDataInForm(final OnGetFormDataListener listener) {
        insertIQCLotNoByViewList(partList, partItemViewList);
//        Log.d(TAG, "onClick " + Arrays.toString(partList.toArray()));
//        Log.d(TAG, "onClick " + Arrays.toString(getInputDataFromViewList(wipItemViewList).toArray()));
        listener.onOK(partList, getInputDataFromViewList(wipItemViewList));
    }

    private void insertIQCLotNoByViewList(List<Part> partList, List<PartView> partViewList) {
        for (int i = 0; i < partViewList.size(); i++) {
            partList.get(i).setIQC(getInputDataFromViewList(partViewList.get(i).iqcViewList));
        }
    }

    private List<LotNo> getInputDataFromViewList(List<PartLotView> viewList) {
        List<LotNo> list = new ArrayList<>();
        for (int i = 0; i < viewList.size(); i++) {
            LotNo lotNo = new LotNo();
            if (!viewList.get(i).partLotNoEditText.getText().toString().isEmpty()) {
                lotNo.setNumber(viewList.get(i).partLotNoEditText.getText().toString());
                try {
                    lotNo.setQuantity(Integer.parseInt(viewList.get(i).partLotNoQuantityEditText.getText().toString()));
                } catch (NumberFormatException e) {
                    lotNo.setQuantity(0);
                }
                list.add(lotNo);
            }
        }
        return list;
    }

    public void updateRecoverPartData(List<Part> recoverPartList) throws Exception {
        for (int i = 0; i < partItemViewList.size(); i++) {
            for (int j = 0; j < recoverPartList.size(); j++) {
                if (partItemViewList.get(i).partHeaderTextView.getText().toString().equalsIgnoreCase(recoverPartList.get(j).getNumber())) {
                    List<PartLotView> iqcViewList = partItemViewList.get(i).iqcViewList;
                    for (int k = 0; k < iqcViewList.size(); k++) {
                        if (recoverPartList.get(j).getIQC().size() > k) {
                            setTextIfCan(iqcViewList.get(k).partLotNoEditText, recoverPartList.get(j).getIQC().get(k).getNumber());
                            setTextIfCan(iqcViewList.get(k).partLotNoQuantityEditText, String.valueOf(recoverPartList.get(j).getIQC().get(k).getQuantity()));
                        }
                    }
                }
            }
        }
    }

    private void setTextIfCan(EditText editText, String text) {
        try {
            editText.setText(text);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public interface OnGetFormDataListener {
        void onOK(List<Part> partList, List<LotNo> wipLotList);
    }

    public interface OnQRCodeRead {
        void onRead(EditText editText);
    }

    private static class PartView {
        TextView partHeaderTextView;
        LinearLayout partListLayout;
        List<PartLotView> iqcViewList;

        PartView(View v) {
            partHeaderTextView = (TextView) v.findViewById(R.id.partNoTextView);
            partListLayout = (LinearLayout) v.findViewById(R.id.icqListLayout);
        }

        void setIqcViewList(List<PartLotView> iqcViewList) {
            this.iqcViewList = iqcViewList;
        }

        void setEachIQCScanButtonListener(OnQRCodeRead listener) {
            if (iqcViewList != null && iqcViewList.size() > 0) {
                for (int i = 0; i < iqcViewList.size(); i++) {
                    iqcViewList.get(i).setScanButtonListener(listener);
                }
            }
        }
    }

    private static class PartLotView {
        TextView partLotNoText;
        EditText partLotNoEditText;
        EditText partLotNoQuantityEditText;
        LinearLayout scanLayout;

        PartLotView(View v) {
            partLotNoText = (TextView) v.findViewById(R.id.lotNoTextView);
            partLotNoEditText = (EditText) v.findViewById(R.id.partLotEditText);
            partLotNoQuantityEditText = (EditText) v.findViewById(R.id.partLotQuantityEditText);
            scanLayout = (LinearLayout) v.findViewById(R.id.scanLayout);
        }

        protected void setScanButtonListener(final OnQRCodeRead listener) {
            scanLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRead(partLotNoEditText);
                }
            });
        }
    }

}