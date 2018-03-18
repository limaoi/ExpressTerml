package com.femtoapp.expressterml.api;

/**
 * Created by Autism on 2018/1/31.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class api {
    public static final String SMS_CODE = "http://hl.honghehello.com/general/hello/api/sms_code.php?telephone=";           //获取验证码
    public static final String SMS_CODE_CHECK = "http://hl.honghehello.com/general/hello/api/sms_code_check.php?telephone=";       //验证验证码
    public static final String LANSHOU = "http://hl.honghehello.com/general/hello/api/lanshou.php";        //揽件任务
    public static final String LANSHOU_SUBMIT = "http://hl.honghehello.com/general/hello/api/lanshou_submit.php";        //确认揽件订单
    public static final String SEND = "http://hl.honghehello.com/general/hello/api/send.php";        //派件
    public static final String SIGN = "http://hl.honghehello.com/general/hello/api/sign.php";        //签收
    public static final String PORT = "http://hl.honghehello.com/general/hello/api/user_address_port.php";        //坐标上传
    public static final String UPLOAD = "http://hl.honghehello.com/general/hello/api/upload.php";        //图片上传
    public static final String LOGIN1 = "http://hl.honghehello.com/general/hello/api/login1.php";        //快递员登录
    public static final String PSWChange1 = "http://hl.honghehello.com/general/hello/api/password_change1.php";        //快递员忘記密碼
    public static final String PSWChange = "http://hl.honghehello.com/general/hello/api/password_change.php";        //快递员修改密码
    public static final String ACCOUNT = "http://hl.honghehello.com/general/hello/api/account.php";        //余额查询
    public static final String TXAPPLY = "http://hl.honghehello.com/general/hello/api/tx_apply.php";        //提现申请入口
    public static final String TXSUBMIT = "http://hl.honghehello.com/general/hello/api/tx_submit.php";        //确认提现
    public static final String TXQUERY = "http://hl.honghehello.com/general/hello/api/tx_query.php";        //提现记录
    public static final String SRQUERY = "http://hl.honghehello.com/general/hello/api/sr_query.php";        //收入明细
    public static final String INTERVAL = "http://hl.honghehello.com/general/hello/api/time_interval.php ";        //时间间隔
    public static final String CHECK_VERSION = "http://hl.honghehello.com/general/hello/api/check_version.php";      //版本判断
}
