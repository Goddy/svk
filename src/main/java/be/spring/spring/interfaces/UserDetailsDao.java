package be.spring.spring.interfaces;

/**
 * User: Tom De Dobbeleer
 * Date: 12/13/13
 * Time: 3:25 PM
 * Remarks: none
 */
public interface UserDetailsDao {
    String findPasswordByUsername(String email);
}
