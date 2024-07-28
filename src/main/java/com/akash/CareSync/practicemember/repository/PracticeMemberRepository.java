package com.akash.CareSync.practicemember.repository;

import com.akash.CareSync.practicemember.entity.PracticeMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PracticeMemberRepository extends CrudRepository<PracticeMember, Long> {

    Optional<PracticeMember> findByUserName(String userName);

    @Query("SELECT pm FROM PracticeMember pm JOIN pm.roles r WHERE " +
            "(:role IS NULL OR r.name = :role) AND " +
            "(:status IS NULL OR pm.status = :status) AND " +
            "(:search IS NULL OR pm.first_name LIKE %:search% OR pm.last_name LIKE %:search% OR pm.contactDetails.email LIKE %:search%) " +
            "ORDER BY r.id")
    List<PracticeMember> findAllByRoleStatusAndSearch(@Param("role") String role,
                                                      @Param("status") String status,
                                                      @Param("search") String search);
}
