package com.newland.corpxin.conf;

public class Configuration {

	private long duration;
	
	private String brokers;
	
	private String groupId;
	
	private String topic;

	private String kafkaSinkTopic;

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getBrokers() {
		return brokers;
	}

	public void setBrokers(String brokers) {
		this.brokers = brokers;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getKafkaSinkTopic() {
		return kafkaSinkTopic;
	}

	public void setKafkaSinkTopic(String kafkaSinkTopic) {
		this.kafkaSinkTopic = kafkaSinkTopic;
	}

	@Override
	public String toString() {
		return String.format("duration=%s,brokers=%s,,topic=%s,group.id=%s,setKafkaSinkTopic=%s", this.duration,this.brokers,this.topic,this.groupId,this.kafkaSinkTopic);
	}
	
}
