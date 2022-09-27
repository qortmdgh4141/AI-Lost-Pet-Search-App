package com.park.proto_1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Row {

    @SerializedName("SIGUN_CD")
    @Expose
    private String sigunCd;
    @SerializedName("SIGUN_NM")
    @Expose
    private String sigunNm;
    @SerializedName("ABDM_IDNTFY_NO")
    @Expose
    private String abdmIdntfyNo;
    @SerializedName("THUMB_IMAGE_COURS")
    @Expose
    private String thumbImageCours;
    @SerializedName("RECEPT_DE")
    @Expose
    private String receptDe;
    @SerializedName("DISCVRY_PLC_INFO")
    @Expose
    private String discvryPlcInfo;
    @SerializedName("SPECIES_NM")
    @Expose
    private String speciesNm;
    @SerializedName("COLOR_NM")
    @Expose
    private String colorNm;
    @SerializedName("AGE_INFO")
    @Expose
    private String ageInfo;
    @SerializedName("BDWGH_INFO")
    @Expose
    private String bdwghInfo;
    @SerializedName("PBLANC_IDNTFY_NO")
    @Expose
    private String pblancIdntfyNo;
    @SerializedName("PBLANC_BEGIN_DE")
    @Expose
    private String pblancBeginDe;
    @SerializedName("PBLANC_END_DE")
    @Expose
    private String pblancEndDe;
    @SerializedName("IMAGE_COURS")
    @Expose
    private String imageCours;
    @SerializedName("STATE_NM")
    @Expose
    private String stateNm;
    @SerializedName("SEX_NM")
    @Expose
    private String sexNm;
    @SerializedName("NEUT_YN")
    @Expose
    private String neutYn;
    @SerializedName("SFETR_INFO")
    @Expose
    private String sfetrInfo;
    @SerializedName("SHTER_NM")
    @Expose
    private String shterNm;
    @SerializedName("SHTER_TELNO")
    @Expose
    private String shterTelno;
    @SerializedName("PROTECT_PLC")
    @Expose
    private String protectPlc;
    @SerializedName("JURISD_INST_NM")
    @Expose
    private String jurisdInstNm;
    @SerializedName("CHRGPSN_NM")
    @Expose
    private Object chrgpsnNm;
    @SerializedName("CHRGPSN_CONTCT_NO")
    @Expose
    private Object chrgpsnContctNo;
    @SerializedName("PARTCLR_MATR")
    @Expose
    private Object partclrMatr;
    @SerializedName("REFINE_LOTNO_ADDR")
    @Expose
    private Object refineLotnoAddr;
    @SerializedName("REFINE_ROADNM_ADDR")
    @Expose
    private Object refineRoadnmAddr;
    @SerializedName("REFINE_ZIP_CD")
    @Expose
    private Object refineZipCd;
    @SerializedName("REFINE_WGS84_LOGT")
    @Expose
    private Object refineWgs84Logt;
    @SerializedName("REFINE_WGS84_LAT")
    @Expose
    private Object refineWgs84Lat;

    public String getSigunCd() {
        return sigunCd;
    }

    public void setSigunCd(String sigunCd) {
        this.sigunCd = sigunCd;
    }

    public String getSigunNm() {
        return sigunNm;
    }

    public void setSigunNm(String sigunNm) {
        this.sigunNm = sigunNm;
    }

    public String getAbdmIdntfyNo() {
        return abdmIdntfyNo;
    }

    public void setAbdmIdntfyNo(String abdmIdntfyNo) {
        this.abdmIdntfyNo = abdmIdntfyNo;
    }

    public String getThumbImageCours() {
        return thumbImageCours;
    }

    public void setThumbImageCours(String thumbImageCours) {
        this.thumbImageCours = thumbImageCours;
    }

    public String getReceptDe() {
        return receptDe;
    }

    public void setReceptDe(String receptDe) {
        this.receptDe = receptDe;
    }

    public String getDiscvryPlcInfo() {
        return discvryPlcInfo;
    }

    public void setDiscvryPlcInfo(String discvryPlcInfo) {
        this.discvryPlcInfo = discvryPlcInfo;
    }

    public String getSpeciesNm() {
        return speciesNm;
    }

    public void setSpeciesNm(String speciesNm) {
        this.speciesNm = speciesNm;
    }

    public String getColorNm() {
        return colorNm;
    }

    public void setColorNm(String colorNm) {
        this.colorNm = colorNm;
    }

    public String getAgeInfo() {
        return ageInfo;
    }

    public void setAgeInfo(String ageInfo) {
        this.ageInfo = ageInfo;
    }

    public String getBdwghInfo() {
        return bdwghInfo;
    }

    public void setBdwghInfo(String bdwghInfo) {
        this.bdwghInfo = bdwghInfo;
    }

    public String getPblancIdntfyNo() {
        return pblancIdntfyNo;
    }

    public void setPblancIdntfyNo(String pblancIdntfyNo) {
        this.pblancIdntfyNo = pblancIdntfyNo;
    }

    public String getPblancBeginDe() {
        return pblancBeginDe;
    }

    public void setPblancBeginDe(String pblancBeginDe) {
        this.pblancBeginDe = pblancBeginDe;
    }

    public String getPblancEndDe() {
        return pblancEndDe;
    }

    public void setPblancEndDe(String pblancEndDe) {
        this.pblancEndDe = pblancEndDe;
    }

    public String getImageCours() {
        return imageCours;
    }

    public void setImageCours(String imageCours) {
        this.imageCours = imageCours;
    }

    public String getStateNm() {
        return stateNm;
    }

    public void setStateNm(String stateNm) {
        this.stateNm = stateNm;
    }

    public String getSexNm() {
        return sexNm;
    }

    public void setSexNm(String sexNm) {
        this.sexNm = sexNm;
    }

    public String getNeutYn() {
        return neutYn;
    }

    public void setNeutYn(String neutYn) {
        this.neutYn = neutYn;
    }

    public String getSfetrInfo() {
        return sfetrInfo;
    }

    public void setSfetrInfo(String sfetrInfo) {
        this.sfetrInfo = sfetrInfo;
    }

    public String getShterNm() {
        return shterNm;
    }

    public void setShterNm(String shterNm) {
        this.shterNm = shterNm;
    }

    public String getShterTelno() {
        return shterTelno;
    }

    public void setShterTelno(String shterTelno) {
        this.shterTelno = shterTelno;
    }

    public String getProtectPlc() {
        return protectPlc;
    }

    public void setProtectPlc(String protectPlc) {
        this.protectPlc = protectPlc;
    }

    public String getJurisdInstNm() {
        return jurisdInstNm;
    }

    public void setJurisdInstNm(String jurisdInstNm) {
        this.jurisdInstNm = jurisdInstNm;
    }

    public Object getChrgpsnNm() {
        return chrgpsnNm;
    }

    public void setChrgpsnNm(Object chrgpsnNm) {
        this.chrgpsnNm = chrgpsnNm;
    }

    public Object getChrgpsnContctNo() {
        return chrgpsnContctNo;
    }

    public void setChrgpsnContctNo(Object chrgpsnContctNo) {
        this.chrgpsnContctNo = chrgpsnContctNo;
    }

    public Object getPartclrMatr() {
        return partclrMatr;
    }

    public void setPartclrMatr(Object partclrMatr) {
        this.partclrMatr = partclrMatr;
    }

    public Object getRefineLotnoAddr() {
        return refineLotnoAddr;
    }

    public void setRefineLotnoAddr(Object refineLotnoAddr) {
        this.refineLotnoAddr = refineLotnoAddr;
    }

    public Object getRefineRoadnmAddr() {
        return refineRoadnmAddr;
    }

    public void setRefineRoadnmAddr(Object refineRoadnmAddr) {
        this.refineRoadnmAddr = refineRoadnmAddr;
    }

    public Object getRefineZipCd() {
        return refineZipCd;
    }

    public void setRefineZipCd(Object refineZipCd) {
        this.refineZipCd = refineZipCd;
    }

    public Object getRefineWgs84Logt() {
        return refineWgs84Logt;
    }

    public void setRefineWgs84Logt(Object refineWgs84Logt) {
        this.refineWgs84Logt = refineWgs84Logt;
    }

    public Object getRefineWgs84Lat() {
        return refineWgs84Lat;
    }

    public void setRefineWgs84Lat(Object refineWgs84Lat) {
        this.refineWgs84Lat = refineWgs84Lat;
    }

}