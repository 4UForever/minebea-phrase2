package com.devsenses.minebea.model.ngmodel;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by User on 8/9/2560.
 */

@Parcel(Parcel.Serialization.BEAN)
public class NGDetail {
    private static final String FIELD_NG = "ng";
    private static final String FIELD_QUANTITY = "quantity";

    @SerializedName(FIELD_NG)
    private NG ng;
    @SerializedName(FIELD_QUANTITY)
    private String quantity;

    public NG getNg() {
        return ng;
    }

    public void setNg(NG ng) {
        this.ng = ng;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "NGDetail{" +
                "ng=" + ng +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
