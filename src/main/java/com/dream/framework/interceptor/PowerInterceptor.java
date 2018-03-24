package com.dream.framework.interceptor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dream.brick.admin.bean.Operation;
import com.dream.brick.admin.bean.Role;
import com.dream.brick.admin.bean.User;
import com.dream.brick.admin.dao.IOperationDao;


/**
 * 权限验证径拦截器
 * 
 * @author maolei
 * 
 */
public class PowerInterceptor implements HandlerInterceptor {

	@Resource
	private IOperationDao operationDao;

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		if(1==1){
			return true;
		}
		HttpSession session = request.getSession();
		String cls = request.getParameter("cls");
		String URI = request.getRequestURI().trim();
		User user = (User) session.getAttribute("admin");
		List<Operation> opList =null;
		Map<String,List<Operation>> operationMap=null;
		//每个模块对应的一组权限
		if(session.getAttribute("operationMap")!=null){
			operationMap=(Map<String,List<Operation>>)session.getAttribute("operationMap");
		}else{
			operationMap=new HashMap<String,List<Operation>>();
			session.setAttribute("operationMap",operationMap);
		}
		if (user != null && !"admin".equals(user.getName())) {
			Set<String> powerSet = new HashSet<String>();
			if(StringUtils.isNotBlank(cls)){
				if(operationMap.containsKey(cls)){
					//已经存在的不需要查询
					opList=operationMap.get(cls);
				}else{
					opList = operationDao.query("from Operation where cls = ?", 0, 0,cls);
					operationMap.put(cls,opList);
					//查询某个模块对应的所有权限，第一次进入的时候需要查询
					//这里做了一个简单的缓存
				}
				for (Role r : user.getRoles()) {
					System.out.println(r.getOperations());
					for (Operation op : r.getOperations()) {
						powerSet.add(op.getCls() + op.getOpt());
						//加到权限组中
					}
				}
			}else{
				cls="";
				if (session.getAttribute("opList") == null) {
					opList = operationDao.list();
					session.setAttribute("opList", opList);
				} else {
					opList = (List<Operation>) session.getAttribute("opList");
				}
				for (Role r : user.getRoles()) {
					if(r.getOperations()==null){
						continue;
					}
					for (Operation op : r.getOperations()) {
						powerSet.add(op.getOpt());
						//添加到权限组中
					}
				}
			}

			
			
			for (Operation o : opList) {
				if (URI.contains(o.getOpt())) {
					//说明是当前请求的路径
					if (powerSet.contains(cls+o.getOpt())){
						//包括权限
						return true;
					}
					response.sendRedirect(request.getAttribute("basePath")
							+ "/view/commons/noPower.jsp");
					return false;
				}
			}
			powerSet=null;
		}
		return true;
	}
}
