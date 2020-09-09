package com.lyht.business.process.dto.response.model;
import io.swagger.annotations.ApiModelProperty;

public class BackStepData {
	@ApiModelProperty(value = "步骤ID")
	//
	private String stepId;
	@ApiModelProperty(value = "步骤名称")
	//
	private String name;
	@ApiModelProperty(value = "步骤显示名称")
	//
	private String displayName;
	@ApiModelProperty(value = "所有者")
	//
	private String owner;
	@ApiModelProperty(value = "代理人")
	//
	private String agent;
	@ApiModelProperty(value = "接收时间")
	//
	private String receiveAt;
	@ApiModelProperty(value = "邀请步骤ID")
	//
	private String inviter;
	@ApiModelProperty(value = "邀请步骤ID(最顶级)")
	//
	private String topInviter;
	@ApiModelProperty(value = "邀请行为 如：加签，加签并返回，阅知，代理")
	//
	private String inviteAction;
	@ApiModelProperty(value = "层级审批相关用户")
	//
	private String hierarchyUser;
	@ApiModelProperty
	//
	private String handler;
	@ApiModelProperty(value = "处理时间")
	//
	private String handleAt;
	@ApiModelProperty(value = "处理意见")
	//
	private String comment;
	@ApiModelProperty(value = "步骤状态")
	//
	private String state;
	@ApiModelProperty(value = "流程ID")
	//
	private String taskId;
	@ApiModelProperty(value = "书签ID")
	//
	private String bookMarkId;
	@ApiModelProperty(value = "流程实例内容")
	//
	private String instanceXML;
	@ApiModelProperty(value = "处理周期数，退回再重走Round就会加1")
	//
	private int round;
	@ApiModelProperty(value = "流程步骤权限")
	//
	private int permission;
	@ApiModelProperty(value = "操作名称")
	//
	private String operationName;
	@ApiModelProperty(value = "表单URL")
	//
	private String formUrl;
	@ApiModelProperty(value = "同一审批人自动审批")
	//
	private boolean sameApproverAutoProcess;
	@ApiModelProperty(value = "自动同意")
	//
	private boolean isAutoProcess;
	@ApiModelProperty(value = "多人接收，一个审批步骤就通过")
	//
	private boolean isAnyone;
	@ApiModelProperty(value = "从哪个步骤退回")
	//
	private String pickBackStepId;
	@ApiModelProperty(value = "驱动到MIS")
	//
	private boolean driveToMis;
	@ApiModelProperty(value = "步骤描述")
	//
	private String description;
	@ApiModelProperty(value = "不填写审批意见")
	//
	private boolean allowEmptyOpinion;
	@ApiModelProperty(value = "不需要签名密码")
	//
	private boolean notSignaturePwd;
	@ApiModelProperty(value = "默认审批意见")
	//
	private String defaultOpinion;
	@ApiModelProperty(value = "是否是表决活动")
	//
	private boolean isVote;
	@ApiModelProperty(value = "同一投票结果比例, 当同一选项达到这比例时工作流自动流转")
	//
	private String voteResultRate;
	@ApiModelProperty(value = "投票结果")
	//
	private String voteResult;
	@ApiModelProperty(value = "同意表决选项的文本")
	//
	private String voteApproveText;
	@ApiModelProperty(value = "不同意表决选项文本")
	//
	private String voteDisapproveText;
	@ApiModelProperty(value = "弃权选项文本")
	//
	private String voteAbstainText;
	@ApiModelProperty(value = "允许弃权")
	//
	private String voteAllowAbstain;
	@ApiModelProperty(value = "是否发送消息到微信")
	//
	private String sendMsgToWechat;
	@ApiModelProperty(value = "微信消息内容，为空取Description内容")
	//
	private String wechatMsgContent;
	@ApiModelProperty(value = "审批时是否允许上传附件")
	//
	private boolean allowAttachment;
	@ApiModelProperty(value = "审批时上传的附件")
	//
	private String attachment;
	@ApiModelProperty(value = "过滤内容")
	//
	private String filterContent;
	@ApiModelProperty(value = "标识字段，用户自定义其用途")
	//
	private String intFlag;
	@ApiModelProperty(value = "此列仅用户控制从表保存时的行记录顺序，不存储在数据库")
	//
	private int editRowIndex;
	@ApiModelProperty(value = "未知")
	//
	private String displayRecordUser;
	@ApiModelProperty(value = "未知")
	//
	private String displayDepartment;
	@ApiModelProperty(value = "数据归属于系统，预留字段，应用于多项目或者多租户。")
	//
	private String belongToSystemCode;
	@ApiModelProperty(value = "主键ID")
	//
	private String id;
	@ApiModelProperty(value = "最初发起用户")
	//
	private String recordUser;
	@ApiModelProperty(value = "最初发起时间")
	//
	private String recordTime;
	@ApiModelProperty(value = "处理步骤的用户职位名")
	//
	private String departmentId;
	@ApiModelProperty(value = "处理步骤的用户姓名")
	//
	private String modifyUser;
	@ApiModelProperty(value = "修改时间")
	//
	private String modifyTime;
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getReceiveAt() {
		return receiveAt;
	}
	public void setReceiveAt(String receiveAt) {
		this.receiveAt = receiveAt;
	}
	public String getInviter() {
		return inviter;
	}
	public void setInviter(String inviter) {
		this.inviter = inviter;
	}
	public String getTopInviter() {
		return topInviter;
	}
	public void setTopInviter(String topInviter) {
		this.topInviter = topInviter;
	}
	public String getInviteAction() {
		return inviteAction;
	}
	public void setInviteAction(String inviteAction) {
		this.inviteAction = inviteAction;
	}
	public String getHierarchyUser() {
		return hierarchyUser;
	}
	public void setHierarchyUser(String hierarchyUser) {
		this.hierarchyUser = hierarchyUser;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	public String getHandleAt() {
		return handleAt;
	}
	public void setHandleAt(String handleAt) {
		this.handleAt = handleAt;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getBookMarkId() {
		return bookMarkId;
	}
	public void setBookMarkId(String bookMarkId) {
		this.bookMarkId = bookMarkId;
	}
	public String getInstanceXML() {
		return instanceXML;
	}
	public void setInstanceXML(String instanceXML) {
		this.instanceXML = instanceXML;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getFormUrl() {
		return formUrl;
	}
	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}
	public boolean isSameApproverAutoProcess() {
		return sameApproverAutoProcess;
	}
	public void setSameApproverAutoProcess(boolean sameApproverAutoProcess) {
		this.sameApproverAutoProcess = sameApproverAutoProcess;
	}
	public boolean isIsAutoProcess() {
		return isAutoProcess;
	}
	public void setIsAutoProcess(boolean isAutoProcess) {
		this.isAutoProcess = isAutoProcess;
	}
	public boolean isIsAnyone() {
		return isAnyone;
	}
	public void setIsAnyone(boolean isAnyone) {
		this.isAnyone = isAnyone;
	}
	public String getPickBackStepId() {
		return pickBackStepId;
	}
	public void setPickBackStepId(String pickBackStepId) {
		this.pickBackStepId = pickBackStepId;
	}
	public boolean isDriveToMis() {
		return driveToMis;
	}
	public void setDriveToMis(boolean driveToMis) {
		this.driveToMis = driveToMis;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isAllowEmptyOpinion() {
		return allowEmptyOpinion;
	}
	public void setAllowEmptyOpinion(boolean allowEmptyOpinion) {
		this.allowEmptyOpinion = allowEmptyOpinion;
	}
	public boolean isNotSignaturePwd() {
		return notSignaturePwd;
	}
	public void setNotSignaturePwd(boolean notSignaturePwd) {
		this.notSignaturePwd = notSignaturePwd;
	}
	public String getDefaultOpinion() {
		return defaultOpinion;
	}
	public void setDefaultOpinion(String defaultOpinion) {
		this.defaultOpinion = defaultOpinion;
	}
	public boolean isIsVote() {
		return isVote;
	}
	public void setIsVote(boolean isVote) {
		this.isVote = isVote;
	}
	public String getVoteResultRate() {
		return voteResultRate;
	}
	public void setVoteResultRate(String voteResultRate) {
		this.voteResultRate = voteResultRate;
	}
	public String getVoteResult() {
		return voteResult;
	}
	public void setVoteResult(String voteResult) {
		this.voteResult = voteResult;
	}
	public String getVoteApproveText() {
		return voteApproveText;
	}
	public void setVoteApproveText(String voteApproveText) {
		this.voteApproveText = voteApproveText;
	}
	public String getVoteDisapproveText() {
		return voteDisapproveText;
	}
	public void setVoteDisapproveText(String voteDisapproveText) {
		this.voteDisapproveText = voteDisapproveText;
	}
	public String getVoteAbstainText() {
		return voteAbstainText;
	}
	public void setVoteAbstainText(String voteAbstainText) {
		this.voteAbstainText = voteAbstainText;
	}
	public String getVoteAllowAbstain() {
		return voteAllowAbstain;
	}
	public void setVoteAllowAbstain(String voteAllowAbstain) {
		this.voteAllowAbstain = voteAllowAbstain;
	}
	public String getSendMsgToWechat() {
		return sendMsgToWechat;
	}
	public void setSendMsgToWechat(String sendMsgToWechat) {
		this.sendMsgToWechat = sendMsgToWechat;
	}
	public String getWechatMsgContent() {
		return wechatMsgContent;
	}
	public void setWechatMsgContent(String wechatMsgContent) {
		this.wechatMsgContent = wechatMsgContent;
	}
	public boolean isAllowAttachment() {
		return allowAttachment;
	}
	public void setAllowAttachment(boolean allowAttachment) {
		this.allowAttachment = allowAttachment;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getFilterContent() {
		return filterContent;
	}
	public void setFilterContent(String filterContent) {
		this.filterContent = filterContent;
	}
	public String getIntFlag() {
		return intFlag;
	}
	public void setIntFlag(String intFlag) {
		this.intFlag = intFlag;
	}
	public int getEditRowIndex() {
		return editRowIndex;
	}
	public void setEditRowIndex(int editRowIndex) {
		this.editRowIndex = editRowIndex;
	}
	public String getDisplayRecordUser() {
		return displayRecordUser;
	}
	public void setDisplayRecordUser(String displayRecordUser) {
		this.displayRecordUser = displayRecordUser;
	}
	public String getDisplayDepartment() {
		return displayDepartment;
	}
	public void setDisplayDepartment(String displayDepartment) {
		this.displayDepartment = displayDepartment;
	}
	public String getBelongToSystemCode() {
		return belongToSystemCode;
	}
	public void setBelongToSystemCode(String belongToSystemCode) {
		this.belongToSystemCode = belongToSystemCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRecordUser() {
		return recordUser;
	}
	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}


}
