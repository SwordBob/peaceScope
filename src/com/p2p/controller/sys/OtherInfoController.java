package com.p2p.controller.sys;

import java.io.IOException;

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
import com.p2p.model.sys.Borrow;
import com.p2p.model.sys.OtherInfo;
import com.p2p.model.sys.CarInfo;
import com.p2p.model.sys.OtherInfo;
import com.p2p.model.sys.RepaymentState;
import com.p2p.model.sys.SysUser;
import com.p2p.model.sys.UserInfo;


import com.p2p.service.sys.AccountService;
import com.p2p.service.sys.BorrowService;
import com.p2p.service.sys.OtherInfoService;

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
@RequestMapping("/sys/otherInfo")
public class OtherInfoController extends p2pBaseController<OtherInfo> implements Constant {

	@Resource
	private OtherInfoService otherInfoService;
	@Resource
	private AccountService accountService;
	@Resource
	private BorrowService borrowService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * 2016-1-13下午3:56:26
	 * void
	 */
	@RequestMapping("/getOtherInfoName")
	public void getOtherInfoName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<OtherInfo> otherInfoList = otherInfoService.doQueryAll();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < otherInfoList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("OtherInfoText", otherInfoList.get(i).getOtherInfoName());
			//System.out.println(otherInfoList.get(i).getRealName());
			jsonObject.element("OtherInfoValue", otherInfoList.get(i).getOtherInfoId());
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
	@RequestMapping(value = "/getOtherInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void getOtherInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		Integer maxResults = Integer.valueOf(request.getParameter("limit"));
		String sortedObject = null;
		String sortedValue = null;
		List<LinkedHashMap<String, Object>> sortedList = mapper.readValue(request.getParameter("sort"), List.class);
		for (int i = 0; i < sortedList.size(); i++) {
			Map<String, Object> map = sortedList.get(i);
			sortedObject = (String) map.get("property");
			sortedValue = (String) map.get("direction");
		}
		OtherInfo otherInfo = new OtherInfo();
		
		String otherInfoId = request.getParameter("otherInfoId");
		if (StringUtils.isNotBlank(otherInfoId)) {
			int otherId=Integer.parseInt(otherInfoId);
			otherInfo.set$eq_otherInfoId(otherId);
		}
		String borrowId = request.getParameter("borrowId");
		if (StringUtils.isNotBlank(borrowId)) {
			int testId=Integer.parseInt(borrowId);
			otherInfo.set$eq_borrowId(testId);
		}
		String otherInfoName = request.getParameter("otherInfoName");
		if (StringUtils.isNotBlank(otherInfoName)) {
			otherInfo.set$like_otherInfoName(otherInfoName);
		}
		String auditState = request.getParameter("auditState");
		if (StringUtils.isNotBlank(auditState)) {
			otherInfo.set$like_auditState(auditState);
		}
		String auditor=request.getParameter("auditor");
		if (StringUtils.isNotBlank(auditor)) {				
			otherInfo.set$like_auditor(auditor);
		}
		otherInfo.setFirstResult(firstResult);
		otherInfo.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		otherInfo.setSortedConditions(sortedCondition);
		QueryResult<OtherInfo> queryResult = otherInfoService.doPaginationQuery(otherInfo);
		List<OtherInfo> otherInfoList = otherInfoService.getOtherInfoList(queryResult.getResultList());
		ListView<OtherInfo> otherInfoListView = new ListView<OtherInfo>();
		otherInfoListView.setData(otherInfoList);
		otherInfoListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, otherInfoListView);
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

	@Override
	@RequestMapping(value = "/saveOtherInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(OtherInfo entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		SysUser sysUser =(SysUser) request.getSession().getAttribute(SESSION_SYS_USER);
		Borrow borrow = borrowService.getByProerties("borrowId",entity.getBorrowId());
		if(borrow.getState().equals("初审通过")){
		if (entity.getAuditState().equals("复审通过")) {		
			entity.setAuditor(sysUser.getRealName());
			borrow.setState("投标中");
			borrowService.update(borrow);
		}
		entity.setBorrow(borrow);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			
			otherInfoService.update(entity);

		} else if (CMD_NEW.equals(parameter.getCmd())) {
			otherInfoService.persist(entity);
		}		
			parameter.setSuccess(true);			
		}else {
			parameter.setSuccess(false);
		}
		parameter.setCmd(CMD_EDIT);
		
		writeJSON(response, parameter);
	}

	@RequestMapping("/deleteOtherInfo")
	public void deleteOtherInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		if (Arrays.asList(ids).contains(Long.valueOf("1"))) {
			writeJSON(response, "{success:false,msg:'删除项包含超级管理员，超级管理员不能删除！'}");
		} else {
			boolean flag = otherInfoService.deleteByPK(ids);
			if (flag) {
				writeJSON(response, "{success:true}");
			} else {
				writeJSON(response, "{success:false}");
			}
		}
	}

	

}
