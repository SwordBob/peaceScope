package com.p2p.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.p2p.dao.sys.UserInfoDao;
import com.p2p.model.sys.UserInfo;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class UserInfoDaoImpl extends BaseDao<UserInfo> implements UserInfoDao {

	public UserInfoDaoImpl() {
		super(UserInfo.class);
	}

}
