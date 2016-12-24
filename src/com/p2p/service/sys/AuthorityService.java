package com.p2p.service.sys;

import java.util.List;

import com.p2p.model.sys.Authority;
import com.p2p.model.sys.RoleAuthority;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface AuthorityService extends Service<Authority> {

	List<Authority> queryByParentIdAndRole(Short role);

	List<Authority> queryChildrenByParentIdAndRole(Integer parentId, Short role);

	String querySurfaceAuthorityList(List<RoleAuthority> queryByProerties, Integer id, String buttons);

}
