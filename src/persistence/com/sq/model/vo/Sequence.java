package com.sq.model.vo;


/**
 * Sequence entity. @author MyEclipse Persistence Tools
 */

public class Sequence implements java.io.Serializable {

	// Fields

	private SequenceId id;
	private String format;

	// Constructors

	/** default constructor */
	public Sequence() {
	}

	/** full constructor */
	public Sequence(SequenceId id, String format) {
		this.id = id;
		this.format = format;
	}

	// Property accessors

	public SequenceId getId() {
		return this.id;
	}

	public void setId(SequenceId id) {
		this.id = id;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}