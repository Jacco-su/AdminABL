package com.dream.framework.dao;

import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.HashMap;
@Repository(value="pager")
public class Pager implements IPager{

	private int totalRow;
	private int pageSize = 10;
	private int currentPage = 1;
	private int totalPage;
	private Map<String,String> params=new HashMap<String,String>();

	public int endRow() {
		return currentPage * pageSize;
	}

	public int startRow() {
		if(currentPage>0)
		return (currentPage - 1) * pageSize;
		else return 0;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
		setTotalPage((int) Math.ceil((double) totalRow / pageSize));
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize > 0) {
			this.pageSize = pageSize;
		} 
	}

	public int getCurrentPage() {
		if(currentPage>0)
		return currentPage;
		else return 1;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		if (currentPage < 1) {
			this.currentPage = 1;
		}
	}

	public int getFirst() {
		return 1;
	}

	public int getPrevious() {
		if ((currentPage - 1) < 1) {
			return 1;
		}
		return currentPage - 1;
	}

	public int getNext() {
		if ((currentPage + 1) > totalPage) {
			return totalPage;
		}
		return currentPage + 1;
	}

	public int getLast() {
		return totalPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
		if (currentPage > totalPage)
			currentPage = totalPage;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public void addParam(String key,String value){
		params.put(key, value);
	}
	
	public String getParamValue(String key){
		return params.get(key);
	}

}
