package example;

import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.utils.StringUtils;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;


public class Client {
    public static void main(String[] args) {
        String url = "http://171.15.17.128:6888/ormrpc/services/EASLogin?wsdl";
        String method = "login";

        Object[] parms = new Object[]{"user","","eas","WX003","L2",0};
//        parms[6]="BaseDB";


        Client webClient = new Client();

        String svrResult = webClient.CallMethod(url, method, parms);

        System.out.println(svrResult);
    }

    public String CallMethod(String url, String method, Object[] args) {
        String result = null;

        if(StringUtils.isEmpty(url))
        {
            return "url地址为空";
        }

        if(StringUtils.isEmpty(method))
        {
            return "method地址为空";
        }

        Call rpcCall = null;
        Call rpcCall2 = null;


        try {
            //实例websevice调用实例
            Service webService = new Service();
            rpcCall = (Call) webService.createCall();
            rpcCall2 = (Call) webService.createCall();
            rpcCall.setTargetEndpointAddress(new java.net.URL(url));
            rpcCall2.setTargetEndpointAddress(new java.net.URL("http://171.15.17.128:6888/ormrpc/services/WSWSqxInterfaceInvokeFacade?wsdl"));
            rpcCall.setOperationName(method);
            rpcCall2.setOperationName("invokePaymentBill");
            //执行webservice方法
            rpcCall.addParameter("userName", org.apache.axis.Constants.XSD_STRING,
                    ParameterMode.IN);
            rpcCall.addParameter("password", org.apache.axis.Constants.XSD_STRING,
                    ParameterMode.IN);
            rpcCall.addParameter("slnName", org.apache.axis.Constants.XSD_STRING,
                    ParameterMode.IN);
            rpcCall.addParameter("dcName", org.apache.axis.Constants.XSD_STRING,
                    ParameterMode.IN);
            rpcCall.addParameter("sessionId", org.apache.axis.Constants.XSD_STRING,
                    ParameterMode.IN);
            rpcCall.addParameter("dbType", Constants.XSD_INT,
                    ParameterMode.IN);

            QName qn= new QName("urn:client","WSContext");
            rpcCall.registerTypeMapping(WSContext.class, qn, new BeanSerializerFactory(

                    WSContext.class, qn), new BeanDeserializerFactory(WSContext.class, qn));
            rpcCall.setReturnType(qn,WSContext.class);
            WSContext wscontext = (WSContext) rpcCall.invoke(args);
            System.out.println(wscontext.getSessionId());
            String invoke = rpcCall2.invoke(new Object[]{"<?xml version='1.0' encoding='utf-8'?>\n" +
                     "<PayBill>\n" +
                    "  <billHead>\n" +
                    "    <CU>101</CU>\n" +
                    "    <creator>user</creator>\n" +
                    "    <createtime>2019-05-05 00:00:00</createtime>\n" +
                    "    <lastupdateuser />\n" +
                    "    <lastupdatetime />\n" +
                    "    <number />\n" +
                    "    <bizdate>2019-05-06 11:16:03</bizdate>\n" +
                    "    <handler />\n" +
                    "    <description />\n" +
                    "    <haseffected>0</haseffected>\n" +
                    "    <auditor />\n" +
                    "    <sourcebillid />\n" +
                    "    <sourcefunction />\n" +
                    "    <company>101</company>\n" +
                    "    <sourcesystype>103</sourcesystype>\n" +
                    "    <sourcetype>103</sourcetype>\n" +
                    "    <currency>BB01</currency>\n" +
                    "    <exchangerate>1</exchangerate>\n" +
                    "    <isexchanged />\n" +
                    "    <lastexhangerate />\n" +
                    "    <settlementtype>汇款</settlementtype>\n" +
                    "    <settlementnumber />\n" +
                    "    <oppaccount />\n" +
                    "    <fpitem />\n" +
                    "    <iscommitsettle />\n" +
                    "    <settlebiztype />\n" +
                    "    <auditdate />\n" +
                    "    <cashier />\n" +
                    "    <accountant />\n" +
                    "    <isinitializebill />\n" +
                    "    <settlementstatus />\n" +
                    "    <fundtype>102</fundtype>\n" +
                    "    <isimport />\n" +
                    "    <amount />\n" +
                    "    <localamt />\n" +
                    "    <adminorgunit />\n" +
                    "    <person />\n" +
                    "    <accessoryamt />\n" +
                    "    <conceit />\n" +
                    "    <summary />\n" +
                    "    <contractno />\n" +
                    "    <dayaccount />\n" +
                    "    <capitalamount />\n" +
                    "    <contractbillid />\n" +
                    "    <feetype />\n" +
                    "    <project />\n" +
                    "    <projectmanager />\n" +
                    "    <paytype>999</paytype>\n" +
                    "    <actrecamt />\n" +
                    "    <actrecamtvc />\n" +
                    "    <actreclocamt />\n" +
                    "    <actreclocamtvc />\n" +
                    "    <payerbank />\n" +
                    "    <payeraccountbank>5003978800018</payeraccountbank>\n" +
                    "    <payeraccount></payeraccount>\n" +
                    "    <payeetype />\n" +
                    "    <payeename />\n" +
                    "    <payeenumber />\n" +
                    "    <bankacctname />\n" +
                    "    <payeebank />\n" +
                    "    <payeeaccountbank />\n" +
                    "    <oppinneracct />\n" +
                    "  </billHead>\n" +
                    "  <billEntries>\n" +
                    "    <entry>\n" +
                    "      <seq>1</seq>\n" +
                    "      <amount>-5000.0000</amount>\n" +
                    "      <amountvc />\n" +
                    "      <localamt>-5000.0000</localamt>\n" +
                    "      <localamtvc />\n" +
                    "      <unvcamount />\n" +
                    "      <unvclocamount />\n" +
                    "      <unverifyexgrateloc />\n" +
                    "      <rebate />\n" +
                    "      <rebateamtvc />\n" +
                    "      <rebatelocamt />\n" +
                    "      <rebatelocamtvc />\n" +
                    "      <actualamt>-5000.0000</actualamt>\n" +
                    "      <actualamtvc />\n" +
                    "      <actuallocamt>-5000.0000</actuallocamt>\n" +
                    "      <actuallocamtvc />\n" +
                    "      <remark />\n" +
                    "      <lockamt />\n" +
                    "      <locklocamt />\n" +
                    "      <unlockamt />\n" +
                    "      <unlocklocamt />\n" +
                    "      <sourcebillid />\n" +
                    "      <sourcebillentryid />\n" +
                    "      <vcstatus />\n" +
                    "      <hisunvcamount />\n" +
                    "      <hisunvclocamount />\n" +
                    "      <corebilltype />\n" +
                    "      <corebillid />\n" +
                    "      <corebillentryid />\n" +
                    "      <corebillnumber />\n" +
                    "      <corebillentryseq />\n" +
                    "      <tracknumbet />\n" +
                    "      <currency />\n" +
                    "      <oppaccount>2241.02</oppaccount>\n" +
                    "      <asstitems>\n" +
                    "        <assttype>客户</assttype>\n" +
                    "        <asstnumber>10100000004</asstnumber>\n" +
                    "        <asstname>test1123456；test2123456；test3123456；test4123456</asstname>\n" +
                    "      </asstitems>\n" +
                    "    </entry>\n" +
                    "  </billEntries>\n" +
                    "</PayBill>"}).toString();
            System.out.println(invoke);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

}
