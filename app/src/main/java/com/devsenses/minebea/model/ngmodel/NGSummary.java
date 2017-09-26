package com.devsenses.minebea.model.ngmodel;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by USER on 9/9/2560.
 */

@Parcel(Parcel.Serialization.BEAN)
public class NGSummary {
    private static final String FIELD_NG = "ng";
    private static final String FIELD_NG_1 = "ng1";
    private static final String FIELD_NG_2 = "ng2";

    @SerializedName(FIELD_NG)
    private NG ng;
    @SerializedName(FIELD_NG_1)
    private String ng1;
    @SerializedName(FIELD_NG_2)
    private String ng2;

    public NG getNg() {
        return ng;
    }

    public void setNg(NG ng) {
        this.ng = ng;
    }

    public String getNg1() {
        return ng1 != null ? ng1 : "";
    }

    public void setNg1(String ng1) {
        this.ng1 = ng1;
    }

    public String getNg2() {
        return ng2 != null ? ng2 : "";
    }

    public void setNg2(String ng2) {
        this.ng2 = ng2;
    }

    @Override
    public String toString() {
        return "NGSummary{" +
                "ng=" + ng.getTitle() +
                ", ng2='" + ng2 + '\'' +
                '}';
    }
}
