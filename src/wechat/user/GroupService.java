package wechat.user;

import java.util.HashMap;
import java.util.Map;
import wechat.util.HttpUtil;

import com.alibaba.fastjson.JSONObject;

public class GroupService {
	private static final String GROUP_CREATE_URI = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
	private static final String GROUP_GET_URI = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	private static final String GROUP_GETID_URI = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
	private static final String GROUP_UPDATE_URI = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
	private static final String GROUP_MEMBERS_UPDATE_URI = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
	
	/**
	 * 返回用户分组信息
	 * @param accessToken
	 * @return 
	 */
	public JSONObject getGroupList(String accessToken){
		String url = GROUP_GET_URI.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpUtil.httpsRequest(url, "GET", null);
		return jsonObject;
	}
	
	/**
	 * 查询用户分组ID
	 * @param accessToken
	 * @param openid
	 * @return 
	 */
	public JSONObject getUserGroupId(String accessToken, String openid){
		String url = GROUP_GETID_URI.replace("ACCESS_TOKEN", accessToken);
	    JSONObject jsonObject = HttpUtil.httpsRequest(url, "POST", "{\"openid\":\""+openid+"\"}");
        return  jsonObject;
	}
	
	/**
	 * 修改组名
	 * @param accessToken
	 * @param groupId
	 * @param groupName
	 * @return 
	 */
	public JSONObject updateGroupName(String accessToken,String groupId,String groupName){
		String url = GROUP_UPDATE_URI.replace("ACCESS_TOKEN", accessToken);
		Map<String,Object> group = new HashMap<String,Object>();
        Map<String,Object> member = new HashMap<String,Object>();
        member.put("name", groupName);
        member.put("id", groupId);
        group.put("group", member);
        String post = JSONObject.toJSONString(group);
	    JSONObject jsonObject = HttpUtil.httpsRequest(url, "POST", post);
        return  jsonObject;
	}
	
	/**
	 * 创建分组
	 * @param accessToken
	 * @param groupName
	 * @return 
	 */
	public JSONObject createGroup(String accessToken,String groupName){
		String url = GROUP_CREATE_URI.replace("ACCESS_TOKEN", accessToken);
		Map<String,Object> group = new HashMap<String,Object>();
        Map<String,Object> member = new HashMap<String,Object>();
        member.put("name", groupName);
        group.put("group", member);
        String post = JSONObject.toJSONString(group);
	    JSONObject jsonObject = HttpUtil.httpsRequest(url, "POST", post);
        return  jsonObject;
	}
	
	/***
	 * 移动用户分组
	 * @param accessToken
	 * @param openid
	 * @param to_groupid
	 * @return
	 * @throws Exception
	 */
	public JSONObject membersMove(String accessToken,String openid,String to_groupid) throws Exception {
		String url = GROUP_MEMBERS_UPDATE_URI.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpUtil.httpsRequest(url,"POST","{\"openid\":\""+openid+"\",\"to_groupid\":"+to_groupid+"}");
        return jsonObject;
	}
}
