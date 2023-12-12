package com.smartmanager.dao;
import com.smartmanager.entities.UserLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface UserLogRepository extends JpaRepository<UserLog, Long> {

    UserLog findByUsernameAndLogoutTimeIsNull(String username);
    List<UserLog> findAllByLogoutTimeIsNull();
    Page<UserLog> findAllByLogoutTimeIsNotNull(Pageable pageable);
    Page<UserLog> findByUsernameContainingIgnoreCaseAndLogoutTimeIsNotNull(String keyword,Pageable pageable);

//    void deleteAllAndLogoutTimeIsNotNull();
//    void deleteAllAndLogoutTimeIsNotNull();
//    List<UserLog> findAllAndLogoutTimeIsNull();
}
