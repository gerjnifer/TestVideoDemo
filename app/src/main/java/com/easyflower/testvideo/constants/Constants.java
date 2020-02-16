package com.easyflower.testvideo.constants;

import android.os.Environment;

/**
 * 项目名：FindFlowers
 * 时间：2017/4/17 10:42
 * 作用：常量类
 */

public class Constants {

    //新浪资源
    public static final String APP_KEY = "282807010";
    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * <p>
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
//    public static final String REDIRECT_URL = "http://www.sina.com";

    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * <p>
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * <p>
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * <p>
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE =
            "statuses/share";


    // 多种跳转不要这么写 很麻烦
    public static final String PUSH_JUMP = "push_jump";
    // 推送点击后 返回启动 mainactivity  订单列表
    public static final int FROM_MY_RECEIVER_ORDERLIST = 2041;
    // 推送点击后 返回启动 mainactivity  订单列表
    public static final int FROM_MY_RECEIVER_ORDERLIST_2 = 2042;
    // 推送点击后 返回启动 mainactivity  优惠券
    public static final int FROM_MY_RECEIVER_COUPON = 2043;
    // 微信资源
    public static final String WEIXIN_APP_ID = "wx0f33c5ea9b52bbe1";

    public static final String FROM = "from";

    public static final int Shop_list_TO_Shop_detail = 5001;

    //qq分享 appid
    public static String QQSHARE_APPID = "1104769196";


    // 启动页时间
    public static final int ROOT_TIME_SEC = 3;

    //首页切换fragment
    public static final int TAG_HOME = 1;
    public static final int TAG_SHOP = 2;
    public static final int TAG_GOODCART = 3;
    public static final int TAG_MINE = 4;


    public static final int HOME_SHOP_LIST = 2;
    public static final int HOME_CART_ = 4;
    public static final int HOME_MINE = 5;
    public static final int HOME_SHOP_DETAIL = 6;

    public static final String FLORIST_ID = "toMine_florist_id";


    public static final int MINE_FROUCS = 1;
    public static final int COMMON_BUY_LIST = 0;

    /**
     * 是否需要在有缓存的情况下 重新加载新的数据
     */
    public static final boolean REPLACE_LOAD_DATA_ = false;

    /**
     * 缓存时间
     */
    public static final int CACHE_MIN = 7; // 分钟

    /**
     * 钱包支付成功
     */
    public static final int emoneyPay_SUCCESS = 100;
    /**
     * 历史记录保存数量
     */
    public static final int SEARCH_HISTORY_COUNT = 1000;


    public static final String PRODUCT_DETAIL_IMAGES = "Show_big_pic";

    /**
     * 进入商品详情SEL_SPU_ID
     */
    public static final String SEL_SPU_ID = "select_spu_id";

    /**
     * 进入我的店铺   从个人中心我
     */
    public static final int MINE = 2;

    /**
     * 进入我的店铺    从登陆完成后 没有花店信息  如果手机号码登陆用户 点击绑定微信 在绑定微信页面 BindLoginActivyt 中 判断用的 也是该字段
     */
    public static final int LOGIN_STATE_NOT_SHOP = 901;


    /**
     * 上传身份证照片
     */
    public static final int ID_PIC_UPLOAD = 2001;

    /**
     * 上传营业执照照片
     */
    public static final int LIC_PIC_UPLOAD = 2002;


    public static final int TAKE_IMAGE_CODE = 1100;
    public static final int PHOTO_RESULT = 1101;
    public static final int CHOOLE_IMAGE = 1102;
    public static final int PHOTO_LIC_RESULT = 1103; // 执照的裁剪返回


    public static final int CREATE_LOCATION_REQUESIONCODE = 1104; // 定位请求码
    public static final int CREATE_ID_REQUESIONCODE = 1106; // 身份请求码
    public static final int CREATE_FREIGHT_RESULTCODE = 1105; // 运费模版
    public static final int REQUEST_CODE_SCAN = 1108; // 运费模版
    public static final int REQUEST_CODE_TYPE = 1109; // 分类
    public static final int REQUEST_CODE_PIC = 1110; // 分类
    /*
     * 图片保存路径
     */
    public static final String FILE_PATH_BASE = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/easyflower/pic";

    public static final String FILE_PATH_ABSOLUTE_BASE = Environment.getDataDirectory().getAbsolutePath() + "/easyflower/pic";

    public static final String FILE_PATH_TEMP = FILE_PATH_BASE;
    public static final String FILE_PATH_TEMP_CROP = FILE_PATH_BASE
            + "/tempcrop";
    public static String photoFilePath;


    public static void setPathFilePhoto(String photoFilePath2) {
        photoFilePath = photoFilePath2;
    }

    public static String getPathFilePhoto() {
        return photoFilePath;
    }


    // 带有返回结果的 resultcode
    public static final int FROM_MINA_HOME_RESULT = 1002;
    // home 切换位置 返回码
    public static final int FROM_HOME_SWITCH_RESULT = 1003;


    /**
     * 启动MainActivity 不需要定位
     */
    public static final int NOMAL_PAGE_NOT_LOACTION = 10;

    // 商品列表 跳转商品详情
    public static final String SEL_CATEGROY_ID = "Sel_Categroy_id";
    public static final String SEL_COLOR_ID = "Sel_Color_id";
    //    public static final String SEL_FLOWER = "Sel_Flower_id";
    public static final String FLOWER_NAME = "flowe_name";


    /**
     * 地址选择的来源   值
     */
    public static final int FROM_HOME = 0;  // 首页
    public static final int FROM_LOGIN = 1; // 登陆
    public static final int FROM_MINE = 2; // 个人中心
    public static final int FROM_MYSHOP = 3; // 商店
    public static final int FROM_HOME_PAGER = 4; // HOMEFragment 选择地址

    /**
     * 选择地址在intent中的标记  跟结果码
     */
    public static final String POSITION_SEELCT_FROM = "pos_select_from";
    public static final int FROM_POS = 1001;

    /**
     * 进入相应的个人中心资料修改功能
     */
    public static final int PERSION_INTO_SHOPTABLE = 2001;
    public static final int PERSION_INTO_REALCHECK = 2002;

    /**
     * 修改的花店信息
     */
    public static final String MODIFICAITON_FLORIST_INFO_TYPE = "infoType";//类型

    public static final String FLORIST_NAME = "floristName";
    public static final String FLORIST_MANAGE_NAME = "floristManageName";


    // 核对订单选择优惠券后 要保持数据
    public static final int INTO_COUPON_PAGE = 2001;

    // 订单详情 去评价
    public static final int Comment_SYSTEM = 1234;
    //订单详情 去售后
    public static final int After_SERVICE = 2234;

    // 取消订单后返回订单详情
    public static final int ORDER_LIST_TO_DETAIL_CANCEL = 1234;

    // 前往支付 从订单列表 或者订单详情
    public static final int ORDER_LIST = 1;
    // 前往支付 从核对订单
    public static final int ORDER_CHECK = 2;


    // 前往支付 返回
    public static final int TOPAY_BY_ORDER_LIST_DETAIL = 2001;

    //个人中心跳转到 订单列表的指定位置
    public static final String NEED_PAY = "need_PAy";
    public static final String NEED_TAKE_STUFF = "need_reciver";
    public static final String NEED_COMMENT = "need_comment";
    public static final String NEED_AFTER = "need_after_service";
    public static final String NEED_FINISH = "need_finish";
    public static final String CANCELED = "canceled";
    public static final String All = "all";



    // 通用刷新 加载状态
    public static final int REFLASH_NOMAL = 0;
    public static final int REFLASH_LOADING = 1;
    public static final int REFLASH_REFLASH = 2;

    // 关注进入商品详情 然后取消关注
    public static final int COMMON_INTO_PRODUCTDETAIL = 123;

    // 订单详情去支付
    public static final int ORDER_DETAIL_TO_PAY = 3001;

    //个人中心进入修改店铺信息 收货地址 如果成功返回后需要清空购物车
    public static final int TO_MYSHOP_FOR_MINE = 2020;

    // 数据获取状态
    public static final int Net_Connent_Fail = 1;
    public static final int Net_Not_Data = 2;

    // 核对订单 切换 配送方式
    public static final int CHECK_ORDER_SWAP_DELIVERY = 5001;

    public static final String MatcherPhone = "[1][36578]\\d{9}";

    // 从会员跳转到不同的页面做任务
    public static final int FROM_MEMBER = 176;

    // 欢迎页面
    public static final int WELCOME = 190;


    //购物车单个商品增加数量 上限
    public static final int GoodCart_Buy_Limit = 999;


    /**
     * 直发
     */
    // 购物车模式
    public static final String DIRECT_GOODCART = "DIRECT_GOODCART";

    /**
     * 直发购物车核对也 商品 增加上线
     */
    public static final int Direct_Goodcart_Limit_Count = 999;


    /**
     * 商品列表的类型的筛选中 多出用的都是 这个 字符串 类型判断  原因是因为 后台数据中  商品没有该属性 无法用统一标示来确定 所以暂时用 字符串  如有变动 改这里
     */
    public static final String SHOP_FILTER_CATEGROY = "商品类型";
    public static final String SHOP_FILTER_SEND_DATE = "起送时间";


    /**
     * 订单列表支付尾款状态 跳转页面前的校验后 跳转分支
     */
    //进入核对订单
    public static final int ORDER_LIST_FAIL_TO_CHECK = 0;
    //进入收银台
    public static final int ORDER_LIST_FAIL_TO_CASHIER = 1;

    // 个人中心挑入 设置后 返回处理结果
    public static final int MAIN_TO_SETTING = 9101;

    //微信支付成功后是否跳转活动页面
    public static  boolean isJumpActivity = false;

    //商品列表鲜花等级说明开关
    public static  boolean isClose = false;

    //商品列表鲜花等级说明开关
    public static  int productSecondPos = -1;



    //当前网络状态
    public static int NET_TYPE_OFF = 0;
    public static int NET_TYPE_WIFI = 1;
    public static int NET_TYPE_MOBILE = 2;

}
