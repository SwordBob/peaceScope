package com.p2p.service.sys.impl;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.p2p.dao.sys.AuthorityDao;
import com.p2p.model.sys.Authority;
import com.p2p.model.sys.RoleAuthority;
import com.p2p.service.sys.AuthorityService;

import core.service.BaseService;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class AuthorityServiceImpl extends BaseService<Authority> implements AuthorityService {

	private AuthorityDao authorityDao;

	@Resource
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
		this.dao = authorityDao;
	}


	public List<Authority> queryByParentIdAndRole(Short role) {
		return authorityDao.queryByParentIdAndRole(role);
	}


	public List<Authority> queryChildrenByParentIdAndRole(Integer parentId, Short role) {
		return authorityDao.queryChildrenByParentIdAndRole(parentId, role);
	}


	public String querySurfaceAuthorityList(List<RoleAuthority> queryByProerties, Integer id, String buttons) {
		StringBuilder sb = new StringBuilder();
		String[] buttonsArray = buttons.split(",");
		for (RoleAuthority roleAuthority : queryByProerties) {
			if (!isNumeric(roleAuthority.getAuthorityId())) {
				for (int z = 0; z < buttonsArray.length; z++) {
					if ((id + buttonsArray[z]).equalsIgnoreCase(roleAuthority.getAuthorityId())) {
						sb.append(buttonsArray[z] + ",");
					}
				}
			}
		}
		return sb.toString();
	}

	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

}
