package com.p2p.service.sys;

import java.util.List;

import com.p2p.model.sys.IncomeInfo;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface IncomeInfoService extends Service<IncomeInfo> {

	List<IncomeInfo> getIncomeInfoList(List<IncomeInfo> resultList);

}
