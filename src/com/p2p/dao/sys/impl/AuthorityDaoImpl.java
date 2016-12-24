package com.p2p.dao.sys.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.p2p.dao.sys.AuthorityDao;
import com.p2p.model.sys.Authority;

import core.dao.BaseDao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class AuthorityDaoImpl extends BaseDao<Authority> implements AuthorityDao {

	public AuthorityDaoImpl() {
		super(Authority.class);
	}

	
	public List<Authority> queryByParentIdAndRole(Short role) {
		// MySQL
		//SQLQuery query = getSession().createSQLQuery("select distinct a.* from authority a,role_authority ra where a.id = ra.authority_id and a.parent_id is null and ra.role = ?"); 
		// Oracle
		 SQLQuery query = getSession().createSQLQuery("select distinct a.* from authority a,role_authority ra where to_char(a.id) = ra.authority_id and a.parent_id is null and ra.role = ?");
		// SQL Server
		// SQLQuery query = getSession().createSQLQuery("select distinct a.* from authority a,role_authority ra where convert(char(8),a.id) = ra.authority_id and a.parent_id is null and ra.role = ?");
		query.setParameter(0, role.intValue());
		query.addEntity(Authority.class);
		return query.list();
	}


	public List<Authority> queryChildrenByParentIdAndRole(Integer parentId, Short role) {
		// MySQL
		//SQLQuery query = getSession().createSQLQuery("select distinct a.* from authority a,role_authority ra where a.id = ra.authority_id and a.parent_id = ? and ra.role = ?"); 
		// Oracle
		 SQLQuery query = getSession().createSQLQuery("select distinct a.* from authority a,role_authority ra where to_char(a.id) = ra.authority_id and a.parent_id = ? and ra.role = ?");
		// SQL Server
		// SQLQuery query = getSession().createSQLQuery("select distinct a.* from authority a,role_authority ra where convert(char(8),a.id) = ra.authority_id and a.parent_id = ? and ra.role = ?");
		query.setParameter(0, parentId);
		query.setParameter(1, role);
		query.addEntity(Authority.class);
		return query.list();
	}

}
