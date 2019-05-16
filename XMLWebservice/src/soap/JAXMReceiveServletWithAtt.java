package soap;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import javax.activation.DataHandler;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.xml.messaging.JAXMServlet;
import javax.xml.messaging.ReqRespListener;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
public class JAXMReceiveServletWithAtt extends JAXMServlet implements ReqRespListener {
    static MessageFactory mf = null;
    private String dir = "c:\\";
    private HashMap<String, String> map;
    // 初始化MIME信息与文件扩展名的对照
    public void initMIMEInfo() {
        map = new HashMap<String, String>();
        map.put("image/jpeg", ".jpg");
        map.put("image/gif", ".gif");
    }
    public void init(ServletConfig sc) throws ServletException {
        super.init(sc);
        // 初始化MIME信息
        initMIMEInfo();
        // 创建一个消息工厂
        try {
            mf = MessageFactory.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 处理传过来的SOAP消息，并返回一个SOAP消息
    public SOAPMessage onMessage(SOAPMessage msg) {
        SOAPMessage resp = null;
        try {
            FileOutputStream fos = null;
            // 循环处理附件
            Iterator attIterator = msg.getAttachments();
            while (attIterator.hasNext()) {
                AttachmentPart attPart = (AttachmentPart) attIterator.next();
                // 获取附件ID
                String contentId = attPart.getContentId();
                // 获取附件类型
                String contentType = attPart.getContentType();
                System.out.println("Content ID :" + contentId);
                System.out.println("Content Type :" + contentType);
                DataHandler handler = attPart.getDataHandler();
                // 根据附件ID、附件类型构建要写入的文件名
                String filename = dir + contentId + map.get(contentType);
                // 将附件内容写入文件
                fos = new FileOutputStream(new File(filename));
                handler.writeTo(fos);
                fos.close();
            }
            // 写入文件前，移除所有附件
            msg.removeAllAttachments();
            // 将SOAP请求消息写入指定文件
            fos = new FileOutputStream(new File(dir + "soapmessage.xml"));
            msg.writeTo(fos);
            fos.close();
            // 此处省略返回消息处理的业务逻辑，而用硬编码形式创建一个返回消息
            resp = mf.createMessage();
            SOAPEnvelope se = resp.getSOAPPart().getEnvelope();
            se.getBody().addChildElement(se.createName("ResponseMessage")).addTextNode("Received Message,Thanks");
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }
}
