package com.restful.jersey.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "books")
public class Books implements Serializable {
    private static final long serialVersionUID = -5070487415443208853L;
    private List<Book> bookList;

    /**
     * <p>Constructor for Books.</p>
     */
    public Books() {
        super();
    }

    /**
     * <p>Constructor for Books.</p>
     *
     * @param bookList a {@link List} object.
     */
    public Books(final List<Book> bookList) {
        super();
        this.bookList = bookList;
    }

    /**
     * <p>Getter for the field <code>bookList</code>.</p>
     *
     * @return a {@link List} object.
     */
    @XmlElement(name = "book")
    @XmlElementWrapper
    public List<Book> getBookList() {
        return bookList;
    }

    /**
     * <p>Setter for the field <code>bookList</code>.</p>
     *
     * @param bookList a {@link List} object.
     */
    public void setBookList(final List<Book> bookList) {
        this.bookList = bookList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "{" + bookList + "}";
    }
}