package com.p2p.service.sys;

import java.util.List;

import com.p2p.model.sys.RepaymentState;


import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface RepaymentStateService extends Service<RepaymentState> {

	List<RepaymentState> getRepaymentStateList(List<RepaymentState> resultList);

	
	
}
