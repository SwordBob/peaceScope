package com.p2p.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.p2p.dao.sys.InvestmentDao;
import com.p2p.model.sys.Investment;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class InvestmentDaoImpl extends BaseDao<Investment> implements InvestmentDao {

	public InvestmentDaoImpl() {
		super(Investment.class);
	}

}
