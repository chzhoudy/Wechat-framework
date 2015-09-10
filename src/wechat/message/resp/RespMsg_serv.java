package wechat.message.resp;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wechat.message.mass.Filter;
import wechat.message.mass.MassImage;
import wechat.message.mass.MassNews;
import wechat.message.mass.MassText;
import wechat.message.mass.MassVideo;
import wechat.message.mass.MassVoice;
import wechat.message.touser.Mediaid;
import wechat.message.touser.Music;
import wechat.message.touser.Text;
import wechat.message.touser.TouserImage;
import wechat.message.touser.TouserMusic;
import wechat.message.touser.TouserText;
import wechat.message.touser.TouserVideo;
import wechat.message.touser.TouserVoice;
import wechat.message.touser.Video;
import wechat.util.BaseUtil;
import wechat.util.MessageUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class RespMsg_serv {
	protected String module = "";
	public RespMsg_serv() {

	}

	public RespMsg_serv(String module) {
		this.module = module;
	}

	/**
	 * 文本信息转换
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public String textMessage(String fromUserName, String toUserName,
			String content) {
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setContent(content);
		String text = MessageUtil.messageToXml(textMessage);
		textMessage = null;
		return text;
	}

	/**
	 * 图片消息转换
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param MediaId
	 * @return
	 */
	public String imageMessage(String fromUserName, String toUserName,
			String MediaId) {
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setToUserName(fromUserName);
		imageMessage.setFromUserName(toUserName);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setMediaId(MediaId);
		String image = MessageUtil.messageToXml(imageMessage);
		imageMessage = null;
		return image;
	}

	/**
	 * 声音信息转换
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param MediaId
	 * @return
	 */
	public String voiceMessage(String fromUserName, String toUserName,
			String MediaId) {
		VoiceMessage voiceMessage = new VoiceMessage();
		voiceMessage.setToUserName(fromUserName);
		voiceMessage.setFromUserName(toUserName);
		voiceMessage.setCreateTime(new Date().getTime());
		voiceMessage.setMediaId(MediaId);
		String voice = MessageUtil.messageToXml(voiceMessage);
		voiceMessage = null;
		return voice;
	}

	/**
	 * 视频信息转换
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param MediaId
	 * @param title
	 * @param Description
	 * @return
	 */
	public String videoMessage(String fromUserName, String toUserName,
			String MediaId, String title, String Description) {
		VideoMessage videoMessage = new VideoMessage();
		videoMessage.setToUserName(fromUserName);
		videoMessage.setFromUserName(toUserName);
		videoMessage.setCreateTime(new Date().getTime());
		videoMessage.setMediaId(MediaId);
		videoMessage.setTitle(title);
		videoMessage.setDescription(Description);
		String video = MessageUtil.messageToXml(videoMessage);
		videoMessage = null;
		return video;
	}

	/***
	 * 音乐消息转换
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param title
	 * @param Description
	 * @param MusicURL
	 * @param HQMusicUrl
	 * @param ThumbMediaId
	 * @return
	 */
	public String MusicMessage(String fromUserName, String toUserName,
			String title, String Description, String MusicURL,
			String HQMusicUrl, String ThumbMediaId) {
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setToUserName(fromUserName);
		musicMessage.setFromUserName(toUserName);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setTitle(title);
		musicMessage.setDescription(Description);
		musicMessage.setMusicUrl(MusicURL);
		musicMessage.setHQMusicUrl(HQMusicUrl);
		musicMessage.setThumbMediaId(ThumbMediaId);
		String music = MessageUtil.messageToXml(musicMessage);
		musicMessage = null;
		return music;
	}

	/**
	 * 图文信息转换
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param ArticleCount
	 * @param Articles
	 * @return
	 */
	public String NewsMessage(String fromUserName, String toUserName,
			int ArticleCount, List<Article> Articles) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setArticleCount(ArticleCount);
		newsMessage.setArticles(Articles);
		String news = MessageUtil.messageToXml(newsMessage);
		newsMessage = null;
		return news;
	}

	/**
	 * 根据组号群发
	 * 
	 * @param group_id
	 * @param media_id
	 * @param flag
	 * @return
	 */
	public String massSendGroup(String group_id, String media_id, String flag) {
		String news = "";
		Filter filter = new Filter();
		filter.setGroup_id(group_id);
		if (flag.equals("image")) {
			MassImage massImage = new MassImage(media_id);
			massImage.setFilter(filter);
			news = MessageUtil.messageToXml(massImage);
			massImage = null;
		} else if (flag.equals("mpnews")) {
			MassNews massNews = new MassNews(media_id);
			massNews.setFilter(filter);
			news = MessageUtil.messageToXml(massNews);
			massNews = null;
		} else if (flag.equals("text")) {
			MassText massText = new MassText(media_id);
			massText.setFilter(filter);
			news = MessageUtil.messageToXml(massText);
			massText = null;
		} else if (flag.equals("voice")) {
			MassVoice massVoice = new MassVoice(media_id);
			massVoice.setFilter(filter);
			news = MessageUtil.messageToXml(massVoice);
			massVoice = null;
		} else if (flag.equals("video")) {
			MassVideo massVideo = new MassVideo(media_id);
			massVideo.setFilter(filter);
			news = MessageUtil.messageToXml(massVideo);
			massVideo = null;
		}
		return news;
	}

	/**
	 * 根据openid群发
	 * 
	 * @param group_id
	 * @param media_id
	 * @param flag
	 * @return
	 */
	public String massSendOpenID(Set<String> openid, String media_id,
			String flag) {
		String news = "";
		if (flag.equals("image")) {
			MassImage massImage = new MassImage(media_id);
			massImage.setTouser(openid);
			news = MessageUtil.messageToXml(massImage);
			massImage = null;
		} else if (flag.equals("mpnews")) {
			MassNews massNews = new MassNews(media_id);
			massNews.setTouser(openid);
			news = MessageUtil.messageToXml(massNews);
			massNews = null;
		} else if (flag.equals("text")) {
			MassText massText = new MassText(media_id);
			massText.setTouser(openid);
			news = MessageUtil.messageToXml(massText);
			massText = null;
		} else if (flag.equals("voice")) {
			MassVideo massVoice = new MassVideo(media_id);
			massVoice.setTouser(openid);
			news = MessageUtil.messageToXml(massVoice);
			massVoice = null;
		} else if (flag.equals("video")) {
			MassVideo massVideo = new MassVideo(media_id);
			massVideo.setTouser(openid);
			news = MessageUtil.messageToXml(massVideo);
			massVideo = null;
		}
		return news;
	}

	/**
	 * 客服文字消息
	 * 
	 * @param content
	 * @param touser
	 * @return
	 */
	public String customText(String touser, String content) {
		Text text = new Text(content);
		TouserText touserText = new TouserText(text, touser);
		JSONObject obj = (JSONObject) JSONObject.toJSON(touserText.getText());
		String retmsg = BaseUtil.customSend(module, obj);
		text = null;
		touserText = null;
		return retmsg;
	}
 
	/**
	 * 客服视频消息
	 * 
	 * @param touser
	 * @param video
	 * @return
	 */
	public String customVideo(String touser, String video) {
		Video video1 = JSON.toJavaObject(JSON.parseObject(video), Video.class);
		TouserVideo touserVideo = new TouserVideo(video1, touser);
		JSONObject obj = (JSONObject) JSONObject.toJSON(touserVideo.getVideo());
		String retmsg = BaseUtil.customSend(module, obj);
		video = null;
		touserVideo = null;
		return retmsg;
	}

	/**
	 * 客服图片消息
	 * 
	 * @param media_id
	 * @param touser
	 * @return
	 */
	public String customImage(String touser, String media_id) {
		Mediaid image = new Mediaid(media_id);
		TouserImage touserImage = new TouserImage(image, touser);
		JSONObject obj = (JSONObject) JSONObject.toJSON(touserImage.getImage());
		String retmsg = BaseUtil.customSend(module, obj);
		image = null;
		touserImage = null;
		return retmsg;
	}

	/**
	 * 客服语音消息
	 * 
	 * @param touser
	 * @param media_id
	 * @return
	 */
	public String customVoice(String touser, String media_id) {
		Mediaid voice = new Mediaid(media_id);
		TouserVoice touserVoice = new TouserVoice(voice, touser);
		JSONObject obj = (JSONObject) JSONObject.toJSON(touserVoice.getVoice());
		String retmsg = BaseUtil.customSend(module, obj);
		voice = null;
		touserVoice = null;
		return retmsg;
	}

	/**
	 * 客服音乐消息
	 * 
	 * @param touser
	 * @param music
	 * @return
	 */
	public String customMusic(String touser, String music) {
		Music music1 = JSON.toJavaObject(JSON.parseObject(music), Music.class);
		TouserMusic touserMuisc = new TouserMusic(music1, touser);
		JSONObject obj = (JSONObject) JSONObject.toJSON(touserMuisc.getMusic());
		String retmsg = BaseUtil.customSend(module, obj);
		music = null;
		touserMuisc = null;
		return retmsg;
	}

	/**
	 * 客服图文消息
	 * 
	 * @param touser
	 * @param articles
	 * @return
	 */
	public String customNew(String touser, String articles) {
		JSONObject obj = JSONObject.parseObject(articles);
		String retmsg = BaseUtil.customSend(module, obj);
		return retmsg;
	}

}
