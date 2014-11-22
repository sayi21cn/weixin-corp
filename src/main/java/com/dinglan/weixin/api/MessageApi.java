package com.dinglan.weixin.api;

import com.dinglan.weixin.kit.HttpKit;
import com.dinglan.weixin.msg.JsonMsg;

/**
 * message api
 */
public class MessageApi {
	private static String msgUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";
	
	/**
	 * 发送消息
	 */
	public static ApiResult sendMsg(JsonMsg outMsg) {
		String data=outMsg.toString();
		return sendMsg(data);
	}
	
	public static ApiResult sendMsg(String msg) {
		String jsonResult = HttpKit.post(msgUrl + AccessTokenApi.getAccessToken().getAccessToken(),msg);
		return new ApiResult(jsonResult);
	}
}