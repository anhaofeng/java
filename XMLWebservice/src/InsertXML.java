import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
public class InsertXML {
    public static void main(String args[]) {
        Element students = null;
        Element student = null;
        Element name = null;
        Element address = null;
        Element tel = null;
        Element email = null;
        try {
            // 得到DOM解析器的工厂实例
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 从DOM工厂实例获得DOM解析器
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 把要解析的XML文档读入到DOM解析器中
            Document doc = builder.parse("students.xml");
            // 得到文档中名称为students的元素的结点列表
            NodeList nl = doc.getElementsByTagName("students");
            students = (Element) nl.item(0);
            // 创建名称为student的元素
            student = doc.createElement("student");
            // 设置元素的id属性值为95005
            student.setAttribute("id", "95005");
            // 创建名称为NAME的元素
            name = doc.createElement("name");
            // 创建名称为Tony Blair的文本结点并将其作为子结点添加到name元素中
            name.appendChild(doc.createTextNode("Tony Blair"));
            // 将name元素作为子元素添加到person元素中
            student.appendChild(name);
            address = doc.createElement("address");
            address.appendChild(doc.createTextNode("Yantai"));
            student.appendChild(address);
            tel = doc.createElement("tel");
            tel.appendChild(doc.createTextNode("(0533)6666666"));
            student.appendChild(tel);
            email = doc.createElement("email");
            email.appendChild(doc.createTextNode("blair@163.com"));
            student.appendChild(email);
            // 将student元素作为子元素添加到XML文档树的根结点students中
            students.appendChild(student);
            // 将内存中的文档树通过文件流生成students_new.xml文档
            Transformer transformer = TransformerFactory.newInstance().newTransformer(); // 得到转换器
            // 设置换行
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // 写入文件
            transformer.transform(new DOMSource(doc),new StreamResult(new File("students.xml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
