package com.librarymanagement.collegelibrarymanagementsystem.ServiceTest;


import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Book;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;


public class TestObjectFactory {

    public static class UserFactory {
        public static User getUser(String username) {
                return new User("name",username,"password", User_Type.STUDENT);
        }
    }

    public static class BookFactory {
        public static Book getBook(String title, String author, String publisher, Book_category category) {
            return new Book(title,author,publisher,category);
        }
    }

}

