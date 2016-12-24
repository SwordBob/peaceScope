package com.p2p.controller.sys;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.p2p.core.Constant;
import com.p2p.core.p2pBaseController;
import com.p2p.model.sys.Account;
import com.p2p.model.sys.Authz;
import com.p2p.model.sys.UserInfo;
import com.p2p.model.sys.Contacts;
import com.p2p.model.sys.RepaymentState;
import com.p2p.model.sys.SysUser;
import com.p2p.model.sys.UserInfo;


import com.p2p.service.sys.AccountService;
import com.p2p.service.sys.AuthzService;
import com.p2p.service.sys.ContactsService;
import com.p2p.service.sys.UserInfoService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.Item;
import core.support.QueryResult;
import core.util.MD5;
import core.web.SystemCache;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Controller
@RequestMapping("/sys/userInfo")
public class UserInfoController extends p2pBaseController<UserInfo> implements Constant {

	@Resource
	private UserInfoService userInfoService;
	@Resource
	private AccountService accountService;
	@Resource
	private AuthzService authzService;
	@Resource
	private ContactsService contactsService;

	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	
/*
	@RequestMapping("/resetPassword")
	public void resetPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = request.getParameter("userId");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = userInfoService.getByProerties("userId", userId);
		if (!userInfo.getPassword().equals(MD5.crypt(oldPassword))) {
			result.put("result", -2);
			writeJSON(response, result);
			return;
		}
		result.put("result", 1);
		userInfo.setPassword(MD5.crypt(newPassword));
		userInfoService.update(userInfo);
		writeJSON(response, result);
	}
*/
	@RequestMapping("/externalVerifyUserInfo")
	public void externalVerifyUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserInfo userInfo = userInfoService.getByProerties(new String[] { "userId", "password" }, new Object[] { username, MD5.crypt(password) });
		if (null == userInfo) {
			writeJSON(response, "{success:false}");
		} else {
			writeJSON(response, "{success:true}");
		}
	}
	
	
	@Override
	@RequestMapping(value = "/saveUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(UserInfo entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		UserInfo userInfo = userInfoService.getByProerties("userId", entity.getUserId());
		if (entity.getAccountId()!=null) {				
			Account account = accountService.get(entity.getAccountId());
			entity.setAccount(account);
		}
		if (entity.getAuthzId()!=null) {
			Authz authz = authzService.get(entity.getAuthzId());
			entity.setAuthz(authz);			
		}
		if (entity.getContactId()!=null) {
			Contacts contacts = contactsService.get(entity.getContactId());										
//				SysUser sysUser =(SysUser) request.getSession().getAttribute(SESSION_SYS_USER);
//				contacts.setAuditor(sysUser.getRealName());
//				contactsService.update(contacts);
			entity.setContacts(contacts);
			
		}
		
		
		// System.out.println(userInfo.getRealName());
		if (CMD_EDIT.equals(parameter.getCmd())) {
			entity.setCreateDate(userInfo.getCreateDate());
			userInfoService.update(entity);

		} else if (CMD_NEW.equals(parameter.getCmd())) {
			userInfoService.persist(entity);
		}
		parameter.setCmd(CMD_EDIT);
		parameter.setSuccess(true);
		
		writeJSON(response, parameter);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * 2016-1-13下午3:56:26
	 * void
	 */
	@RequestMapping("/getUserInfoName")
	public void getUserInfoName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<UserInfo> userInfoList = userInfoService.doQueryAll();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < userInfoList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("UserNameText", userInfoList.get(i).getPhone());
			//System.out.println(userInfoList.get(i).getRealName());
			jsonObject.element("UserNameValue", userInfoList.get(i).getUserId());
			jsonArray.add(jsonObject);
		}
		JSONObject resultJSONObject = new JSONObject();
		resultJSONObject.element("list", jsonArray);
		writeJSON(response, resultJSONObject);
	}
	/**
	 * 模糊查询
	 * @param request
	 * @param response
	 * @throws Exception
	 * 2016-1-13下午3:56:49
	 * void
	 */
	@RequestMapping(value = "/getUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		Integer maxResults = Integer.valueOf(request.getParameter("limit"));
		String sortedObject = null;
		String sortedValue = null;
		QueryResult<UserInfo> queryResult=null;
		List<UserInfo> userInfoList=null;
		List<LinkedHashMap<String, Object>> sortedList = mapper.readValue(request.getParameter("sort"), List.class);
		for (int i = 0; i < sortedList.size(); i++) {
			Map<String, Object> map = sortedList.get(i);
			sortedObject = (String) map.get("property");
			sortedValue = (String) map.get("direction");
		}
		UserInfo userInfo = new UserInfo();
		String userId = request.getParameter("userId");
		if (StringUtils.isNotBlank(userId)) {
			userInfo.set$eq_userId(Integer.parseInt(userId));
		}
//		String createDate = request.getParameter("createDate");
//		if (StringUtils.isNotBlank(createDate)) {
//			System.out.println(sdfDate.format(sdfDate.parse(createDate)));
//		String createTest="to_date('"+sdfDate.format(sdfDate.parse(createDate))+"','yyyy/MM/dd')";
//			userInfo.set$like_createDate(createTest);
//		}
			
		String phone = request.getParameter("phone");
		if (StringUtils.isNotBlank(phone)) {
			userInfo.set$like_phone("%"+phone+"%");
		}
		String email = request.getParameter("email");
		if (StringUtils.isNotBlank(email)) {
			userInfo.set$like_email("%"+email+"%");
			System.out.println("%"+email+"%");
		}
		String accountId = request.getParameter("accountId");
		if (StringUtils.isNotBlank(accountId)) {
			userInfo.set$eq_accountId(Integer.parseInt(accountId));
		}
		
		userInfo.setFirstResult(firstResult);
		userInfo.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		userInfo.setSortedConditions(sortedCondition);	
		String createDate = request.getParameter("createDate");
		if (StringUtils.isNotBlank(createDate)) {
			//String createTest="'"+sdfDate.format(sdfDate.parse(createDate))+"','yyyy/MM/dd'";
			String createTest=sdfDate.format(sdfDate.parse(createDate));
			//String newString ="to_date('"+createTest+"','yyyy-MM-dd')";
			//System.out.println(createTest);
			userInfo.set$like_createDate(createTest);
			queryResult = userInfoService.doQueryIncludeDate(userInfo);
			userInfoList = userInfoService.getUserInfoList(queryResult.getResultList());
			
		}else{	
		queryResult = userInfoService.doPaginationQuery(userInfo);
		userInfoList = userInfoService.getUserInfoList(queryResult.getResultList());
		}
		System.out.println(queryResult.toString());
		ListView<UserInfo> userInfoListView = new ListView<UserInfo>();
		//userInfoList.add(queryResult.getResultList().get(0));
		userInfoListView.setData(userInfoList);
		userInfoListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, userInfoListView);
	}

	@RequestMapping("/getAccountId")
	public void getAccountId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Account> accountList = accountService.doQueryAll();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < accountList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("AccountText", accountList.get(i).getAccountId());
			//System.out.println(accountList.get(i).);
			jsonObject.element("AccountValue", accountList.get(i).getAccountId());
			jsonArray.add(jsonObject);
		}
		JSONObject resultJSONObject = new JSONObject();
		resultJSONObject.element("list", jsonArray);
		writeJSON(response, resultJSONObject);
	}
	
	@RequestMapping("/deleteUserInfo")
	public void deleteUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		if (Arrays.asList(ids).contains(Long.valueOf("1"))) {
			writeJSON(response, "{success:false,msg:'删除项包含超级管理员，超级管理员不能删除！'}");
		} else {
			boolean flag = userInfoService.deleteByPK(ids);
			if (flag) {
				writeJSON(response, "{success:true}");
			} else {
				writeJSON(response, "{success:false}");
			}
		}
	}

	/*@RequestMapping(value = "/getRoleNameList")
	public void getRoleNameList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List emailNameList = new ArrayList();
		for (Map.Entry<String, Item> emailMap : SystemCache.DICTIONARY.get("SYSUSER_ROLE").getItems().entrySet()) {
			Item item = emailMap.getValue();
			UserInfo userInfo = new UserInfo();
			userInfo.setRole(Integer.parseInt(item.getKey()));
			userInfo.setRoleName(item.getValue());
			emailNameList.add(userInfo);
		}
		ListView emailNameListView = new ListView();
		emailNameListView.setData(emailNameList);
		emailNameListView.setTotalRecord(emailNameList.size());
		writeJSON(response, emailNameListView);
	}*/

}
