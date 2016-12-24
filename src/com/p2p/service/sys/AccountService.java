package com.p2p.service.sys;

import java.util.List;

import com.p2p.model.sys.Account;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface AccountService extends Service<Account> {

	List<Account> getAccountList(List<Account> resultList);

}
