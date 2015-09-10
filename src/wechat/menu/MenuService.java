package wechat.menu;

import com.alibaba.fastjson.JSONObject;

import wechat.enums.ErrCode;
import wechat.util.HttpUtil;

public class MenuService {
	public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	public final static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	public final static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	/**
	 * 创建菜单
	 * @param menu
	 * @param accessToken
	 * @return
	 */
	public String createMenu(Menu menu, String accessToken){
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        String jsonMenu = JSONObject.toJSONString(menu);
        JSONObject jsonObject = HttpUtil.httpsRequest(url, "POST", jsonMenu);
        return ErrCode.errcodeis(Integer.parseInt(jsonObject.getString("errcode")));
    }
	/**
	 * 删除菜单
	 * @param accessToken
	 * @return
	 */
	public String deleteMenu(String accessToken){
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = HttpUtil.httpsRequest(url, "GET", null);
        return ErrCode.errcodeis(Integer.parseInt(jsonObject.getString("errcode")));
    }
	/**
	 * 获取菜单
	 * @param accessToken
	 * @return
	 */
	public JSONObject getMenu(String accessToken){
		String url = menu_get_url.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpUtil.httpsRequest(url, "GET", null);
		return jsonObject;
	}
}
