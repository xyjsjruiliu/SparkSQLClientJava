package com.xy.lr.tics.example;

import com.xy.lr.tics.spark.sql.SparkSQLClientJava;

/**
 * Created by xylr on 15-5-1.
 */
public class sqlClient {
    public static void main(String[] args){

        // 参数是配置文件的路径
        SparkSQLClientJava sqlClient = new SparkSQLClientJava("TICSInfo.properties");

        // 通过车牌号查询轨迹
        System.out.println(sqlClient.queryCarTrace("10001", "" , ""));
        System.out.println(sqlClient.queryCarTrace("10001", ""));

        // 查询黑名单列表
        System.out.println(sqlClient.querySuspectedCar());

        // 查询黑名单车辆轨迹
        System.out.println(sqlClient.sqlGrapgByBlackList("10004"));

        //添加黑名单车辆
        System.out.println(sqlClient.AddSuspectedCar("10000"));

        //删除黑名单车辆
        System.out.println(sqlClient.DelSuspectedCar("10000"));

        //查询嫌疑车辆报警日志
        System.out.println(sqlClient.querySuspectedCarAlarmLog("","","",""));

        //查询疑似套牌车报警日志
        System.out.println(sqlClient.queryFakeLicensedCarAlarmLog("","","",""));

        //使用sql查询车辆信息表 还没有完成 执行sql查询
        System.out.println(sqlClient.sql(""));
    }
}
