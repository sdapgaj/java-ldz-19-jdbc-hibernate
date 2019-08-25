import entities.Location;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainHibernate {

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("HibernateDBConnection");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        Location newLocation = new Location();

        System.out.println("Please fill in the city");
        newLocation.setCity(scanner.nextLine());
        System.out.println("Whats the address?");
        newLocation.setAddress(scanner.nextLine());
        System.out.println("What is the name of the location?");
        newLocation.setName(scanner.nextLine());

        entityManager.getTransaction().begin();
        entityManager.persist(newLocation);
        entityManager.getTransaction().commit();

        showLocationList(entityManager);

        Location location = entityManager.find(Location.class, 6);
        System.out.println(location);

        entityManager.getTransaction().begin();
//        entityManager.remove(location);
        System.out.println("Enter new name for location");
        location.setName(scanner.nextLine());
        entityManager.merge(location);
        entityManager.getTransaction().commit();

        showLocationList(entityManager);

        entityManagerFactory.close();
    }

    private static void showLocationList(EntityManager entityManager) {
        List<Location> locations =
                entityManager.createQuery("SELECT loc FROM Location loc", Location.class).getResultList();

        locations.forEach(location -> {
            System.out.println(location.toString());
        });
    }

}
