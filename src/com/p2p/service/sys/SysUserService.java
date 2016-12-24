package com.p2p.service.sys;

import java.util.List;


import com.p2p.model.sys.SysUser;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface SysUserService extends Service<SysUser> {

	List<SysUser> getSysUserList(List<SysUser> resultList);
	public void getSysMoney();

}
