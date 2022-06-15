package com.gilang.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gylang
 * @date 2021/1/2
 */
@AllArgsConstructor
@Getter
public enum BaseResultCode implements BaseCode {
    /**
     * 成功
     */
    OK("00000", "ok"),
    /**
     * 用户端错误 AXXX
     */
    USER_ERROR("A0001", "用户端错误 一级宏观错误码"),

    /**
     * 注册异常
     */
    USER_REGISTRY_ERROR("A0100", "用户注册错误 二级宏观错误码"),
    NOT_ACCEPT_PROTOCOL("A0101", "用户未同意隐私协议"),
    REGISTRY_AREA_LIMIT("A0102", "注册国家或地区受限"),
    USERNAME_CHECK_ERROR("A0110", "用户名校验失败"),
    USERNAME_EXIST("A0111", "用户名已存在"),
    USERNAME_SENSITIVE("A0112", "用户名包含敏感词"),
    USERNAME_SPECIAL_CHAR("A0113", "用户名包含特殊字符"),
    PASSWORD_CHECK_ERROR("A0120", "密码校验失败"),
    PASSWORD_TOO_SHORT("A0121", "密码长度不够"),
    PASSWORD_NOT_STRENGTH("A0122", "密码强度不够"),
    VERIFY_CODE_ERROR("A0130", "校验码输入错误"),
    SMS_CODE_ERROR("A0131", "短信校验码输入错误"),
    EMAIL_CODE_ERROR("A0132", "邮件校验码输入错误"),
    IVR_CODE_ERROR("A0133", "语音校验码输入错误"),
    ID_ERROR("A0140", "用户证件异常"),
    NOT_SELECT_ID_TYPE("A0141", "用户证件类型未选择"),
    NID_CHECK_ERROR("A0142", "大陆身份证编号校验非法"),
    PASSPORT_ERROR("A0143", "护照编号校验非法"),
    MID_CARD("A0144", "军官证编号校验非法"),
    BASE_INF_ERROR("A0150", "用户基本信息校验失败"),
    CELLPHONE_ERROR("A0151", "手机格式校验失败"),
    ADDRESS_ERROR("A0152", "地址格式校验失败"),
    MAIL_ERROR("A0153", "邮箱格式校验失败"),

    /**
     * 登录异常
     */
    LOGIN_ERROR("A0200", "用户登录异常 二级宏观错误码"),
    USER_NOT_EXIST("A0201", "用户账户不存在"),
    USER_STOP("A0202", "用户账户被冻结"),
    USER_HAS_CANCEL("A0203", "用户账户已作废"),
    USERNAME_PASSWORD_ERROR("A0210", "用户密码错误"),
    NAME("A0211", "用户输入密码错误次数超限"),
    USER_IDENTITY_CHECK_ERROR("A0220", "用户身份校验失败"),
    FINGER_ID_ERROR("A0221", "用户指纹识别失败"),
    FACE_ID_ERROR("A0222", "用户面容识别失败"),
    OAUTH2_NOT_PERMIT("A0223", "用户未获得第三方登录授权"),
    USER_LOGIN_EXPIRE("A0230", "用户登录已过期"),
    USER_VERIFY_CODE_ERROR("A0240", "用户验证码错误"),
    GET_USER_VERIFY_CODE_LIMIT("A0241", "用户验证码尝试次数超限"),
    USER_OFFLINE("A0242", "用户不在线"),
    /**
     * 权限异常
     */
    PERMIT_ERROR("A0300", "访问权限异常 二级宏观错误码"),
    NOT_PERMIT("A0301", "访问未授权"),
    PERMITTING("A0302", "正在授权中"),
    USER_AUTH_BE_REJECTED("A0303", "用户授权申请被拒绝"),
    NOT_ACCESS_PRIVATE_RESOURCE("A0310", "因访问对象隐私设置被拦截"),
    PERMIT_EXPIRE("A0311", "授权已过期"),
    NOT_PERMIT_API("A0312", "无权限使用 API"),
    VISIT_INTERCEPT("A0320", "用户访问被拦截"),
    IS_USER_BLACK_LIST("A0321", "黑名单用户"),
    ILLEGAL_IP("A0323", "非法 IP 地址"),
    GATEWAY_ACCESS_LIMIT("A0324", "网关访问受限"),
    IS_AREA_BLACK_LIST("A0325", "地域黑名单"),
    SERVER_ARREAR("A0330", "服务已欠费"),
    USER_SIGN_ERROR("A0340", "用户签名异常"),
    RSA_SIGN_ERROR("A0341", "RSA 签名错误"),

    /**
     * 请求参数错误
     */
    PARAMS_ERROR("A0400", "用户请求参数错误 二级宏观错误码"),
    HAVE_SPITE_REDIRECT_URL("A0401", "包含非法恶意跳转链接"),
    INVALID_INPUT("A0402", "无效的用户输入"),
    REQUIRE_PARAMS_HAVE_EMPTY("A0410", "请求必填参数为空"),
    ORDER_ID_EMPTY("A0411", "用户订单号为空"),
    PUCHASE_NUM_IS_EMPTY("A0412", "订购数量为空"),
    LACK_TIMESTAMP("A0413", "缺少时间戳参数"),
    INVALID_TIMESTAMP("A0414", "非法的时间戳参数"),
    PARAMS_VALUE_OVER_LIMIT("A0420", "请求参数值超出允许的范围"),
    PARAMS_TYPE_NOT_MATCH("A0421", "参数格式不匹配"),
    NOT_AT_SERVER_AREA("A0422", "地址不在服务范围"),
    NOT_AT_SERVER_TIME("A0423", "时间不在服务范围"),
    AMOUNT_OVER_LIMIT("A0424", "金额超出限制"),
    LOGIN_NUM_LIMIT("A0425", "数量超出限制"),
    BATCH_OVER_LIMIT("A0426", "请求批量处理总个数超出限制"),
    JSON_ERROR("A0427", "请求 JSON 解析失败"),
    ILLEGALITY_INPUT("A0430", "用户输入内容非法"),
    HAVE_SENSITIVE_CHAR("A0431", "包含违禁敏感词"),
    IMG_HAVE_ILLEGALITY_INF("A0432", "图片包含违禁信息"),
    FILE_PIRACY("A0433", "文件侵犯版权"),
    USER_OPEARTION_ERROR("A0440", "用户操作异常"),
    USER_PAY_OVER_TIME("A0441", "用户支付超时"),
    CONFIRM_ORDER_OVER_TIME("A0442", "确认订单超时"),
    ORDER_CLOSED("A0443", "订单已关闭"),

    /**
     * 用户请求服务异常
     */
    USER_REQUEST_ERROR("A0500", "用户请求服务异常 二级宏观错误码"),
    REQUEST_NUM_OVER_LIMIT("A0501", "请求次数超出限制"),
    PARALLEL_REQUEEST_LIMIT("A0502", "请求并发数超出限制"),
    WAIT_FOR_ACTION("A0503", "用户操作请等待"),
    WEBSOCKET_CONNECTION_ERROR("A0504", "WebSocket 连接异常"),
    WEBSOCKET_CONNECTION_CLOSED("A0505", "WebSocket 连接断开"),
    REPEAT_REQUEST("A0506", "用户重复请求"),

    /**
     * 用户资源异常
     */
    USER_RESOURCE_ERROR("A0600", "用户资源异常 二级宏观错误码"),
    BALANCE_NOT_SUFFICIENT("A0601", "账户余额不足"),
    USER_DISK_NOT_SUFFICIENT("A0602", "用户磁盘空间不足"),
    USER_MEMORY_NOT_SUFFICIENT("A0603", "用户内存空间不足"),
    USER_OSS_NOT_SUFFICIENT("A0604", "用户 OSS 容量不足"),
    RESOURCE_ALL_USED("A0605", "用户配额已用光"),
    /**
     * 用户上传错误
     */
    UPLOAD_ERRRO("A0700", "用户上传文件异常 二级宏观错误码"),
    UPLOAD_FILE_TYPE_ERROR("A0701", "用户上传文件类型不匹配"),
    UPLOAD_FILE_TOO_BIG("A0702", "用户上传文件太大"),
    UPLOAD_IMG_TOO_BIG("A0703", "用户上传图片太大"),
    UPLOAD_VIDEO_TOO_BIG("A0704", "用户上传视频太大"),
    IPLOAD_ZIP_TOO_BIG("A0705", "用户上传压缩文件太大"),
    /**
     * 版本错误
     */
    VERSION_ERROR("A0800", "用户当前版本异常 二级宏观错误码"),
    VERSION_NOT_FIT("A0801", "用户安装版本与系统不匹配"),
    VERION_TOO_LOW("A0802", "用户安装版本过低"),
    VERSION_TOO_HEIGHT("A0803", "用户安装版本过高"),
    VERSION_EXPIRE("A0804", "用户安装版本已过期"),
    API_VERSION_NOT_FIT("A0805", "用户 API 请求版本不匹配"),
    API_VERSION_TOO_HEIGHT("A0806", "用户 API 请求版本过高"),
    API_VERSION_TOO_LOW("A0807", "用户 API 请求版本过低"),

    /**
     * 隐私授权
     */
    PRIVACY_ERROR("A0900", "用户隐私未授权 二级宏观错误码"),
    PRIVACY_NO_SIGN("A0901", "用户隐私未签署"),
    CAMERA_NOT_PERMIT("A0902", "用户摄像头未授权"),
    PHOTO_ALBUM_NOT_PERMIT("A0904", "用户图片库未授权"),
    FILE_NOT_PERMIT("A0905", "用户文件未授权"),
    LOCATION_NOT_PERMIT("A0906", "用户位置信息未授权"),
    ADDRESS_BOOK_NOT_PERMIT("A0907", "用户通讯录未授权"),
    /**
     * 设备异常
     */
    EQUIPMENT_ERROR("A1000", "用户设备异常 二级宏观错误码"),
    CAMERA_ERROR("A1001", "用户相机异常"),
    MIC_ERROR("A1002", "用户麦克风异常"),
    TELHONE_RECEIVE_ERROR("A1003", "用户听筒异常"),
    LOUDSPEAKER_ERROR("A1004", "用户扬声器异常"),
    GPS_ERROR("A1005", "用户 GPS 定位异常"),

    /**
     * 系统执行异常
     */
    SYSTEM_ERROR("B0001", "系统执行出错 一级宏观错误码"),
    /**
     * 系统执行异常 - 超时
     */
    SYSTEM_PROCESS_ERROR("B0100", "系统执行超时 二级宏观错误码"),

    ORDER_PROCESS_VOER_TIME("B0101", "系统订单处理超时"),
    /**
     * 容灾异常
     */
    SYSTEM_DISASTER_ERROR("B0200", "系统容灾功能被触发 二级宏观错误码"),
    SYSTEM_CURRENT_LIMITING("B0210", "系统限流"),
    SYSTEM_FUNCTION_DEGRADATION("B0220", "系统功能降级"),
    /**
     * 系统资源异常
     */
    SYSTEM_RESOURCE_ERROR("B0300", "系统资源异常 二级宏观错误码"),
    SYSTEM_RESOURCE_USE_UP("B0310", "系统资源耗尽"),
    SYSTEM_DISK_USE_UP("B0311", "系统磁盘空间耗尽"),
    SYSTEM_MEMORY_USE_UP("B0312", "系统内存耗尽"),
    SYSTEM_FILE_HANDLER_USE_UP("B0313", "文件句柄耗尽"),
    SYSTEM_CONNECTION_POOL_USE_UP("B0314", "系统连接池耗尽"),
    SYSTEM_THREAD_POOL_USE_UP("B0315", "系统线程池耗尽"),
    SYSTEM_VISIT_RESOURCE_ERROR("B0320", "系统资源访问异常"),
    SYSTEM_VISIT_DISK_FILE_ERROR("B0321", "系统读取磁盘文件失败"),

    /**
     * 调用第三方服务错误
     */
    INVOKE_OTHER_SERVICE_ERROR("C0001", "调用第三方服务出错 一级宏观错误码"),

    MIDDLEWARE_ERROR("C0100", "中间件服务出错 二级宏观错误码"),
    RPC_SERVICE_ERROR("C0110", "RPC 服务出错"),
    RPC_SERVICE_NOT_FOUND("C0111", "RPC 服务未找到"),
    RPC_SERVICE_NOT_REGISTRY("C0112", "RPC 服务未注册"),
    API_NOT_EXIST("C0113", "接口不存在"),
    MESSAGE_SERVICE_ERROR("C0120", "消息服务出错"),
    MESSAGE_SERVICE_PUSH_ERROR("C0121", "消息投递出错"),
    MESSAGE_SERVICE_CONSUME_ERROR("C0122", "消息消费出错"),
    MESSAGE_SERVICE_SUBSCRIBE_ERROR("C0123", "消息订阅出错"),
    MESSAGE_SERVICE_GOURP_NOT_FOUND("C0124", "消息分组未查到"),
    CACHE_SERVICE_ERROR("C0130", "缓存服务出错"),
    KEY_OVER_LIMIT("C0131", "key 长度超过限制"),
    VALUE_OVER_LIMIT("C0132", "value 长度超过限制"),
    STORAGE_MEMORY_USE_UP("C0133", "存储容量已满"),
    NOT_SUPPORT_THIS_DATA_TYPE("C0134", "不支持的数据格式"),
    CONFIG_SERVICE_ERROR("C0140", "配置服务出错"),
    NETWORK_RESOURCE_SERVICE_ERROR("C0150", "网络资源服务出错"),
    VPC_SERVICE_ERROR("C0151", "VPN 服务出错"),
    CDN_SERVICE_ERROR("C0152", "CDN 服务出错"),
    DOMAIN_NAME_SERVICE_ERROR("C0153", "域名解析服务出错"),
    GATEWAY_ERROR("C0154", "网关服务出错"),

    /**
     * 第三方系统执行错误
     */
    OTHER_PROCESS_OVER_TIME("C0200", "第三方系统执行超时 二级宏观错误码"),
    RPC_OVER_TIME("C0210", "RPC 执行超时"),
    MESSAGE_PUSH_OVER_TIME("C0220", "消息投递超时"),
    CACHE_SERVICE_OVER_TIME("C0230", "缓存服务超时"),
    CONFIG_SERVICE_OVER_TIME("C0240", "配置服务超时"),
    DATABASE_OVER_TIME("C0250", "数据库服务超时"),
    /**
     * 数据库服务异常
     */
    DATABASE_ERROR("C0300", "数据库服务出错 二级宏观错误码"),
    TABLE_NOT_EXIST("C0311", "表不存在"),
    COLUMN_NOT_EXIST("C0312", "列不存在"),
    MULTI_TABLE_SELECT_HAVE_SAME_COLUMN("C0321", "多表关联中存在多个相同名称的列"),
    DATABASE_DEADLOCK("C0331", "数据库死锁"),
    PRIMARY_KEY_CONFLICT("C0341", "主键冲突"),

    OTHER_SYSTEM_DISASTER("C0400", "第三方容灾系统被触发 二级宏观错误码"),
    OTHER_SYSTEM_CURRENT_LIMITING("C0401", "第三方系统限流"),
    OTHER_SYSTEM_FUNCTION_DEGRADATION("C0402", "第三方功能降级"),
    /**
     * 通知服务出错
     */
    NOTIFY_SERVICE_ERROR("C0500", "通知服务出错 二级宏观错误码"),
    SMS_NOTIFY_SERVICE_FAIL("C0501", "短信提醒服务失败"),
    IVR_NOTIFY_SERVICE_FAIL("C0502", "语音提醒服务失败"),
    MAIL_NOTIFY_FAIL("C0503", "邮件提醒服务失败"),
    ;
    private final String code;
    private final String msg;
}
