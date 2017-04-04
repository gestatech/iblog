package com.alexkbit.iblog.services.impl;

import com.alexkbit.iblog.model.Book;
import com.alexkbit.iblog.model.ModelPage;
import com.alexkbit.iblog.repositories.api.BookRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * Test for {@link BookServiceImpl}
 */
@RunWith(EasyMockRunner.class)
public class BookServiceImplTest extends EasyMockSupport {

    @Mock
    private BookRepository bookRepository;

    @TestSubject
    private BookServiceImpl bookService = new BookServiceImpl();

    @Test
    public void testSave() {
        String id = UUID.randomUUID().toString();
        Book book = new Book();
        book.setId(id);
        expect(bookRepository.save(anyObject(Book.class))).andReturn(book);
        replayAll();
        assertEquals(id, bookService.save(new Book()).getId());
        verifyAll();
    }

    @Test
    public void testSaveNull() {
        replayAll();
        assertNull(bookService.save(null));
        verifyAll();
    }

    @Test
    public void testGetPage() {
        expect(bookRepository.findAll(eq(0), eq(10))).andReturn(new ModelPage<>());
        replayAll();
        assertNotNull(bookService.get(0,10));
        verifyAll();
    }

}