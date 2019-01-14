# Androidtoservlet
用于局域网内Android与Tomcat服务器通信，服务器上建了一个简单的servlet

上代码：

public class loginServlet extends HttpServlet { 

	@Override 
	protected void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException{ 
		 String username = request.getParameter("username");
		 String password = request.getParameter("password");
		 System.out.println("username:"+username+":+password"+password);
		 response.setContentType("text/html");
		 response.setCharacterEncoding("utf-8");
		 PrintWriter out = response.getWriter();
		 String msg = null;
		 if(username != null && username.equals("xiaoming") && password != null && password.equals("123")){
			 msg="get登录成功，哈哈哈哈";
		 } 
		 else {
			 msg = "get登录失败";
		 } 
		 out.print(msg);
		 out.flush();
		 out.close();
	} 
	@Override  
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
	        String uname=req.getParameter("uname");  
	        String upass=req.getParameter("upass");  
	          
	        String a=null;  
	        if("admin".equals(uname) && "123".equals(upass)){  
	            a="post登录成功，哈哈哈哈";  
	            System.out.println(a);  
	        }else{  
	            a="post登录失败";  
	            System.out.println(a);  
	        }  
	        PrintWriter pw=resp.getWriter();  
	        pw.write(a);  
	        pw.close();  
	    }  

}
