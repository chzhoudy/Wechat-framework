package wechat.user;

import wechat.util.HttpUtil;

import com.alibaba.fastjson.JSONObject;


public class UserService {
	private static final String user_info_url= "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	private static final String user_fellower_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	/**
	 * 返回用户信息实体
	 * @param accessToken
	 * @param openid
	 * @return 
	 * @throws Exception
	 */
	public UserInfo getUserInfo(String accessToken, String openid){
		String url = user_info_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
	    JSONObject jsonObject = HttpUtil.httpsRequest(url, "GET", null);
        return  JSONObject.toJavaObject(jsonObject, UserInfo.class);
	}
	/**
	 * 返回关注者列表 最多10000个 若不够 next_openid作下一次查询使用
	 * @param accessToken
	 * @param next_openid
	 * @return 
	 */
	public JSONObject getFollwerList(String accessToken, String next_openid){
		String url = user_fellower_url.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", next_openid);
		return HttpUtil.httpsRequest(url, "GET", null);
	}
}
