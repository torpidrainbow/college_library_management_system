package com.librarymanagement.collegelibrarymanagementsystem.model.type;

import java.util.Optional;

public enum User_Type {
    LIBRARIAN(Constants.LIBRARIAN_VALUE),
    STAFF(Constants.STAFF_VALUE),
    STUDENT(Constants.STUDENT_VALUE);

    User_Type(String type) {

    }

    public static class Constants {

        public static final String LIBRARIAN_VALUE = "LIBRARIAN";
        public static final String STUDENT_VALUE = "STUDENT";
        public static final String STAFF_VALUE = "STAFF";

    }
}
