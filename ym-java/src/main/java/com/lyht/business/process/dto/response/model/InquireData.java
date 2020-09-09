package com.lyht.business.process.dto.response.model;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author HuangTianhao
 * @date 2019年12月16日
 */
public class InquireData {
		@ApiModelProperty(value = "排序码")
		//@JsonProperty("curRowIndex")                      
		private int curRowIndex;
		@ApiModelProperty(value = "流程名")
		//@JsonProperty("name")                      
		private String name;
		@ApiModelProperty(value = "发起人")
		//@JsonProperty(value = "applicant")                       
		private String applicant;
		@ApiModelProperty(value = "发起人用户名")
		//@JsonProperty(value = "applicantUserName")                         
		private String applicantUserName;
		@ApiModelProperty(value = "发起时间")
		//@JsonProperty(value = "applyAt")                     
		private String applyAt;
		@ApiModelProperty(value = "状态")
		//@JsonProperty(value = "state")                         
		private String state;
		@ApiModelProperty(value = "描述")
		//@JsonProperty(value = "description")                         
		private String description;
		@ApiModelProperty(value = "处理时间")
		//@JsonProperty(value = "formUrl")                        
		private String formUrl;
		@ApiModelProperty(value = "当前页")
		//@JsonProperty(value = "handleAt")                        
		private String handleAt;
		@ApiModelProperty(value = "申请人")
		//@JsonProperty(value = "displayApplicant")                        
		private String displayApplicant;
		@ApiModelProperty(value = "流程归属系统")
		//@JsonProperty(value = "ownerSystem")                        
		private String ownerSystem;
		@ApiModelProperty(value = "状态")
		//@JsonProperty(value = "chsState")                       
		private String chsState;
		@ApiModelProperty(value = "工作流Id")
		//@JsonProperty(value = "taskId")                     
		private String taskId;
		@ApiModelProperty(value = "流程状态")
		//@JsonProperty(value = "taskStatus")                       
		private String taskStatus;
		@ApiModelProperty(value = "过滤内容")
		//@JsonProperty(value = "filterContent")                        
		private String filterContent;
		@ApiModelProperty(value = "标识字段，用户自定义其用途")
		//@JsonProperty(value = "intFlag")                     
		private String intFlag;
		@ApiModelProperty(value = "此列仅用户控制从表保存时的行记录顺序，不存储在数据库")
		//@JsonProperty(value = "editRowIndex")                    
		private int editRowIndex;
		@ApiModelProperty(value = "新增用户")
		//@JsonProperty(value = "displayRecordUser")                      
		private String displayRecordUser;
		@ApiModelProperty(value = "归属部门")
		//@JsonProperty(value = "displayDepartment")                     
		private String displayDepartment;
		@ApiModelProperty(value = "数据归属于系统，预留字段，应用于多项目或者多租户。")
		//@JsonProperty(value = "belongToSystemCode")                     
		private String belongToSystemCode;
		@ApiModelProperty(value = "主键ID")
		//@JsonProperty(value = "id")                        
		private String id;
		@ApiModelProperty(value = "最初发起用户")
		//@JsonProperty(value = "recordUser")                         
		private String recordUser;
		@ApiModelProperty(value = "最初发起时间")
		//@JsonProperty(value = "recordTime")                       
		private String recordTime;
		@ApiModelProperty(value = "修改用户部门")
		//@JsonProperty(value = "departmentId")                        
		private String departmentId;
		@ApiModelProperty(value = "修改用户")
		//@JsonProperty(value = "modifyUser")                        
		private String modifyUser;
		@ApiModelProperty(value = "修改时间")
		//@JsonProperty(value = "modifyTime")                          
		private String modifyTime;
		public int getCurRowIndex() {
			return curRowIndex;
		}
		public void setCurRowIndex(int curRowIndex) {
			this.curRowIndex = curRowIndex;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getApplicant() {
			return applicant;
		}
		public void setApplicant(String applicant) {
			this.applicant = applicant;
		}
		public String getApplicantUserName() {
			return applicantUserName;
		}
		public void setApplicantUserName(String applicantUserName) {
			this.applicantUserName = applicantUserName;
		}
		public String getApplyAt() {
			return applyAt;
		}
		public void setApplyAt(String applyAt) {
			this.applyAt = applyAt;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getFormUrl() {
			return formUrl;
		}
		public void setFormUrl(String formUrl) {
			this.formUrl = formUrl;
		}
		public String getHandleAt() {
			return handleAt;
		}
		public void setHandleAt(String handleAt) {
			this.handleAt = handleAt;
		}
		public String getDisplayApplicant() {
			return displayApplicant;
		}
		public void setDisplayApplicant(String displayApplicant) {
			this.displayApplicant = displayApplicant;
		}
		public String getOwnerSystem() {
			return ownerSystem;
		}
		public void setOwnerSystem(String ownerSystem) {
			this.ownerSystem = ownerSystem;
		}
		public String getChsState() {
			return chsState;
		}
		public void setChsState(String chsState) {
			this.chsState = chsState;
		}
		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}
		public String getTaskStatus() {
			return taskStatus;
		}
		public void setTaskStatus(String taskStatus) {
			this.taskStatus = taskStatus;
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


//
//
//package com.lyht.business.process.dto.response.model;
//
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//
//import io.swagger.annotations.ApiModelProperty;
//
///**
// * @author HuangTianhao
// * @date 2019年12月16日
// */
//public class InquireData {
//		@ApiModelProperty(value = "排序码")
//		@JsonProperty("curRowIndex")                      
//		private int CurRowIndex;
//		@ApiModelProperty(value = "流程名")
//		@JsonProperty("name")                      
//		private String Name;
//		@ApiModelProperty(value = "发起人")
//		@JsonProperty(value = "applicant")                       
//		private String Applicant;
//		@ApiModelProperty(value = "发起人用户名")
//		@JsonProperty(value = "applicantUserName")                         
//		private String ApplicantUserName;
//		@ApiModelProperty(value = "发起时间")
//		@JsonProperty(value = "applyAt")                     
//		private String ApplyAt;
//		@ApiModelProperty(value = "状态")
//		@JsonProperty(value = "state")                         
//		private String State;
//		@ApiModelProperty(value = "描述")
//		@JsonProperty(value = "description")                         
//		private String Description;
//		@ApiModelProperty(value = "处理时间")
//		@JsonProperty(value = "formUrl")                        
//		private String FormUrl;
//		@ApiModelProperty(value = "当前页")
//		@JsonProperty(value = "handleAt")                        
//		private String HandleAt;
//		@ApiModelProperty(value = "申请人")
//		@JsonProperty(value = "displayApplicant")                        
//		private String DisplayApplicant;
//		@ApiModelProperty(value = "流程归属系统")
//		@JsonProperty(value = "ownerSystem")                        
//		private String OwnerSystem;
//		@ApiModelProperty(value = "状态")
//		@JsonProperty(value = "chsState")                       
//		private String ChsState;
//		@ApiModelProperty(value = "工作流Id")
//		@JsonProperty(value = "taskId")                     
//		private String TaskId;
//		@ApiModelProperty(value = "流程状态")
//		@JsonProperty(value = "taskStatus")                       
//		private String TaskStatus;
//		@ApiModelProperty(value = "过滤内容")
//		@JsonProperty(value = "filterContent")                        
//		private String FilterContent;
//		@ApiModelProperty(value = "标识字段，用户自定义其用途")
//		@JsonProperty(value = "intFlag")                     
//		private String IntFlag;
//		@ApiModelProperty(value = "此列仅用户控制从表保存时的行记录顺序，不存储在数据库")
//		@JsonProperty(value = "editRowIndex")                    
//		private int EditRowIndex;
//		@ApiModelProperty(value = "新增用户")
//		@JsonProperty(value = "displayRecordUser")                      
//		private String DisplayRecordUser;
//		@ApiModelProperty(value = "归属部门")
//		@JsonProperty(value = "displayDepartment")                     
//		private String DisplayDepartment;
//		@ApiModelProperty(value = "数据归属于系统，预留字段，应用于多项目或者多租户。")
//		@JsonProperty(value = "belongToSystemCode")                     
//		private String BelongToSystemCode;
//		@ApiModelProperty(value = "主键ID")
//		@JsonProperty(value = "id")                        
//		private String Id;
//		@ApiModelProperty(value = "最初发起用户")
//		@JsonProperty(value = "recordUser")                         
//		private String RecordUser;
//		@ApiModelProperty(value = "最初发起时间")
//		@JsonProperty(value = "recordTime")                       
//		private String RecordTime;
//		@ApiModelProperty(value = "修改用户部门")
//		@JsonProperty(value = "departmentId")                        
//		private String DepartmentId;
//		@ApiModelProperty(value = "修改用户")
//		@JsonProperty(value = "modifyUser")                        
//		private String ModifyUser;
//		@ApiModelProperty(value = "修改时间")
//		@JsonProperty(value = "modifyTime")                          
//		private String ModifyTime;
//		public int getCurRowIndex() {
//			return CurRowIndex;
//		}
//		public void setCurRowIndex(int curRowIndex) {
//			CurRowIndex = curRowIndex;
//		}
//		public String getName() {
//			return Name;
//		}
//		public void setName(String name) {
//			Name = name;
//		}
//		public String getApplicant() {
//			return Applicant;
//		}
//		public void setApplicant(String applicant) {
//			Applicant = applicant;
//		}
//		public String getApplicantUserName() {
//			return ApplicantUserName;
//		}
//		public void setApplicantUserName(String applicantUserName) {
//			ApplicantUserName = applicantUserName;
//		}
//		public String getApplyAt() {
//			return ApplyAt;
//		}
//		public void setApplyAt(String applyAt) {
//			ApplyAt = applyAt;
//		}
//		public String getState() {
//			return State;
//		}
//		public void setState(String state) {
//			State = state;
//		}
//		public String getDescription() {
//			return Description;
//		}
//		public void setDescription(String description) {
//			Description = description;
//		}
//		public String getFormUrl() {
//			return FormUrl;
//		}
//		public void setFormUrl(String formUrl) {
//			FormUrl = formUrl;
//		}
//		public String getHandleAt() {
//			return HandleAt;
//		}
//		public void setHandleAt(String handleAt) {
//			HandleAt = handleAt;
//		}
//		public String getDisplayApplicant() {
//			return DisplayApplicant;
//		}
//		public void setDisplayApplicant(String displayApplicant) {
//			DisplayApplicant = displayApplicant;
//		}
//		public String getOwnerSystem() {
//			return OwnerSystem;
//		}
//		public void setOwnerSystem(String ownerSystem) {
//			OwnerSystem = ownerSystem;
//		}
//		public String getChsState() {
//			return ChsState;
//		}
//		public void setChsState(String chsState) {
//			ChsState = chsState;
//		}
//		public String getTaskId() {
//			return TaskId;
//		}
//		public void setTaskId(String taskId) {
//			TaskId = taskId;
//		}
//		public String getTaskStatus() {
//			return TaskStatus;
//		}
//		public void setTaskStatus(String taskStatus) {
//			TaskStatus = taskStatus;
//		}
//		public String getFilterContent() {
//			return FilterContent;
//		}
//		public void setFilterContent(String filterContent) {
//			FilterContent = filterContent;
//		}
//		public String getIntFlag() {
//			return IntFlag;
//		}
//		public void setIntFlag(String intFlag) {
//			IntFlag = intFlag;
//		}
//		public int getEditRowIndex() {
//			return EditRowIndex;
//		}
//		public void setEditRowIndex(int editRowIndex) {
//			EditRowIndex = editRowIndex;
//		}
//		public String getDisplayRecordUser() {
//			return DisplayRecordUser;
//		}
//		public void setDisplayRecordUser(String displayRecordUser) {
//			DisplayRecordUser = displayRecordUser;
//		}
//		public String getDisplayDepartment() {
//			return DisplayDepartment;
//		}
//		public void setDisplayDepartment(String displayDepartment) {
//			DisplayDepartment = displayDepartment;
//		}
//		public String getBelongToSystemCode() {
//			return BelongToSystemCode;
//		}
//		public void setBelongToSystemCode(String belongToSystemCode) {
//			BelongToSystemCode = belongToSystemCode;
//		}
//		public String getId() {
//			return Id;
//		}
//		public void setId(String id) {
//			Id = id;
//		}
//		public String getRecordUser() {
//			return RecordUser;
//		}
//		public void setRecordUser(String recordUser) {
//			RecordUser = recordUser;
//		}
//		public String getRecordTime() {
//			return RecordTime;
//		}
//		public void setRecordTime(String recordTime) {
//			RecordTime = recordTime;
//		}
//		public String getDepartmentId() {
//			return DepartmentId;
//		}
//		public void setDepartmentId(String departmentId) {
//			DepartmentId = departmentId;
//		}
//		public String getModifyUser() {
//			return ModifyUser;
//		}
//		public void setModifyUser(String modifyUser) {
//			ModifyUser = modifyUser;
//		}
//		public String getModifyTime() {
//			return ModifyTime;
//		}
//		public void setModifyTime(String modifyTime) {
//			ModifyTime = modifyTime;
//		}
//
//
//	
//	
//}
//
