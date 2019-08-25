package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
@Getter
@Setter
@Entity
@Table(name = "transactionTicket")
public class TransactionTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "transactionID")
    private Transaction transaction;
    @ManyToOne
    @JoinColumn(name = "ticketID")
    private Ticket ticket;

    private Integer number;
}
