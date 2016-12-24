package com.p2p.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.p2p.dao.sys.OtherInfoDao;
import com.p2p.model.sys.OtherInfo;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class OtherInfoDaoImpl extends BaseDao<OtherInfo> implements OtherInfoDao {

	public OtherInfoDaoImpl() {
		super(OtherInfo.class);
	}

}
