package com.domain;

public class Review {
	protected int oid;
	protected String id;
	protected String date;
	protected String comment;

	public Review(int aOid, String aId, String aDate, String aComment) {
		oid = aOid;
		id = aId;
		date = aDate;
		comment = aComment;
	}

	//set은 DB가 알아서 생기므로 제외
	public int getOid()
	{
		return oid;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String setComment) {
		comment = setComment;
	}

	
	
	public String getId() {
		return id;
	}

	public void setId(String aId) {
		id = aId;
	}

	
	public String getDate() {
		return date;
	}

	public void setDate(String aDate) {
		date = aDate;
	}
	/*
	 * 테스트 데이터입니다. 사용하지 않습니다.
	//전부 그냥 한줄로 출력
	public String getFull()
	{
		return oid+"번"+id+"손님"+date+"에 남긴 글 "+comment;
	}
	*/
}
