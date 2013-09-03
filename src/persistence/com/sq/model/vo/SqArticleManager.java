package com.sq.model.vo;

import java.util.Date;

/**
 * SqArticleManager entity. @author MyEclipse Persistence Tools
 */

public class SqArticleManager implements java.io.Serializable {

	// Fields

	private String artId;
	private Date delivefyDate;
	private Date publishDate;
//	private String delivefyUserId;
//	private String publishUserId;
	private String artTitle;
	private String artContent;
	private String plate;
	private String status;
	private SqUserTab delivefyUser ;
	private SqUserTab publishUser ;

	// Constructors

	/** default constructor */
	public SqArticleManager() {
	}

	/** full constructor */
	public SqArticleManager(Date delivefyDate, Date publishDate,
			String delivefyUserId, String publishUserId, String artTitle,
			String artContent, String plate, String status) {
		this.delivefyDate = delivefyDate;
		this.publishDate = publishDate;
		this.artTitle = artTitle;
		this.artContent = artContent;
		this.plate = plate;
		this.status = status;
	}

	// Property accessors

	public String getArtId() {
		return this.artId;
	}

	public void setArtId(String artId) {
		this.artId = artId;
	}

	public Date getDelivefyDate() {
		return this.delivefyDate;
	}

	public void setDelivefyDate(Date delivefyDate) {
		this.delivefyDate = delivefyDate;
	}

	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

//	public String getDelivefyUserId() {
//		return this.delivefyUserId;
//	}
//
//	public void setDelivefyUserId(String delivefyUserId) {
//		this.delivefyUserId = delivefyUserId;
//	}
//
//	public String getPublishUserId() {
//		return this.publishUserId;
//	}
//
//	public void setPublishUserId(String publishUserId) {
//		this.publishUserId = publishUserId;
//	}

	public String getArtTitle() {
		return this.artTitle;
	}

	public void setArtTitle(String artTitle) {
		this.artTitle = artTitle;
	}

	public String getArtContent() {
		return this.artContent;
	}

	public void setArtContent(String artContent) {
		this.artContent = artContent;
	}

	public String getPlate() {
		return this.plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SqUserTab getDelivefyUser() {
		return delivefyUser;
	}

	public void setDelivefyUser(SqUserTab delivefyUser) {
		this.delivefyUser = delivefyUser;
	}

	public SqUserTab getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(SqUserTab publishUser) {
		this.publishUser = publishUser;
	}

}