package com.beotkkotthon.areyousleeping.repository;

import com.beotkkotthon.areyousleeping.domain.Report;
import com.beotkkotthon.areyousleeping.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByReportedUser(User repportedUser);

    boolean existsByReporterAndReportedUser(User reporter, User reportedUser);

    @Query("SELECT r FROM Report r WHERE r.reportedUser = :reportedUser")
    List<Report> findReportsByReportedUser(User reportedUser);


}
