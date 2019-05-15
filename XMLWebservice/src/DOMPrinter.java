import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DOMPrinter {
/**
 * 采用递归调用，输出指定结点下的所有后代结点。 注：为了简单起见，
 * 只对处理文档结点、指令结点、元素结点、属性结点和文本结点进行了处理。
 **/
public static void printNode(Node node) {
    short nodeType = node.getNodeType();
    switch (nodeType) {
        // 处理指令结点
        case Node.PROCESSING_INSTRUCTION_NODE:
            System.out.println("指令结点:<" + node.getNodeName() + " "+ node.getNodeValue()+">");
            break;
        // 处理元素结点
        case Node.ELEMENT_NODE:
            System.out.print("元素结点:<" + node.getNodeName());
            // 处理属性结点
            NamedNodeMap attrs = node.getAttributes();
            int attrNum = attrs.getLength();
            for (int i = 0; i < attrNum; i++) {
                Node attr = attrs.item(i);
                System.out.print( " "+attr.getNodeName() + "="+ attr.getNodeValue());
            }
            System.out.println(">");
            break;
        // 处理文本结点
        case Node.TEXT_NODE:
            if (node.getNodeValue()!=null&& node.getNodeValue().trim().length()!=0) {
                System.out.println("文本结点:"+node.getNodeValue());
            }
            break;
        // 处理文档结点
        case Node.DOCUMENT_NODE:
            System.out.println("文档结点");
            break;
        default:
            System.out.println("未知结点");
            break;
    }
    // 递归调用，遍历复合的node
    // for(Node child = node.getFirstChild();child != null;
    // child=child.getNextSibling()){
    // printNode(child);
    // }
    Node child = node.getFirstChild();
    while (child != null) {
        printNode(child);
        child = child.getNextSibling();
    }
    // 打印元素结束符
    if(node.getNodeType() == Node.ELEMENT_NODE ){
        System.out.println("结点结束:"+"</"+node.getNodeName()+">");
    }
}
    public static void main(String[] args) {
        try {
            // 得到DOM解析器的工厂实例
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // 从DOM工厂实例获得DOM解析器
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("student1.xml"));
            // 调用递归方法
            printNode(doc);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


 }
