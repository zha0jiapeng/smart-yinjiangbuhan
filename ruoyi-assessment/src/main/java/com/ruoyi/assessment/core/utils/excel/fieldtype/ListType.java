/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.ruoyi.assessment.core.utils.excel.fieldtype;

import com.ruoyi.assessment.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字段类型转换
 * @author jeeplus
 * @version 2016-5-29
 */
public class ListType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		List<String> list = new ArrayList<>();
		if(!StringUtils.isBlank(val)) {
			for (String s : val.split(",")) {
				list.add(s);
			}
		}
		return list;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null){
			List<String> list = (List<String>)val;
			StringBuffer sb = null;
			for (String item: list){
				if(StringUtils.isBlank(item)){
					continue;
				}
				if(sb == null){
					sb = new StringBuffer(item);
				}else{
					sb.append(",").append(item);
				}
			}

			if(sb!=null) {
				return sb.toString().replace("[]", "");
			}
		}
		return "";
	}
	
}
