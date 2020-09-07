import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

public class XML_HTML {
    public static void main(String[] args) throws DocumentException {
        String filePath = "xml_html/doc.kml";
        File file = new File(filePath).getAbsoluteFile();
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(file);
        Element folder = document.getRootElement().element("Document").element("Folder");
//        for (Object o : folder.elements()) {
//            Element placeMark = (Element) o;
//            if ("Placemark".equals(placeMark.getName())) {
//                System.out.println("\n============================================================");
//                Element description = placeMark.element("description");
//                String html = (String) description.getData();
//                JsoupUtil.readHtml(html);
//            }
//        }

        String html = (String) ((Element) folder.elements().get(4)).element("description").getData();
        JsoupUtil.readHtml(html);
    }

}
