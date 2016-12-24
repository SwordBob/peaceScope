package com.p2p.service.sys;

import java.util.List;

import com.p2p.model.sys.AccountRecord;


import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface AccountRecordService extends Service<AccountRecord> {

	List<AccountRecord> getAccountRecordList(List<AccountRecord> resultList);

	
	
}
