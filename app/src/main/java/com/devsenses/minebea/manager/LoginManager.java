package com.devsenses.minebea.manager;

import android.content.Context;

import com.devsenses.minebea.dialog.DialogLoginFailed;
import com.devsenses.minebea.listener.OnBaseApiFailure;
import com.devsenses.minebea.model.loginmodel.LoginModel;
import com.devsenses.minebea.model.loginmodel.Model;
import com.devsenses.minebea.task.Task;
import com.devsenses.minebea.task.TaskLogin;

import java.util.List;

/**
 * Created by pong.p on 12/11/2015.
 */
public class LoginManager {
    public static void loginWithQRCode(final Context context,String qrCode,final LoginListener listener){
        TaskLogin.asyncLogin(context, qrCode, new LoginListener() {
            @Override
            public void onLoginSuccess(LoginModel modelReturn) {
                List<Model> modelList = modelReturn.getDatum().getModels();
                if (modelList != null && modelList.size() > 0) {
                    listener.onLoginSuccess(modelReturn);
                } else {
                    showLoginFailDialog(context, DialogLoginFailed.TYPE_LOGIN_FAILED.NO_DATA);
                }
            }

            @Override
            public void onFailure(String reason) {
                listener.onFailure(reason);
            }
        });
    }

    private static void showLoginFailDialog(Context context,DialogLoginFailed.TYPE_LOGIN_FAILED type) {
        new DialogLoginFailed(context, type).show();
    }

    public interface LoginListener extends OnBaseApiFailure {
        void onLoginSuccess(LoginModel modelReturn);
    }

}