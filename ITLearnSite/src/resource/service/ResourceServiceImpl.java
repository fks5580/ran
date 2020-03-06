package resource.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import resource.db.ResourceBean;
import resource.db.ResourceDAOImpl;

public class ResourceServiceImpl implements ResourceService {
	ResourceDAOImpl rDao;

	public ResourceServiceImpl() {
		rDao = new ResourceDAOImpl();
	}
	
	// 자료실 글쓰기
	@Override
	public int addResource(ResourceBean rBean) {
		return rDao.insertResource(rBean);
	}
		
	// 자료실 글수정
	@Override
	public void modResource(ResourceBean rBean) {
		rDao.updateResource(rBean);
	}
		
	// 자료실 내용
	@Override
	public ResourceBean resourceView(int res_no) {
		ResourceBean rBean = rDao.resourceView(res_no);
		return rBean;
	}
	
	// 자료실  내용 삭제
	@Override
	public void resourceDelete(int res_no) {
		rDao.resourceDelete(res_no);
	}
	
	//자료실 리스트
	@Override
	public Map listResource(Map<String, Integer> pagingMap, String opt, String condition) {
	    Map resourcesMap = new HashMap();
		List<ResourceBean> resourcesList = rDao.selectAllResources(pagingMap, opt, condition);
		int totResources = rDao.selectTotResources(opt, condition);
		resourcesMap.put("resourcesList", resourcesList);
		resourcesMap.put("totResources", totResources);
		return resourcesMap;
	}
	
	//자료실 검색
	@Override
	public ArrayList<ResourceBean> resourceSelect(String opt,String condition){
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);
		ArrayList<ResourceBean> ResourceList = rDao.resourceSelect(listOpt);
		return ResourceList;

	}
}
