package com.p2p.dao.sys;

import java.util.List;


import com.p2p.model.sys.Borrow;


import core.dao.Dao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface BorrowDao extends Dao<Borrow> {
	List<Object[]> queryExportedBorrow(Integer[] ids);
	

}
