package org.drpl.telebe.repository;

import org.drpl.telebe.model.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {
}
