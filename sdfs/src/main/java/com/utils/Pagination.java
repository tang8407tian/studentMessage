package com.utils;

import java.util.List;

/**
 * ��ҳ������
 * 
 * @author �ƿ���
 * 
 * @param <T>
 */
public class Pagination<T> {
	
	private List<T> records; // ��ҳ����
	private int totalRecord; // �ܼ�¼��
	private int pageNo; // ��ǰҳ��,�ڼ�ҳ
	private int pageSize; // ÿҳ��ʾ�ļ�¼��,ÿҳ��ʾ����������
	private int totalPage; // ��ҳ��
	private int startIndex; // ��ʼ����
	private int endIndex; // ��������
	private int indexCount = 10;// ��ʾ��������Ŀ,��:10�Ļ��� ����ʾ1-10�� 2-11
	
	public Pagination(List<T> records, int totalRecord, Integer pageNo, Integer pageSize) {
		
		pageNo=(pageNo==null?1:pageNo);
		pageSize=(pageSize==null?15:pageSize);
		this.records = records;
		this.totalRecord = totalRecord;
		this.pageNo = pageNo;
		pageNo = pageNo>=1?pageNo:1;
		this.pageSize = pageSize;
		// �����ܼ�¼����ÿҳ��ʾ��������ҳ��(totalRecord+pageSize->totalPage)
		totalPage = this.totalRecord / this.pageSize;
		totalPage = (this.totalRecord % pageSize == 0) ? totalPage : (totalPage + 1);
		// ����������Ŀ����ǰҳ����ҳ�����㿪ʼ�����ͽ�������(indexCount+pageNo+totalPage->startIndex+endIndex)
		startIndex = indexCount / 2;
		startIndex = pageNo - (indexCount % 2 == 0 ? (startIndex - 1) : startIndex);
		endIndex = pageNo + indexCount / 2;
		// 1 <= startIndex < pageNo < endIndex <= totalPage
		// startIndex = pageNo - indexCount/2
		// endIndex = pageNo + indexCount/2
		if (startIndex < 1) {
			startIndex = 1;
			if (totalPage >= indexCount) {
				endIndex = indexCount;
			} else {
				endIndex = totalPage;
			}
		}
		if (endIndex > totalPage) {
			endIndex = totalPage;
			if (endIndex > indexCount) {
				startIndex = endIndex - indexCount + 1;
			} else {
				startIndex = 1;
			}
		}
	}

	/**
	 * ��ȡ��ҳ����
	 * 
	 * @return
	 */
	public List<T> getRecords() {
		return records;
	}

	/**
	 * ��ȡ�ܼ�¼��
	 * 
	 * @return
	 */
	public int getTotalRecord() {
		return totalRecord;
	}

	/**
	 * ��ǰҳ��(�ڼ�ҳ)
	 * 
	 * @return
	 */
	public int getPageNo() {
		return pageNo>=1?pageNo:1;
	}

	/**
	 * ÿҳ��ʾ���ݼ�¼��
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * ��ҳ��
	 * 
	 * @return
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * ��ʼ����
	 * 
	 * @return
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * ��������
	 * 
	 * @return
	 */
	public int getEndIndex() {
		return endIndex;
	}

	public int getIndexCount() {
		return indexCount;
	}

	public void setIndexCount(int indexCount) {
		this.indexCount = indexCount;
	}

	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}
	
	
}
