package cn.appoa.afui.bean;

import java.io.Serializable;
import java.util.List;

public class BMapCityList implements Serializable {

    public String area_code;
    public String area_name;
    public int area_type;
    public String geo;
    public List<BMapDistrictList> sub;
    public boolean isSelected;

    public BMapCityList() {
        super();
    }

    public BMapCityList(String area_code, String area_name) {
        this.area_code = area_code;
        this.area_name = area_name;
    }
}
