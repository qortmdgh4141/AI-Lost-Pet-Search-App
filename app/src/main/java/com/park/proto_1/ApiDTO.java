package com.park.proto_1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiDTO {

    @SerializedName("AbdmAnimalProtect")
    @Expose
    private List<AbdmAnimalProtect> abdmAnimalProtect = null;

    public List<AbdmAnimalProtect> getAbdmAnimalProtect() {
        return abdmAnimalProtect;
    }

    public void setAbdmAnimalProtect(List<AbdmAnimalProtect> abdmAnimalProtect) {
        this.abdmAnimalProtect = abdmAnimalProtect;
    }

}
