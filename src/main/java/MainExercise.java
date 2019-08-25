import entities.Event;
import entities.Location;
import entities.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainExercise {
    private static EntityManager entityManager;

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("HibernateDBConnection");
        entityManager = entityManagerFactory.createEntityManager();

        testAddEvent();



        entityManagerFactory.close();
    }

    private static void testAddEvent() {
        Map<BigDecimal,Integer> tickets = new HashMap<>();
        tickets.put(BigDecimal.valueOf(100.00),100);
        tickets.put(BigDecimal.valueOf(50.00),200);
        tickets.put(BigDecimal.valueOf(25.55),500);

        //addEvent("Krzysztof Cugowski",LocalDateTime.of(2019,12,12,11,11,11),
        //      LocalDateTime.of(2019,12,15,23,23,23),1,tickets);

        List<Ticket> from_ticket = entityManager.
                createQuery("FROM Ticket", Ticket.class).getResultList();

//        from_ticket.forEach(t -> System.out.println(t));


        entityManager.createQuery("FROM Event", Event.class)
                .getResultList().forEach(e-> System.out.println(e));
    }

    private static void addEvent (String name,
                                  LocalDateTime startDate,
                                  LocalDateTime endDate,
                                  int locationId,
                                  Map<BigDecimal, Integer> availableTickets){
        entityManager.getTransaction().begin();

        Event event = new Event();
        event.setName(name);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setLocation(entityManager.find(Location.class,locationId));

        entityManager.persist(event);

        for (Map.Entry<BigDecimal, Integer> tickets : availableTickets.entrySet()) {
            Ticket ticket = new Ticket();
            ticket.setEvent(event);
            ticket.setPrice(tickets.getKey());
            ticket.setNumber(tickets.getValue());

            entityManager.persist(ticket);
        }

        entityManager.getTransaction().commit();
    }

}
