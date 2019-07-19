import annoation.ContextResource;
import annoation.FormResource;
import domain.Book;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.grizzly.connector.GrizzlyConnectorProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicLong;

public class MethodTest extends JerseyTest {
    static Logger logger= Logger.getLogger(MethodTest.class);
    @Override
    protected Application configure() {
        //关注点3：加载的是实现类而不是接口
//        return new ResourceConfig(EBookResourceImpl.class);
//        return new ResourceConfig(FormResource.class);
//        return new ResourceConfig(BeanParamResource.class);
//        return new ResourceConfig(CookieResource.class);
        return new ResourceConfig(ContextResource.class);

    }

    @Test
    public void testGet() {
        Response response = target("book").request().get();
        Assert.assertEquals("180M", response.readEntity(String.class));
    }


    public static AtomicLong clientBookSequence = new AtomicLong();
    @Test
    public void testNew() {

        final Book newBook = new Book(  clientBookSequence.incrementAndGet(),
                "book-" + System.nanoTime());
        MediaType contentTypeMediaType = MediaType.APPLICATION_XML_TYPE;
        MediaType acceptMediaType = MediaType.TEXT_PLAIN_TYPE;
        final  Entity<Book>  bookEntity  =  Entity.entity(newBook,
                contentTypeMediaType);
        final String lastUpdate = target("book").request(acceptMediaType)
                .put(bookEntity, String.class);
        //关注点3：资源方法定义了Produces注解和Consumes注解
        Assert.assertNotNull(lastUpdate);
        logger.debug("++++"+lastUpdate);
    }
    //*********************************************
    @Test
    public void testDelete() {
        final Response response =target("book").queryParam("bookId", "9527")
                .request().delete();
        int status = response.getStatus();
        logger.debug(status);
        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), status);
    }
    @Test
    public void testCreate() {
        final Book newBook = new Book("book-" + System.nanoTime());
        MediaType contentTypeMediaType = MediaType.APPLICATION_XML_TYPE;
        MediaType acceptMediaType = MediaType.APPLICATION_XML_TYPE;
        final Entity<Book> bookEntity = Entity.entity(newBook, contentTypeMediaType);
        final Book book =target("book").request(acceptMediaType).post(bookEntity,
                Book.class);
        //关注点2：测试POST方法的断言
        Assert.assertNotNull(book.getBookId());
        System.out.println("Server Id="+book.getBookId());
    }


//*********************************************
    @Override
    protected void configureClient(ClientConfig clientConfig) {
        //关注点1：定义Grizzly连接器
        clientConfig.connectorProvider(new GrizzlyConnectorProvider());
        super.configureClient(clientConfig);
    }
    @Test
    public void testWebDav() {
        //关注点2:HTTP MOVE请求
        final Response response = target("book").request().method("MOVE");
        Boolean result = response.readEntity(Boolean.class);
        //关注点3:Move方法测试断言
        Assert.assertEquals(Boolean.TRUE, result);
    }
    //*********************************************
    @Test
    public void testPost2() {
        final Form form = new Form();
        form.param(FormResource.USER, "feuyeux");
        form.param(FormResource.PW, "北京");
        form.param(FormResource.NPW, "上海");
        form.param(FormResource.VNPW, "上海");
        final String result = target("form-resource").request().
                post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
        System.out.println(result);
        Assert.assertEquals("encoded should let it to disable decoding",
                "feuyeux:%E5%8C%97%E4%BA%AC:%E4%B8%8A%E6%B5%B7:上海", result);
    }
    //*********************************************
    @Test
    public void testBeanParam() {
        final WebTarget queryTarget = target("bean-resource").path("China").path("northeast")
                .path("shenyang").path("tiexi")
                .queryParam("station", "Workers Village").queryParam("vehicle", "bus");
        String result = queryTarget.request().get().readEntity(String.class);
        //关注点3：查询结果断言
        System.out.println(result);
        Assert.assertEquals("China/northeast:tiexi:Workers Village:bus", result);
    }
    //*********************************************

    @Test
    public void testContexts() {
        final Invocation.Builder request = target("kuky-resource").request();
        request.cookie("longitude", "123.38");
        request.cookie("latitude", "41.8");
        request.cookie("population", "822.8");
        request.cookie("area", "12948");
        final  String result = request.get().readEntity(String.class);
        Assert.assertEquals("123.38,41.8 population=822.8,area=12948", result);
    }

    @Test
    public void testContext() {
        final Invocation.Builder request = target("ctx-resource").request();
        request.cookie("longitude", "123.38");
        request.cookie("latitude", "41.8");
        request.cookie("population", "822.8");
        request.cookie("area", "12948");
        final  String result = request.get().readEntity(String.class);
        Assert.assertEquals("123.38,41.8 population=822.8,area=12948", result);
    }

}


