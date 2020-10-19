package cn.appoa.afui.bean;

import java.io.Serializable;

public class BMapDistrictList implements Serializable {

    public String area_code;
    public String area_name;
    public int area_type;
    public String geo;
    public int sup_business_area;
    public boolean isSelected;

    public BMapDistrictList() {
    }

    public BMapDistrictList(String area_code, String area_name) {
        this.area_code = area_code;
        this.area_name = area_name;
    }
}
