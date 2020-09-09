package com.lyht.business.tab.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.util.DateUtil;
import com.lyht.util.Randomizer;

import io.swagger.annotations.Api;

@Api(value = "/excel/data", tags = "excel数据处理相关api")
@RestController
@RequestMapping("/excel/data")
public class ConfigDataController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("/family")
	public void family() {
		List<Map<String, Object>> all = jdbcTemplate.queryForList("SELECT * FROM t_info_family;");
		String ownerNm = null;
		for (Map<String, Object> map : all) {
			String owner_nm = (String) map.get("owner_nm");
			String id_card = (String) map.get("id_card");
			//E2A29C1823
			String master_relationship = (String) map.get("master_relationship");
			if (StringUtils.equals(master_relationship, "E2A29C1823")) {//户主
				if (StringUtils.isNotBlank(owner_nm) && StringUtils.isNotBlank(id_card)) {
					List<Map<String, Object>> queryForList = jdbcTemplate.queryForList("SELECT * FROM t_info_owner WHERE name='" + owner_nm + "' AND id_card='" + id_card + "';");
					if (queryForList != null && !queryForList.isEmpty()) {
						Map<String, Object> map2 = queryForList.get(0);
						ownerNm = (String) map2.get("nm");
					} else {
						ownerNm = null;
					}
				} else {
					ownerNm = null;
				}
			}
			//赋值
			Integer id = (Integer) map.get("id");
			if (StringUtils.isNotBlank(ownerNm)) {
				jdbcTemplate.execute("UPDATE t_info_family tt SET owner_nm='" + ownerNm + "' WHERE tt.id = " + id);
			} 
		}
	}

	@GetMapping("/zhibiao")
	public void zhibiao() {
		List<Map<String, Object>> all = jdbcTemplate.queryForList("SELECT * FROM t_test_data;");
		int count = 0;
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0 ; i < all.size() ; i ++) {
			count ++;
			list.add(all.get(i));
			if(count >= 500) {
				MyThread thr = new MyThread(list, jdbcTemplate);
				Thread thread = new Thread(thr);
				thread.start();
				
				count = 0;
				list = new ArrayList<>();
			}
		}
		if(list.size() > 0) {
			MyThread thr = new MyThread(list, jdbcTemplate);
			Thread thread = new Thread(thr);
			thread.start();
		}
	}

}

class MyThread implements Runnable{

	JdbcTemplate jdbcTemplate;
	List<Map<String, Object>> list;
	
	public MyThread(List<Map<String, Object>> list,JdbcTemplate jdbcTemplate) {
		this.list = list;
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void run() {
		int count = 0;
		System.out.println("===开始====================线程：" + Thread.currentThread().getName());
		for (Map<String, Object> map : list) {
			count ++;
			System.out.println("===线程：" + Thread.currentThread().getName() + "====数量：" + count);
			// 权属人内码
			String nm = (String) map.get("nm");
			// 行政区域
			String a1 = (String) map.get("a1");
			String a2 = (String) map.get("a2");
			String a4 = (String) map.get("a4");
			String a5 = (String) map.get("a5");
			String region = "";
			region = a1 + a2 + a4 + a5;
			// 乡、镇
			String a3 = (String) map.get("a3");
			String zblx = a3;
			// 征地范围
			String a6 = (String) map.get("a6");
			String scope = a6;
			// 企事业单位类型
			String a8 = (String) map.get("a8");
			if (StringUtils.isNotBlank(a8)) {
				continue;
			}
			// 权属性质
			String a9 = (String) map.get("a9");
			String ownerNature = a9;

//			// 房屋--正房-包括专业厂房--框架
//			String a15 = (String) map.get("a15");
//			if (checkValue(a15)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a15));
//				insert("t_info_houses", strBuffer.toString(), "正房,框架", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--正房-包括专业厂房--砖混
//			String a16 = (String) map.get("a16");
//			if (checkValue(a16)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a16));
//				insert("t_info_houses", strBuffer.toString(), "正房,砖混", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--正房-包括专业厂房--砖(石)木
//			String a17 = (String) map.get("a17");
//			if (checkValue(a17)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a17));
//				insert("t_info_houses", strBuffer.toString(), "正房,砖(石)木", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--正房-包括专业厂房--砖土木
//			String a18 = (String) map.get("a18");
//			if (checkValue(a18)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a18));
//				insert("t_info_houses", strBuffer.toString(), "正房,砖土木", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--正房-包括专业厂房--土木
//			String a19 = (String) map.get("a19");
//			if (checkValue(a19)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a19));
//				insert("t_info_houses", strBuffer.toString(), "正房,土木", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--正房-包括专业厂房--木(竹)
//			String a20 = (String) map.get("a20");
//			if (checkValue(a20)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a20));
//				insert("t_info_houses", strBuffer.toString(), "正房,木(竹)", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--杂房--砖混
//			String a21 = (String) map.get("a21");
//			if (checkValue(a21)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a21));
//				insert("t_info_houses", strBuffer.toString(), "杂房,砖混", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--杂房--砖(石)木
//			String a22 = (String) map.get("a22");
//			if (checkValue(a22)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a22));
//				insert("t_info_houses", strBuffer.toString(), "杂房,砖(石)木", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--杂房--砖土木
//			String a23 = (String) map.get("a23");
//			if (checkValue(a23)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a23));
//				insert("t_info_houses", strBuffer.toString(), "杂房,砖土木", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--杂房--土木
//			String a24 = (String) map.get("a24");
//			if (checkValue(a24)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a24));
//				insert("t_info_houses", strBuffer.toString(), "杂房,土木", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--杂房--木(竹)
//			String a25 = (String) map.get("a25");
//			if (checkValue(a25)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a25));
//				insert("t_info_houses", strBuffer.toString(), "杂房,木(竹)", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--生产辅助用房--砖土木
//			String a26 = (String) map.get("a26");
//			if (checkValue(a26)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a26));
//				insert("t_info_houses", strBuffer.toString(), "生产辅助用房,砖土木", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--生产辅助用房--砖(石)木
//			String a27 = (String) map.get("a27");
//			if (checkValue(a27)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a27));
//				insert("t_info_houses", strBuffer.toString(), "生产辅助用房,砖(石)木", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--生产辅助用房--土木
//			String a28 = (String) map.get("a28");
//			if (checkValue(a28)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a28));
//				insert("t_info_houses", strBuffer.toString(), "生产辅助用房,土木", region, nm, zblx, scope, ownerNature);
//			}
//			// 房屋--生产辅助用房--木(竹)
//			String a29 = (String) map.get("a29");
//			if (checkValue(a29)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getAreaSql(a29));
//				insert("t_info_houses", strBuffer.toString(), "生产辅助用房,木(竹)", region, nm, zblx, scope, ownerNature);
//			}

//			// 土地--宅基地(㎡)
//			String a30 = (String) map.get("a30");
//			if (checkValue(a30)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a30));
//				insert("t_info_land", strBuffer.toString(), "宅基地", region, nm, zblx, scope, ownerNature);
//			}
//
//			// 附属建筑物--田间挡护体(m³)--混凝土
//			String a31 = (String) map.get("a31");
//			if (checkValue(a31)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m³"));
//				strBuffer.append(getVolumeSql(a31));
//				insert("t_info_building", strBuffer.toString(), "田间挡护体,混凝土", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--田间挡护体(m³)--浆砌石
//			String a32 = (String) map.get("a32");
//			if (checkValue(a32)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m³"));
//				strBuffer.append(getVolumeSql(a32));
//				insert("t_info_building", strBuffer.toString(), "田间挡护体,浆砌石", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--田间挡护体(m³)--干砌石
//			String a33 = (String) map.get("a33");
//			if (checkValue(a33)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m³"));
//				strBuffer.append(getVolumeSql(a33));
//				insert("t_info_building", strBuffer.toString(), "田间挡护体,干砌石", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--院落挡护体(m³)--混凝土
//			String a34 = (String) map.get("a34");
//			if (checkValue(a34)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m³"));
//				strBuffer.append(getVolumeSql(a34));
//				insert("t_info_building", strBuffer.toString(), "院落挡护体,混凝土", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--院落挡护体(m³)--浆砌石
//			String a35 = (String) map.get("a35");
//			if (checkValue(a35)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m³"));
//				strBuffer.append(getVolumeSql(a35));
//				insert("t_info_building", strBuffer.toString(), "院落挡护体,浆砌石", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--院落挡护体(m³)--干砌石
//			String a36 = (String) map.get("a36");
//			if (checkValue(a36)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m³"));
//				strBuffer.append(getVolumeSql(a36));
//				insert("t_info_building", strBuffer.toString(), "院落挡护体,干砌石", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--台步(㎡)
//			String a37 = (String) map.get("a37");
//			if (checkValue(a37)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a37));
//				insert("t_info_building", strBuffer.toString(), "台步", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--晒坪(㎡)--水泥
//			String a38 = (String) map.get("a38");
//			if (checkValue(a38)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a38));
//				insert("t_info_building", strBuffer.toString(), "晒坪,水泥", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--晒坪(㎡)--素土
//			String a39 = (String) map.get("a39");
//			if (checkValue(a39)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a39));
//				insert("t_info_building", strBuffer.toString(), "晒坪,素土", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--室外楼梯(㎡)--铁制楼梯
//			String a40 = (String) map.get("a40");
//			if (checkValue(a40)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a40));
//				insert("t_info_building", strBuffer.toString(), "室外楼梯,铁制楼梯", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--室外楼梯(㎡)--木质楼梯
//			String a41 = (String) map.get("a41");
//			if (checkValue(a41)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a41));
//				insert("t_info_building", strBuffer.toString(), "室外楼梯,木质楼梯", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--门楼--砖混面积
//			String a42 = (String) map.get("a42");
//			// 附属建筑物--门楼--砖混个数
//			String a43 = (String) map.get("a43");
//			if (checkValue(a42) || checkValue(a42)) {
//				StringBuffer strBuffer = new StringBuffer();
//				if (checkValue(a42)) {
//					strBuffer.append(getAreaSql(a42));
//				}
//				if (checkValue(a43)) {
//					strBuffer.append(getNumSql(a43));
//				}
//				insert("t_info_building", strBuffer.toString(), "门楼,砖混", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--门楼--砖木面积
//			String a44 = (String) map.get("a44");
//			// 附属建筑物--门楼--砖木个数
//			String a45 = (String) map.get("a45");
//			if (checkValue(a44) || checkValue(a45)) {
//				StringBuffer strBuffer = new StringBuffer();
//				if (checkValue(a44)) {
//					strBuffer.append(getAreaSql(a44));
//				}
//				if (checkValue(a45)) {
//					strBuffer.append(getNumSql(a45));
//				}
//				insert("t_info_building", strBuffer.toString(), "门楼,砖木", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--门楼--土木面积
//			String a46 = (String) map.get("a46");
//			// 附属建筑物--门楼--土木个数
//			String a47 = (String) map.get("a47");
//			if (checkValue(a46) || checkValue(a47)) {
//				StringBuffer strBuffer = new StringBuffer();
//				if (checkValue(a46)) {
//					strBuffer.append(getAreaSql(a46));
//				}
//				if (checkValue(a47)) {
//					strBuffer.append(getNumSql(a47));
//				}
//				insert("t_info_building", strBuffer.toString(), "门楼,土木", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--大门(对)--金属
//			String a48 = (String) map.get("a48");
//			if (checkValue(a48)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("对"));
//				strBuffer.append(getNumSql(a48));
//				insert("t_info_building", strBuffer.toString(), "大门,金属", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--大门(对)--木质
//			String a49 = (String) map.get("a49");
//			if (checkValue(a49)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("对"));
//				strBuffer.append(getNumSql(a49));
//				insert("t_info_building", strBuffer.toString(), "大门,木质", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--围墙(㎡)--砖围墙
//			String a50 = (String) map.get("a50");
//			if (checkValue(a50)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a50));
//				insert("t_info_building", strBuffer.toString(), "砖围墙", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--围墙(㎡)--土围墙
//			String a51 = (String) map.get("a51");
//			if (checkValue(a51)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a51));
//				insert("t_info_building", strBuffer.toString(), "砖围墙", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--围墙(㎡)--砼围墙
//			String a52 = (String) map.get("a52");
//			if (checkValue(a52)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a52));
//				insert("t_info_building", strBuffer.toString(), "砼围墙", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--防护栏(㎡)--金属
//			String a53 = (String) map.get("a53");
//			if (checkValue(a53)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a53));
//				insert("t_info_building", strBuffer.toString(), "防护栏,金属", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--防护栏(㎡)--砌体(水泥)
//			String a54 = (String) map.get("a54");
//			if (checkValue(a54)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a54));
//				insert("t_info_building", strBuffer.toString(), "防护栏,砌体(水泥)", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--防护栏(㎡)--刺铁丝
//			String a55 = (String) map.get("a55");
//			if (checkValue(a55)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a55));
//				insert("t_info_building", strBuffer.toString(), "防护栏,刺铁丝", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--防护栏(㎡)--木栅栏
//			String a56 = (String) map.get("a56");
//			if (checkValue(a56)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a56));
//				insert("t_info_building", strBuffer.toString(), "防护栏,木栅栏", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--简易棚(㎡)--铁质
//			String a57 = (String) map.get("a57");
//			if (checkValue(a57)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a57));
//				insert("t_info_building", strBuffer.toString(), "简易棚,铁质", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--简易棚(㎡)--木质
//			String a58 = (String) map.get("a58");
//			if (checkValue(a58)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a58));
//				insert("t_info_building", strBuffer.toString(), "简易棚,木质", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--铁架棚(㎡)
//			String a59 = (String) map.get("a59");
//			if (checkValue(a59)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a59));
//				insert("t_info_building", strBuffer.toString(), "铁架棚", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--兰花房(㎡)
//			String a60 = (String) map.get("a60");
//			if (checkValue(a60)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a60));
//				insert("t_info_building", strBuffer.toString(), "兰花房", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--水池(m³)
//			String a61 = (String) map.get("a61");
//			if (checkValue(a61)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m³"));
//				strBuffer.append(getVolumeSql(a61));
//				insert("t_info_building", strBuffer.toString(), "水池", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--给排水管(m)--φ25钢管
//			String a62 = (String) map.get("a62");
//			if (checkValue(a62)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a62));
//				insert("t_info_building", strBuffer.toString(), "φ25钢管", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--给排水管(m)--φ32钢管
//			String a63 = (String) map.get("a63");
//			if (checkValue(a63)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a63));
//				insert("t_info_building", strBuffer.toString(), "φ32钢管", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--给排水管(m)--φ50钢管
//			String a64 = (String) map.get("a64");
//			if (checkValue(a64)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a64));
//				insert("t_info_building", strBuffer.toString(), "φ50钢管", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--给排水管(m)--φ80钢管
//			String a65 = (String) map.get("a65");
//			if (checkValue(a65)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a65));
//				insert("t_info_building", strBuffer.toString(), "φ80钢管", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--给排水管(m)--φ100钢管
//			String a66 = (String) map.get("a66");
//			if (checkValue(a66)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a66));
//				insert("t_info_building", strBuffer.toString(), "φ100钢管", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--给排水管(m)--φ120钢管
//			String a67 = (String) map.get("a67");
//			if (checkValue(a67)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a67));
//				insert("t_info_building", strBuffer.toString(), "φ120钢管", region, nm, a3, a6, a9);
//			}
//			// 附属建筑物--给排水管(m)--DN25胶(塑)管
//			String a68 = (String) map.get("a68");
//			if (checkValue(a68)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a68));
//				insert("t_info_building", strBuffer.toString(), "DN25胶(塑)管", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--给排水管(m)--DN32胶(塑)管
//			String a69 = (String) map.get("a69");
//			if (checkValue(a69)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a69));
//				insert("t_info_building", strBuffer.toString(), "DN32胶(塑)管", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--给排水管(m)--DN50胶(塑)管
//			String a70 = (String) map.get("a70");
//			if (checkValue(a70)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a70));
//				insert("t_info_building", strBuffer.toString(), "DN50胶(塑)管", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--给排水管(m)--DN80胶(塑)管
//			String a71 = (String) map.get("a71");
//			if (checkValue(a71)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a71));
//				insert("t_info_building", strBuffer.toString(), "DN80胶(塑)管", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--给排水管(m)--DN100胶(塑)管
//			String a72 = (String) map.get("a72");
//			if (checkValue(a72)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a72));
//				insert("t_info_building", strBuffer.toString(), "DN100胶(塑)管", region, nm, zblx, scope,
//						ownerNature);
//			}
//			// 附属建筑物--给排水管(m)--DN120胶(塑)管
//			String a73 = (String) map.get("a73");
//			if (checkValue(a73)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a73));
//				insert("t_info_building", strBuffer.toString(), "DN120胶(塑)管", region, nm, zblx, scope,
//						ownerNature);
//			}
//			// 附属建筑物--给排水管(m)--DN300胶管
//			String a74 = (String) map.get("a74");
//			if (checkValue(a74)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a74));
//				insert("t_info_building", strBuffer.toString(), "DN300胶管", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--给排水管(m)--排水(管)道
//			String a75 = (String) map.get("a75");
//			if (checkValue(a75)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a75));
//				insert("t_info_building", strBuffer.toString(), "排水(管)道", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--炉灶(眼)
//			String a76 = (String) map.get("a76");
//			if (checkValue(a76)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("眼"));
//				strBuffer.append(getNumSql(a76));
//				insert("t_info_building", strBuffer.toString(), "炉灶", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--沼气池(个)
//			String a77 = (String) map.get("a77");
//			if (checkValue(a77)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("个"));
//				strBuffer.append(getNumSql(a77));
//				insert("t_info_building", strBuffer.toString(), "沼气池", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--太阳能热水器(套)
//			String a78 = (String) map.get("a78");
//			if (checkValue(a78)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("套"));
//				strBuffer.append(getNumSql(a78));
//				insert("t_info_building", strBuffer.toString(), "太阳能热水器", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--卫星接收器(套)
//			String a79 = (String) map.get("a79");
//			if (checkValue(a79)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("套"));
//				strBuffer.append(getNumSql(a79));
//				insert("t_info_building", strBuffer.toString(), "卫星接收器", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--有线电视(套)
//			String a80 = (String) map.get("a80");
//			if (checkValue(a80)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("套"));
//				strBuffer.append(getNumSql(a80));
//				insert("t_info_building", strBuffer.toString(), "有线电视", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--固定电话(门)
//			String a81 = (String) map.get("a81");
//			if (checkValue(a81)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("门"));
//				strBuffer.append(getNumSql(a81));
//				insert("t_info_building", strBuffer.toString(), "固定电话", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--光纤网络(套)
//			String a82 = (String) map.get("a82");
//			if (checkValue(a82)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("套"));
//				strBuffer.append(getNumSql(a82));
//				insert("t_info_building", strBuffer.toString(), "光纤网络", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--花台(㎡)
//			String a83 = (String) map.get("a83");
//			if (checkValue(a83)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a83));
//				insert("t_info_building", strBuffer.toString(), "花台", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--固定休息台(㎡)
//			String a84 = (String) map.get("a84");
//			if (checkValue(a84)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a84));
//				insert("t_info_building", strBuffer.toString(), "固定休息台", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--固定操作台(㎡)
//			String a85 = (String) map.get("a85");
//			if (checkValue(a85)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a85));
//				insert("t_info_building", strBuffer.toString(), "固定操作台", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--固定洗漱台(㎡)
//			String a86 = (String) map.get("a86");
//			if (checkValue(a86)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a86));
//				insert("t_info_building", strBuffer.toString(), "固定洗漱台", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--固定橱架(个)
//			String a87 = (String) map.get("a87");
//			if (checkValue(a87)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("个"));
//				strBuffer.append(getNumSql(a87));
//				insert("t_info_building", strBuffer.toString(), "固定橱架", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--牲畜圈(㎡)
//			String a88 = (String) map.get("a88");
//			if (checkValue(a88)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a88));
//				insert("t_info_building", strBuffer.toString(), "牲畜圈", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--锅炉(个)
//			String a89 = (String) map.get("a89");
//			if (checkValue(a89)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("个"));
//				strBuffer.append(getNumSql(a89));
//				insert("t_info_building", strBuffer.toString(), "锅炉", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--垃圾坑(m³)
//			String a90 = (String) map.get("a90");
//			if (checkValue(a90)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m³"));
//				strBuffer.append(getVolumeSql(a90));
//				insert("t_info_building", strBuffer.toString(), "垃圾坑", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--粪池(厕坑)(个)
//			String a91 = (String) map.get("a91");
//			if (checkValue(a91)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("个"));
//				strBuffer.append(getNumSql(a91));
//				insert("t_info_building", strBuffer.toString(), "粪池(厕坑)", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--旗杆(个)
//			String a92 = (String) map.get("a92");
//			if (checkValue(a92)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("个"));
//				strBuffer.append(getNumSql(a92));
//				insert("t_info_building", strBuffer.toString(), "旗杆", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--固定篮球架(个)
//			String a93 = (String) map.get("a93");
//			if (checkValue(a93)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("个"));
//				strBuffer.append(getNumSql(a93));
//				insert("t_info_building", strBuffer.toString(), "固定篮球架", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--固定乒乓球台(个)
//			String a94 = (String) map.get("a94");
//			if (checkValue(a94)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("个"));
//				strBuffer.append(getNumSql(a94));
//				insert("t_info_building", strBuffer.toString(), "固定乒乓球台", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--固定宣传栏(㎡)
//			String a95 = (String) map.get("a95");
//			if (checkValue(a95)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a95));
//				insert("t_info_building", strBuffer.toString(), "固定宣传栏", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--空调(台)
//			String a96 = (String) map.get("a96");
//			if (checkValue(a96)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("台"));
//				strBuffer.append(getNumSql(a96));
//				insert("t_info_building", strBuffer.toString(), "空调", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--照壁(㎡)
//			String a97 = (String) map.get("a97");
//			if (checkValue(a97)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a97));
//				insert("t_info_building", strBuffer.toString(), "照壁", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--假山(m³)
//			String a98 = (String) map.get("a98");
//			if (checkValue(a98)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m³"));
//				strBuffer.append(getVolumeSql(a98));
//				insert("t_info_building", strBuffer.toString(), "假山", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--坟墓(冢)--单坟--包坟(单)
//			String a99 = (String) map.get("a99");
//			if (checkValue(a99)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("冢"));
//				strBuffer.append(getNumSql(a99));
//				insert("t_info_building", strBuffer.toString(), "坟墓,单坟", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--坟墓(冢)--单坟--碑坟(单)
//			String a100 = (String) map.get("a100");
//			if (checkValue(a100)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("冢"));
//				strBuffer.append(getNumSql(a100));
//				insert("t_info_building", strBuffer.toString(), "坟墓,单坟", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--坟墓(冢)--单坟--土坟(单)
//			String a101 = (String) map.get("a101");
//			if (checkValue(a101)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("冢"));
//				strBuffer.append(getNumSql(a101));
//				insert("t_info_building", strBuffer.toString(), "坟墓,单坟", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--坟墓(冢)--双坟--包坟(双)
//			String a102 = (String) map.get("a102");
//			if (checkValue(a102)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("冢"));
//				strBuffer.append(getNumSql(a102));
//				insert("t_info_building", strBuffer.toString(), "坟墓,双坟", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--坟墓(冢)--双坟--碑坟(双)
//			String a103 = (String) map.get("a103");
//			if (checkValue(a103)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("冢"));
//				strBuffer.append(getNumSql(a103));
//				insert("t_info_building", strBuffer.toString(), "坟墓,双坟", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--坟墓(冢)--双坟--土坟(双)
//			String a104 = (String) map.get("a104");
//			if (checkValue(a104)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("冢"));
//				strBuffer.append(getNumSql(a104));
//				insert("t_info_building", strBuffer.toString(), "坟墓,双坟", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--蜂桶(箱)(个)
//			String a105 = (String) map.get("a105");
//			if (checkValue(a105)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("个"));
//				strBuffer.append(getNumSql(a105));
//				insert("t_info_building", strBuffer.toString(), "蜂桶(箱)", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--冲碓(个)
//			String a106 = (String) map.get("a106");
//			if (checkValue(a106)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("个"));
//				strBuffer.append(getNumSql(a106));
//				insert("t_info_building", strBuffer.toString(), "冲碓", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--沟渠(m)
//			String a107 = (String) map.get("a107");
//			if (checkValue(a107)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a107));
//				insert("t_info_building", strBuffer.toString(), "沟渠", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--砖瓦窑(座)--可用窑
//			String a108 = (String) map.get("a108");
//			if (checkValue(a108)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("座"));
//				strBuffer.append(getNumSql(a108));
//				insert("t_info_building", strBuffer.toString(), "砖瓦窑,可用窑", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--砖瓦窑(座)--废弃窑
//			String a109 = (String) map.get("a109");
//			if (checkValue(a109)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("座"));
//				strBuffer.append(getNumSql(a109));
//				insert("t_info_building", strBuffer.toString(), "砖瓦窑,废弃窑", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--农村小型专项--机耕道(m)
//			String a110 = (String) map.get("a110");
//			if (checkValue(a110)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a110));
//				insert("t_info_building", strBuffer.toString(), "机耕道", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--农村小型专项--水塔(m³)
//			String a111 = (String) map.get("a111");
//			if (checkValue(a111)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m³"));
//				strBuffer.append(getVolumeSql(a111));
//				insert("t_info_building", strBuffer.toString(), "水塔", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--农村小型专项--水井(眼)
//			String a112 = (String) map.get("a112");
//			if (checkValue(a112)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("眼"));
//				strBuffer.append(getNumSql(a112));
//				insert("t_info_building", strBuffer.toString(), "水井", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--农村小型专项--变压器(kVA)
//			String a113 = (String) map.get("a113");
//			if (checkValue(a113)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("kVA"));
//				strBuffer.append(getNumSql(a113));
//				insert("t_info_building", strBuffer.toString(), "变压器(KVA)", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--农村小型专项--变压器(台)
//			String a114 = (String) map.get("a114");
//			if (checkValue(a114)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("台"));
//				strBuffer.append(getNumSql(a114));
//				insert("t_info_building", strBuffer.toString(), "变压器(台)", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--农村小型专项--0.4kV电力线路(m)
//			String a115 = (String) map.get("a115");
//			if (checkValue(a115)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m"));
//				strBuffer.append(getLongsSql(a115));
//				insert("t_info_building", strBuffer.toString(), "0.4kV电力线路", region, nm, zblx, scope,
//						ownerNature);
//			}
//			// 附属建筑物--农村小型专项--冷库(m³)
//			String a116 = (String) map.get("a116");
//			if (checkValue(a116)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("m³"));
//				strBuffer.append(getVolumeSql(a116));
//				insert("t_info_building", strBuffer.toString(), "冷库", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--农村小型专项--蔬菜大棚(㎡)
//			String a117 = (String) map.get("a117");
//			if (checkValue(a117)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("㎡"));
//				strBuffer.append(getAreaSql(a117));
//				insert("t_info_building", strBuffer.toString(), "蔬菜大棚", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--农村小型专项--监控系统(套)
//			String a118 = (String) map.get("a118");
//			if (checkValue(a118)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("套"));
//				strBuffer.append(getNumSql(a118));
//				insert("t_info_building", strBuffer.toString(), "监控系统", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--农村小型专项--微型水电（kW/套）--微型水电(kW)
//			String a119 = (String) map.get("a119");
//			if (checkValue(a119)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("kW"));
//				strBuffer.append(getNumSql(a119));
//				insert("t_info_building", strBuffer.toString(), "微型水电(KW)", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--农村小型专项--微型水电（kW/套）--微型水电(套)
//			String a120 = (String) map.get("a120");
//			if (checkValue(a120)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("套"));
//				strBuffer.append(getNumSql(a120));
//				insert("t_info_building", strBuffer.toString(), "微型水电(套)", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--其他项目--电线杆
//			String a121 = (String) map.get("a121");
//			if (checkValue(a121)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getNumSql(a121));
//				insert("t_info_building", strBuffer.toString(), "电线杆", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--其他项目--溜索--长度
//			String a122 = (String) map.get("a122");
//			// 附属建筑物--其他项目--溜索--个数
//			String a123 = (String) map.get("a123");
//			if (checkValue(a122) || checkValue(a123)) {
//				StringBuffer strBuffer = new StringBuffer();
//				if (checkValue(a122)) {
//					strBuffer.append(getLongsSql(a122));
//				}
//				if (checkValue(a123)) {
//					strBuffer.append(getNumSql(a123));
//				}
//				insert("t_info_building", strBuffer.toString(), "溜索", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--其他项目--桁架--面积
//			String a124 = (String) map.get("a124");
//			// 附属建筑物--其他项目--桁架--个数
//			String a125 = (String) map.get("a125");
//			if (checkValue(a124) || checkValue(a125)) {
//				StringBuffer strBuffer = new StringBuffer();
//				if (checkValue(a124)) {
//					strBuffer.append(getAreaSql(a124));
//				}
//				if (checkValue(a125)) {
//					strBuffer.append(getNumSql(a125));
//				}
//				insert("t_info_building", strBuffer.toString(), "桁架", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--其他项目--人行桥--长度
//			String a126 = (String) map.get("a126");
//			// 附属建筑物--其他项目--人行桥--个数
//			String a127 = (String) map.get("a127");
//			if (checkValue(a126) || checkValue(a127)) {
//				StringBuffer strBuffer = new StringBuffer();
//				if (checkValue(a126)) {
//					strBuffer.append(getLongsSql(a126));
//				}
//				if (checkValue(a127)) {
//					strBuffer.append(getNumSql(a127));
//				}
//				insert("t_info_building", strBuffer.toString(), "人行桥", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--其他项目--水泥桥--长度
//			String a128 = (String) map.get("a128");
//			// 附属建筑物--其他项目--水泥桥--个数
//			String a129 = (String) map.get("a129");
//			if (checkValue(a128) || checkValue(a129)) {
//				StringBuffer strBuffer = new StringBuffer();
//				if (checkValue(a128)) {
//					strBuffer.append(getLongsSql(a128));
//				}
//				if (checkValue(a129)) {
//					strBuffer.append(getNumSql(a129));
//				}
//				insert("t_info_building", strBuffer.toString(), "水泥桥", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--其他项目--景观石（个）
//			String a130 = (String) map.get("a130");
//			if (checkValue(a130)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("个"));
//				strBuffer.append(getNumSql(a130));
//				insert("t_info_building", strBuffer.toString(), "景观石", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--其他项目--热泵（套）
//			String a131 = (String) map.get("a131");
//			if (checkValue(a131)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("套"));
//				strBuffer.append(getNumSql(a131));
//				insert("t_info_building", strBuffer.toString(), "热泵", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--其他项目--保温桶
//			String a132 = (String) map.get("a132");
//			if (checkValue(a132)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("个"));
//				strBuffer.append(getNumSql(a132));
//				insert("t_info_building", strBuffer.toString(), "保温桶", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--其他项目--法台
//			String a133 = (String) map.get("a133");
//			if (checkValue(a133)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("个"));
//				strBuffer.append(getNumSql(a133));
//				insert("t_info_building", strBuffer.toString(), "法台", region, nm, zblx, scope, ownerNature);
//			}
//			// 附属建筑物--其他项目--其他()
//			String a134 = (String) map.get("a134");
//			// 附属建筑物--其他项目--其他备注
//			String a135 = (String) map.get("a135");
//			if (checkValue(a134)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getNumSql(a134));
//				if (checkValue(a135)) {
//					strBuffer.append(getRemarkSql(a135));
//				}
//				insert("t_info_building", strBuffer.toString(), "其他", region, nm, zblx, scope, ownerNature);
//			}
//
//			// 零星树木--果树--其他果树--结果
//			String a136 = (String) map.get("a136");
//			if (checkValue(a136)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a136));
//				insert("t_info_trees", strBuffer.toString(), "其他果树,结果", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--果树--其他果树--未结果
//			String a137 = (String) map.get("a137");
//			if (checkValue(a137)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a137));
//				insert("t_info_trees", strBuffer.toString(), "其他果树,未结果", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--果树--核桃--特大
//			String a138 = (String) map.get("a138");
//			if (checkValue(a138)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a138));
//				insert("t_info_trees", strBuffer.toString(), "核桃,特大", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--果树--核桃--大树
//			String a139 = (String) map.get("a139");
//			if (checkValue(a139)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a139));
//				insert("t_info_trees", strBuffer.toString(), "核桃,大树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--果树--核桃--中树
//			String a140 = (String) map.get("a140");
//			if (checkValue(a140)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a140));
//				insert("t_info_trees", strBuffer.toString(), "核桃,中树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--果树--核桃--小树
//			String a141 = (String) map.get("a141");
//			if (checkValue(a141)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a141));
//				insert("t_info_trees", strBuffer.toString(), "核桃,小树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--果树--核桃--幼树
//			String a142 = (String) map.get("a142");
//			if (checkValue(a142)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a142));
//				insert("t_info_trees", strBuffer.toString(), "核桃,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--果树--板栗--特大
//			String a143 = (String) map.get("a143");
//			if (checkValue(a143)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a143));
//				insert("t_info_trees", strBuffer.toString(), "板栗,特大", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--果树--板栗--大树
//			String a144 = (String) map.get("a144");
//			if (checkValue(a144)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a144));
//				insert("t_info_trees", strBuffer.toString(), "板栗,大树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--果树--板栗--中树
//			String a145 = (String) map.get("a145");
//			if (checkValue(a145)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a145));
//				insert("t_info_trees", strBuffer.toString(), "板栗,中树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--果树--板栗--小树
//			String a146 = (String) map.get("a146");
//			if (checkValue(a146)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a146));
//				insert("t_info_trees", strBuffer.toString(), "板栗,小树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--果树--板栗--幼树
//			String a147 = (String) map.get("a147");
//			if (checkValue(a147)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a147));
//				insert("t_info_trees", strBuffer.toString(), "板栗,幼树", region, nm, zblx, scope, ownerNature);
//			}
			// 零星树木--果树--桃--结果
			String a148 = (String) map.get("a148");
			if (checkValue(a148)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a148));
				insert("t_info_trees", strBuffer.toString(), "桃,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--桃--未结果
			String a149 = (String) map.get("a149");
			if (checkValue(a149)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a149));
				insert("t_info_trees", strBuffer.toString(), "桃,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--梨--结果
			String a150 = (String) map.get("a150");
			if (checkValue(a150)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a150));
				insert("t_info_trees", strBuffer.toString(), "梨,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--梨--未结果
			String a151 = (String) map.get("a151");
			if (checkValue(a151)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a151));
				insert("t_info_trees", strBuffer.toString(), "梨,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--柿子--结果
			String a152 = (String) map.get("a152");
			if (checkValue(a152)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a152));
				insert("t_info_trees", strBuffer.toString(), "柿子,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--柿子--未结果
			String a153 = (String) map.get("a153");
			if (checkValue(a153)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a153));
				insert("t_info_trees", strBuffer.toString(), "柿子,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--李子--结果
			String a154 = (String) map.get("a154");
			if (checkValue(a154)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a154));
				insert("t_info_trees", strBuffer.toString(), "李子,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--李子--未结果
			String a155 = (String) map.get("a155");
			if (checkValue(a155)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a155));
				insert("t_info_trees", strBuffer.toString(), "李子,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--苹果--结果
			String a156 = (String) map.get("a156");
			if (checkValue(a156)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a156));
				insert("t_info_trees", strBuffer.toString(), "苹果,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--苹果--未结果
			String a157 = (String) map.get("a157");
			if (checkValue(a157)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a157));
				insert("t_info_trees", strBuffer.toString(), "苹果,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--柑桔--结果
			String a158 = (String) map.get("a158");
			if (checkValue(a158)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a158));
				insert("t_info_trees", strBuffer.toString(), "柑桔,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--柑桔--未结果
			String a159 = (String) map.get("a159");
			if (checkValue(a159)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a159));
				insert("t_info_trees", strBuffer.toString(), "柑桔,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--枇杷--结果
			String a160 = (String) map.get("a160");
			if (checkValue(a160)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a160));
				insert("t_info_trees", strBuffer.toString(), "枇杷,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--枇杷--未结果
			String a161 = (String) map.get("a161");
			if (checkValue(a161)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a161));
				insert("t_info_trees", strBuffer.toString(), "枇杷,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--柚子--结果
			String a162 = (String) map.get("a162");
			if (checkValue(a162)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a162));
				insert("t_info_trees", strBuffer.toString(), "柚子,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--柚子--未结果
			String a163 = (String) map.get("a163");
			if (checkValue(a163)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a163));
				insert("t_info_trees", strBuffer.toString(), "柚子,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--杏--结果
			String a164 = (String) map.get("a164");
			if (checkValue(a164)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a164));
				insert("t_info_trees", strBuffer.toString(), "杏,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--杏--未结果
			String a165 = (String) map.get("a165");
			if (checkValue(a165)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a165));
				insert("t_info_trees", strBuffer.toString(), "杏,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--石榴--结果
			String a166 = (String) map.get("a166");
			if (checkValue(a166)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a166));
				insert("t_info_trees", strBuffer.toString(), "石榴,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--石榴--未结果
			String a167 = (String) map.get("a167");
			if (checkValue(a167)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a167));
				insert("t_info_trees", strBuffer.toString(), "石榴,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--香蕉--结果
			String a168 = (String) map.get("a168");
			if (checkValue(a168)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a168));
				insert("t_info_trees", strBuffer.toString(), "香蕉,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--香蕉--未结果
			String a169 = (String) map.get("a169");
			if (checkValue(a169)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a169));
				insert("t_info_trees", strBuffer.toString(), "香蕉,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--芭蕉--结果
			String a170 = (String) map.get("a170");
			if (checkValue(a170)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a170));
				insert("t_info_trees", strBuffer.toString(), "芭蕉,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--芭蕉--未结果
			String a171 = (String) map.get("a171");
			if (checkValue(a171)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a171));
				insert("t_info_trees", strBuffer.toString(), "芭蕉,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--樱桃--结果
			String a172 = (String) map.get("a172");
			if (checkValue(a172)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a172));
				insert("t_info_trees", strBuffer.toString(), "樱桃,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--樱桃--未结果
			String a173 = (String) map.get("a173");
			if (checkValue(a173)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a173));
				insert("t_info_trees", strBuffer.toString(), "樱桃,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--枣--结果
			String a174 = (String) map.get("a174");
			if (checkValue(a174)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a174));
				insert("t_info_trees", strBuffer.toString(), "枣,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--枣--未结果
			String a175 = (String) map.get("a175");
			if (checkValue(a175)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a175));
				insert("t_info_trees", strBuffer.toString(), "枣,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--梅子--结果
			String a176 = (String) map.get("a176");
			if (checkValue(a176)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a176));
				insert("t_info_trees", strBuffer.toString(), "梅子,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--梅子--未结果
			String a177 = (String) map.get("a177");
			if (checkValue(a177)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a177));
				insert("t_info_trees", strBuffer.toString(), "梅子,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--无花果--结果
			String a178 = (String) map.get("a178");
			if (checkValue(a178)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a178));
				insert("t_info_trees", strBuffer.toString(), "无花果,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--无花果--未结果
			String a179 = (String) map.get("a179");
			if (checkValue(a179)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a179));
				insert("t_info_trees", strBuffer.toString(), "无花果,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--木瓜--结果
			String a180 = (String) map.get("a180");
			if (checkValue(a180)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a180));
				insert("t_info_trees", strBuffer.toString(), "木瓜,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--木瓜--未结果
			String a181 = (String) map.get("a181");
			if (checkValue(a181)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a181));
				insert("t_info_trees", strBuffer.toString(), "木瓜,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--葡萄--结果
			String a182 = (String) map.get("a182");
			if (checkValue(a182)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a182));
				insert("t_info_trees", strBuffer.toString(), "葡萄,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--葡萄--未结果
			String a183 = (String) map.get("a183");
			if (checkValue(a183)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a183));
				insert("t_info_trees", strBuffer.toString(), "葡萄,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--桂圆--结果
			String a184 = (String) map.get("a184");
			if (checkValue(a184)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a184));
				insert("t_info_trees", strBuffer.toString(), "桂圆,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--桂圆--未结果
			String a185 = (String) map.get("a185");
			if (checkValue(a185)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a185));
				insert("t_info_trees", strBuffer.toString(), "桂圆,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--山楂--结果
			String a186 = (String) map.get("a186");
			if (checkValue(a186)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a186));
				insert("t_info_trees", strBuffer.toString(), "山楂,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--山楂--未结果
			String a187 = (String) map.get("a187");
			if (checkValue(a187)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a187));
				insert("t_info_trees", strBuffer.toString(), "山楂,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--青刺果--结果
			String a188 = (String) map.get("a188");
			if (checkValue(a188)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a188));
				insert("t_info_trees", strBuffer.toString(), "青刺果,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--青刺果--未结果
			String a189 = (String) map.get("a189");
			if (checkValue(a189)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a189));
				insert("t_info_trees", strBuffer.toString(), "青刺果,未结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--佛手柑--结果
			String a190 = (String) map.get("a190");
			if (checkValue(a190)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a190));
				insert("t_info_trees", strBuffer.toString(), "佛手柑,结果", region, nm, zblx, scope, ownerNature);
			}
			// 零星树木--果树--佛手柑--未结果
			String a191 = (String) map.get("a191");
			if (checkValue(a191)) {
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(getUnitSql("颗"));
				strBuffer.append(getNumSql(a191));
				insert("t_info_trees", strBuffer.toString(), "佛手柑,未结果", region, nm, zblx, scope, ownerNature);
			}
//			// 零星树木--果树--其它--结果
//			String a192 = (String) map.get("a192");
//			if (checkValue(a192)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a192));
//				insert("t_info_trees", strBuffer.toString(), "其他果树,结果", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--果树--其它--未结果
//			String a193 = (String) map.get("a193");
//			if (checkValue(a193)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a193));
//				insert("t_info_trees", strBuffer.toString(), "其他果树,未结果", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--花椒--成树
//			String a194 = (String) map.get("194");
//			if (checkValue(a194)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a194));
//				insert("t_info_trees", strBuffer.toString(), "花椒,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--花椒--幼树
//			String a195 = (String) map.get("a195");
//			if (checkValue(a195)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a195));
//				insert("t_info_trees", strBuffer.toString(), "花椒,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--漆树--成树
//			String a196 = (String) map.get("a196");
//			if (checkValue(a196)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a196));
//				insert("t_info_trees", strBuffer.toString(), "漆树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--漆树--幼树
//			String a197 = (String) map.get("a197");
//			if (checkValue(a197)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a197));
//				insert("t_info_trees", strBuffer.toString(), "漆树,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--香椿--成树
//			String a198 = (String) map.get("a198");
//			if (checkValue(a198)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a198));
//				insert("t_info_trees", strBuffer.toString(), "香椿,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--香椿--幼树
//			String a199 = (String) map.get("a199");
//			if (checkValue(a199)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a199));
//				insert("t_info_trees", strBuffer.toString(), "香椿,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--油桐--成树
//			String a200 = (String) map.get("a200");
//			if (checkValue(a200)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a200));
//				insert("t_info_trees", strBuffer.toString(), "油桐,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--油桐--幼树
//			String a201 = (String) map.get("a201");
//			if (checkValue(a201)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a201));
//				insert("t_info_trees", strBuffer.toString(), "油桐,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--八角--成树
//			String a202 = (String) map.get("a202");
//			if (checkValue(a202)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a202));
//				insert("t_info_trees", strBuffer.toString(), "八角,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--八角--幼树
//			String a203 = (String) map.get("a203");
//			if (checkValue(a203)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a203));
//				insert("t_info_trees", strBuffer.toString(), "八角,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--枸杞--成树
//			String a204 = (String) map.get("a204");
//			if (checkValue(a204)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a204));
//				insert("t_info_trees", strBuffer.toString(), "枸杞,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--枸杞--幼树
//			String a205 = (String) map.get("a205");
//			if (checkValue(a205)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a205));
//				insert("t_info_trees", strBuffer.toString(), "枸杞,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--油茶--成树
//			String a206 = (String) map.get("a206");
//			if (checkValue(a206)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a206));
//				insert("t_info_trees", strBuffer.toString(), "油茶,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--油茶--幼树
//			String a207 = (String) map.get("a207");
//			if (checkValue(a207)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a207));
//				insert("t_info_trees", strBuffer.toString(), "油茶,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--砂仁--成树
//			String a208 = (String) map.get("a208");
//			if (checkValue(a208)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a208));
//				insert("t_info_trees", strBuffer.toString(), "砂仁,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--砂仁--幼树
//			String a209 = (String) map.get("a209");
//			if (checkValue(a209)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a209));
//				insert("t_info_trees", strBuffer.toString(), "砂仁,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--桑树--成树
//			String a210 = (String) map.get("a210");
//			if (checkValue(a210)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a210));
//				insert("t_info_trees", strBuffer.toString(), "桑树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--桑树--幼树
//			String a211 = (String) map.get("a211");
//			if (checkValue(a211)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a211));
//				insert("t_info_trees", strBuffer.toString(), "桑树,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--杜仲--成树
//			String a212 = (String) map.get("a212");
//			if (checkValue(a212)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a212));
//				insert("t_info_trees", strBuffer.toString(), "杜仲,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--杜仲--幼树
//			String a213 = (String) map.get("a213");
//			if (checkValue(a213)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a213));
//				insert("t_info_trees", strBuffer.toString(), "杜仲,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--金银花--成树
//			String a214 = (String) map.get("a214");
//			if (checkValue(a214)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a214));
//				insert("t_info_trees", strBuffer.toString(), "金银花,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--金银花--幼树
//			String a215 = (String) map.get("a215");
//			if (checkValue(a215)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a215));
//				insert("t_info_trees", strBuffer.toString(), "金银花,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--胡椒棕树--成树
//			String a216 = (String) map.get("a216");
//			if (checkValue(a216)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a216));
//				insert("t_info_trees", strBuffer.toString(), "胡椒棕树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--胡椒棕树--幼树
//			String a217 = (String) map.get("a217");
//			if (checkValue(a217)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a217));
//				insert("t_info_trees", strBuffer.toString(), "胡椒棕树,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--吴茱萸--成树
//			String a218 = (String) map.get("a218");
//			if (checkValue(a218)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a218));
//				insert("t_info_trees", strBuffer.toString(), "吴茱萸,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--吴茱萸--幼树
//			String a219 = (String) map.get("a219");
//			if (checkValue(a219)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a219));
//				insert("t_info_trees", strBuffer.toString(), "吴茱萸,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--茶树--成树
//			String a220 = (String) map.get("a220");
//			if (checkValue(a220)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a220));
//				insert("t_info_trees", strBuffer.toString(), "茶树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--茶树--幼树
//			String a221 = (String) map.get("a221");
//			if (checkValue(a221)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a221));
//				insert("t_info_trees", strBuffer.toString(), "茶树,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--其它--成树
//			String a222 = (String) map.get("a222");
//			if (checkValue(a222)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a222));
//				insert("t_info_trees", strBuffer.toString(), "其他经济树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--经济树--其它--幼树
//			String a223 = (String) map.get("a223");
//			if (checkValue(a223)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a223));
//				insert("t_info_trees", strBuffer.toString(), "其他经济树,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--楸木--成树
//			String a224 = (String) map.get("a224");
//			if (checkValue(a224)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a224));
//				insert("t_info_trees", strBuffer.toString(), "楸木,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--楸木--幼树
//			String a225 = (String) map.get("a225");
//			if (checkValue(a225)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a225));
//				insert("t_info_trees", strBuffer.toString(), "楸木,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--椿树--成树
//			String a226 = (String) map.get("a226");
//			if (checkValue(a226)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a226));
//				insert("t_info_trees", strBuffer.toString(), "椿树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--椿树--幼树
//			String a227 = (String) map.get("a227");
//			if (checkValue(a227)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a227));
//				insert("t_info_trees", strBuffer.toString(), "椿树,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--柏树--成树
//			String a228 = (String) map.get("a228");
//			if (checkValue(a228)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a228));
//				insert("t_info_trees", strBuffer.toString(), "柏树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--柏树--幼树
//			String a229 = (String) map.get("a229");
//			if (checkValue(a229)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a229));
//				insert("t_info_trees", strBuffer.toString(), "柏树,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--樟树--成树
//			String a230 = (String) map.get("a230");
//			if (checkValue(a230)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a230));
//				insert("t_info_trees", strBuffer.toString(), "樟树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--樟树--幼树
//			String a231 = (String) map.get("a231");
//			if (checkValue(a231)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a231));
//				insert("t_info_trees", strBuffer.toString(), "樟树,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--杉树--成树
//			String a232 = (String) map.get("a232");
//			if (checkValue(a232)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a232));
//				insert("t_info_trees", strBuffer.toString(), "杉树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--杉树--幼树
//			String a233 = (String) map.get("a233");
//			if (checkValue(a233)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a233));
//				insert("t_info_trees", strBuffer.toString(), "杉树,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--竹--成树
//			String a234 = (String) map.get("a234");
//			if (checkValue(a234)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a234));
//				insert("t_info_trees", strBuffer.toString(), "竹,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--竹--幼树
//			String a235 = (String) map.get("a235");
//			if (checkValue(a235)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a235));
//				insert("t_info_trees", strBuffer.toString(), "竹,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--榆树--成树
//			String a236 = (String) map.get("a236");
//			if (checkValue(a236)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a236));
//				insert("t_info_trees", strBuffer.toString(), "榆树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--榆树--幼树
//			String a237 = (String) map.get("a237");
//			if (checkValue(a237)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a237));
//				insert("t_info_trees", strBuffer.toString(), "榆树,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--杂树--成树
//			String a238 = (String) map.get("a238");
//			if (checkValue(a238)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a238));
//				insert("t_info_trees", strBuffer.toString(), "杂树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--杂树--幼树
//			String a239 = (String) map.get("a239");
//			if (checkValue(a239)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a239));
//				insert("t_info_trees", strBuffer.toString(), "杂树,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--其它--成树
//			String a240 = (String) map.get("a240");
//			if (checkValue(a240)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a240));
//				insert("t_info_trees", strBuffer.toString(), "其他用材树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--用材树--其它--幼树
//			String a241 = (String) map.get("a241");
//			if (checkValue(a241)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a241));
//				insert("t_info_trees", strBuffer.toString(), "其他用材树,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--白蜡--成树
//			String a242 = (String) map.get("a242");
//			if (checkValue(a242)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a242));
//				insert("t_info_trees", strBuffer.toString(), "白蜡,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--白蜡--幼树
//			String a243 = (String) map.get("a243");
//			if (checkValue(a243)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a243));
//				insert("t_info_trees", strBuffer.toString(), "白蜡,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--玉兰--成树
//			String a244 = (String) map.get("a244");
//			if (checkValue(a244)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a244));
//				insert("t_info_trees", strBuffer.toString(), "玉兰,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--玉兰--幼树
//			String a245 = (String) map.get("a245");
//			if (checkValue(a245)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a245));
//				insert("t_info_trees", strBuffer.toString(), "玉兰,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--桂花--成树
//			String a246 = (String) map.get("a246");
//			if (checkValue(a246)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a246));
//				insert("t_info_trees", strBuffer.toString(), "桂花,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--桂花--幼树
//			String a247 = (String) map.get("a247");
//			if (checkValue(a247)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a247));
//				insert("t_info_trees", strBuffer.toString(), "桂花,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--火把花--成树
//			String a248 = (String) map.get("a248");
//			if (checkValue(a248)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a248));
//				insert("t_info_trees", strBuffer.toString(), "火把花,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--火把花--幼树
//			String a249 = (String) map.get("a249");
//			if (checkValue(a249)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a249));
//				insert("t_info_trees", strBuffer.toString(), "火把花,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--神树--成树
//			String a250 = (String) map.get("a250");
//			if (checkValue(a250)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a250));
//				insert("t_info_trees", strBuffer.toString(), "神树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--神树--幼树
//			String a251 = (String) map.get("a251");
//			if (checkValue(a251)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a251));
//				insert("t_info_trees", strBuffer.toString(), "神树,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--栀子--成树
//			String a252 = (String) map.get("a252");
//			if (checkValue(a252)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a252));
//				insert("t_info_trees", strBuffer.toString(), "栀子,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--栀子--幼树
//			String a253 = (String) map.get("a253");
//			if (checkValue(a253)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a253));
//				insert("t_info_trees", strBuffer.toString(), "栀子,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--银杏--成树
//			String a254 = (String) map.get("a254");
//			if (checkValue(a254)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a254));
//				insert("t_info_trees", strBuffer.toString(), "银杏,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--银杏--幼树
//			String a255 = (String) map.get("a255");
//			if (checkValue(a255)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a255));
//				insert("t_info_trees", strBuffer.toString(), "银杏,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--叶子花--成树
//			String a256 = (String) map.get("a256");
//			if (checkValue(a256)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a256));
//				insert("t_info_trees", strBuffer.toString(), "叶子花,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--叶子花--幼树
//			String a257 = (String) map.get("a257");
//			if (checkValue(a257)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a257));
//				insert("t_info_trees", strBuffer.toString(), "叶子花,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--红叶石楠--成树
//			String a258 = (String) map.get("a258");
//			if (checkValue(a258)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a258));
//				insert("t_info_trees", strBuffer.toString(), "红叶石楠,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--红叶石楠--幼树
//			String a259 = (String) map.get("a259");
//			if (checkValue(a259)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a259));
//				insert("t_info_trees", strBuffer.toString(), "红叶石楠,幼树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--其它--成树
//			String a260 = (String) map.get("a260");
//			if (checkValue(a260)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a260));
//				insert("t_info_trees", strBuffer.toString(), "其他景观树,成树", region, nm, zblx, scope, ownerNature);
//			}
//			// 零星树木--景观树--其它--幼树
//			String a261 = (String) map.get("a261");
//			if (checkValue(a261)) {
//				StringBuffer strBuffer = new StringBuffer();
//				strBuffer.append(getUnitSql("颗"));
//				strBuffer.append(getNumSql(a261));
//				insert("t_info_trees", strBuffer.toString(), "其他景观树,幼树", region, nm, zblx, scope, ownerNature);
//			}
		}
		System.out.println("===结束=====线程：" + Thread.currentThread().getName() + "=========数量：" + count);
	}
	
	public void insert(String table, String sql, String projectNm, String region, String ownerNm, String zblx,
			String scope, String ownerNature) {
		sql = StringUtils.trimToNull(sql);
		String str = "insert into " + table + " set nm='" + Randomizer.generCode(10) + "',";
		str += ("region = '" + region + "',");
		str += ("owner_nm = '" + ownerNm + "',");
		str += ("zblx = '" + zblx + "',");
		str += ("scope = '" + scope + "',");
		String dateTime = DateUtil.getDateTime();
		str += ("create_time = STR_TO_DATE('" + dateTime + "','%Y-%m-%d %H:%i:%s'),");
		str += ("owner_nature = '" + ownerNature + "',");
		str += sql;

		str += ("project_nm = '" + projectNm + "';");
		jdbcTemplate.execute(str);
	}

	public String getLongsSql(String value) {
		String str = ("longs = " + value + ",");
		return str;
	}

	public String getAreaSql(String value) {
		String str = ("area = " + value + ",");
		return str;
	}

	public String getUnitSql(String value) {
		String str = ("unit = '" + value + "',");
		return str;
	}

	public String getNumSql(String value) {
		String str = ("num = " + value + ",");
		return str;
	}

	public String getVolumeSql(String value) {
		String str = ("volume = " + value + ",");
		return str;
	}

	public String getRemarkSql(String value) {
		String str = ("remark = '" + value + "',");
		return str;
	}

	public boolean checkValue(String value) {
		value = StringUtils.trimToNull(value);
		if (StringUtils.isNotBlank(value) && !StringUtils.equalsAnyIgnoreCase(value, "0", "0.0", "null")) {
			return true;
		}
		return false;
	}
	
}