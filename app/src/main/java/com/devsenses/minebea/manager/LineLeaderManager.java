package com.devsenses.minebea.manager;

import android.content.Context;

import com.devsenses.minebea.dialog.DialogLineLeaderSelect;
import com.devsenses.minebea.dialog.DialogWithText;
import com.devsenses.minebea.listener.OnBaseApi;
import com.devsenses.minebea.listener.OnBaseApiFailure;
import com.devsenses.minebea.model.lineleadermodel.LineLeader;
import com.devsenses.minebea.model.partmodel.LotDataModel;
import com.devsenses.minebea.task.TaskLineLeader;

import java.util.List;

/**
 * Created by pong.p on 12/16/2015.
 */
public class LineLeaderManager{

    private Context context;
    private LotDataModel lotDataModel;
    private OnSendLineLeaderCallback listener;
    private List<LineLeader> lineLeaderList;
    private String qrCode;
    private DialogLineLeaderSelect dialog;

    public LineLeaderManager(Context context, LotDataModel lotDataModel){
        this.context = context;
        this.lotDataModel = lotDataModel;
    }

    public void startFillLineLeaderInfo(String qrCode,OnSendLineLeaderCallback listener){
        this.qrCode = qrCode;
        this.listener = listener;
        loadLineLeader(qrCode);
    }

    private void loadLineLeader(final String qrCode){
        TaskLineLeader.getLineLeader(context, qrCode, new OnLoadLineLeaderListener() {
            @Override
            public void onSuccess(List<LineLeader> leaderList) {
                lineLeaderList = leaderList;
                showLineLeaderDialog(lineLeaderList);
            }

            @Override
            public void onFailure(String reason) {
                DialogWithText.showMessage(context, reason);
            }
        });
    }

    private void showLineLeaderDialog(List<LineLeader> lineLeaderList){
        if(dialog == null) {
            dialog = new DialogLineLeaderSelect(context, lineLeaderList, lotDataModel, new DialogLineLeaderSelect.OnDialogLineLeaderListener() {
                @Override
                public void onOk(long lineLeaderID, String firstSerial, String lotNo, Long lotId) {
                    sendLineLeaderAndFirstSerialData(lineLeaderID, firstSerial, lotNo, lotId);
                }
            });
        }
        dialog.show();
    }

    private void sendLineLeaderAndFirstSerialData(long lineLeader, String firstSerialNo, String lotNo, Long lotId) {
        TaskLineLeader.sendLineLeader(context,qrCode,lineLeader,firstSerialNo,lotNo,lotId,new OnBaseApi(){
            @Override
            public void onSuccess() {
                 listener.onFinished(dialog.getLotNoByID());
            }

            @Override
            public void onFailure(String reason) {
                if(dialog!=null) {
                    dialog.show();
                }
                DialogWithText.showMessage(context,reason);
            }
        });
    }

    public interface OnLoadLineLeaderListener extends OnBaseApiFailure{
        void onSuccess(List<LineLeader> lineLeaderList);
    }

    public interface OnSendLineLeaderCallback {
        void onFinished(String lotNo);
    }
}
