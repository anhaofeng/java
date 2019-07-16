import domain.Book;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import annoation.method.EBookResourceImpl;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicLong;

public class MethodTest extends JerseyTest {
    static Logger logger= LoggerFactory.getLogger(MethodTest.class);
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
        logger.debug(lastUpdate);
    }


}
