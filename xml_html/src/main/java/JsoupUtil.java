import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsoupUtil {
    public static void readHtml(String html) {
        Document document1 = Jsoup.parse(html);
        Element body = document1.body();
        Elements trs = body.select("tr");
        trs.remove(1);
        trs.remove(0);
        trs.forEach(tr -> {
            Elements tds = tr.select("td");
            String fieldName = tds.get(0).html();
            String fieldValue = tds.get(1).html();
            switchFun(fieldName, fieldValue);
        });
    }

    public static void main(String[] args) throws Exception {
        MultipartFile file=null;
        file.getInputStream();
        String filePath = "xml_html/20190805.csv";
//        File file = new File(filePath).getAbsoluteFile();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "GBK"));
        if (!reader.ready()) {
            throw new Exception();
        }
        String header = reader.readLine();
        String[] headerArr = header.split(",");
        while (reader.ready()) {
            System.out.println("\n============================================================");
            String line = reader.readLine();
            String[] valueArr = line.split(",");
            if (valueArr.length != headerArr.length) {
                throw new Exception();
            }
            for (int i = 0; i < valueArr.length; i++) {
                switchFun(headerArr[i], valueArr[i]);
            }
        }
    }
    public static String switchFun(String fieldName, String fieldValue) {
        switch (fieldName) {
            case "观测区":
            case "观测日期":
            case "观测时间":
            case "太阳高度角":
            case "太阳方位角":
            case "天气（云量）":
                System.out.println(fieldName + "-----" + fieldValue);
                break;
            case "样地编号":
            case "样方编号":
            case "经度":
            case "纬度":
            case "海拔":
            case "目视盖度%":
            case "黄草比%":
            case "物种数":
            case "草层高度":
            case "Field16":
            case "Field17":
            case "Field18":
            case "Field19":
            case "Field20":
            case "Field21":
            case "Field22":
            case "Field23":
            case "Field24":
            case "Field25":
            case "Field26":
                System.out.println(fieldName + "=======" + fieldValue);
                break;
            case "鲜重(g/0.25m2)":
            case "地上生物量(g/0.25m2)":
            case "地上生物量(g/m2)":
            case "叶面积指数":
            case "Field31":
            case "Field32":
            case "Field33":
            case "叶绿素":
            case "Field35":
            case "Field36":
            case "Field37":
            case "Field38":
            case "Field39":
            case "Field40":
            case "Field41":
                System.out.println(fieldName + "    " + fieldValue);
        }
        return "";
    }
}
