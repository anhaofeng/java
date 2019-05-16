package soap;

import java.io.FileInputStream;
import java.io.IOException;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
//import javax.xml.messaging.URLEndpoint;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
public class SenderWithAtt {
    private String yahooLogo = "yahoo.jpg";
    private String baiduLogo = "baidu.gif";
    private String serviceurl ="http://localhost:8080/soap/StudentInfoServletWithAtt";
    public SOAPMessage getMessage() throws SOAPException, Exception {
        // 消息工厂
        MessageFactory msgFactory = MessageFactory.newInstance();
        SOAPMessage message = msgFactory.createMessage();
        // 获得一个SOAPPart对象
        SOAPPart soapPart = message.getSOAPPart();
        // 从外部文件创建消息
        FileInputStream fis = new FileInputStream("message.msg");
        StreamSource msgSource = new StreamSource(fis);
        // 设置消息内容
        soapPart.setContent(msgSource);
        // 使用DataHandler封装附件
        DataHandler yahooHandler =new DataHandler(new FileDataSource(yahooLogo));
        DataHandler baiduHandler =new DataHandler(new FileDataSource(baiduLogo));
        // 创建附件对象
        AttachmentPart yahooAp = message.createAttachmentPart(yahooHandler);
        AttachmentPart baiduAp = message.createAttachmentPart(baiduHandler);
        yahooAp.setContentId("yahoo_logo");
        baiduAp.setContentId("baidu_logo");
        // 向消息中添加附件对象
        message.addAttachmentPart(yahooAp);
        message.addAttachmentPart(baiduAp);
        message.saveChanges();
        return message;
    }
    public void send(SOAPMessage message) throws SOAPException, IOException {
        // 创建SOAP连接
        SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = scf.createConnection();
        // 使用URLEndpoint构造接收方地址
        URLEndpoint urlEndpoint = new URLEndpoint(serviceurl);
        // 发送SOAP消息到目的地，并返回一个消息
        SOAPMessage response = connection.call(message, urlEndpoint);
        if (response != null) {
            // 输出SOAP消息到控制台
            System.out.println("Receive SOAP message from localhost:");
            response.writeTo(System.out);
        } else {
            System.err.println("No response received from partner!");
        }
        // 关闭连接
        connection.close();
    }
    public static void main(String[] args) throws SOAPException, Exception {
        SenderWithAtt sender = new SenderWithAtt();
        SOAPMessage message = sender.getMessage();
        sender.send(message);
    }
}
