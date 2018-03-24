package com.dream.framework.dao;

public interface IPager {
	public int startRow();

	public int endRow();

	public void setTotalRow(int totalRow);

	public int getTotalRow();

	public void setPageSize(int pageSize);

	public int getPageSize();

	public void setCurrentPage(int currentPage);

	public int getCurrentPage();
}
