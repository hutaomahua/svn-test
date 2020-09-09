package com.lyht.business.process.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

import java.util.*;
/**
 * @author HuangTianhao
 * @date 2019年12月15日
 */
public class ProcessBackStepsResponse {
	@ApiModelProperty(value = "数据")
	@JsonProperty("Data")
	private List<Data> data;
	@ApiModelProperty(value = "错误代码")
	@JsonProperty("ErrorCode")
	private int errorcode;
	@ApiModelProperty(value = "信息")
	@JsonProperty("Message")
	private String message;
	@ApiModelProperty(value = "成功")
	@JsonProperty("Success")
	private boolean success;

	public void setData(List<Data> data) {
		this.data = data;
	}

	public List<Data> getData() {
		return data;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	public int getErrorcode() {
		return errorcode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean getSuccess() {
		return success;
	}

	public class Data {
		@ApiModelProperty(value = "步骤ID")
		@JsonProperty("StepId")
		private String stepid;
		@ApiModelProperty(value = "步骤名称")
		@JsonProperty("Name")
		private String name;
		@ApiModelProperty(value = "步骤显示名称")
		@JsonProperty("DisplayName")
		private String displayname;
		@ApiModelProperty(value = "所有者")
		@JsonProperty("Owner")
		private String owner;
		@ApiModelProperty(value = "代理人")
		@JsonProperty("Agent")
		private String agent;
		@ApiModelProperty(value = "接收时间")
		@JsonProperty("ReceiveAt")
		private String receiveat;
		@ApiModelProperty(value = "邀请步骤ID")
		@JsonProperty("Inviter")
		private String inviter;
		@ApiModelProperty(value = "邀请步骤ID(最顶级)")
		@JsonProperty("TopInviter")
		private String topinviter;
		@ApiModelProperty(value = "邀请行为 如：加签，加签并返回，阅知，代理")
		@JsonProperty("InviteAction")
		private String inviteaction;
		@ApiModelProperty(value = "层级审批相关用户")
		@JsonProperty("HierarchyUser")
		private String hierarchyuser;
		@ApiModelProperty(value = "处理人")
		@JsonProperty("Handler")
		private String handler;
		@ApiModelProperty(value = "处理时间")
		@JsonProperty("HandleAt")
		private String handleat;
		@ApiModelProperty(value = "处理意见")
		@JsonProperty("Comment")
		private String comment;
		@ApiModelProperty(value = "步骤状态")
		@JsonProperty("State")
		private String state;
		@ApiModelProperty(value = "流程ID")
		@JsonProperty("TaskId")
		private String taskid;
		@ApiModelProperty(value = "书签ID")
		@JsonProperty("BookmarkId")
		private String bookmarkid;
		@ApiModelProperty(value = "流程实例内容")
		@JsonProperty("InstanceXML")
		private String instancexml;
		@ApiModelProperty(value = "处理周期数，退回再重走Round就会加1")
		@JsonProperty("Round")
		private int round;
		@ApiModelProperty(value = "流程步骤权限")
		@JsonProperty("Permission")
		private int permission;
		@ApiModelProperty(value = "操作名称")
		@JsonProperty("OperationName")
		private String operationname;
		@ApiModelProperty(value = "表单URL")
		@JsonProperty("FormUrl")
		private String formurl;
		@ApiModelProperty(value = "同一审批人自动审批")
		@JsonProperty("SameApproverAutoProcess")
		private boolean sameapproverautoprocess;
		@ApiModelProperty(value = "自动同意")
		@JsonProperty("IsAutoProcess")
		private boolean isautoprocess;
		@ApiModelProperty(value = "多人接收，一个审批步骤就通过")
		@JsonProperty("IsAnyone")
		private boolean isanyone;
		@ApiModelProperty(value = "从哪个步骤退回")
		@JsonProperty("PickBackStepId")
		private String pickbackstepid;
		@ApiModelProperty(value = "驱动到MIS")
		@JsonProperty("DriveToMis")
		private boolean drivetomis;
		@ApiModelProperty(value = "步骤描述")
		@JsonProperty("Description")
		private String description;
		@ApiModelProperty(value = "不填写审批意见")
		@JsonProperty("AllowEmptyOpinion")
		private boolean allowemptyopinion;
		@ApiModelProperty(value = "不需要签名密码")
		@JsonProperty("NotSignaturePwd")
		private boolean notsignaturepwd;
		@ApiModelProperty(value = "默认审批意见")
		@JsonProperty("DefaultOpinion")
		private String defaultopinion;
		@ApiModelProperty(value = "是否是表决活动")
		@JsonProperty("IsVote")
		private boolean isvote;
		@ApiModelProperty(value = "同一投票结果比例, 当同一选项达到这比例时工作流自动流转")
		@JsonProperty("VoteResultRate")
		private String voteresultrate;
		@ApiModelProperty(value = "投票结果")
		@JsonProperty("VoteResult")
		private String voteresult;
		@ApiModelProperty(value = "同意表决选项的文本")
		@JsonProperty("VoteApproveText")
		private String voteapprovetext;
		@ApiModelProperty(value = "不同意表决选项文本")
		@JsonProperty("VoteDisapproveText")
		private String votedisapprovetext;
		@ApiModelProperty(value = "弃权选项文本")
		@JsonProperty("VoteAbstainText")
		private String voteabstaintext;
		@ApiModelProperty(value = "允许弃权")
		@JsonProperty("VoteAllowAbstain")
		private String voteallowabstain;
		@ApiModelProperty(value = "是否发送消息到微信")
		@JsonProperty("SendMsgToWechat")
		private String sendmsgtowechat;
		@ApiModelProperty(value = "微信消息内容，为空取Description内容")
		@JsonProperty("WechatMsgContent")
		private String wechatmsgcontent;
		@ApiModelProperty(value = "审批时是否允许上传附件")
		@JsonProperty("AllowAttachment")
		private boolean allowattachment;
		@ApiModelProperty(value = "审批时上传的附件")
		@JsonProperty("Attachment")
		private String attachment;
		@ApiModelProperty(value = "过滤内容")
		@JsonProperty("FilterContent")
		private String filtercontent;
		@ApiModelProperty(value = "标识字段，用户自定义其用途")
		@JsonProperty("IntFlag")
		private String intflag;
		@ApiModelProperty(value = "此列仅用户控制从表保存时的行记录顺序，不存储在数据库")
		@JsonProperty("EditRowIndex")
		private int editrowindex;
		@ApiModelProperty(value = "未知")
		@JsonProperty("DisplayRecordUser")
		private String displayrecorduser;
		@ApiModelProperty(value = "未知")
		@JsonProperty("DisplayDepartment")
		private String displaydepartment;
		@ApiModelProperty(value = "数据归属于系统，预留字段，应用于多项目或者多租户。")
		@JsonProperty("BelongToSystemCode")
		private String belongtosystemcode;
		@ApiModelProperty(value = "主键ID")
		@JsonProperty("Id")
		private String id;
		@ApiModelProperty(value = "最初发起用户")
		@JsonProperty("RecordUser")
		private String recorduser;
		@ApiModelProperty(value = "最初发起时间")
		@JsonProperty("RecordTime")
		private String recordtime;
		@ApiModelProperty(value = "处理步骤的用户职位名")
		@JsonProperty("DepartmentId")
		private String departmentid;
		@ApiModelProperty(value = "处理步骤的用户姓名")
		@JsonProperty("ModifyUser")
		private String modifyuser;
		@ApiModelProperty(value = "修改时间")
		@JsonProperty("ModifyTime")
		private String modifytime;

		public void setStepid(String stepid) {
			this.stepid = stepid;
		}

		public String getStepid() {
			return stepid;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setDisplayname(String displayname) {
			this.displayname = displayname;
		}

		public String getDisplayname() {
			return displayname;
		}

		public void setOwner(String owner) {
			this.owner = owner;
		}

		public String getOwner() {
			return owner;
		}

		public void setAgent(String agent) {
			this.agent = agent;
		}

		public String getAgent() {
			return agent;
		}

		public void setReceiveat(String receiveat) {
			this.receiveat = receiveat;
		}

		public String getReceiveat() {
			return receiveat;
		}

		public void setInviter(String inviter) {
			this.inviter = inviter;
		}

		public String getInviter() {
			return inviter;
		}

		public void setTopinviter(String topinviter) {
			this.topinviter = topinviter;
		}

		public String getTopinviter() {
			return topinviter;
		}

		public void setInviteaction(String inviteaction) {
			this.inviteaction = inviteaction;
		}

		public String getInviteaction() {
			return inviteaction;
		}

		public void setHierarchyuser(String hierarchyuser) {
			this.hierarchyuser = hierarchyuser;
		}

		public String getHierarchyuser() {
			return hierarchyuser;
		}

		public void setHandler(String handler) {
			this.handler = handler;
		}

		public String getHandler() {
			return handler;
		}

		public void setHandleat(String handleat) {
			this.handleat = handleat;
		}

		public String getHandleat() {
			return handleat;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public String getComment() {
			return comment;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getState() {
			return state;
		}

		public void setTaskid(String taskid) {
			this.taskid = taskid;
		}

		public String getTaskid() {
			return taskid;
		}

		public void setBookmarkid(String bookmarkid) {
			this.bookmarkid = bookmarkid;
		}

		public String getBookmarkid() {
			return bookmarkid;
		}

		public void setInstancexml(String instancexml) {
			this.instancexml = instancexml;
		}

		public String getInstancexml() {
			return instancexml;
		}

		public void setRound(int round) {
			this.round = round;
		}

		public int getRound() {
			return round;
		}

		public void setPermission(int permission) {
			this.permission = permission;
		}

		public int getPermission() {
			return permission;
		}

		public void setOperationname(String operationname) {
			this.operationname = operationname;
		}

		public String getOperationname() {
			return operationname;
		}

		public void setFormurl(String formurl) {
			this.formurl = formurl;
		}

		public String getFormurl() {
			return formurl;
		}

		public void setSameapproverautoprocess(boolean sameapproverautoprocess) {
			this.sameapproverautoprocess = sameapproverautoprocess;
		}

		public boolean getSameapproverautoprocess() {
			return sameapproverautoprocess;
		}

		public void setIsautoprocess(boolean isautoprocess) {
			this.isautoprocess = isautoprocess;
		}

		public boolean getIsautoprocess() {
			return isautoprocess;
		}

		public void setIsanyone(boolean isanyone) {
			this.isanyone = isanyone;
		}

		public boolean getIsanyone() {
			return isanyone;
		}

		public void setPickbackstepid(String pickbackstepid) {
			this.pickbackstepid = pickbackstepid;
		}

		public String getPickbackstepid() {
			return pickbackstepid;
		}

		public void setDrivetomis(boolean drivetomis) {
			this.drivetomis = drivetomis;
		}

		public boolean getDrivetomis() {
			return drivetomis;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public void setAllowemptyopinion(boolean allowemptyopinion) {
			this.allowemptyopinion = allowemptyopinion;
		}

		public boolean getAllowemptyopinion() {
			return allowemptyopinion;
		}

		public void setNotsignaturepwd(boolean notsignaturepwd) {
			this.notsignaturepwd = notsignaturepwd;
		}

		public boolean getNotsignaturepwd() {
			return notsignaturepwd;
		}

		public void setDefaultopinion(String defaultopinion) {
			this.defaultopinion = defaultopinion;
		}

		public String getDefaultopinion() {
			return defaultopinion;
		}

		public void setIsvote(boolean isvote) {
			this.isvote = isvote;
		}

		public boolean getIsvote() {
			return isvote;
		}

		public void setVoteresultrate(String voteresultrate) {
			this.voteresultrate = voteresultrate;
		}

		public String getVoteresultrate() {
			return voteresultrate;
		}

		public void setVoteresult(String voteresult) {
			this.voteresult = voteresult;
		}

		public String getVoteresult() {
			return voteresult;
		}

		public void setVoteapprovetext(String voteapprovetext) {
			this.voteapprovetext = voteapprovetext;
		}

		public String getVoteapprovetext() {
			return voteapprovetext;
		}

		public void setVotedisapprovetext(String votedisapprovetext) {
			this.votedisapprovetext = votedisapprovetext;
		}

		public String getVotedisapprovetext() {
			return votedisapprovetext;
		}

		public void setVoteabstaintext(String voteabstaintext) {
			this.voteabstaintext = voteabstaintext;
		}

		public String getVoteabstaintext() {
			return voteabstaintext;
		}

		public void setVoteallowabstain(String voteallowabstain) {
			this.voteallowabstain = voteallowabstain;
		}

		public String getVoteallowabstain() {
			return voteallowabstain;
		}

		public void setSendmsgtowechat(String sendmsgtowechat) {
			this.sendmsgtowechat = sendmsgtowechat;
		}

		public String getSendmsgtowechat() {
			return sendmsgtowechat;
		}

		public void setWechatmsgcontent(String wechatmsgcontent) {
			this.wechatmsgcontent = wechatmsgcontent;
		}

		public String getWechatmsgcontent() {
			return wechatmsgcontent;
		}

		public void setAllowattachment(boolean allowattachment) {
			this.allowattachment = allowattachment;
		}

		public boolean getAllowattachment() {
			return allowattachment;
		}

		public void setAttachment(String attachment) {
			this.attachment = attachment;
		}

		public String getAttachment() {
			return attachment;
		}

		public void setFiltercontent(String filtercontent) {
			this.filtercontent = filtercontent;
		}

		public String getFiltercontent() {
			return filtercontent;
		}

		public void setIntflag(String intflag) {
			this.intflag = intflag;
		}

		public String getIntflag() {
			return intflag;
		}

		public void setEditrowindex(int editrowindex) {
			this.editrowindex = editrowindex;
		}

		public int getEditrowindex() {
			return editrowindex;
		}

		public void setDisplayrecorduser(String displayrecorduser) {
			this.displayrecorduser = displayrecorduser;
		}

		public String getDisplayrecorduser() {
			return displayrecorduser;
		}

		public void setDisplaydepartment(String displaydepartment) {
			this.displaydepartment = displaydepartment;
		}

		public String getDisplaydepartment() {
			return displaydepartment;
		}

		public void setBelongtosystemcode(String belongtosystemcode) {
			this.belongtosystemcode = belongtosystemcode;
		}

		public String getBelongtosystemcode() {
			return belongtosystemcode;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public void setRecorduser(String recorduser) {
			this.recorduser = recorduser;
		}

		public String getRecorduser() {
			return recorduser;
		}

		public void setRecordtime(String recordtime) {
			this.recordtime = recordtime;
		}

		public String getRecordtime() {
			return recordtime;
		}

		public void setDepartmentid(String departmentid) {
			this.departmentid = departmentid;
		}

		public String getDepartmentid() {
			return departmentid;
		}

		public void setModifyuser(String modifyuser) {
			this.modifyuser = modifyuser;
		}

		public String getModifyuser() {
			return modifyuser;
		}

		public void setModifytime(String modifytime) {
			this.modifytime = modifytime;
		}

		public String getModifytime() {
			return modifytime;
		}

	}

}