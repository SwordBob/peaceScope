package com.p2p.dao.sys.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.p2p.dao.sys.ReceivePlanDao;
import com.p2p.model.sys.ReceivePlan;
import com.p2p.model.sys.ReceivePlan;


import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class ReceivePlanDaoImpl extends BaseDao<ReceivePlan> implements ReceivePlanDao {

	public ReceivePlanDaoImpl() {
		super(ReceivePlan.class);
	}

	

}
