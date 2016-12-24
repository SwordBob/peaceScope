package com.p2p.controller.sys;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.p2p.core.p2pBaseController;
import com.p2p.model.sys.RoleAuthority;
import com.p2p.service.sys.RoleAuthorityService;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Controller
@RequestMapping("/sys/roleauthority")
public class RoleAuthorityController extends p2pBaseController<RoleAuthority> {

	@Resource
	private RoleAuthorityService roleAuthorityService;
//关键
	@RequestMapping(value = "/saveRoleAuthority")
	public void saveRoleAuthority(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Short role = Short.valueOf(request.getParameter("role"));
		String ids = request.getParameter("ids");
		roleAuthorityService.deleteByProperties("role", role);
		String[] idsValue = ids.split(",");
		for (int i = 0; i < idsValue.length; i++) {
			RoleAuthority roleAuthority = new RoleAuthority();
			roleAuthority.setRole(role);
			roleAuthority.setAuthorityId(idsValue[i]);
			roleAuthorityService.persist(roleAuthority);
		}
		writeJSON(response, "{success:true}");
	}

}