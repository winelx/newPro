package com.example.baselibrary.utils;

/**
 * @author ：winelx
 * 时间：2017/11/22 0022:下午 14:33
 * 说明：网络请求端口'/.</>
 */
public class Requests {
   // public static final String networks = "http://192.168.20.38:8082/pzgc/";
public static final String networks = "http://192.168.20.120:8017/pzgc/";
    //public static final String networks = "http://192.168.20.81:8080/pzgc/";
   // public static final String networks = "http://120.79.142.15/pzgc/";

    /**
     * 登录
     */
    public static final String Login = networks + "admin/login";

    /**
     * 项目成员
     */
    public static final String Members = networks + "iface/mobile/user/staffList";

    /**
     * 修改密码
     */
    public static final String AlterPwd = networks + "iface/mobile/user/alterPwd";

    /**
     * 消息首页
     */
    public static final String TaskMain = networks + "iface/mobile/taskmsg/findWbsTaskMsgByOrg";

    /**
     * wbs树
     */
    public static final String WBSTress = networks + "iface/mobile/taskmain/findWbsTree";

    /**
     * wbsid查询任务维护列表
     */
    public static final String Mission = networks + "iface/mobile/taskmain/findTaskMainList";
    /**
     * 退出登录
     */
    public static final String BackTo = networks + "iface/mobile/logout";

    /**
     * 根据wbs书选择检查项
     */
    public static final String WbsTaskGroup = networks + "iface/mobile/taskmain/findWbsTaskGroup";

    /**
     * 查询任务维护列表
     */
    public static final String WbsTaskMain = networks + "iface/mobile/taskmain/findWbsTaskMain";

    /**
     * 任务详情
     */
    public static final String Detail = networks + "iface/mobile/taskmain/wbsTaskDetail";

    /**
     * 图纸查看
     */
    public static final String Photolist = networks + "iface/mobile/drawing/findDrawingList";

    /**
     * 上传资料
     */
    public static final String Uploade = networks + "iface/mobile/taskmain/addWbsTask";

    /**
     * 发表评论
     */
    public static final String commentaries = networks + "iface/mobile/taskmain/commentWbsTask";

    /**
     * 推送配置列表查看
     */
    public static final String PUSHList = networks + "iface/mobile/taskmain/findWbsCascadePoint";

    /**
     * 根据orgID查询任务列表
     */
    public static final String CascadeList = networks + "iface/mobile/taskmsg/findWbsTaskMsgByWbs";

    /**
     * 推送消息
     */
    public static final String pushOKgo = networks + "iface/mobile/taskmain/startWbsTaskByIds";

    /**
     * 新增推送
     */
    public static final String newPush = networks + "iface/mobile/taskmain/addNewTask";

    /**
     * 查询可切换组织
     */
    public static final String Swatchmakeup = networks + "iface/mobile/user/getUserOrg";

    /**
     * 切换组织
     */
    public static final String Swatch = networks + "iface/mobile/user/changeUserOrg";

    /**
     * 根据ID查人员信息
     */
    public static final String Personal = networks + "iface/mobile/user/staff";

    /**
     * 图纸列表
     */
    public static final String PhotoList = networks + "iface/mobile/drawing/findDrawingTree";

    /**
     * 饼状图
     */
    public static final String PieChart = networks + "iface/mobile/taskmain/findHomePieJson";
    /**
     *
     */
    public static final String Photo_ce = networks + "iface/mobile/drawing/findDrawingByGroup";

    /**
     * 任务配置
     */
    public static final String WbsTaskConfig = networks + "iface/mobile/taskmain/wbsTaskConfig";
    /**
     * 根据ID查询wbs的信息
     */
    public static final String Wbsdetails = networks + "iface/mobile/taskmsg/findWbsInfoById";
    /**
     * 查询联系人
     */
    public static final String UserList = networks + "iface/mobile/user/getStaffList";

    /**
     * 版本更新
     */
    public static final String UpLoading = networks + "iface/mobile/appversion/findAppVersion";

    /**
     * 获取前置条件
     */
    public static final String CASDPOINTD = networks + "iface/mobile/taskmain/findCasePoints";
    /**
     * x修改配置
     */
    public static final String WBSCASEPOINT = networks + "iface/mobile/taskmain/updateWbsCasePoint";
    public static final String WBSCASEPOINTs = networks + "iface/mobile/taskmain/updateWbsCasePoint";
    /**
     * 项目成员的组织树接口
     */
    public static final String ORGTREE = networks + "iface/mobile/user/orgTree";
    /**
     * 查看记录
     */
    public static final String TASKRECORD = networks + "iface/mobile/taskmain/findWbsBrowseRecord";
    //多任务回复界面
    public static final String ContentDetail = networks + "iface/mobile/taskmain/partContentDetail";
    //提亮
    public static final String SartProjectup = networks + "iface/mobile/smartProject/up";
    public static final String SartProjectdown = networks + "iface/mobile/smartProject/down";
    //亮点工程
    public static final String ListByType = networks + "iface/mobile/smartProject/getListByType";
    public static final String ListByType1 = networks + "iface/mobile/smartProject/workbright";
    //评论回复
    public static final String SAVECOMMENT = networks + "iface/mobile/taskmain/saveComment";
    //标准分部分项
    public static final String StandardList = networks + "iface/mobile/standard/findStandardList";
    //标准分类标准
    public static final String STANDARD_TREE = networks + "iface/mobile/standard/findStandardTree";
    public static final String STANDARD_BY_GROUP = networks + "iface/mobile/standard/findStandardByGroup";
    /**
     * 任务
     */
    public static final String SAVECOLLECTION = networks + "/iface/mobile/wbs/taskmain/favorite/save";
    public static final String DELETECOLLECTION = networks + "/iface/mobile/wbs/taskmain/favorite/delete";
    //收藏界面列表
    public static final String GET_LIS = networks + "iface/mobile/taskmsg/findWbsFavoriteOrg";
    //评论首页标段
    public static final String GET_MY_LIST = networks + "iface/mobile/taskmsg/findWbsCommentOrg";
    //评论列表
    public static final String GETMyPAGELIST = networks + "iface/mobile/taskmsg/findCommentTaskMsgByWbs";
    //收藏列表
    public static final String FAVORITETASKMSGBYWBS = networks + "iface/mobile/taskmsg/findFavoriteTaskMsgByWbs";
    //收藏删除
    public static final String delete = networks + "iface/mobile/wbs/favorite/delete";
    /**
     * 标段收藏
     */
    public static final String WBSSAVE = networks + "iface/mobile/wbs/favorite/save";
    /**
     * 收藏列表界面的tree
     */
    public static final String FavoriteWbsTree = networks + "iface/mobile/taskmain/selectFavoriteWbsTree";
    /**
     * 评论列表界面tree
     */
    public static final String GETMYTREE = networks + "iface/mobile/taskmain/selectCommentWbsTree";
    /**
     * 工区
     */
    public static final String WORK_AREA = networks + "iface/mobile/taskmsg/findWbsworkArea";
    /**
     * 流程审核
     */
    public static final String AUDITTask = networks + "admin/wbs/wbstaskauditrecord/auditTask";
    /**
     * 打回
     */
    public static final String AUDIT_BACK_TASK = networks + "admin/wbs/wbstaskauditrecord/auditBackTask";
    /**
     * 审核日期列表
     */
    public static final String TASKDATELIST = networks + "iface/mobile/taskmsg/getAuditTaskDateList";
    /**
     * 每日任务列表已审核和打回
     */
    public static final String GET_AUDIT_TASK_LIST = networks + "iface/mobile/taskmain/getAuditTaskList";
    /**
     * 每日任务列表待审核
     */
    public static final String GET_TASK_LIST = networks + "iface/mobile/taskmsg/getTaskList";
    /**
     * 任务审核记录
     */
    public static final String Auditrecords = networks + "iface/mobile/taskmain/Auditrecords";
    /**
     * 审核报表
     */
    public static final String REPORT_IMG_DATE_APP = networks + "admin/wbs/wbstaskauditrecord/getReportImgDateApp";
    /**
     * 检查管理列表
     */
    public static final String CHECKMANGERLIST = networks + "iface/mobile/appcheck/checkManagerList";
    /**
     * 新增检查
     */
    public static final String CHECKMANGERSAVE = networks + "iface/mobile/appcheck/save";

    /**
     * 检查类别
     */
    public static final String TASK_TYPE_LIST = networks + "iface/mobile/appcheck/getTaskTypeList";
    /**
     * 检查标准
     */
    public static final String CheckStandardApp = networks + "admin/check/checkstandard/getCheckStandardApp";
    /**
     * 根据标段选择类别
     */
    public static final String GET_TASK_TYPE_BY_WBS_ID = networks + "iface/mobile/appcheck/getTaskTypeByWbsId";
    /**
     * 检查项详情
     */
    public static final String CHECKGET_BY_ID = networks + "iface/mobile/appcheck/getById";
    /**
     * 删除通知单
     */
    public static final String DELETEMESSAGE = networks + "iface/mobile/appcheck/batch/delete";

    /**
     * 检查任务的检查项
     */
    public static final String SIMPLE_DETAILS_LIST_BY_APP = networks + "iface/mobile/appcheck/getSimpleDetailsListByApp";
    /**
     * 检查任务的检查项详情
     */
    public static final String INFO_BY_MAIN_ID_AND_SQE = networks + "iface/mobile/appcheck/getInfoByMainIdAndSqe";
    /**
     * 保存整改
     */
    public static final String CREATE_NOTICE_BY_APP = networks + "iface/mobile/appcheck/createNoticeByApp";

    /**
     * 检查管理的联系人
     */
    public static final String GET_PERSON_DATA_APP = networks + "admin/check/checknoticeform/getPersonDataApp";
    /**
     * 保存检查项详情
     */
    public static final String SAVE_DETAILS = networks + "iface/mobile/appcheck/saveDetails";
    /**
     * 获取已有通知单
     */
    public static final String GET_NOTICE_BY_APP = networks + "iface/mobile/appcheck/getNoticeByApp";
    /**
     * 检查标准详情
     */
    public static final String GET_CHECK_STANDARD_DEAL_APP = networks + "admin/check/checkstandarddeal/getCheckStandardDealApp";
    /**
     * 提交检查
     */
    public static final String SEND_DATA = networks + "iface/mobile/appcheck/sendData";
    /**
     * 删除检查
     */
    public static final String BATCHDELETE = networks + "iface/mobile/appcheck/batch/delete";

    /**
     * 检查管理模块登录人有权限的组织
     */
    public static final String getCheckOrg = networks + "iface/mobile/appcheck/getCheckOrg";

    /**
     * 检查是否允许提交
     */
    public static final String CHECK_FINISH = networks + "iface/mobile/appcheck/checkFinish";
    /**
     * 全部我的
     */
    public static final String GET_ALL_NOTICE_ORG_APP = networks + "admin/check/checknoticeform/getAllNoticeOrgApp";

    /**
     * 获取选择标准数据详情(全部)
     * ?rectificationOrgid=&status=
     */
    public static final String GET_NOTICE_DATA_APP = networks + "admin/check/checknoticeform/getNoticeDataApp";

    /**
     * 获取选择标准数据详情我的
     */
    public static final String GET_MY_NOTICE_DATA_APP = networks + "admin/check/checknoticeform/getMyNoticeDataApp";
    /**
     *
     */
    public static final String SAVE_NOTICE_APP = networks + "admin/check/checknoticeform/saveNoticeApp";
    /**
     * 下发
     */
    public static final String SEND_MESSAGE_DATA = networks + "admin/check/checknoticeform/sendData";
    /**
     * 详情
     */
    public static final String getNoticeDateApp = networks + "admin/check/checknoticeform/getNoticeDateDelApp";
    /**
     * 回复
     */
    public static final String saveReplyDataApp = networks + "admin/check/checkreplyform/saveReplyDataApp";
    /**
     * 指派下发人
     */
    public static final String saveAssignPersonApp = networks + "admin/check/checkreplydealperson/saveAssignPersonApp";

    /**
     * ?replyId=
     */
    public static final String verificationSubmit = networks + "admin/check/checkverificationform/verificationSubmit";
    /**
     * 验证保存
     */
    public static final String saveVerificationDataApp = networks + "admin/check/checkverificationform/saveVerificationDataApp";

    /**
     * ?replyId=
     */
    public static final String replySubmit = networks + "admin/check/checkreplyform/replySubmit";
    /**
     * 删除未下发
     */
    public static final String checkdeleteDateApp = networks + "admin/check/checknoticeform/deleteDateApp";
    /**
     *
     */
    public static final String GET_REPORT_ORGS = networks + "admin/check/checkmanage/getReportOrgs";

    /**
     * admin/check/checkmanage/getOrgRanking?
     */
    public static final String getOrgRanking = networks + "admin/check/checkmanage/getOrgRanking";
    /**
     * 报表统计标段得分
     */
    public static final String getOrgScoreDetail = networks + "admin/check/checkmanage/getOrgScoreDetail";

    /**
     * app端权限
     */
    public static final String getMenu = networks + "/iface/mobile/taskmain/getMenu";

    /*特种设备*/
    /**
     * 特种设备全部/我的
     */
    public static final String GETORGINFO = networks + "iface/mobile/appcheck/getOrgInfo";

    /**
     * 特种设备整改任务列表
     */
    public static final String GETSECHECKLIST = networks + "iface/mobile/appcheck/getSECheckList";
    /**
     * 特种设备名称
     */
    public static final String SETASKTYPESELECTLIST = networks + "iface/mobile/appcheck/getSETaskTypeSelectList";
    /**
     * 特种设备违反标准树
     */
    public static final String STANDARDTREE = networks + "iface/mobile/appcheck/standardTree";
    /**
     * 特种设备违反标准
     */
    public static final String SECSTANDARDLIST = networks + "iface/mobile/appcheck/getSECStandardList";
    /**
     * 特种设备隐患等级
     */
    public static final String HIDDENTROUBLELEVEL = networks + "iface/mobile/appcheck/getHiddenTroubleLevel";
    /**
     * 特种设备主要信息保存
     */
    public static final String DEVICESAVESEC = networks + "iface/mobile/appcheck/saveSECheck";
    /**
     * 特种设备详情
     */
    public static final String GETSECMAININFO = networks + "iface/mobile/appcheck/getMainInfoByFlow";
    /**
     * 特种设备检查主要数据
     */
    public static final String GETMAININFOBYEDIT = networks + "iface/mobile/appcheck/getMainInfoByEdit";
    /**
     * 特种设备删除整改通知单
     */
    public static final String devicedelete = networks + "iface/mobile/appcheck/deleteSEC";
    /**
     * 保存整改通知单问题项
     */
    public static final String SAVESECDETAILS = networks + "iface/mobile/appcheck/saveSECDetails";
    /**
     * 下发整改单
     */
    public static final String SENDSECCHECK = networks + "iface/mobile/appcheck/sendSECcheck";
    /**
     * 特种设备整改未下发前详情界面获取数据
     * /
     */
    public static final String GETSECDETAILSBYEDIT = networks + "iface/mobile/appcheck/getSECDetailsByEdit";
    /**
     * 查看所有问题项及其回复数据
     */
    public static final String GETSECDETAILSANDREPLY = networks + "iface/mobile/appcheck/getSECDetailsAndReply";
    /**
     * 特种设备选择指派人
     */
    public static final String SELECTACCPECTUSER = networks + "iface/mobile/appcheck/selectAccpectUser";
    /**
     * 特种设备保存修改回复
     */
    public static final String SAVEREPLYOFSEC = networks + "iface/mobile/appcheck/saveReplyOfSEC";
    /**
     * 创建、修改验证单
     */
    public static final String SAVEVALIDEBYAPP = networks + "iface/mobile/appcheck/saveValideByApp";
    /**
     * 获取验证单
     */
    public static final String GETVALIDATEDATA = networks + "iface/mobile/appcheck/getValidateData";
    /**
     * 特种设备指派
     */
    public static final String assignPersonOfSEC = networks + "iface/mobile/appcheck/assignPersonOfSEC";
    /**
     * 特种设备提交回复
     */
    public static final String SUBMITREPLY = networks + "iface/mobile/appcheck/submitReply";
    /**
     * 特种设备删除检查项
     */
    public static final String DELETESECDETAILSBYID = networks + "iface/mobile/appcheck/deleteSECDetailsById";
    /**
     * 特种设备 提交验证
     */
    public static final String SUBMITVALIDE = networks + "iface/mobile/appcheck/submitValide";
    /**
     * 特种设备操作记录
     */
    public static final String GETOPERHIS = networks + "iface/mobile/appcheck/getOperHis";
    /**
     * 查询审批历史记录
     */
    public static final String GETPROCESSHISCORD = networks + "iface/mobile/newcheck/getProcessHisCord";

    /**
     * 获取设备来源字典接口：
     */
    public static final String getSourceDict = networks + "admin/check/specialequipmentcheck/getSourceDict";
    /*删除回复验证单*/
    public static final String DELETEREPLYFORM = networks + "iface/mobile/newcheck/deleteReplyForm";



    /*新的整改通知*/
    /**
     * 获取“我的”、“全部”组织树
     */
    public static final String GETORGINFOBYCNF = networks + "iface/mobile/newcheck/getOrgInfoBycnf";
    /**
     * 获取列表数据
     */
    public static final String GETCNFLIST = networks + "iface/mobile/newcheck/getCNFList";
    /**
     * 获取整改通知单主要信息
     */
    public static final String GETNOTICEFORMMAININFO = networks + "iface/mobile/newcheck/getNoticeFormMainInfo";
    /**
     * 删除通知单问题项
     */
    public static final String DELETENOTICEDEL = networks + "iface/mobile/newcheck/deleteNoticeDel";
    /**
     * 指派
     */
    public static final String ASSIGNPAGE = networks + "iface/mobile/newcheck/assignPage";
    /**
     * 获取被指派人数据
     */
    public static final String GETPERSONDATA = networks + "iface/mobile/newcheck/getPersonData";
    /**
     * 下发 我回复
     */
    public static final String SENDDATA = networks + "iface/mobile/newcheck/sendData";
    /**
     * 保存、修改整改通知单
     */
    public static final String SAVENOTICEFORM = networks + "iface/mobile/newcheck/saveNoticeForm";
    /**
     * 保存、修改问题项
     */
    public static final String SAVENOTICEDETAILS = networks + "iface/mobile/newcheck/saveNoticeDetails";
    /**
     * 导入问题项时查询监督检查列表
     */
    public static final String CHOOSEMANAGEDATALIST = networks + "iface/mobile/newcheck/chooseManageDataList";
    /**
     * 导入问题项时查询问题项
     */
    public static final String GETDETAILSOFIMPORT = networks + "iface/mobile/newcheck/getDetailsOfImport";
    /**
     * 保存导入问题项
     */
    public static final String BATCHSAVENOTECEDEL = networks + "iface/mobile/newcheck/batchSaveNoteceDel";

    /**
     * 通知单保存状态时查询单个问题项详情
     */
    public static final String GETDETAILSINFOOFSAVESTATUS = networks + "iface/mobile/newcheck/getDetailsInfoOfSaveStatus";

    /**
     * 通知单非保存状态时查询单个问题项详情
     */
    public static final String GETNOTICEDETAILSINFO = networks + "iface/mobile/newcheck/getNoticeDetailsInfo";

    /***
     * 删除整改通知单
     */
    public static final String DELETENOTICE = networks + "iface/mobile/newcheck/deleteNotice";

    /*整改回复单*/
    /**
     * 获取“我的”、“全部”组织树
     */
    public static final String GETORGINFOBYCRF = networks + "iface/mobile/newcheck/getOrgInfoBycrf";
    /**
     * 获取列表数据
     */
    public static final String GETCRFLIST = networks + "iface/mobile/newcheck/getCRFList";
    /**
     * 获取保存状态的整改验证单
     */
    public static final String GETREPLYFORMOFSAVESTATUS = networks + "iface/mobile/newcheck/getReplyFormOfSaveStatus";
    /**
     * 创建整改验证单
     */
    public static final String CREATEREPLYFORM = networks + "iface/mobile/newcheck/createReplyForm";
    /**
     * 创建整改验证单时选择整改通知单
     */
    public static final String GETNOTICEFORMLIST = networks + "iface/mobile/newcheck/getNoticeFormList";
    /**
     * 查询导入问题列表
     */
    public static final String CHOOSENOTICEDELDATA = networks + "iface/mobile/newcheck/chooseNoticeDelData";
    /**
     * 保存导入问题项
     */
    public static final String BATCHSAVEREPLYDEL = networks + "iface/mobile/newcheck/batchSaveReplyDel";
    /**
     * 获取整改验证单数据详情
     */
    public static final String GETREPLYDELDATA = networks + "iface/mobile/newcheck/getReplyDelData";
    /**
     * 删除整改验证单详情
     */
    public static final String DELETEREPLYDEL = networks + "iface/mobile/newcheck/deleteReplyDel";
    /**
     * 获取非保存状态的回复验证单主要信息
     */
    public static final String GETREPLYFORMMAININFO = networks + "iface/mobile/newcheck/getReplyFormMainInfo";
    /**
     * 创建、编辑回复单
     */
    public static final String EDITREPLYFORMDEL = networks + "iface/mobile/newcheck/editReplyFormDel";
    /**
     * 获取回复单数据
     */
    public static final String GETREPLYFORMDEL = networks + "iface/mobile/newcheck/getReplyFormDel";
    /**
     * 提交回复
     */
    public static final String SUBMITREPLYDATA = networks + "iface/mobile/newcheck/submitReplyData";
    /**
     * 验证
     */
    public static final String GETORGINFOBYCNFVALIDREPLY = networks + "iface/mobile/newcheck/validReply";
    /**
     * 签名
     */
    public static final String UPLOADPERSONSIGNATURE = networks + "iface/mobile/systemnotice/uploadPersonSignature";
    /**
     * 监管检查 项目经理确认接口
     */
    public static final String CONFIRM = networks + "admin/check/checkmanage/confirm";
}