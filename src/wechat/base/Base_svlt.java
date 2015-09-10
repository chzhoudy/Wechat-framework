package wechat.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wechat.message.req.Request_serv;
import wechat.util.Config;
import wechat.util.MessageUtil;

public class Base_svlt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected Request_serv reqServ;
	protected static String module;
	protected Map<String, String> methodMap;
	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/plain;charset=utf-8");
		if (MessageUtil.check(module, req)) {
			resp.getWriter().print(req.getParameter("echostr"));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		methodMap = Config.keyValue(module);
		reqServ = new Request_serv();
		reqServ.init(module, req);
		String methodName = reqServ.findMethod();
		if(methodName == null){
			return;
		}
		String jsonName = methodMap.get(methodName);
		String reqMsg = "";
		try {
			if (jsonName != null) {
				Class clazz = Class.forName(jsonName);
				Object obj = clazz.newInstance();
				Method parse = clazz.getMethod("init", Request_serv.class);
				parse.invoke(obj, reqServ);
				Method method = clazz.getMethod(methodName, new Class[0]);
				reqMsg = (String) method.invoke(obj, new Object[0]);
				clazz = null;
			}
			doPostMsg(reqMsg, req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		methodMap = null;
		reqServ = null;
	}

	protected void doPostMsg(String reqMsg, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		PrintWriter pw = resp.getWriter();
		pw.print(reqMsg);
		pw.close();
	}

}
