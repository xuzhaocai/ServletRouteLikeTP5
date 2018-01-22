package com.xuzhaocai.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.xuzhaocai.basic.Controller;

public class Login extends Controller {
	public String add(HttpServletRequest request, HttpServletResponse response){
		return view("/index.jsp");
	}
	public String insert(HttpServletRequest request, HttpServletResponse response){
		return redirect("/admin/register/delete");
	}
}
