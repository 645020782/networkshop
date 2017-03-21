package com.utstar.networkshop.domain;

import java.util.List;

public class Pagination<T> {
	private Integer beginPage=1;
	private Integer currentPage;
	private Integer endPage;
	private Integer beginRecord=0;
	private Integer pageSize=5;
	private Integer recordTotal;
	private List<T> rows;
	public Integer getBeginPage() {
		return beginPage;
	}
	public void setBeginPage(Integer beginPage) {
		this.beginPage = beginPage;
	}
	public Integer getEndPage() {
		return endPage;
	}
	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getRecordTotal() {
		return recordTotal;
	}
	public void setRecordTotal(Integer recordTotal) {
		this.recordTotal = recordTotal;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public Integer getBeginRecord() {
		return beginRecord;
	}
	public void setBeginRecord(Integer beginRecord) {
		this.beginRecord = beginRecord;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
}
