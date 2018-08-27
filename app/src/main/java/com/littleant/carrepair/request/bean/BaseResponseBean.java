package com.littleant.carrepair.request.bean;

import org.json.JSONObject;

public class BaseResponseBean {
	
	private String code = "";
	private String msg = "";
	private JSONObject data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BaseResponseBean{" +
				"code='" + code + '\'' +
				", msg='" + msg + '\'' +
				", data='" + data + '\'' +
				'}';
	}
}
