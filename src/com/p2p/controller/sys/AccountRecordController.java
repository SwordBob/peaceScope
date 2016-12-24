package com.p2p.controller.sys;

import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.p2p.core.p2pBaseController;

import com.p2p.model.sys.AccountRecord;

import com.p2p.service.sys.AccountRecordService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.QueryResult;
import core.util.p2pUtils;


@Controller
@RequestMapping("/sys/accountRecord")
public class AccountRecordController extends p2pBaseController<AccountRecord> {

	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	
	@Resource
	private AccountRecordService accountRecordService;
	
	@RequestMapping("/getAccountRecord")
	public void getAccountRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		AccountRecord accountRecord = new AccountRecord();
		
		String tradeProcesses = request.getParameter("tradeProcesses");
		if (StringUtils.isNotBlank(tradeProcesses)) {
			accountRecord.set$like_tradeProcesses("%"+tradeProcesses+"%");
			System.out.println("%"+tradeProcesses+"%");
		}
		String accountRecordId = request.getParameter("accountRecordId");
		if (StringUtils.isNotBlank(accountRecordId)) {
			accountRecord.set$eq_accountRecordId(Integer.parseInt(accountRecordId));
		}
		String accountId = request.getParameter("accountId");
		if (StringUtils.isNotBlank(accountId)) {
			int testId=Integer.parseInt(accountId);
			accountRecord.set$eq_accountId(testId);
		}
		String tradeTime = request.getParameter("tradeTime");
		if (StringUtils.isNotBlank(tradeTime)) {
			//String createTest="'"+sdfDate.format(sdfDate.parse(tradeTime))+"','yyyy/MM/dd'";
			String createTest=sdfDate.format(sdfDate.parse(tradeTime));
			//String newString ="to_date('"+createTest+"','yyyy-MM-dd')";
			//System.out.println(createTest);
			accountRecord.set$like_tradeTime(createTest);
		}
		accountRecord.setFirstResult(firstResult);
		accountRecord.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		accountRecord.setSortedConditions(sortedCondition);
		QueryResult<AccountRecord> queryResult = accountRecordService.doQueryIncludeDate(accountRecord);
		List<AccountRecord> accountRecordList = accountRecordService.getAccountRecordList(queryResult.getResultList());
		ListView<AccountRecord> accountRecordListView = new ListView<AccountRecord>();
		accountRecordListView.setData(accountRecordList);
		accountRecordListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, accountRecordListView);
	}

	

	@Override
	@RequestMapping(value = "/saveAccountRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(AccountRecord entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			accountRecordService.update(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			accountRecordService.persist(entity);
		}
		
		parameter.setCmd(CMD_EDIT);
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	@RequestMapping("/getAccountRecordById")
	public void getAccountRecordById(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id) throws Exception {
		AccountRecord accountRecord = accountRecordService.get(id);
		writeJSON(response, accountRecord);
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");



}
