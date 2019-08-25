import entities.Event;
import entities.Location;
import entities.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainRelation {

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("HibernateDBConnection");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Event> eventList = entityManager.createQuery("FROM Event", Event.class).getResultList();
        eventList.forEach(System.out::println);

        Location location = entityManager.find(Location.class, 2);
        location = entityManager
                .createQuery("FROM Location loc JOIN FETCH loc.events WHERE loc.id = 2", Location.class)
                .getSingleResult();
//        "SELECT * FROM events WHERE locationId = 2"
        TypedQuery<Event> query =
                entityManager.createQuery("FROM Event WHERE location = :location", Event.class);
        query.setParameter("location", location);
        eventList = query.getResultList();
        eventList.forEach(System.out::println);

        location.getEvents().forEach(System.out::println);

//        addTicketToDataBase(entityManager, eventList);

        List<Ticket> tickets = entityManager.createQuery("FROM Ticket", Ticket.class).getResultList();
        tickets.forEach(t -> System.out.println(t));

        entityManagerFactory.close();
    }

    private static void addTicketToDataBase(EntityManager entityManager, List<Event> eventList) {
        Scanner sc = new Scanner(System.in);
        Ticket ticket = new Ticket();
        System.out.println("Price:");
        ticket.setPrice(BigDecimal.valueOf(sc.nextDouble()));
        System.out.println("Select event from list");
        eventList.forEach(System.out::println);
        int idOfEvent = sc.nextInt();
        Event event = entityManager.find(Event.class, idOfEvent);
        ticket.setEvent(event);
        System.out.println("Number of available tickets:");
        ticket.setNumber(sc.nextInt());
        entityManager.getTransaction().begin();
        entityManager.persist(ticket);
        entityManager.getTransaction().commit();
    }

}
