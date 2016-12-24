package com.p2p.service.sys;

import java.util.List;


import com.p2p.model.sys.Borrow;

import core.service.Service;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface BorrowService extends Service<Borrow> {

	List<Borrow> getBorrowList(List<Borrow> resultList);
	
	List<Object[]> queryExportedBorrow(Integer[] ids);
	
}
