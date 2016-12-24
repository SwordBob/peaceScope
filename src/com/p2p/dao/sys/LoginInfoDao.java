package com.p2p.dao.sys;

import java.util.List;

import com.p2p.model.sys.LoginInfo;

import core.dao.Dao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface LoginInfoDao extends Dao<LoginInfo> {

	List<Object[]> doGetLoginInfoStatistics();

}
