package resource.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import resource.db.ResourceBean;

public interface ResourceService {
	//자료실 글쓰기
	public int addResource(ResourceBean rBean);
	
	//자료실 글수정
	public void modResource(ResourceBean rBean);
	
	//자료실 글보기
	public ResourceBean resourceView(int res_no);
	
	//자료실 글삭제
	public void resourceDelete(int res_no);
	
	//페이징 기능에 필요한 글목록과 전체 글 개를 각각 조회할 수 있도록 구성
	public Map listResource(Map<String, Integer> pagingMap, String opt, String condition);
	
	//자료실 검색
	public ArrayList<ResourceBean> resourceSelect(String opt,String condition);

	
}


