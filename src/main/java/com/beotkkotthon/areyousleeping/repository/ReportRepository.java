package com.beotkkotthon.areyousleeping.repository;

import com.beotkkotthon.areyousleeping.domain.Report;
import com.beotkkotthon.areyousleeping.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByReportedUser(User repportedUser);

    boolean existsByReporterAndReportedUser(User reporter, User reportedUser);

    @Query("SELECT r FROM Report r WHERE r.reportedUser = :reportedUser")
    List<Report> findReportsByReportedUser(User reportedUser);

    // 신고를 10번 이상 당한 유저 리스트를 반환
    @Query("SELECT r.reportedUser, COUNT(r) as reportCount FROM Report r GROUP BY r.reportedUser HAVING COUNT(r) >= 10")
    List<Object[]> findUsersWithReportCountGreaterThanEqual();

}
