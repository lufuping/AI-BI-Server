package com.yupi.springbootinit.utils;

import java.util.Random;

/**
 * @author xiaolu
 */
public class NameUtils {
    private static String[] surnames = {
            "赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈",
            "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许",
            "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏",
            "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章",
            "云", "苏", "潘", "葛", "奚", "范", "彭", "郎", "鲁", "韦",
            "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳",
            "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺",
            "倪", "汤", "滕", "殷", "罗", "毕", "郝", "邬", "安", "常",
            "乐", "于", "时", "付", "皮", "卞", "齐", "康", "伍", "余",
            "元", "卜", "顾", "孟", "平", "黄", "和", "穆", "萧", "尹"
    };
    public String getNameString(){
        int minChars = 2;
        int maxChars = 3;
        Random random = new Random();

        int randomLength = random.nextInt(maxChars - minChars + 1) + minChars;
        StringBuilder name = new StringBuilder();

        // 生成姓氏
        name.append(surnames[random.nextInt(surnames.length)]);

        // 生成名字
        for (int i = 1; i < randomLength; i++) {
            // 生成一个随机的 Unicode 中文字符的编码
            int charCode = random.nextInt(0x9FFF - 0x4E00 + 1) + 0x4E00;

            // 将 Unicode 编码转换为实际字符并拼接到名字字符串中
            name.append((char) charCode);
        }

        return name.toString();
    }
}
