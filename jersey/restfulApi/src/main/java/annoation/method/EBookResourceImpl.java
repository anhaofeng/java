package annoation.method;


import domain.Book;
import domain.Books;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class EBookResourceImpl implements BookResource {
    private final static Logger LOGGER = LoggerFactory.getLogger(EBookResourceImpl.class);
    public static AtomicLong serverBookSequence = new AtomicLong();

    @Override
    public String getWeight() {
        return "150M";
    }

    @Override
    public String newBook(Book book) {
        SimpleDateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
        Date lastUpdate = Calendar.getInstance().getTime();
        //...
        LOGGER.debug(book.getBookId().toString());
        return f.format(lastUpdate);
    }

    @Override
    public Book createBook(Book book) {
        book.setBookId(serverBookSequence.incrementAndGet());
        return book;
    }

    @Override
    public void delete(long bookId) {
        System.out.println(bookId);
    }

    @Override
    public boolean moveBooks(Books books) {
        LOGGER.debug("MOVE method");
        return true;
    }


}
