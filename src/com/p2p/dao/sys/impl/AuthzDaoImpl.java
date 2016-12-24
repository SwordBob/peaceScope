package com.p2p.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.p2p.dao.sys.AuthzDao;
import com.p2p.model.sys.Authz;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class AuthzDaoImpl extends BaseDao<Authz> implements AuthzDao {

	public AuthzDaoImpl() {
		super(Authz.class);
	}

}
