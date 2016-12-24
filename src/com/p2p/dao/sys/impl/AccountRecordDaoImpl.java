package com.p2p.dao.sys.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.p2p.dao.sys.AccountRecordDao;
import com.p2p.model.sys.AccountRecord;
import com.p2p.model.sys.AccountRecord;


import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class AccountRecordDaoImpl extends BaseDao<AccountRecord> implements AccountRecordDao {

	public AccountRecordDaoImpl() {
		super(AccountRecord.class);
	}

	

}
