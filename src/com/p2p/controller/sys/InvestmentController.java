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
import com.p2p.model.sys.Investment;
import com.p2p.model.sys.CarInfo;
import com.p2p.model.sys.Investment;
import com.p2p.model.sys.RepaymentState;
import com.p2p.model.sys.UserInfo;


import com.p2p.service.sys.AccountService;
import com.p2p.service.sys.BorrowService;
import com.p2p.service.sys.InvestmentService;
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
@RequestMapping("/sys/investment")
public class InvestmentController extends p2pBaseController<Investment> implements Constant {

	@Resource
	private InvestmentService investmentService;
	@Resource
	private AccountService accountService;
	@Resource
	private BorrowService borrowService;
	@Resource
	private UserInfoService userInfoService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * 2016-1-13下午3:56:26
	 * void
	 */
	/*@RequestMapping("/getInvestmentName")
	public void getInvestmentName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Investment> investmentList = investmentService.doQueryAll();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < investmentList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("InvestmentText", investmentList.get(i).getInvestmentName());
			//System.out.println(investmentList.get(i).getRealName());
			jsonObject.element("InvestmentValue", investmentList.get(i).getInvestmentId());
			jsonArray.add(jsonObject);
		}
		JSONObject resultJSONObject = new JSONObject();
		resultJSONObject.element("list", jsonArray);
		writeJSON(response, resultJSONObject);
	}*/
	/**
	 * 模糊查询
	 * @param request
	 * @param response
	 * @throws Exception
	 * 2016-1-13下午3:56:49
	 * void
	 */
	@RequestMapping(value = "/getInvestment", method = { RequestMethod.POST, RequestMethod.GET })
	public void getInvestment(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		Investment investment = new Investment();
		
		String investmentId = request.getParameter("investmentId");
		if (StringUtils.isNotBlank(investmentId)) {
			int investId=Integer.parseInt(investmentId);
			investment.set$eq_investmentId(investId);
		}
		String userId = request.getParameter("userId");
		if (StringUtils.isNotBlank(userId)) {
			int uId=Integer.parseInt(userId);
			investment.set$eq_userId(uId);
		}
		String borrowId = request.getParameter("borrowId");
		if (StringUtils.isNotBlank(borrowId)) {
			int testId=Integer.parseInt(borrowId);
			investment.set$eq_borrowId(testId);
		}
		
		String title = request.getParameter("title");
		if (StringUtils.isNotBlank(title)) {
			List<Borrow> list=borrowService.queryByLikeProerties("title", "%"+title+"%");
			if (!(list.isEmpty())) {				
			Borrow borrow=list.get(0);
			investment.set$eq_borrowId(borrow.getBorrowId());
			}else {
				investment.set$eq_borrowId(0);
			}
		}
		/*
		String investmentName = request.getParameter("investmentName");
		if (StringUtils.isNotBlank(investmentName)) {
			investment.set$like_investmentName(investmentName);
		}
		String auditor=request.getParameter("auditor");
		if (StringUtils.isNotBlank(auditor)) {				
			investment.set$like_auditor(auditor);
		}*/
		String phone = request.getParameter("phone");
		if (StringUtils.isNotBlank(phone)) {
			List<UserInfo> list=userInfoService.queryByLikeProerties("phone", "%"+phone+"%");
			if (!(list.isEmpty())) {				
			UserInfo userInfo=list.get(0);
			investment.set$eq_userId(userInfo.getUserId());
			}else {
				investment.set$eq_userId(0);
			}
		}
		investment.setFirstResult(firstResult);
		investment.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		investment.setSortedConditions(sortedCondition);
		QueryResult<Investment> queryResult = investmentService.doPaginationQuery(investment);
		List<Investment> investmentList = investmentService.getInvestmentList(queryResult.getResultList());
		ListView<Investment> investmentListView = new ListView<Investment>();
		investmentListView.setData(investmentList);
		investmentListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, investmentListView);
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
	@RequestMapping(value = "/saveInvestment", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(Investment entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		
		Borrow borrow = borrowService.getByProerties("borrowId",entity.getBorrowId());
		
		entity.setBorrow(borrow);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			
			investmentService.update(entity);

		} else if (CMD_NEW.equals(parameter.getCmd())) {
			investmentService.persist(entity);
		}
		parameter.setCmd(CMD_EDIT);
		parameter.setSuccess(true);
		
		writeJSON(response, parameter);
	}

	@RequestMapping("/deleteInvestment")
	public void deleteInvestment(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		if (Arrays.asList(ids).contains(Long.valueOf("1"))) {
			writeJSON(response, "{success:false,msg:'删除项包含超级管理员，超级管理员不能删除！'}");
		} else {
			boolean flag = investmentService.deleteByPK(ids);
			if (flag) {
				writeJSON(response, "{success:true}");
			} else {
				writeJSON(response, "{success:false}");
			}
		}
	}

	

}
