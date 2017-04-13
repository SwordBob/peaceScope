package core.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import core.support.BaseParameter;
import core.support.QueryResult;


 
public class BaseDao<E> implements Dao<E> {

	protected final Logger log = Logger.getLogger(BaseDao.class);

	private static Map<String, Method> MAP_METHOD = new HashMap<String, Method>();

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Resource(name = "sessionFactory")
	public void setSF(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	/**
	 * 声明传入的类
	 */
	protected Class<E> entityClass;

	public BaseDao(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public void persist(E entity) {
		getSession().clear();
		getSession().save(entity);
	}

	public boolean deleteByPK(Serializable... id) {
		boolean result = false;
		if (id != null && id.length > 0) {
			for (int i = 0; i < id.length; i++) {
				E entity = get(id[i]);
				if (entity != null) {
					getSession().delete(entity);
					result = true;
				}
			}
		}
		return result;
	}

	public void deleteByProperties(String[] propName, Object[] propValue) {
		if (propName != null && propName.length > 0 && propValue != null && propValue.length > 0 && propValue.length == propName.length) {
			StringBuffer sb = new StringBuffer("delete from " + entityClass.getName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			query.executeUpdate();
		}
	}

	public void delete(E entity) {
		getSession().delete(entity);
	}

	public void deleteByProperties(String propName, Object propValue) {
		deleteByProperties(new String[] { propName }, new Object[] { propValue });
	}

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue) {
		if (propertyName != null && propertyName.length > 0 && propertyValue != null && propertyValue.length > 0 && propertyName.length == propertyValue.length && conditionValue != null && conditionValue.length > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("update " + entityClass.getName() + " o set ");
			for (int i = 0; i < propertyName.length; i++) {
				sb.append(propertyName[i] + " = :p_" + propertyName[i] + ",");
			}
			//删除最后一个字符
			sb.deleteCharAt(sb.length() - 1);
			sb.append(" where 1=1 ");
			appendQL(sb, conditionName, conditionValue);
			Query query = getSession().createQuery(sb.toString());
			for (int i = 0; i < propertyName.length; i++) {
				query.setParameter("p_" + propertyName[i], propertyValue[i]);
			}
			setParameter(query, conditionName, conditionValue);
			query.executeUpdate();
		} else {
			throw new IllegalArgumentException("Method updateByProperties in BaseDao argument is illegal!");
		}
	}

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String propertyName, Object propertyValue) {
		updateByProperties(conditionName, conditionValue, new String[] { propertyName }, new Object[] { propertyValue });
	}

	public void updateByProperties(String conditionName, Object conditionValue, String[] propertyName, Object[] propertyValue) {
		updateByProperties(new String[] { conditionName }, new Object[] { conditionValue }, propertyName, propertyValue);
	}

	public void updateByProperties(String conditionName, Object conditionValue, String propertyName, Object propertyValue) {
		updateByProperties(new String[] { conditionName }, new Object[] { conditionValue }, new String[] { propertyName }, new Object[] { propertyValue });
	}

	public void update(E entity) {
		getSession().clear();
		//getSession().refresh(entity);
		getSession().update(entity);
	}

	public void updateMy(E entity) {
		getSession().update(entity);
	}
	
	public void update(E entity, Serializable oldId) {
		deleteByPK(oldId);
		persist(entity);
	}

	public E merge(E entity) {
		getSession().refresh(entity);
		return (E) getSession().merge(entity);
	}

	public E getByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		if (propName != null && propName.length > 0 && propValue != null && propValue.length > 0 && propValue.length == propName.length) {
			StringBuffer sb = new StringBuffer("select o from " + entityClass.getName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			if (sortedCondition != null && sortedCondition.size() > 0) {
				sb.append(" order by ");
				for (Entry<String, String> e : sortedCondition.entrySet()) {
					sb.append(e.getKey() + " " + e.getValue() + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			Query query = getSession().createQuery(sb.toString());
			System.out.println(sb.toString());
			setParameter(query, propName, propValue);
		
			List<E> list = query.list();
			if (list != null && list.size() > 0)
				return list.get(0);
		}
		return null;
	}

	public E get(Serializable id) {
		return (E) getSession().get(entityClass, id);
	}

	public E load(Serializable id) {
		return (E) getSession().load(entityClass, id);
	}

	public E getByProerties(String[] propName, Object[] propValue) {
		return getByProerties(propName, propValue, null);
	}

	public E getByProerties(String propName, Object propValue) {
		return getByProerties(new String[] { propName }, new Object[] { propValue });
	}

	public E getByProerties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return getByProerties(new String[] { propName }, new Object[] { propValue }, sortedCondition);
	}

	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top) {
		if (propName != null && propValue != null && propValue.length == propName.length) {
			StringBuffer sb = new StringBuffer("select o from " + entityClass.getName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			if (sortedCondition != null && sortedCondition.size() > 0) {
				sb.append(" order by ");
				for (Entry<String, String> e : sortedCondition.entrySet()) {
					sb.append(e.getKey() + " " + e.getValue() + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			Query query = getSession().createQuery(sb.toString());
			System.out.println(sb.toString());
			setParameter(query, propName, propValue);
			if (top != null) {
				query.setFirstResult(0);
				query.setMaxResults(top);
			}
			return query.list();
		}
		return null;
	}
	
	public List<E> queryByLikeProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top) {
		if (propName != null && propValue != null && propValue.length == propName.length) {
			StringBuffer sb = new StringBuffer("select o from " + entityClass.getName() + " o where 1=1 ");
			appendPramLikeQL(sb, propName, propValue);
			if (sortedCondition != null && sortedCondition.size() > 0) {
				sb.append(" order by ");
				for (Entry<String, String> e : sortedCondition.entrySet()) {
					sb.append(e.getKey() + " " + e.getValue() + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			Query query = getSession().createQuery(sb.toString());
			System.out.println(sb.toString());
			setParameterNew(query, propName, propValue);
			if (top != null) {
				query.setFirstResult(0);
				query.setMaxResults(top);
			}
			return query.list();
		}
		return null;
	}

	public List<E> queryByProerties(String[] propName, Object[] propValue, Integer top) {
		return queryByProerties(propName, propValue, null, top);
	}

	public List<E> queryByProerties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		return queryByProerties(propName, propValue, sortedCondition, null);
	}

	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition, Integer top) {
		return queryByProerties(new String[] { propName }, new Object[] { propValue }, sortedCondition, top);
	}

	public List<E> queryByProerties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return queryByProerties(new String[] { propName }, new Object[] { propValue }, sortedCondition, null);
	}

	public List<E> queryByProerties(String propName, Object propValue, Integer top) {
		return queryByProerties(new String[] { propName }, new Object[] { propValue }, null, top);
	}

	public List<E> queryByProerties(String[] propName, Object[] propValue) {
		return queryByProerties(propName, propValue, null, null);
	}

	public List<E> queryByProerties(String propName, Object propValue) {
		return queryByProerties(new String[] { propName }, new Object[] { propValue }, null, null);
	}
	
	public List<E> queryByLikeProerties(String propName, Object propValue) {
		return queryByLikeProerties(new String[] { propName }, new Object[] { propValue }, null, null);
	}

	public Integer countAll() {
		return (Integer) getSession().createQuery("select count(*) from " + entityClass.getName()).uniqueResult();
	}

	public void clear() {
		getSession().clear();
	}

	public void evict(E entity) {
		getSession().evict(entity);
	}

	public List<E> doQueryAll(Map<String, String> sortedCondition, Integer top) {
		Criteria criteria = getSession().createCriteria(entityClass);
		if (sortedCondition != null && sortedCondition.size() > 0) {
			for (Iterator<String> it = sortedCondition.keySet().iterator(); it.hasNext();) {
				String pm = it.next();
				System.out.println(pm);
				if (BaseParameter.SORTED_DESC.equals(sortedCondition.get(pm))) {
					criteria.addOrder(Order.desc(pm));
				} else if (BaseParameter.SORTED_ASC.equals(sortedCondition.get(pm))) {
					criteria.addOrder(Order.asc(pm));
				}
			}
		}
		if (top != null) {
			criteria.setMaxResults(top);
			criteria.setFirstResult(0);
		}
		return criteria.list();
	}

	public List<E> doQueryAll() {
		return doQueryAll(null, null);
	}

	public List<E> doQueryAll(Integer top) {
		return doQueryAll(null, top);
	}
/**
 * 获得结果行数
 */
	public Integer doCount(BaseParameter param) {
		if (param == null)
			return null;
		Criteria criteria = getSession().createCriteria(entityClass);
		processQuery(criteria, param);
		try {
			criteria.setProjection(Projections.rowCount());
			return ((Number) criteria.uniqueResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<E> doQuery(BaseParameter param) {
		if (param == null)
			return null;
		Criteria criteria = getSession().createCriteria(entityClass);
		processQuery(criteria, param);
		try {
			if (param.getSortedConditions() != null && param.getSortedConditions().size() > 0) {
				Map<String, String> map = param.getSortedConditions();
				for (Iterator<String> it = param.getSortedConditions().keySet().iterator(); it.hasNext();) {
					String pm = it.next();
					System.out.println(pm);
					//判断是正序还是反序
					if (BaseParameter.SORTED_DESC.equals(map.get(pm))) {
						criteria.addOrder(Order.desc(pm));
					} else if (BaseParameter.SORTED_ASC.equals(map.get(pm))) {
						criteria.addOrder(Order.asc(pm));
					}
				}
			}
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public QueryResult<E> doPaginationQuery(BaseParameter param) {
		return doPaginationQuery(param, true);
	}

	public QueryResult<E> doPaginationQuery(BaseParameter param, boolean bool) {
		if (param == null)
			return null;
		Criteria criteria = getSession().createCriteria(entityClass);

		if (bool)
			processQuery(criteria, param);
		else
			extendprocessQuery(criteria, param);

		try {
			QueryResult<E> qr = new QueryResult<E>();
			//获得总行数
			criteria.setProjection(Projections.rowCount());
			qr.setTotalCount(((Number) criteria.uniqueResult()).intValue());
			if (qr.getTotalCount() > 0) {
				if (param.getSortedConditions() != null && param.getSortedConditions().size() > 0) {
					Map<String, String> map = param.getSortedConditions();
					for (Iterator<String> it = param.getSortedConditions().keySet().iterator(); it.hasNext();) {
						String pm = it.next();
						System.out.println(pm);
						//判断是正序还是反序
						if (BaseParameter.SORTED_DESC.equals(map.get(pm))) {
							criteria.addOrder(Order.desc(pm));
						} else if (BaseParameter.SORTED_ASC.equals(map.get(pm))) {
							criteria.addOrder(Order.asc(pm));
						}
					}
				}
				criteria.setProjection(null);
				criteria.setMaxResults(param.getMaxResults());
				criteria.setFirstResult(param.getFirstResult());
				qr.setResultList(criteria.list());
			} else {
				qr.setResultList(new ArrayList<E>());
			}
			System.out.println(qr);
			return qr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void appendQL(StringBuffer sb, String[] propName, Object[] propValue) {
		for (int i = 0; i < propName.length; i++) {
			String name = propName[i];
			Object value = propValue[i];
			if (value instanceof Object[] || value instanceof Collection<?>) {
				Object[] arraySerializable = (Object[]) value;
				if (arraySerializable != null && arraySerializable.length > 0) {
					sb.append(" and o." + name + " in (:" + name.replace(".", "") + ")");
				}
			} else {
				if (value == null) {
					sb.append(" and o." + name + " is null ");
				} else {
					sb.append(" and o." + name + "=:" + name.replace(".", ""));
				}
			}
		}
	}
	
	private void appendPramLikeQL(StringBuffer sb, String[] propName, Object[] propValue) {
		for (int i = 0; i < propName.length; i++) {
			String nameTemp = propName[i];		
			String name = getPropName(nameTemp);
			Object value = propValue[i];
			if (value instanceof Object[] || value instanceof Collection<?>) {
				Object[] arraySerializable = (Object[]) value;
				if (arraySerializable != null && arraySerializable.length > 0) {
					sb.append(" and o." + name + " in (:" + name.replace(".", "") + ")");
				}
			} else {
				if (value == null) {
					sb.append(" and o." + name + " is null ");
				} else {
					if(((String)value).contains("-")){				
						//sb.append(" and o." + name + "=:" + name.replace(".", ""));
						sb.append(" and o." + name + " like to_date (:" + name.replace(".", "")+",'yyyy-MM-dd')");
						}else
					sb.append(" and o." + name + " like :" + name.replace(".", ""));
				}
			}
		}
	}
	
	

	private void appendLikeQL(StringBuffer sb, String name, Object value) {
		
			if (value instanceof Object[] || value instanceof Collection<?>) {
				Object[] arraySerializable = (Object[]) value;
				if (arraySerializable != null && arraySerializable.length > 0) {
					//sb.append(" and o." + name + " like to_date ('" + name.replace(".", "") + "','yyyy-MM-dd')");
					sb.append(" and o." + name + " in (:" + name.replace(".", "") + ")");
				}
			} else {
				if (value == null) {
					sb.append(" and o." + name + " is null ");
				} else if(value instanceof String){
					if(((String)value).contains("-")){				
					//sb.append(" and o." + name + "=:" + name.replace(".", ""));
					sb.append(" and o." + name + " like to_date (:" + name.replace(".", "")+",'yyyy-MM-dd')");
					}else{
						sb.append(" and o." + name + " like :" + name.replace(".", ""));
					}
				}else{
					//
					sb.append(" and o." + name + "=:" + name.replace(".", ""));
				}
			}
		}
	
	
	private void setParameter(Query query, String[] propName, Object[] propValue) {
		for (int i = 0; i < propName.length; i++) {
			String name = propName[i];
			Object value = propValue[i];
			//判断object数组内的是数组，还是集合，或者单纯类
			if (value != null) {
				if (value instanceof Object[]) {
					query.setParameterList(name.replace(".", ""), (Object[]) value);
				} else if (value instanceof Collection<?>) {
					query.setParameterList(name.replace(".", ""), (Collection<?>) value);
				} else {
					
					query.setParameter(name.replace(".", ""), value);
				}
			}
		}
	}
	
	private void setParameterNew(Query query, String[] propName, Object[] propValue) {
		for (int i = 0; i < propName.length; i++) {
			String nameTemp = propName[i];
			Object value = propValue[i];
			String name = getPropName(nameTemp);
			//判断object数组内的是数组，还是集合，或者单纯类
			if (value != null) {
				if (value instanceof Object[]) {
					query.setParameterList(name.replace(".", ""), (Object[]) value);
				} else if (value instanceof Collection<?>) {
					query.setParameterList(name.replace(".", ""), (Collection<?>) value);
				} else {
					
					query.setParameter(name.replace(".", ""), value);
				}
			}
		}
	}
	
	
	
/**
 * 添加排序hql语句
 * @param param
 * @param hql
 * 2016-1-14下午10:39:40
 * void
 */
	protected void buildSorted(BaseParameter param, StringBuffer hql) {
		if (param.getSortedConditions() != null && param.getSortedConditions().size() > 0) {
			hql.append(" order by ");
			Map<String, String> sorted = param.getSortedConditions();
			for (Iterator<String> it = sorted.keySet().iterator(); it.hasNext();) {
				String col = it.next();
				System.out.println(col);
				hql.append(col + " " + sorted.get(col) + ",");
			}
			hql.deleteCharAt(hql.length() - 1);
		}
	}
	/**
	 * 从第一个出现_的地方截取
	 * @param queryCondition
	 * @return
	 * 2016-1-14下午10:11:41
	 * String
	 */
	private String transferColumn(String queryCondition) {
		return queryCondition.substring(queryCondition.indexOf('_', 1) + 1);
	}
	/**
	 * 将mapParameter中的key value对应赋值
	 * 重要
	 * @param mapParameter
	 * @param query
	 * 2016-1-14下午10:18:28
	 * void
	 */
	protected void setParameter(Map<String, Object> mapParameter, Query query) {
		for (Iterator<String> it = mapParameter.keySet().iterator(); it.hasNext();) {
			
			String parameterName = (String) it.next();
			
			Object value = mapParameter.get(parameterName);
			//String prop = getPropName(parameterName);
			
			query.setParameter(parameterName, value);
		}
	}
	protected void setParameterByMyMap(Map<String, Object> mapParameter, Query query) {
		for (Iterator<String> it = mapParameter.keySet().iterator(); it.hasNext();) {			
			String parameterName = (String) it.next();
			Object value = mapParameter.get(parameterName);
			if(value!=null){
			String prop = getPropName(parameterName);
			query.setParameter(prop, value);
		}
		}
	}
/**
 * 向map中加入含$的parameter
 * @param param
 * @return
 * @throws Exception
 * 2016-1-14下午10:43:49
 * Map
 */
	protected Map handlerConditions(BaseParameter param) throws Exception {
		Map staticConditions = core.util.BeanUtils.describe(param);
		Map<String, Object> dynamicConditions = param.getQueryDynamicConditions();
		if (dynamicConditions.size() > 0) {
			for (Iterator it = staticConditions.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				Object value = staticConditions.get(key);
				if (key.startsWith("$") && value != null && !"".equals(value)) {
					dynamicConditions.put(key, value);
				}
			}
			staticConditions = dynamicConditions;
		}
		return staticConditions;
	}

	/** ************ for QBC ********** */
	/**
	 * 通过name获得method并塞入map中
	 * @param name
	 * @return
	 * 2016-1-14下午10:53:35
	 * Method
	 */
	private Method getMethod(String name) {
		if (!MAP_METHOD.containsKey(name)) {
			Class<Restrictions> clazz = Restrictions.class;
			//声明三种数组形式
			Class[] paramType = new Class[] { String.class, Object.class };
			Class[] likeParamType = new Class[] { String.class, String.class, MatchMode.class };
			Class[] isNullType = new Class[] { String.class };
			try {
				Method method = null;
				if ("like".equals(name)) {
					method = clazz.getMethod(name, likeParamType);
				} else if ("isNull".equals(name)) {
					method = clazz.getMethod(name, isNullType);
				} else {
					method = clazz.getMethod(name, paramType);
				}
				MAP_METHOD.put(name, method);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return MAP_METHOD.get(name);
	}
 /**
  * 通过name获得method并塞入map中
  * @param name
  * @return
  * 2016-1-14下午10:55:39
  * Method
  */
	private Method getExtendMethod(String name) {
		if (!MAP_METHOD.containsKey(name)) {
			Class<Restrictions> clazz = Restrictions.class;
			Class[] paramType = new Class[] { String.class, Object.class };
			Class[] likeParamType = new Class[] { String.class, String.class, MatchMode.class };
			Class[] isNullType = new Class[] { String.class };
			// Class[] inparamType=new Class[]{String.class,Arrays.class};
			try {
				Method method = null;
				if ("like".equals(name)) {
					method = clazz.getMethod(name, likeParamType);
				} else if ("isNull".equals(name)) {
					method = clazz.getMethod(name, isNullType);
				} else if ("IN".equals(name.toUpperCase())) {
					// method=clazz.getMethod(name,inparamType);
				} else {
					method = clazz.getMethod(name, paramType);
				}
				MAP_METHOD.put(name, method);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return MAP_METHOD.get(name);
	}
/**
 * 获得从零至以第一个_开始的截取字符，并获得以第一字符开始的字符串
 * @param value
 * @return
 * 2016-1-14下午10:56:59
 * String
 */
	private String getOpt(String value) {
		return (value.substring(0, value.indexOf('_', 1))).substring(1);
	}
/**
 * 获得以第一个_开始加一的截取字符
 * @param value
 * @return
 * 2016-1-14下午11:02:56
 * String
 */
	private String getPropName(String value) {
		return value.substring(value.indexOf('_', 1) + 1);
	}
/**
 * 高级查询方法，加入模糊查询，加入自动识别方法及属性
 * 加入判断当天时间大于小于
 * @param criteria
 * @param param
 * 2016-1-14下午11:19:56
 * void
 */
	private void processQuery(Criteria criteria, BaseParameter param) {
		try {
			//适用于本表中存在的属性
			Map<String, Object> staticConditionMap = core.util.BeanUtils.describeAvailableParameter(param);
			Map<String, Object> dynamicConditionMap = param.getQueryDynamicConditions();
			if ((staticConditionMap != null && staticConditionMap.size() > 0)) {
				for (Entry<String, Object> e : staticConditionMap.entrySet()) {
					Object value = e.getValue();
					System.out.println(value);
					if (value != null && !(value instanceof String && "".equals((String) value))) {
						String prop = getPropName(e.getKey());
						String methodName = getOpt(e.getKey());
						Method m = getMethod(methodName);
						System.out.println(prop);
						System.out.println(methodName);
						System.out.println(m);
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value, MatchMode.ANYWHERE }));
						} else if ("isNull".equals(methodName) && value instanceof Boolean) {
							if ((Boolean) value) {
								criteria.add(Restrictions.isNull(prop));
							} else {
								criteria.add(Restrictions.isNotNull(prop));
							}
						} else {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value }));
						}
					}
				}
			}
			if (dynamicConditionMap != null && dynamicConditionMap.size() > 0) {
				Object bean = entityClass.newInstance();
				Map<String, Object> map = new HashMap<String, Object>();
				for (Entry<String, Object> e : dynamicConditionMap.entrySet()) {
					map.put(getPropName(e.getKey()), e.getValue());
				}
				BeanUtils.populate(bean, map);
				for (Entry<String, Object> e : dynamicConditionMap.entrySet()) {
					String pn = e.getKey();
					String prop = getPropName(pn);
					String methodName = getOpt(pn);
					Method m = getMethod(methodName);
					System.out.println(prop);
					System.out.println(methodName);
					System.out.println(m);
					Object value = PropertyUtils.getNestedProperty(bean, prop);

					if (value != null && !(value instanceof String && "".equals((String) value))) {
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value, MatchMode.ANYWHERE }));
						} else if ("isNull".equals(methodName) && value instanceof Boolean) {
							if ((Boolean) value) {
								criteria.add(Restrictions.isNull(prop));
							} else {
								criteria.add(Restrictions.isNotNull(prop));
							}
						} else {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value }));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 模糊查询date
	 * @param criteria
	 * @param param
	 * 2016-1-19下午2:55:36
	 * void
	 */
	private QueryResult<E> processQueryDate(Criteria criteria, BaseParameter param) {
		try {
			//适用于本表中存在的属性
			QueryResult<E> qr = new QueryResult<E>();
			Map<String, Object> staticConditionMap = core.util.BeanUtils.describeAvailableParameter(param);
			Map<String, Object> dynamicConditionMap = param.getQueryDynamicConditions();
			String[] props={};
			if ((staticConditionMap != null && staticConditionMap.size() > 0)) {
				Query query =null;
				StringBuffer sb = new StringBuffer("select o from " + entityClass.getName() + " o where 1=1 ");
				for (Entry<String, Object> e : staticConditionMap.entrySet()) {
					Object value = e.getValue();
					System.out.println(value);
					//if(value==null) continue;
					if (value != null ) {
						String prop = getPropName(e.getKey());
						String methodName = getOpt(e.getKey());
						//if(prop==null) continue;
						//if(methodName==null) continue;
						Method m = getMethod(methodName);
						System.out.println(prop);
						System.out.println(methodName);
						System.out.println(m);
						
						if ("like".equals(methodName)) {
							//if(((String)value).contains("-"))
								//StringBuffer sb = new StringBuffer("from " + entityClass.getName() + " o where 1=1 ");
								appendLikeQL(sb, prop, value);
								/*Map<String, String> sortedCondition=param.getSortedConditions();
								if (sortedCondition != null && sortedCondition.size() > 0) {
									sb.append(" order by ");
									for (Entry<String, String> e1 : sortedCondition.entrySet()) {
										sb.append(e1.getKey() + " " + e1.getValue() + ",");
									}
									//----------------sb.deleteCharAt(sb.length() - 1);
								}
								//-------------------Query query = getSession().createQuery(sb.toString());
								System.out.println(sb.toString());*/
								//System.out.println(sb.toString());
								//query.setParameter("createDate", "'2016/01/12','yyyy/MM/dd'");
								//-----System.out.println(query.toString());
								//------setParameterByOne(query, prop, value);
//								if (top != null) {
//									query.setFirstResult(0);
//									query.setMaxResults(top);
//								}
								//------System.out.println(query.getQueryString());
								//------qr.setResultList(query.list());
								
								
								
								//------return qr ;
							
							//------return null;
						}else {
							//criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value }));
							appendLikeQL(sb, prop, value);
						}						
						
						
					}
				}
				
				
				Map<String, String> sortedCondition=param.getSortedConditions();
				if (sortedCondition != null && sortedCondition.size() > 0) {
					sb.append(" order by ");
					for (Entry<String, String> e1 : sortedCondition.entrySet()) {
						sb.append(e1.getKey() + " " + e1.getValue() + ",");
					}
					sb.deleteCharAt(sb.length() - 1);
					System.out.println(sb);
				}
				query = getSession().createQuery(sb.toString());
				setParameterByMyMap(staticConditionMap, query);	
				qr.setResultList(query.list());
				System.out.println(qr.toString());
				return qr;
				
			}
		} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
		}
		
			
	
/**
 * 扩展高级查询，加入in查询
 * @param criteria
 * @param param
 * 2016-1-14下午11:23:48
 * void
 */
	private void extendprocessQuery(Criteria criteria, BaseParameter param) {
		try {
			Map<String, Object> staticConditionMap = core.util.BeanUtils.describeAvailableParameter(param);
			Map<String, Object> dynamicConditionMap = param.getQueryDynamicConditions();
			if ((staticConditionMap != null && staticConditionMap.size() > 0)) {
				for (Entry<String, Object> e : staticConditionMap.entrySet()) {
					Object value = e.getValue();
					if (value != null&&!("".equals(value)) && !(value instanceof String && "".equals((String) value))) {
						String prop = getPropName(e.getKey());
						String methodName = getOpt(e.getKey());
						Method m = getExtendMethod(methodName);
						System.out.println(prop);
						System.out.println(methodName);
						System.out.println(m);
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value, MatchMode.ANYWHERE }));
						} else if ("isNull".equals(methodName) && value instanceof Boolean) {
							if ((Boolean) value) {
								criteria.add(Restrictions.isNull(prop));
							} else {
								criteria.add(Restrictions.isNotNull(prop));
							}
						} else {
							if (value != null && value instanceof Object[] && "IN".equals(methodName.toUpperCase())) {
								Object[] obj = (Object[]) value;
								criteria.add(Restrictions.in(prop, obj));
								// criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, obj }));
							} else {
								criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value }));
							}
						}
					}
				}
			}

			if (dynamicConditionMap != null && dynamicConditionMap.size() > 0) {
				Object bean = entityClass.newInstance();
				Map<String, Object> map = new HashMap<String, Object>();
				for (Entry<String, Object> e : dynamicConditionMap.entrySet()) {
					map.put(getPropName(e.getKey()), e.getValue());
				}
				BeanUtils.populate(bean, map);
				for (Entry<String, Object> e : dynamicConditionMap.entrySet()) {
					String pn = e.getKey();
					String prop = getPropName(pn);
					String methodName = getOpt(pn);
					Method m = getMethod(methodName);
					System.out.println(prop);
					System.out.println(methodName);
					System.out.println(m);
					Object value = PropertyUtils.getNestedProperty(bean, prop);

					if (value != null && !(value instanceof String && "".equals((String) value))) {
						if ("like".equals(methodName)) {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value, MatchMode.ANYWHERE }));
						} else if ("isNull".equals(methodName) && value instanceof Boolean) {
							if ((Boolean) value) {
								criteria.add(Restrictions.isNull(prop));
							} else {
								criteria.add(Restrictions.isNotNull(prop));
							}
						} else {
							criteria.add((Criterion) m.invoke(Restrictions.class, new Object[] { prop, value }));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

public QueryResult<E> doPaginationQueryDate(BaseParameter param, boolean bool) {
	if (param == null)
		return null;
	Criteria criteria = getSession().createCriteria(entityClass);

	if (bool)
		return processQueryDate(criteria, param);
	else
		extendprocessQuery(criteria, param);

	/*try {
		QueryResult<E> qr = new QueryResult<E>();
		//获得总行数
		criteria.setProjection(Projections.rowCount());
		qr.setTotalCount(((Number) criteria.uniqueResult()).intValue());
		if (qr.getTotalCount() > 0) {
			if (param.getSortedConditions() != null && param.getSortedConditions().size() > 0) {
				Map<String, String> map = param.getSortedConditions();
				for (Iterator<String> it = param.getSortedConditions().keySet().iterator(); it.hasNext();) {
					String pm = it.next();
					System.out.println(pm);
					//判断是正序还是反序
					if (BaseParameter.SORTED_DESC.equals(map.get(pm))) {
						criteria.addOrder(Order.desc(pm));
					} else if (BaseParameter.SORTED_ASC.equals(map.get(pm))) {
						criteria.addOrder(Order.asc(pm));
					}
				}
			}
			criteria.setProjection(null);
			criteria.setMaxResults(param.getMaxResults());
			criteria.setFirstResult(param.getFirstResult());
			qr.setResultList(criteria.list());
		} else {
			qr.setResultList(new ArrayList<E>());
		}
		System.out.println(qr);
		return qr;
	} catch (Exception e) {
		e.printStackTrace();
	}*/
	return null;

}




}
