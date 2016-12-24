package com.p2p.dao.sys.impl;


import java.util.List;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.p2p.dao.sys.BorrowDao;

import com.p2p.model.sys.Borrow;


import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class BorrowDaoImpl extends BaseDao<Borrow> implements BorrowDao {

	public BorrowDaoImpl() {
		super(Borrow.class);
	}
	

	public List<Object[]> queryExportedBorrow(Integer[] ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sb.append(ids[i] + ",");
		}
		Query query = getSession().createSQLQuery(
				"select f.epc_id,f.name fn,f.plant_time,f.entry_time,ft.name ftn from p2p_type ft,i_borrow f where ft.id = f.repay_id and f.id in (" + sb.deleteCharAt(sb.toString().length() - 1).toString() + ")");
		return query.list();
	}

}
