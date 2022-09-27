package com.park.proto_1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RealItem {


    @SerializedName("row")
    @Expose
    private List<Row> row;

    @Override
    public String toString(){
        return "RealItem{" + "row=" + row + "}";
    }


}
