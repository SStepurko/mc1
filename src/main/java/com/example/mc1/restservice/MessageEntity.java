package com.example.mc1.restservice;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class MessageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long sessionId;
	@Temporal(TemporalType.TIMESTAMP)
	private Date mc1Timestamp;
	@Temporal(TemporalType.TIMESTAMP)
	private Date mc2Timestamp;
	@Temporal(TemporalType.TIMESTAMP)
	private Date mc3Timestamp;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTimestamp;

	public MessageEntity() {
	}

	public MessageEntity(Long sessionId, Date mc1Timestamp, Date mc2Timestamp, Date mc3Timestamp, Date endTimestamp) {
		this.sessionId = sessionId;
		this.mc1Timestamp = mc1Timestamp;
		this.mc2Timestamp = mc2Timestamp;
		this.mc3Timestamp = mc3Timestamp;
		this.endTimestamp = endTimestamp;
	}

	public MessageEntity(Long sessionId, Date mc1Timestamp) {
		this.sessionId = sessionId;
		this.mc1Timestamp = mc1Timestamp;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public Date getMc1Timestamp() {
		return mc1Timestamp;
	}

	public void setMc1Timestamp(Date mc1Timestamp) {
		this.mc1Timestamp = mc1Timestamp;
	}

	public Date getMc2Timestamp() {
		return mc2Timestamp;
	}

	public void setMc2Timestamp(Date mc2Timestamp) {
		this.mc2Timestamp = mc2Timestamp;
	}

	public Date getMc3Timestamp() {
		return mc3Timestamp;
	}

	public void setMc3Timestamp(Date mc3Timestamp) {
		this.mc3Timestamp = mc3Timestamp;
	}

	public Date getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(Date endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	@Override
	public String toString() {
		return "ServiceMessage{" +
				"id=" + id +
				", sessionId=" + sessionId +
				", mc1Timestamp=" + mc1Timestamp +
				", mc2Timestamp=" + mc2Timestamp +
				", mc3Timestamp=" + mc3Timestamp +
				", endTimestamp=" + endTimestamp +
				'}';
	}
}
