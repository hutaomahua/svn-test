package com.lyht.util.tree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: xzp
 * @Date: 2020/8/4 15:14
 **/
public class AddressTreeUtils {

    /*
     * @Description: 获取征地范围树形结构
     * @Author: xzp
     * @Date: 2020/8/4 10:36
     **/
    public List<Map> addressCommonList;
    public List<Map> addressList = new ArrayList<>();

    public List<Map> getAddressList(List<Map> menu) {
        this.addressCommonList = menu;
        Map<String, Object> mapArr;
        for (Map m : menu) {
            mapArr = new LinkedHashMap<>();
            if (String.valueOf(m.get("level")).equals("1")) {
                mapArr.put("code", m.get("code"));
                mapArr.put("pCode", m.get("pCode"));
                mapArr.put("name", m.get("name"));
                List<Map> listMap = getAddressChildrenList((String) m.get("code"));
                if(null != listMap && !listMap.isEmpty() && listMap.size() > 0){
                    mapArr.put("children", listMap);
                }
                addressList.add(mapArr);
            }
        }
        return addressList;
    }

    public List<Map> getAddressChildrenList(String code) {
        List<Map> lists = new ArrayList<>();
        Map<String, Object> mapArr;
        for (Map m : addressCommonList) {
            mapArr = new LinkedHashMap<>();
            if (code.equals(m.get("pCode"))) {
                mapArr.put("code", m.get("code"));
                mapArr.put("pCode", m.get("pCode"));
                mapArr.put("name", m.get("name"));
                List<Map> listMap = getAddressChildrenList((String) m.get("code"));
                if(null != listMap && !listMap.isEmpty() && listMap.size() > 0){
                    mapArr.put("children", listMap);
                }
                lists.add(mapArr);
            }
        }
        return lists;
    }

    /*
     * @Description: 获取行政区树形结构
     * @Author: xzp
     * @Date: 2020/8/4 10:36
     **/
    public List<Map> regionCommonList;
    public List<Map> regionList = new ArrayList<>();
    BigDecimal amount;

    static List<Map> childStatistics = new ArrayList<>();

    public static List<Map> treeCashList(List<Map> menuList, String pCode){
        for(Map mu: menuList){
            if(pCode.equals(mu.get("pCode").toString())){
                treeCashList(menuList, mu.get("code").toString());
                childStatistics.add(mu);
            }
        }
        return childStatistics;
    }

    public List<Map> getRegionList(List<Map> menu) {
        this.regionCommonList = menu;
        Map<String, Object> mapArr;
        for (Map m : menu) {
            mapArr = new LinkedHashMap<>();
            if (String.valueOf(m.get("level")).equals("1")) {
                mapArr.put("code", m.get("code"));
                mapArr.put("pCode", m.get("pCode"));
                mapArr.put("name", m.get("name"));
                amount = BigDecimal.ZERO;
                List<Map> listMap = getRegionChildrenList((String) m.get("code"));
                if(null != listMap && !listMap.isEmpty() && listMap.size() > 0){
                    mapArr.put("children", listMap);
                }
                BigDecimal amountNew = amount.add((BigDecimal) m.get("amount"));
                if(amountNew.compareTo(BigDecimal.ZERO) == 1){
                    regionList.add(mapArr);
                }
            }
        }
        return regionList;
    }

    public List<Map> getRegionChildrenList(String code) {
        List<Map> lists = new ArrayList<>();
        Map<String, Object> mapArr;
        for (Map m : regionCommonList) {
            mapArr = new LinkedHashMap<>();
            if (code.equals(m.get("pCode"))) {
                mapArr.put("code", m.get("code"));
                mapArr.put("pCode", m.get("pCode"));
                mapArr.put("name", m.get("name"));
                childStatistics = new ArrayList<>();
                List<Map> list = treeCashList(regionCommonList, m.get("code").toString());
                BigDecimal amountChild = (BigDecimal) m.get("amount");
                for(Map s : list){
                    amountChild = amountChild.add((BigDecimal) s.get("amount"));
                }
                amount = amount.add((BigDecimal) m.get("amount"));
                List<Map> listMap = getRegionChildrenList((String) m.get("code"));
                if(null != listMap && !listMap.isEmpty() && listMap.size() > 0){
                    mapArr.put("children", listMap);
                }
                if(amountChild.compareTo(BigDecimal.ZERO) == 1){
                    lists.add(mapArr);
                }
            }
        }
        return lists;
    }

}
