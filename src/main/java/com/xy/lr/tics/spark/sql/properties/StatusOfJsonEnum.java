package com.xy.lr.tics.spark.sql.properties;

/**
 * Created by xylr on 15-5-6.
 */
public enum StatusOfJsonEnum {
    //操作成功
    SUCCESS,

    //参数不合法
    INVALID_ARGUMENTS,

    //内部错误
    INTERNAL_ERROR,

    //已经存在
    ALREADY_EXIST,

    //不存在
    INEXISTENCE
}