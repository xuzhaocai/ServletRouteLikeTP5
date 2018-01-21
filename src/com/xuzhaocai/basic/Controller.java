package com.xuzhaocai.basic;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {
	private String packageName;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		ServletContext config = getServletContext();
		//获取模块+控制器所在包名
		packageName = config.getInitParameter("packageName");
	}
	 @Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取请求的uri
		String path = request.getRequestURI();
		
		
		int methodIndex = path.lastIndexOf("/");
		//获取方法名
		String method=path.substring(methodIndex+1);

		int controllerIndex=path.substring(0, methodIndex).lastIndexOf("/");
		//获取控制器名称
		String controller= path.substring(controllerIndex+1,methodIndex);
		
		int moduleIndex=path.substring(0,controllerIndex).lastIndexOf("/");
		//获取模块名称
		String module=path.substring(moduleIndex+1,controllerIndex);
		
		/**
		 * 使用反射来执行方法
		 */
		String[] packageNames = packageName.split(",");
		
		for (String string : packageNames) {
			int packageIndex=string.indexOf(":");
			if(module.equals(string.substring(0,packageIndex))){
					Class<?> forName=null;
				
					try {
						forName = Class.forName(string.substring(packageIndex+1)+"."+controller.substring(0, 1).toUpperCase()+controller.substring(1));
						
						
						Method methodInvoke = forName.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
						String result = (String)methodInvoke.invoke(forName.newInstance(), request,response);
					
						if(result!=null){
							String subResult=result.substring(0, result.indexOf(":"));
							//根据方法反返回结果来进行转发或者重定向处理
							switch (subResult) {
							case "ss":
								//根据方法名转发
								request.getRequestDispatcher("/view/"+module+"/"+controller+"/"+method+".jsp").forward(request, response);
								break;
							case "s":
								//自定义路径转发
								request.getRequestDispatcher(result.substring(result.indexOf(":")+1)).forward(request, response);
								break;
							
							case "r":
								//重定向
								response.sendRedirect(request.getContextPath()+result.substring(result.indexOf(":")+1));
								break;
							
							default:
								break;
							}
						}
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
			}
			
		
		}
		
	}
	 /**
	  * 转发
	  * @param strings
	  * @return
	  */
	public String view(String...strings){
		if(strings.length==0){
			return "ss:";
		}else if (strings.length==1) {
			return "s:"+strings;
		}else {
			return null;
		}
	}
	/**
	 * 重定向
	 * @param string
	 * @return
	 */
	public String redirect(String string){
		if(string!=null){
			return "r:"+string;
		}
		return null;
	}
	
	
}