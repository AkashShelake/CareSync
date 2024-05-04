package com.akash.CareSync.practicemember.repository;

import com.akash.CareSync.practicemember.entity.PracticeMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeMemberRepository extends CrudRepository<PracticeMember, Long> {
}
