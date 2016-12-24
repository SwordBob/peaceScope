package com.p2p.service.sys;

import java.util.List;

import com.p2p.model.sys.Investment;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface InvestmentService extends Service<Investment> {

	List<Investment> getInvestmentList(List<Investment> resultList);

}
