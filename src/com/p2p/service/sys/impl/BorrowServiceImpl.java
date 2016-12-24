package com.p2p.service.sys.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;



import com.p2p.dao.sys.BorrowDao;
import com.p2p.dao.sys.CarInfoDao;
import com.p2p.dao.sys.OtherInfoDao;
import com.p2p.dao.sys.SysUserDao;
import com.p2p.dao.sys.UserInfoDao;

import com.p2p.dao.sys.RepaymentStateDao;

import com.p2p.model.sys.Borrow;

import com.p2p.service.sys.BorrowService;




import core.service.BaseService;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Service
public class BorrowServiceImpl extends BaseService<Borrow> implements BorrowService {
	
	private BorrowDao borrowDao;
	/*@Resource
	private AttachmentDao attachmentDao;*/

	@Resource
	private RepaymentStateDao repaymentStateDao;
	@Resource
	private UserInfoDao userInfoDao;
	@Resource
	private CarInfoDao carInfoDao;
	@Resource
	private OtherInfoDao otherInfoDao;
	

	/**
	 * 重要注入
	 * @param borrowDao
	 * 2016-1-15下午2:04:20
	 * void
	 */
	@Resource
	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
		this.dao = borrowDao;
	}
	
	
	public List<Borrow> getBorrowList(List<Borrow> resultList) {
		List<Borrow> borrowList = new ArrayList<Borrow>();
		//HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//String imagePath = request.getContextPath();
		for (Borrow entity : resultList) {
			Borrow borrow = new Borrow();
			borrow.setBorrowId(entity.getBorrowId());
			borrow.setTitle(entity.getTitle());
			borrow.setAmount(entity.getAmount());			
			borrow.setInterest(entity.getInterest());
			borrow.setStages(entity.getStages());
			borrow.setBidType(entity.getBidType());
			borrow.setType(entity.getType());
			borrow.setBorrowDate(entity.getBorrowDate());
			borrow.setState(entity.getState());
			borrow.setBidName(entity.getBidName());
			borrow.setGender(entity.getGender());
			borrow.setMarriage(entity.getMarriage());
			borrow.setAddress(entity.getAddress());
			if (entity.getUserInfo()!=null) {
			borrow.setUserInfoPhone(entity.getUserInfo().getPhone());
			//p2p.setSysUserName("wocao");
			//System.out.println(p2p.getSysUserName());
		//	System.out.println(p2p.getSysUserName());
			borrow.setUserId(entity.getUserInfo().getUserId());
			}
			if (entity.getCarInfo()!=null) {							
			borrow.setCarInfoId(entity.getCarInfo().getCarInfoId());
			borrow.setCarBrand(entity.getCarInfo().getCarBrand());
			borrow.setCarId(entity.getCarInfo().getCarId());
			borrow.setMileage(entity.getCarInfo().getMileage());			
		    borrow.setAuditDate(carInfoDao.get(entity.getCarInfo().getCarInfoId()).getAuditDate());						
			borrow.setAuditor(carInfoDao.get(entity.getCarInfo().getCarInfoId()).getAuditor());			
			borrow.setOrgPrice(entity.getCarInfo().getOrgPrice());
			borrow.setEvaluate(carInfoDao.get(entity.getCarInfo().getCarInfoId()).getEvaluate());
			}
			if (entity.getRepaymentState()!=null) {					
			borrow.setRepaymentDate(entity.getRepaymentState().getRepaymentDate());
			borrow.setRepayId(entity.getRepaymentState().getRepaymentStateId());
			borrow.setRepaymentEnd(repaymentStateDao.get(entity.getRepaymentState().getRepaymentStateId()).getRepaymentEnd());
			}
			//borrow.setOtherInfoId(otherInfoDao.get(entity.getOtherInfo().))
			/*borrow.setPayet(repaymentStateDao.get(entity.getRepaymentState().getRepaymentStateId()).getPayet());
			borrow.setEstimate(repaymentStateDao.get(entity.getRepaymentState().getRepaymentStateId()).getEstimate());
			borrow.setActual(repaymentStateDao.get(entity.getRepaymentState().getRepaymentStateId()).getActual());
			borrow.setPenalty(repaymentStateDao.get(entity.getRepaymentState().getRepaymentStateId()).getPenalty());
			*/
			//	System.out.println(entity.getSysUser().getId());
//			p2p.setUserId(entity.getSysUser().getId());
			/**
			 * 待改善
			 */
			
			
			//borrow.setRepaymentDate("cao");
			//System.out.println(p2p.getRepaymentDate());
			
			//System.out.println(entity.getRepaymentState().getRepaymentStateId()).getRepaymentDate());
			
			//System.out.println(p2p.getRepaymentStateId());
			/**
			 * 附件保存
			 */
			/*List<Attachment> attachmentList = attachmentDao.queryByProerties("p2ptypeId", entity.getTypeId());
			StringBuilder sb = new StringBuilder("");
			for (int i = 0; i < attachmentList.size(); i++) {
				sb.append("<img src='" + imagePath + "/static/img/upload/" + attachmentList.get(i).getFilePath() + "' width = 300 height = 200 />");
			}

			p2p.setp2pTypeImagePath(sb.toString());*/
			borrowList.add(borrow);
		}
		return borrowList;
	}


	public List<Object[]> queryExportedBorrow(Integer[] ids) {
		return borrowDao.queryExportedBorrow(ids);
	}
	

	


	

	/*public RepaymentStateDao getRepaymentStateDao() {
		return repaymentStateDao;
	}


	public void setRepaymentStateDao(RepaymentStateDao repaymentStateDao) {
		this.repaymentStateDao = repaymentStateDao;
	}


	public CarInfoDao getCarInfoDao() {
		return carInfoDao;
	}


	public void setCarInfoDao(CarInfoDao carInfoDao) {
		this.carInfoDao = carInfoDao;
	}


	public BorrowDao getBorrowDao() {
		return borrowDao;
	}


	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}


	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

*/
	

	

}
