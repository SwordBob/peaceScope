package com.p2p.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.p2p.dao.sys.RepaymentStateDao;
import com.p2p.model.sys.RepaymentState;


import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class RepaymentStateDaoImpl extends BaseDao<RepaymentState> implements RepaymentStateDao {

	public RepaymentStateDaoImpl() {
		super(RepaymentState.class);
	}
	
	

}
