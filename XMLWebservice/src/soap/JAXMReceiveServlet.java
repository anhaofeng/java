package soap;

import java.io.File;
import java.io.FileOutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.xml.messaging.JAXMServlet;
import javax.xml.messaging.ReqRespListener;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
public class JAXMReceiveServlet extends JAXMServlet implements ReqRespListener {
    static MessageFactory mf = null;

    public void init(ServletConfig sc) throws ServletException {
        super.init(sc);
        // 创建一个消息工厂
        try {
            mf = MessageFactory.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 处理传过来的SOAP消息，并返回一个SOAP消息
    public SOAPMessage onMessage(SOAPMessage msg) {
        // 返回消息
        SOAPMessage resp = null;
        try {
            // 将SOAP请求消息写入指定文件
            FileOutputStream fos =
                    new FileOutputStream(new File("c:\\soapmessage.xml"));
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