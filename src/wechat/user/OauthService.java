package wechat.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import wechat.util.Config;
import wechat.util.HttpUtil;

import com.alibaba.fastjson.JSONObject;

public class OauthService {
	private final static String OAUTH_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	private final static String OAUTH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	private final static String OAUTH_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	private final static String SNSAPI_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	private final static String OAUTH_TOKEN_CHECK_URL = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
	
	
	/**
	 * 授权申请code 
	 * @param module  微信号
	 * @param redirect_uri   授权后重定向的回调链接地址，请使用urlencode对链接进行处理
	 * @param scope   应用授权作用域，snsapi_base
	 *            （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo
	 *            （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	 * @param state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	 * @return  如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
	 *          若用户禁止授权，则重定向后不会带上code参数，仅会带上state参数redirect_uri?state=STATE
	 * code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
	 * @throws UnsupportedEncodingException s
	 */
	public JSONObject getOauthCode(String module,String redirect_uri,String scope) throws UnsupportedEncodingException{
		String appid = Config.getValue(module, "appID");
		String url = OAUTH_CODE_URL.replace("APPID", appid)
				.replace("REDIRECT_URI", URLEncoder.encode(redirect_uri, "UTF-8")).replace("SCOPE", scope)
				.replace("STATE", String.valueOf(Math.random() * 1000));
		JSONObject jsonObj = HttpUtil.httpsRequest(url, "GET", null);
		return jsonObj;
	}
	
	/**
	 * 根据code获取access_token
	 * @param module
	 * @param code
	 * @return
	 */
    public JSONObject getOauthToken(String module,String code){
    	String appid = Config.getValue(module, "appID");
    	String appsecret = Config.getValue(module, "appsecret");
		String url = OAUTH_TOKEN_URL.replace("APPID", appid)
				.replace("SECRET", appsecret).replace("CODE", code);
		JSONObject jsonObj = HttpUtil.httpsRequest(url, "GET", null);
		return jsonObj;
    }
    
    
    /**
     * 刷新access_token
     * @param refresh_token 获取access_token时候附带的refresh_Token
     * @return
     */
    public JSONObject refreshToken(String refresh_token){
    	String url = OAUTH_REFRESH_TOKEN_URL.replace("REFRESH_TOKEN", refresh_token);
    	JSONObject jsonObj = HttpUtil.httpsRequest(url, "GET", null);
    	return jsonObj;
    }
    
    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     * @param token
     * @param openid
     * @return
     * 网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息了
     */
    public JSONObject getSnsapiUserinfo(String token,String openid){
    	String url = SNSAPI_USERINFO_URL.replace("TOKEN", token).replace("OPENID",openid);
    	JSONObject jsonObj = HttpUtil.httpsRequest(url, "GET", null);
    	return jsonObj;
    }
    
    public JSONObject checkOpenidToken(String access_token,String openid){
    	String url = OAUTH_TOKEN_CHECK_URL.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
    	JSONObject jsonObj = HttpUtil.httpsRequest(url, "GET", null);
		return jsonObj;
    }
}
