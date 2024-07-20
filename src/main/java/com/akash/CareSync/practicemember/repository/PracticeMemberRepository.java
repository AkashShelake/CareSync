package com.akash.CareSync.practicemember.repository;

import com.akash.CareSync.practicemember.entity.PracticeMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PracticeMemberRepository extends CrudRepository<PracticeMember, Long> {

    Optional<PracticeMember> findByUserName(String userName);
}
