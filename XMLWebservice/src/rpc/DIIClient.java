package rpc;

/**
 * 创建动态调用接口客户端
 *
 * @author haiersoft
 *
 */
public class DIIClient {
    public static void main(String[] args) throws Exception {
        /**
         * 创建一个Service实例
         */
        // 服务名称
        String serviceName = "OrderService";
        // 定义服务工厂
        ServiceFactory factory = ServiceFactory.newInstance();
        // 获取服务对象
        Service service = factory.createService(new QName(serviceName));
        /**
         * 创建一个Call实例
         */
        //获取Call对象
        Call call = service.createCall();
        //命名空间
        String targetNameSpace = "http://order.org/wsdl/OrderService";
        //设置操作名称
        QName operationName = new QName(targetNameSpace,"getOrderPrice");
        call.setOperationName(operationName);
        //设置输入参数
        call.addParameter(
                "String_1",//参数名称 ,注意：wsdl中发布的参数名称为String_1
                XMLType.XSD_STRING,//参数在XML中的类型
                String.class,//参数在Java中类型
                ParameterMode.IN//输入参数
        );
        //设置返回类型
        call.setReturnType(XMLType.XSD_STRING);
        //设置RPC风格的操作属性
        call.setProperty(Call.OPERATION_STYLE_PROPERTY,"rpc");
        //soap编码格式
        String encoding = "http://schemas.xmlsoap.org/soap/encoding/";
        //设置属性
        call.setProperty(Call.ENCODINGSTYLE_URI_PROPERTY, encoding);
        //终端
        String endpoint = "http://localhost:8080/ch10/order";
        call.setTargetEndpointAddress(endpoint);
        /**
         * 调用Web服务端的方法
         */
        String[] params = { "20100503" };
        //返回结果
        String result = (String) call.invoke(params);
        //打印结果
        System.out.println(result);
    }
}
