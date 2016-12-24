package com.p2p.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.p2p.dao.sys.IncomeInfoDao;
import com.p2p.model.sys.IncomeInfo;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class IncomeInfoDaoImpl extends BaseDao<IncomeInfo> implements IncomeInfoDao {

	public IncomeInfoDaoImpl() {
		super(IncomeInfo.class);
	}

}
