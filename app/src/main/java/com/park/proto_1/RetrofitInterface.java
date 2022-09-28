package com.park.proto_1;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET("/AbdmAnimalProtect?KEY=25af84057280489d90e4a25dd9138611&Type=json&pIndex=1&pSize=10&SIGUN_CD=41590&SPECIES_NM=[개]")
    Call<ApiDTO> getAllData();
}
