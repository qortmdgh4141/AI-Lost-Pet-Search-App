package com.park.proto_1;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("/AbdmAnimalProtect?KEY=25af84057280489d90e4a25dd9138611&Type=json&pIndex=1&pSize=10&SIGUN_CD=41590&SPECIES_NM=[개]")
    Call<List<AbdmAnimalProtect>> getAllData();
}
