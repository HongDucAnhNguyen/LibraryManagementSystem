package com.LibManagementSystem.LibManagementSystem.service.CirculationManagementService;

public class CirculationService {




    //user borrows book service
    /*
     * issue a book for a user, this book is then marked borrowed in bookStatus
     * entry for a new issue saved to _borrowed_status
     * record issue timestamp
     * record user and bookid
     * timestamp used for when user returns book to determine an overdued or in time return
     * */



    //user returns book
    /*
    * returns the book,
    * entry with the userid and bookid retrieved from repo
    * compare current date with timestamp, if more than 2 weeks had passed, mark as overdued, otherwise in time
    * mark book as available in bookRepo
    * code a feature where entries that are more than 1 month old, are dropped from the database, check every week? is this far fetched?
    * */


}
