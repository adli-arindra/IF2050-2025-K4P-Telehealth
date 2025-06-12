package org.drpl.telebe.repository;

import org.drpl.telebe.model.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {

    @Query("SELECT cs FROM ChatSession cs JOIN cs.users u WHERE u.id = :userId")
    List<ChatSession> findByParticipantId(@Param("userId") Long userId);
}