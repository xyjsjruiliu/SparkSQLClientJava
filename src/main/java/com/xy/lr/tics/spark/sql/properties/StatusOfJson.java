package com.xy.lr.tics.spark.sql.properties;



import org.json.JSONObject;

import java.util.EnumMap;

/**
 * Created by xylr on 15-5-6.
 */
public class StatusOfJson {

    //
    public static EnumMap<StatusOfJsonEnum,String> statusOfJsonEnumStringEnumMap =
            new EnumMap<StatusOfJsonEnum, String>(StatusOfJsonEnum.class);

    //静态方法
    static {
        statusOfJsonEnumStringEnumMap.put(StatusOfJsonEnum.SUCCESS, "E00");
        statusOfJsonEnumStringEnumMap.put(StatusOfJsonEnum.ALREADY_EXIST, "E03");
        statusOfJsonEnumStringEnumMap.put(StatusOfJsonEnum.INEXISTENCE, "E04");
        statusOfJsonEnumStringEnumMap.put(StatusOfJsonEnum.INVALID_ARGUMENTS, "E01");
        statusOfJsonEnumStringEnumMap.put(StatusOfJsonEnum.INTERNAL_ERROR, "E02");
    }

    //返回内部错误json字符串
    public static String getError(StatusOfJsonEnum args){
        JSONObject json = new JSONObject();
//        System.out.println(s.get(args));
        json.put("status", statusOfJsonEnumStringEnumMap.get(args));
        return json.toString();
    }

    //main function
    public static void main(String[] args){
        System.out.println(StatusOfJson.getError(StatusOfJsonEnum.INVALID_ARGUMENTS));
    }
}