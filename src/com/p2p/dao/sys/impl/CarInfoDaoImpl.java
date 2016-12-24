package com.p2p.dao.sys.impl;

import org.springframework.stereotype.Repository;


import com.p2p.dao.sys.CarInfoDao;

import com.p2p.model.sys.CarInfo;


import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class CarInfoDaoImpl extends BaseDao<CarInfo> implements CarInfoDao {

	public CarInfoDaoImpl() {
		super(CarInfo.class);
	}

}
