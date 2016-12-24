package com.p2p.controller.sys;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.p2p.core.p2pBaseController;
import com.p2p.model.sys.CarInfo;
import com.p2p.model.sys.OtherInfo;
import com.p2p.model.sys.RepaymentState;
import com.p2p.model.sys.SysUser;
import com.p2p.model.sys.UserInfo;
import com.p2p.model.sys.Borrow;

import com.p2p.service.sys.CarInfoService;
import com.p2p.service.sys.OtherInfoService;
import com.p2p.service.sys.UserInfoService;
import com.p2p.service.sys.BorrowService;
import com.p2p.service.sys.RepaymentStateService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.QueryResult;
import core.util.p2pUtils;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Controller
@RequestMapping("/sys/borrow")
public class BorrowController extends p2pBaseController<Borrow> {

	private static final Logger log = Logger.getLogger(BorrowController.class);
	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

	@Resource
	private BorrowService borrowService;
	@Resource
	private RepaymentStateService repaymentStateService;
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private CarInfoService carInfoService;
	@Resource
	private OtherInfoService otherInfoService;
	

	@RequestMapping(value = "/getBorrow", method = { RequestMethod.POST, RequestMethod.GET })
	public void getBorrow(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Integer firstResult = Integer.valueOf(request.getParameter("start"));
		Integer maxResults = Integer.valueOf(request.getParameter("limit"));
		String sortedObject = null;
		String sortedValue = null;
		String repaymentEnd="";
		Integer userId=0;
		String state=request.getParameter("state");
		String title = request.getParameter("title");
		String userInfoPhone = request.getParameter("userInfoPhone");
		String userIdTemp = request.getParameter("userId");
		if (StringUtils.isNotBlank(userIdTemp)){
			userId=Integer.parseInt(userIdTemp);
		}
		List<LinkedHashMap<String, Object>> sortedList = mapper.readValue(request.getParameter("sort"), List.class);
		for (int i = 0; i < sortedList.size(); i++) {
			Map<String, Object> map = sortedList.get(i);
			sortedObject = (String) map.get("property");
			// System.out.println(sortedObject);
			sortedValue = (String) map.get("direction");
		}
		Borrow borrow = new Borrow();
		ok: if (!sortedList.isEmpty()) {
			
			if (StringUtils.isNotBlank(userInfoPhone)) {
				UserInfo userInfo = userInfoService.getByProerties("phone", userInfoPhone);
				if (userInfo != null) {
					 userId = userInfo.getUserId();
					// Integer testInteger=Integer.valueOf(sysUser.getId());
					borrow.set$eq_userId(userId);
				} else {
					borrow.set$eq_userId(0);
					break ok;
				}
			}
			
			if (StringUtils.isNotBlank(state)) {				
				borrow.set$like_state(state);
			}
			 repaymentEnd = request.getParameter("repaymentEnd");
			if (StringUtils.isNotBlank(repaymentEnd)) {
				//System.out.println();
				RepaymentState repaymentState = repaymentStateService.getByProerties("repaymentEnd", sdfDate.parse(repaymentEnd));
				if (repaymentState!=null)
				borrow.set$eq_repayId(repaymentState.getRepaymentStateId());
			}

			
			if (StringUtils.isNotBlank(userIdTemp)) {
				userId=Integer.parseInt(userIdTemp);
				borrow.set$eq_userId(userId);
			}
			
			if (StringUtils.isNotBlank(title)) {

				borrow.set$like_title(title);
			}
		}
		borrow.setFirstResult(firstResult);
		borrow.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		borrow.setSortedConditions(sortedCondition);
		//连接至processQuery，核心
		QueryResult<Borrow> queryResult = borrowService.doPaginationQuery(borrow);
		// System.out.println(queryResult.getResultList());
		//List<Borrow> borrowList = borrowService.getBorrowList(queryResult.getResultList());
		List<Borrow> borrowList = new ArrayList<Borrow>();
		ListView<Borrow> borrowListView = new ListView<Borrow>();
		if (StringUtils.isNotBlank(repaymentEnd)) {
		List<RepaymentState> repaymentStates=repaymentStateService.queryByLikeProerties("$like_repaymentEnd",sdfDate.format(sdfDate.parse(repaymentEnd)));
		//Borrow borrow2=null;		
		ok:for (int i = 0; i < repaymentStates.size(); i++) {
			borrow=borrowService.getByProerties("repayId", repaymentStates.get(i).getRepaymentStateId());
			borrow.set$eq_repayId(repaymentStates.get(i).getRepaymentStateId());
			if(userId!=0)
			borrow.set$eq_userId(userId);
			if(StringUtils.isNotBlank(state))
			borrow.set$like_state(state);
			if(StringUtils.isNotBlank(title))
				borrow.set$like_title(title);
			if (StringUtils.isNotBlank(userInfoPhone)) 
				if (StringUtils.isNotBlank(userInfoPhone)) {
					UserInfo userInfo = userInfoService.getByProerties("phone", userInfoPhone);
					if (userInfo != null) {
						 userId = userInfo.getUserId();
						// Integer testInteger=Integer.valueOf(sysUser.getId());
						borrow.set$eq_userId(userId);
					} else {
						borrow.set$eq_userId(0);
						break ok;
					}
				}
			borrow.setFirstResult(firstResult);
			borrow.setMaxResults(maxResults);
			sortedCondition.put(sortedObject, sortedValue);
			borrow.setSortedConditions(sortedCondition);
			QueryResult<Borrow> qResult = borrowService.doPaginationQuery(borrow);
			for(int j=0;j<qResult.getResultList().size();j++) {
				List<Borrow> lBorrows = borrowService.getBorrowList(qResult.getResultList());
				//lBorrows.add(queryResult1.getResultList().get(0));
				borrowList.add(lBorrows.get(0));
				
			}
		}
		}else {
			borrowList = borrowService.getBorrowList(queryResult.getResultList());
		}
		//borrowList.add(borrow2);
		borrowListView.setData(borrowList);

		borrowListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, borrowListView);
	}

	
	
	
	@RequestMapping("/getRepaymentDate")
	public void getRepaymentStateName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<RepaymentState> repaymentStateList = repaymentStateService.doQueryAll();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < repaymentStateList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("ItemText", repaymentStateList.get(i).getRepaymentDate());
			jsonObject.element("ItemValue", repaymentStateList.get(i).getRepaymentStateId());
			jsonArray.add(jsonObject);
		}
		JSONObject resultJSONObject = new JSONObject();
		resultJSONObject.element("list", jsonArray);
		writeJSON(response, resultJSONObject);
	}

	@Override
	@RequestMapping(value = "/saveBorrow", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(Borrow entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		Borrow borrow = borrowService.getByProerties("borrowId", entity.getBorrowId());
		if (entity.getRepayId()!=null) {				
		RepaymentState repaymentState = repaymentStateService.get(entity.getRepayId());
		entity.setRepaymentState(repaymentState);
		}
		if (entity.getUserId()!=null) {
			UserInfo userInfo = userInfoService.get(entity.getUserId());
			entity.setUserInfo(userInfo);			
		}
		//if (entity.getCarInfoId()!=null) {
		List<OtherInfo> otherInfos=otherInfoService.queryByProerties("borrowId", entity.getBorrowId());
		System.out.println(otherInfos.size());
		Set<OtherInfo> otherInfoSet=new HashSet<OtherInfo>();	
		CarInfo carInfo = carInfoService.get(entity.getCarInfoId());
			carInfo.setAuditDate(new Date());				
			if(!borrow.getState().equals(entity.getState())){
				SysUser sysUser =(SysUser) request.getSession().getAttribute(SESSION_SYS_USER);
				carInfo.setAuditor(sysUser.getRealName());
				carInfoService.update(carInfo);
				for(int i=0;i<otherInfos.size();i++){
					OtherInfo otherInfo=otherInfos.get(i);
					otherInfo.setAuditState("初审通过");
					otherInfo.setAuditor(sysUser.getRealName());
					otherInfoService.update(otherInfo);
					otherInfoSet.add(otherInfo);
				}
							
			}
			entity.setOtherInfo(otherInfoSet);
			entity.setCarInfo(carInfo);
			
		//}
		
		
		// System.out.println(userInfo.getRealName());
		if (CMD_EDIT.equals(parameter.getCmd())) {
			entity.setBorrowDate(borrow.getBorrowDate());
			borrowService.update(entity);

		} else if (CMD_NEW.equals(parameter.getCmd())) {
			borrowService.persist(entity);
		}
		parameter.setCmd(CMD_EDIT);
		parameter.setSuccess(true);
		
		writeJSON(response, parameter);
	}

	

	

}
