import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
public class JAXPDemo {
    public static void main(String args[]) {
        try {
            // 得到DOM解析器的工厂实例
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 从DOM工厂实例获得DOM解析器
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 把要解析的XML文档读入到DOM解析器中
            Document doc = builder.parse("student1.xml");
            // 得到文档中名称为PERSON的元素的结点列表
            NodeList nl = doc.getElementsByTagName("student");
            // 遍历该列表，显示列表中元素及其子元素的名字
            for (int i = 0; i < nl.getLength(); i++) {
                Element node = (Element) nl.item(i);
                String value = null;
                // 获取元素的id属性
                System.out.println("id:" + node.getAttribute("id"));
                // 获取名称为name的元素列表中第一个元素的第一个子元素的值
                value = node.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
                System.out.println("name: " + value);
                value = node.getElementsByTagName("age").item(0).getFirstChild().getNodeValue();
                System.out.println("age: " + value);
                value = node.getElementsByTagName("address").item(0).getFirstChild().getNodeValue();
                System.out.println("address: " + value);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

            }
