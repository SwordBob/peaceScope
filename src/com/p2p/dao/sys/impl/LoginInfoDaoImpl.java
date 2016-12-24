package com.p2p.dao.sys.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.p2p.dao.sys.LoginInfoDao;
import com.p2p.model.sys.LoginInfo;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class LoginInfoDaoImpl extends BaseDao<LoginInfo> implements LoginInfoDao {

	public LoginInfoDaoImpl() {
		super(LoginInfo.class);
	}


	public List<Object[]> doGetLoginInfoStatistics() {
		// MySQL
		//Query query = getSession().createSQLQuery("select concat(date_format(record_time, '%H'),':00') name,round(avg(sensor_value),2) data1,max(sensor_value) mx,min(sensor_value) mn,cast(group_concat(sensor_value) as char) gc from sensor_data sd where sensor_type = ? group by date_format(record_time, '%Y-%m-%d %H')"); 
		// Oracle
		//wm_concat(userid)获取全部数值
		 Query query = getSession().createSQLQuery("select concat(to_char(logintime, 'yyyy-mm-dd hh24'),':00') ltime,wm_concat(userid) gc,count(userid) mount1 from p2p_logininfo group by to_char(logintime, 'yyyy-mm-dd hh24')");
		// SQL Server
		// Query query = getSession().createSQLQuery("select CONVERT(varchar(12) ,record_time, 114) name,round(avg(sensor_value),2) data1,max(sensor_value) mx,min(sensor_value) mn,'' gc from sensor_data sd where sensor_type = ? group by CONVERT(varchar(12) ,record_time, 114)");
		
		return query.list();
	}

}
