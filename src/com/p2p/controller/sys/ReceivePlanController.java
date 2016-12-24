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

import com.p2p.model.sys.ReceivePlan;

import com.p2p.service.sys.ReceivePlanService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.QueryResult;
import core.util.p2pUtils;

/**
 * @author Yang Tian
 * @receiveState 1298588579@qq.com
 */
@Controller
@RequestMapping("/sys/receivePlan")
public class ReceivePlanController extends p2pBaseController<ReceivePlan> {

	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	
	@Resource
	private ReceivePlanService receivePlanService;
	/*@Resource
	private AttachmentService attachmentService;
*/
	@RequestMapping("/getReceivePlan")
	public void getReceivePlan(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		ReceivePlan receivePlan = new ReceivePlan();
		
		String receiveState = request.getParameter("receiveState");
		if (StringUtils.isNotBlank(receiveState)) {
			receivePlan.set$like_receiveState("%"+receiveState+"%");
			System.out.println("%"+receiveState+"%");
		}
		String receivePlanId = request.getParameter("receivePlanId");
		if (StringUtils.isNotBlank(receivePlanId)) {
			receivePlan.set$eq_receivePlanId(Integer.parseInt(receivePlanId));
		}
		String borrowId = request.getParameter("borrowId");
		if (StringUtils.isNotBlank(borrowId)) {
			int testId=Integer.parseInt(borrowId);
			receivePlan.set$eq_borrowId(testId);
		}
		String planDate = request.getParameter("planDate");
		if (StringUtils.isNotBlank(planDate)) {
			//String createTest="'"+sdfDate.format(sdfDate.parse(planDate))+"','yyyy/MM/dd'";
			String createTest=sdfDate.format(sdfDate.parse(planDate));
			//String newString ="to_date('"+createTest+"','yyyy-MM-dd')";
			//System.out.println(createTest);
			receivePlan.set$like_planDate(createTest);
		}
		receivePlan.setFirstResult(firstResult);
		receivePlan.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		receivePlan.setSortedConditions(sortedCondition);
		QueryResult<ReceivePlan> queryResult = receivePlanService.doQueryIncludeDate(receivePlan);
		List<ReceivePlan> receivePlanList = receivePlanService.getReceivePlanList(queryResult.getResultList());
		ListView<ReceivePlan> receivePlanListView = new ListView<ReceivePlan>();
		receivePlanListView.setData(receivePlanList);
		receivePlanListView.setTotalRecord(queryResult.getTotalCount());
		writeJSON(response, receivePlanListView);
	}

	@RequestMapping("/deleteReceivePlan")
	public void deleteReceivePlan(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Integer[] ids) throws IOException {
		boolean flag = receivePlanService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

	@Override
	@RequestMapping(value = "/saveReceivePlan", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(ReceivePlan entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			receivePlanService.update(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			receivePlanService.persist(entity);
		}
		/**
		 * 附件上传保存
		 */
		//attachmentService.deleteAttachmentByReceivePlanId(entity.getId());
		/*String[] content = entity.getDescription().split(" ");
		for (int i = 0; i < content.length; i++) {
			if (content[i].indexOf("/static/img/upload/") != -1) {
				Attachment attachment = new Attachment();
				attachment.setFileName(entity.getName());
				attachment.setFilePath(content[i].substring(content[i].indexOf("2"), content[i].lastIndexOf("\"")));
				attachment.setReceivePlan(entity);
				attachmentService.persist(attachment);
			}
		}*/
		parameter.setCmd(CMD_EDIT);
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	@RequestMapping("/getReceivePlanById")
	public void getReceivePlanById(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id) throws Exception {
		ReceivePlan receivePlan = receivePlanService.get(id);
		writeJSON(response, receivePlan);
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	@RequestMapping(value = "/uploadAttachement", method = RequestMethod.POST)
	public void uploadAttachement(@RequestParam(value = "uploadAttachment", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		JSONObject json = new JSONObject();
		if (!file.isEmpty()) {
			if (file.getSize() > 2097152) {
				json.put("msg", requestContext.getMessage("g_fileTooLarge"));
			} else {
				try {
					String originalFilename = file.getOriginalFilename();
					String fileName = sdf.format(new Date()) + p2pUtils.getRandomString(3) + originalFilename.substring(originalFilename.lastIndexOf("."));
					File filePath = new File(getClass().getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/static/img/upload/" + DateFormatUtils.format(new Date(), "yyyyMM")));
					if (!filePath.exists()) {
						filePath.mkdirs();
					}
					file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
					json.put("success", true);
					json.put("data", DateFormatUtils.format(new Date(), "yyyyMM") + "/" + fileName);
					json.put("msg", requestContext.getMessage("g_uploadSuccess"));
				} catch (Exception e) {
					e.printStackTrace();
					json.put("msg", requestContext.getMessage("g_uploadFailure"));
				}
			}
		} else {
			json.put("msg", requestContext.getMessage("g_uploadNotExists"));
		}
		writeJSON(response, json.toString());
	}

}
