import annoation.method.EBookResourceImpl;
import domain.Book;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicLong;

public class MethodTest extends JerseyTest {
    static Logger logger= Logger.getLogger(MethodTest.class);
    @Override
    protected Application configure() {
        //关注点3：加载的是实现类而不是接口
        return new ResourceConfig(EBookResourceImpl.class);
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
        logger.debug("Server Id="+book.getBookId());
    }


}
