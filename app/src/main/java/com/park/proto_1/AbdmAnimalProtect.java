package com.park.proto_1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AbdmAnimalProtect {

    @SerializedName("head")

    public List<Head> head;

    @SerializedName("row")

    public List<Row> row;

    public List<Head> getHead() {
        return head;
    }

    public void setHead(List<Head> head) {
        this.head = head;
    }

    public List<Row> getRow() {
        return row;
    }

    public void setRow(List<Row> row) {
        this.row = row;
    }



}

