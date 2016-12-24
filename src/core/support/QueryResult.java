package core.support;

import java.util.List;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class QueryResult<E> {

	private List<E> resultList;
	private Integer totalCount;

	public List<E> getResultList() {
		return resultList;
	}

	public void setResultList(List<E> resultList) {
		this.resultList = resultList;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer i) {
		this.totalCount = i;
	}

}
