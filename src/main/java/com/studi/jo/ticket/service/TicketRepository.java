package com.studi.jo.ticket.service;

import com.studi.jo.ticket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Modifying
    @Query("UPDATE Ticket t SET t.validityStatus = 'EXPIRED' WHERE t.validityStatus = 'VALID'")
    void updateTicketValidityStatusToExpired();
}

