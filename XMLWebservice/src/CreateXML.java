import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import java.io.*;

public class CreateXML {
    public static void main(String args[]) {
        Document doc;
        // 声明XML文档中的各个元素对象
        Element students, student;
        Element name = null;
        Element address = null;
        Element tel = null;
        Element email = null;
        try {
            // 得到DOM解析器的工厂实例
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // 从DOM工厂实例获得DOM解析器
            DocumentBuilder builder = dbf.newDocumentBuilder();
            // 创建文档树模型对象
            doc = builder.newDocument();
            // 如果创建的文档树模型不为空
            if (doc != null) {
                // 创建students元素
                students = doc.createElement("students");
                // 创建student元素
                student = doc.createElement("student");
                // 设置student元素的属性id的值为95003
                student.setAttribute("id", "95003");
                // 将student元素添加为students的子元素
                students.appendChild(student);
                // 创建name元素
                name = doc.createElement("name");
                // 将一个文本结点添加为name元素的子结点
                name.appendChild(doc.createTextNode("Mr.Zhang"));
                // 将name元素添加为student的子元素
                student.appendChild(name);
                address = doc.createElement("address");
                address.appendChild(doc.createTextNode("Shandong Qingdao"));
                student.appendChild(address);
                tel = doc.createElement("tel");
                tel.appendChild(doc.createTextNode("(0532)88888888"));
                student.appendChild(tel);
                email = doc.createElement("email");
                email.appendChild(doc.createTextNode("zhangfei@163.com"));
                student.appendChild(email);
                // 将students元素作为根元素添加到XML文档树中
                doc.appendChild(students);
                // 将内存中的文档树保存为students.xml文档
                Transformer transformer = TransformerFactory.newInstance().newTransformer(); // 得到转换器
                // 设置换行
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                // 写入文件
                transformer.transform(new DOMSource(doc),new StreamResult(new File("d:/student.xml")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
