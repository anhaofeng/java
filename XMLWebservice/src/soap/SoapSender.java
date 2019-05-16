package soap;

import javax.xml.soap.*;
import java.io.IOException;

public class SoapSender {
    // 接收方（服务器）地址
    private String serviceurl = "http://localhost:8080/soap/StudentInfoServlet";
    // 消息构建
    public SOAPMessage getMessage() throws SOAPException, Exception {
        // 消息工厂
        MessageFactory msgFactory = MessageFactory.newInstance();
        SOAPMessage message = msgFactory.createMessage();
        // 获得一个SOAPPart对象
        SOAPPart soapPart = message.getSOAPPart();
        // 获得信封
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
        // 获得消息头
        SOAPHeader soapHeader = soapEnvelope.getHeader();
        // 获得SOAP主体
        SOAPBody soapBody = soapEnvelope.getBody();
        // 创建名称
        Name headerName = soapEnvelope.createName("studentInfo", "stu","http://localhost:8080/soap");
        // 创建并添加新的报头元素
        SOAPHeaderElement headerElement = soapHeader.addHeaderElement(headerName);
        // 设置mustUnderstand属性
        headerElement.setMustUnderstand(true);
        // 添加结点值
        headerElement.addTextNode("ZKCW9901");
        // 添加消息主体
        Name bodyName = soapEnvelope.createName("getStudentInfo", "stu","http://localhost:8080/soap");
        SOAPBodyElement bodyElement = soapBody.addBodyElement(bodyName);
        // 创建并添加主体元素
        Name eleName = soapEnvelope.createName("StudentName");
        SOAPElement se = bodyElement.addChildElement(eleName);
        se.addTextNode("Zhang Fei");
        // 更新SOAP消息
        message.saveChanges();
        return message;
    }
    // 消息发送
    public void send(SOAPMessage message) throws SOAPException, IOException {
        // 创建SOAP连接
        SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = scf.createConnection();
        // 使用URLEndpoint构造接收方地址
        URLEndpoint urlEndpoint = new URLEndpoint(serviceurl);
        // 发送SOAP消息到目的地，并返回一个消息
        SOAPMessage response = connection.call(message, urlEndpoint);
        if (response != null) {
            System.out.println("Receive SOAP message from localhost:");
            response.writeTo(System.out);
        } else {
            System.out.println("No response received from partner!");
        }
        // 关闭连接
        connection.close();
    }
    public static void main(String[] args) throws SOAPException, Exception {
        SoapSender sender = new SoapSender();
        SOAPMessage message = sender.getMessage();
        sender.send(message);
    }

}
