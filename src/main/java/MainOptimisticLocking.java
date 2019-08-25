import entities.Location;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainOptimisticLocking {

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("HibernateDBConnection");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
//        Location location = entityManager.find(Location.class, 1, LockModeType.PESSIMISTIC_WRITE);
        Location location = entityManager.find(Location.class, 1);
        System.out.println(location);
        System.out.println("Enter new name");
        location.setName(scanner.nextLine());
        System.out.println("Ok");

        scanner.nextLine();

        entityManager.merge(location);
        entityManager.getTransaction().commit();
        System.out.println("Saved!");

        entityManagerFactory.close();
    }

}
