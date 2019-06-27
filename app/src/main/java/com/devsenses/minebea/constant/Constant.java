package com.devsenses.minebea.constant;

import android.os.Environment;

/**
 * Created by Horus on 2/10/2015.
 */
public class Constant {

    //    public static final String baseUrl = "http://dev-minebea.devsenses.net/";
//  public static final String baseUrl = "http://staging-minebea.devsenses.net/";
    public static final String baseUrl = "http://192.168.1.95/Minebea2017/"; //Real

    public static final String urlLogin = baseUrl + "/api/user/login";
    public static final String urlProcessStatus = baseUrl + "/api/process/check-status";
    public static final String urlStartProcess = baseUrl + "/api/activity/process/start";
    public static final String urlStopProcess = baseUrl + "/api/activity/process/end";


    public static final String urlGetDocument = baseUrl + "/api/document?qr_code=%s&process_id=%s&product_id=%s";
    public static final String urlGetDocument_PI = baseUrl + "/api/document/pi/download?qr_code=%s";
    public static final String urlGetDocument_PI_PR = baseUrl + "/api/document/pi-pr/download?qr_code=%s";
    public static final String urlGetDocument_RE = baseUrl + "/api/document/re/download?qr_code=%s";
    public static final String urlGetDocument_PI_SET = baseUrl + "/api/document/pi-set/download?qr_code=%s";

    public static final String getUrlGetDocumentList = baseUrl + "/api/document-category?qr_code=%s&line_id=%s&product_id=%s&process_id=%s";

    public static final String pathExternalStorage = Environment.getExternalStorageDirectory() + "";
    public static final String pathMinebea = "/Minebea";

}
