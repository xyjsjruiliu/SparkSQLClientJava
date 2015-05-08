package com.xy.lr.tics.spark.sql;

import com.xy.lr.tics.properties.StatusOfJson;
import com.xy.lr.tics.properties.StatusOfJsonEnum;
import com.xy.lr.tics.properties.TICSInfoJava;
import org.zeromq.ZMQ;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xylr on 15-5-1.
 * SparkSQLClientJava
 */
public class SparkSQLClientJava {

    //zeroMQ
    private final static ZMQ.Context sparkSQLClientContext = ZMQ.context(1);

    //设置模式 (应答模式)
    private final static ZMQ.Socket sparkSQLClientSocket = sparkSQLClientContext.socket(ZMQ.REQ);

    //构造函数
    public SparkSQLClientJava(String path){
        //配置文件
        TICSInfoJava ticsInfo = new TICSInfoJava(path);
        //连接SparkSQLServer
        sparkSQLClientSocket.connect("tcp://" + ticsInfo.getSparkSQLServerUrl() +
                ":" + ticsInfo.getSparkSQLServerPort());
    }

    //获取当前时间
    private String getCurrentTime(){
        try {
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.format(date);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    //生成查询请求
    private byte[] makeRequest(String req){
        try{
            String requestString = req + " ";
            byte[] request = requestString.getBytes();
            request[request.length - 1] = 0;

            return request;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //解析查询结果
    private String getReply(byte[] rep){
        try {
            return new String(rep, 0, rep.length - 1);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    //
    private String test(String req){
        try{
            String currentTime = getCurrentTime();
            if(currentTime.equals("error")){
                return "error";
            }
            System.out.println("Connecting to [ SparkSQLServer ] at " + currentTime);

            //生成查询
            byte[] request = makeRequest(req);
            if(request == null){
                return "error";
            }

            System.out.println("Sending request : " + req);
            //发送查询请求
            sparkSQLClientSocket.send(request, 0);

            //接受查询结果
            byte[] reply = sparkSQLClientSocket.recv(0);
            String rep = getReply(reply);
            if(rep.equals("error")){
                return "error";
            }

            System.out.println("Received reply " + ": [" + rep + "]");

            //返回结果
            return rep;
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    //从黑名单中查询车辆轨迹
    public String sqlGrapgByBlackList(String carNumber){
        try{
            String tmp = test("GBL" + carNumber);
            if(tmp.equals("error")){
                //内部错误
                return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
            }
            return tmp;
        }catch (Exception e){
            //内部错误
            return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
        }
    }

    //获取黑名单列表
    public String querySuspectedCar(){
        try{
            String tmp = test("BL");
            if(tmp.equals("error")){
                //内部错误
                return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
            }
            return tmp;
        }catch (Exception e){
            e.printStackTrace();
            //内部错误
            return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
        }
    }

    //使用sql查询车辆信息表
    public String sql(String query){
        try{
            String tmp = test("sql" + query);
            if(tmp.equals("error")){
                //内部错误
                return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
            }
            return tmp;
        }catch (Exception e){
            e.printStackTrace();
            //内部错误
            return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
        }

    }

    //通过车牌号查询车辆轨迹
    public String queryCarTrace(String... args){
        try{
            String carNo, startTime, endTime;
            if(args.length == 3){
                carNo = args[0];
                startTime = args[1];
                endTime = args[2];

                String tmp = test(carNo + "\t" + startTime + "\t" + endTime);
                if(tmp.equals("error")){
                    //内部错误
                    return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
                }
                return tmp;
            }
            else if(args.length == 2){
                carNo = args[0];
                startTime = args[1];
                endTime = getCurrentTime();

                String tmp = test(carNo + "\t" + startTime + "\t" + endTime);
                if(tmp.equals("error")){
                    //内部错误
                    return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
                }
                return tmp;
            }
            else{
                //参数格式不对
                return StatusOfJson.getError(StatusOfJsonEnum.INVALID_ARGUMENTS);
            }
        }catch (Exception e){
            e.printStackTrace();
            //内部错误
            return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
        }
    }

    //添加嫌疑车辆
    public String AddSuspectedCar(String carNo){
        try{
            String tmp = test("ADD" + carNo);
            if(tmp.equals("error")){
                //内部错误
                return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
            }
            return tmp;
        }catch (Exception e){
            e.printStackTrace();
            //内部错误
            return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
        }

    }

    //删除嫌疑车辆
    public String DelSuspectedCar(String carNo){
        try{
            String tmp = test("DEL" + carNo);
            if(tmp.equals("error")){
                //内部错误
                return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
            }
            return tmp;
        }catch (Exception e){
            e.printStackTrace();
            //内部错误
            return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
        }
    }

    //查询嫌疑车辆报警日志
    public String querySuspectedCarAlarmLog(String cNo, String sTime, String eTime, String pNo){
        try{
            String carNo, startTime, endTime, placeNo;
            //车牌号
            if(cNo.equals("")){
                carNo = "carNo";
            }else{
                carNo = cNo;
            }
            //开始时间
            if(sTime.equals("")){
                startTime = "startTime";
            }else {
                startTime = sTime;
            }
            //结束时间
            if(eTime.equals("")){
                endTime = "endTime";
            }else{
                endTime = eTime;
            }
            //卡口
            if(pNo.equals("")){
                placeNo = "placeNo";
            }else{
                placeNo = pNo;
            }

            String tmp = test(carNo + ":" + startTime + ":" + endTime + ":" + placeNo);
            if(tmp.equals("error")){
                return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
            }

            return tmp;
            /*if(true){
                return StatusOfJson.getError(StatusOfJsonEnum.INEXISTENCE);
            }else{
                //参数格式不对
                return StatusOfJson.getError(StatusOfJsonEnum.INVALID_ARGUMENTS);
            }*/
        }catch (Exception e){
            e.printStackTrace();
            //内部错误
            return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
        }
    }

    //查询疑似套牌车报警日志
    public String queryFakeLicensedCarAlarmLog(String cNo, String sTime, String eTime, String pNo){
        try{
            String carNo, startTime, endTime, placeNo;
            //车牌号
            if(cNo.equals("")){
                carNo = "carNo";
            }else{
                carNo = cNo;
            }
            //开始时间
            if(sTime.equals("")){
                startTime = "startTime";
            }else {
                startTime = sTime;
            }
            //结束时间
            if(eTime.equals("")){
                endTime = "endTime";
            }else{
                endTime = eTime;
            }
            //卡口
            if(pNo.equals("")){
                placeNo = "placeNo";
            }else{
                placeNo = pNo;
            }

            String tmp = test(carNo + ";" + startTime + ";" + endTime + ";" + placeNo);
            if(tmp.equals("error")){
                return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
            }

            return tmp;
        }catch (Exception e){
            e.printStackTrace();
            //内部错误
            return StatusOfJson.getError(StatusOfJsonEnum.INTERNAL_ERROR);
        }
    }
}