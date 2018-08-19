package com.file.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2018-08-19 10:12
 */
public class FileTest {

    public static void main(String[] args) {
        long start_time = new Date().getTime();
        System.out.println("开始时间"+start_time);
        String fileName = "D://files";
        File file = new File(fileName);
        String content = getContent(file);
        System.out.println("<======读取文件内容========>");
        System.out.println(content);

        JSONObject jsonObject = getJSONObject(content);
        System.out.println("<======转化JsonObject========>");
        System.out.println(jsonObject.getString("hardInfo"));
        System.out.println(jsonObject.getJSONArray("data"));

        final JSONArray data = jsonObject.getJSONArray("data");
        CopyOnWriteArrayList list = dataList(data, "whs", new CopyOnWriteArrayList());

        System.out.println("《=========================》");
        long end_time = new Date().getTime();
        System.out.println("结束时间"+end_time);

        System.out.println("耗时" + (end_time - start_time));

        System.out.println(list.size());




    }

    /**
     * 获取文件内容
     * @param file
     * @return
     */
    public static String getContent(File file){
        String fileContent = null;
        try {
            fileContent = FileUtils.readFileToString(file, "GBK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    /**
     * 转换为 JSONObject
     * @param fileContent
     * @return
     */
    public static JSONObject getJSONObject(String fileContent){
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isBlank(fileContent)) return jsonObject;
        try {
            jsonObject = JSONObject.parseObject(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     *  处理数据
     * @param dataArray
     * @param device
     * @param list
     */
    public static  CopyOnWriteArrayList dataList(JSONArray dataArray,String  device ,CopyOnWriteArrayList list){
        long time = new Date().getTime();
        for (Object datum : dataArray) {
            JSONObject data_obj = (JSONObject)datum;
            data_obj.put("device",device);
            data_obj.put("time",time);
            list.add(data_obj);
        }
        return list;
    }

}
