package com.p2p.service.sys;

import java.util.List;

import com.p2p.model.sys.ReceivePlan;


import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface ReceivePlanService extends Service<ReceivePlan> {

	List<ReceivePlan> getReceivePlanList(List<ReceivePlan> resultList);

	
	
}
