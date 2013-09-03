package com.sq.model.vo;

/**
 * SequenceId entity. @author MyEclipse Persistence Tools
 */

public class SequenceId implements java.io.Serializable {

	// Fields

	private String name;
	private Integer seqNo;

	// Constructors

	/** default constructor */
	public SequenceId() {
	}

	/** full constructor */
	public SequenceId(String name, Integer seqNo) {
		this.name = name;
		this.seqNo = seqNo;
	}

	// Property accessors

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SequenceId))
			return false;
		SequenceId castOther = (SequenceId) other;

		return ((this.getName() == castOther.getName()) || (this.getName() != null
				&& castOther.getName() != null && this.getName().equals(
				castOther.getName())))
				&& ((this.getSeqNo() == castOther.getSeqNo()) || (this
						.getSeqNo() != null
						&& castOther.getSeqNo() != null && this.getSeqNo()
						.equals(castOther.getSeqNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getName() == null ? 0 : this.getName().hashCode());
		result = 37 * result
				+ (getSeqNo() == null ? 0 : this.getSeqNo().hashCode());
		return result;
	}

}