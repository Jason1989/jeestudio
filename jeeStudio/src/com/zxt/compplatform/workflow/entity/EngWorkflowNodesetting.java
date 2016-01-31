package com.zxt.compplatform.workflow.entity;

import java.sql.Blob;


/**
 * EngWorkflowNodesetting entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EngWorkflowNodesetting implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9057205506614576963L;

	// Fields
	private String id;
	private String nodeId;
	private String nodeState;
	private String nodeParticipator;
	private Blob nodeAuthSetting;
	private String templateId;
	private String modelId;
	private String buttons;
	private String jsonSetting;

	// Constructors

	public String getJsonSetting() {
		return jsonSetting;
	}

	public void setJsonSetting(String jsonSetting) {
		this.jsonSetting = jsonSetting;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	/** default constructor */
	public EngWorkflowNodesetting() {
	}

	/** minimal constructor */
	public EngWorkflowNodesetting(String id) {
		this.id = id;
	}

	/** full constructor */
	public EngWorkflowNodesetting(String id, String nodeId, String nodeState,
			String nodeParticipator, Blob nodeAuthSetting) {
		this.id = id;
		this.nodeId = nodeId;
		this.nodeState = nodeState;
		this.nodeParticipator = nodeParticipator;
		this.nodeAuthSetting = nodeAuthSetting;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeState() {
		return this.nodeState;
	}

	public void setNodeState(String nodeState) {
		this.nodeState = nodeState;
	}

	public String getNodeParticipator() {
		return this.nodeParticipator;
	}

	public void setNodeParticipator(String nodeParticipator) {
		this.nodeParticipator = nodeParticipator;
	}

	public Blob getNodeAuthSetting() {
		return this.nodeAuthSetting;
	}

	public void setNodeAuthSetting(Blob nodeAuthSetting) {
		this.nodeAuthSetting = nodeAuthSetting;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EngWorkflowNodesetting))
			return false;
		EngWorkflowNodesetting castOther = (EngWorkflowNodesetting) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getNodeId() == castOther.getNodeId()) || (this
						.getNodeId() != null
						&& castOther.getNodeId() != null && this.getNodeId()
						.equals(castOther.getNodeId())))
				&& ((this.getNodeState() == castOther.getNodeState()) || (this
						.getNodeState() != null
						&& castOther.getNodeState() != null && this
						.getNodeState().equals(castOther.getNodeState())))
				&& ((this.getNodeParticipator() == castOther
						.getNodeParticipator()) || (this.getNodeParticipator() != null
						&& castOther.getNodeParticipator() != null && this
						.getNodeParticipator().equals(
								castOther.getNodeParticipator())))
				&& ((this.getNodeAuthSetting() == castOther
						.getNodeAuthSetting()) || (this.getNodeAuthSetting() != null
						&& castOther.getNodeAuthSetting() != null && this
						.getNodeAuthSetting().equals(
								castOther.getNodeAuthSetting())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getNodeId() == null ? 0 : this.getNodeId().hashCode());
		result = 37 * result
				+ (getNodeState() == null ? 0 : this.getNodeState().hashCode());
		result = 37
				* result
				+ (getNodeParticipator() == null ? 0 : this
						.getNodeParticipator().hashCode());
		result = 37
				* result
				+ (getNodeAuthSetting() == null ? 0 : this.getNodeAuthSetting()
						.hashCode());
		return result;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getButtons() {
		return buttons;
	}

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}
}