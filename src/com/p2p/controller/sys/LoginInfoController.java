package com.p2p.controller.sys;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.p2p.core.p2pBaseController;
import com.p2p.model.sys.LoginInfo;
import com.p2p.service.sys.LoginInfoService;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Controller
@RequestMapping("/sys/loginInfo")
public class LoginInfoController extends p2pBaseController<LoginInfo> {

	@Resource
	private LoginInfoService loginInfoService;

	@RequestMapping(value = "/getLoginInfoStatistics")
	public void getLoginInfoStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Object[]> list = loginInfoService.doGetLoginInfoStatistics();
		List<Object[]> enhanceList = loginInfoService.doGetEnhanceLoginInfoStatistics(list);
		writeJSON(response, enhanceList);
	}

}
