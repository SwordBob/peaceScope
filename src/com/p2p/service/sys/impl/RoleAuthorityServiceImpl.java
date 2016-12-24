package com.p2p.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.p2p.dao.sys.RoleAuthorityDao;
import com.p2p.model.sys.RoleAuthority;
import com.p2p.service.sys.RoleAuthorityService;

import core.service.BaseService;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class RoleAuthorityServiceImpl extends BaseService<RoleAuthority> implements RoleAuthorityService {

	private RoleAuthorityDao roleAuthorityDao;

	@Resource
	public void setRoleAuthorityDao(RoleAuthorityDao roleAuthorityDao) {
		this.roleAuthorityDao = roleAuthorityDao;
		this.dao = roleAuthorityDao;
	}

}
