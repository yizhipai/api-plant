package com.klb.portal.Contants;

/**
 * Author: 董杨
 * Date: 2020/11/27 16:05
 * Content:
 */
public class Contants {
    //植物状态 0：正常  1：删除
    public static final int PLANT_IS_DEL = 1;
    public static final int PLANT_IS_NORMAL = 0;


    //商品规格状态状态  1：上架/0：下架   2:删除  3:待审 4:审核拒绝
    public static final int GOODS_SPECS_UP = 1;
    public static final int GOODS_SPECS_DOWN = 0;
    public static final int GOODS_SPECS_DELETE = 2;
    public static final int GOODS_SPECS_VERIFY_WAIT = 3;
    public static final int GOODS_SPECS_VERIFY_REFUSE = 4;
    //商品类别 FIX:一口价商品,AUCTION:拍卖商品,POINTS：积分商品
    public static final String GOODS_TYPE_FIX = "FIX";
    public static final String GOODS_TYPE_AUCTION = "AUCTION";
    public static final String GOODS_TYPE_POINTS = "POINTS";
    // 数据状态
    public static final int STATUS_ABLE = 1; // 生效
    public static final int STATUS_DISABLE = 0; // 失效
    // 操作结果
    public static final Long SUCCESS = 0L; // 操作成功
    // 商品邮寄类型
    public static final int MAIL_FREE = 0; // 包邮
    public static final int MAIL_SF = 1; // 顺丰到付
    public static final int MAIL_FREIGHT = 2; // 有偿邮寄
    // 拍品状态: 1上架,21:流拍,22:拍卖成功
    public static final int AUCTION_UP = 1; // 上架
    public static final int AUCTION_FAIL = 21; // 流拍
    public static final int AUCTION_SUCCESS = 22; // 拍卖成功
    //商品查询拍品状态: 11:即将拍卖,12:拍卖中,21:流拍,22:拍卖成功
    public static final int GOODS_AUCTION_WAIT = 11;
    public static final int GOODS_AUCTION_DOING = 12;
    public static final int GOODS_AUCTION_FAIL = 21;
    public static final int GOODS_AUCTION_SUCCESS = 22;
    // 平台信息
    public static final String EC_PLATFORM_USER_NAME = "平台自营"; // 电商平台商家名称
    //拍品排序: 1:综合排序,2:最近上拍,3:即将结拍
    public static final int SORT_ALL = 1;
    public static final int SORT_NEW = 2;
    public static final int SORT_OLD = 3;
    //保证金
    public static final String BUY_EARNEST = "buyEarnest";
    public static final String SELL_EARNEST = "sellEarnest";
    //错误编码
    public static final Long ERROR_EARNEST = -3L;//保证金不足

}
