package com.librarymanagement.collegelibrarymanagementsystem.RepositoryTest;


import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Book;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Record;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;


public class TestObjectFactory {

    public static class UserFactory {
        public static User getUser(String name, String username, String password, User_Type type)  {
                return new User(name,username,password,type);
        }
    }

    public static class BookFactory {
        public static Book getBook(String title, String author, String publisher, Book_category category) {
            return new Book(title,author,publisher,category);
        }
    }

}

