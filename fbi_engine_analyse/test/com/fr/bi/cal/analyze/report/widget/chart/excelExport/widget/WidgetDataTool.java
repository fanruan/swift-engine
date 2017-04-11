//package excelExport.widget;
//
//import com.fr.json.JSONException;
//import com.fr.json.JSONObject;
//
///**
// * Created by Kary on 2017/3/7.
// * 生成widget的data数据
// */
//public class WidgetDataTool {
//    /*普通汇总表，单维度*/
//    public static JSONObject getNormalSummaryDataWithSingleDim() throws JSONException {
//        JSONObject dataJSON = new JSONObject("{\"s\":[95],\"c\":[{\"s\":[5],\"c\":[{\"s\":[1],\"x\":1,\"n\":\"安徽古井集团公司 \"},{\"s\":[1],\"x\":1,\"n\":\"安徽金仕达集团公司 \"},{\"s\":[1],\"x\":1,\"n\":\"安徽精方药业股份有限公司 \"},{\"s\":[1],\"x\":1,\"n\":\"马鞍山川洋制药有限公司 \"},{\"s\":[1],\"x\":1,\"n\":\"芜湖三益制药有限公司 \"}],\"x\":6,\"n\":\"安徽\"},{\"s\":[5],\"x\":1,\"n\":\"北京 \"},{\"s\":[4],\"x\":1,\"n\":\"福建 \"},{\"s\":[7],\"x\":1,\"n\":\"河北\"},{\"s\":[1],\"x\":1,\"n\":\"河南\"},{\"s\":[4],\"x\":1,\"n\":\"河南 \"},{\"s\":[8],\"x\":1,\"n\":\"黑龙江 \"},{\"s\":[1],\"x\":1,\"n\":\"湖北 \"},{\"s\":[7],\"x\":1,\"n\":\"吉林\"},{\"s\":[13],\"x\":1,\"n\":\"江苏\"},{\"s\":[4],\"x\":1,\"n\":\"江西\"},{\"s\":[2],\"x\":1,\"n\":\"辽宁\"},{\"s\":[1],\"x\":1,\"n\":\"内蒙\"},{\"s\":[1],\"x\":1,\"n\":\"青海\"},{\"s\":[7],\"x\":1,\"n\":\"山东\"},{\"s\":[4],\"x\":1,\"n\":\"山西\"}],\"x\":22}");
//        return dataJSON;
//    }
//
//    /*普通汇总表，多维度*/
//    public static JSONObject getNormalSummaryDataWithMultiDims() throws JSONException {
//        JSONObject dataJSON = new JSONObject("{\"s\":[95],\"c\":[{\"s\":[5],\"c\":[{\"s\":[1],\"x\":1,\"n\":\"安徽古井集团公司 \"},{\"s\":[1],\"x\":1,\"n\":\"安徽金仕达集团公司 \"},{\"s\":[1],\"x\":1,\"n\":\"安徽精方药业股份有限公司 \"},{\"s\":[1],\"x\":1,\"n\":\"马鞍山川洋制药有限公司 \"},{\"s\":[1],\"x\":1,\"n\":\"芜湖三益制药有限公司 \"}],\"x\":6,\"n\":\"安徽\"},{\"s\":[5],\"x\":1,\"n\":\"北京 \"},{\"s\":[4],\"x\":1,\"n\":\"福建 \"},{\"s\":[7],\"x\":1,\"n\":\"河北\"},{\"s\":[1],\"x\":1,\"n\":\"河南\"},{\"s\":[4],\"x\":1,\"n\":\"河南 \"},{\"s\":[8],\"x\":1,\"n\":\"黑龙江 \"},{\"s\":[1],\"x\":1,\"n\":\"湖北 \"},{\"s\":[7],\"x\":1,\"n\":\"吉林\"},{\"s\":[13],\"x\":1,\"n\":\"江苏\"},{\"s\":[4],\"x\":1,\"n\":\"江西\"},{\"s\":[2],\"x\":1,\"n\":\"辽宁\"},{\"s\":[1],\"x\":1,\"n\":\"内蒙\"},{\"s\":[1],\"x\":1,\"n\":\"青海\"},{\"s\":[7],\"x\":1,\"n\":\"山东\"},{\"s\":[4],\"x\":1,\"n\":\"山西\"}],\"x\":22}");
//        return dataJSON;
//    }
//
//    /*正常交叉表*/
//    public static JSONObject getNormalCrossData() throws JSONException {
//        JSONObject dataJSON = new JSONObject("{\"t\":{\"c\":[{\"c\":[{\"n\":\"a032435fe0c7e8d4\"}],\"n\":\"阿坝县\"},{\"c\":[{\"n\":\"a032435fe0c7e8d4\"}],\"n\":\"阿城市\"},{\"c\":[{\"n\":\"a032435fe0c7e8d4\"}],\"n\":\"阿尔山市\"},{\"c\":[{\"n\":\"a032435fe0c7e8d4\"}],\"n\":\"阿合奇县\"},{\"c\":[{\"n\":\"a032435fe0c7e8d4\"}],\"n\":\"阿克塞哈萨克族自治县\"},{\"c\":[{\"n\":\"a032435fe0c7e8d4\"}],\"n\":\"阿克苏市\"},{\"c\":[{\"n\":\"a032435fe0c7e8d4\"}],\"n\":\"阿克陶县\"}]},\"l\":{\"s\":{\"s\":[2305],\"c\":[{\"s\":[1]},{\"s\":[1]},{\"s\":[1]},{\"s\":[1]},{\"s\":[1]},{\"s\":[1]},{\"s\":[1]}]},\"c\":[{\"s\":{\"s\":[3],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"北京市\"},{\"s\":{\"s\":[3],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"上海市\"},{\"s\":{\"s\":[4],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"天津市\"},{\"s\":{\"s\":[19],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"海南省\"},{\"s\":{\"s\":[20],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"宁夏回族自治区\"},{\"s\":{\"s\":[28],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"重庆市\"},{\"s\":{\"s\":[37],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[1]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"内蒙古自治区\"},{\"s\":{\"s\":[40],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"青海省\"},{\"s\":{\"s\":[49],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"吉林省\"},{\"s\":{\"s\":[57],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"辽宁省\"},{\"s\":{\"s\":[69],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"福建省\"},{\"s\":{\"s\":[74],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"浙江省\"},{\"s\":{\"s\":[76],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"江苏省\"},{\"s\":{\"s\":[78],\"c\":[{\"s\":[0]},{\"s\":[1]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"黑龙江省\"},{\"s\":{\"s\":[78],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"西藏自治区\"},{\"s\":{\"s\":[79],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"湖北省\"},{\"s\":{\"s\":[80],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"贵州省\"},{\"s\":{\"s\":[81],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[1]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"甘肃省\"},{\"s\":{\"s\":[83],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[0]}]},\"n\":\"安徽省\"},{\"s\":{\"s\":[88],\"c\":[{\"s\":[0]},{\"s\":[0]},{\"s\":[0]},{\"s\":[1]},{\"s\":[0]},{\"s\":[1]},{\"s\":[1]}]},\"n\":\"新疆维吾尔自治区\"}]}}");
//        return dataJSON;
//    }
//
//
//}
