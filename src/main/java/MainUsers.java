import entities.User;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.text.html.parser.Entity;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Scanner;

public class MainUsers {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("HibernateDBConnection");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        User firstUser = createUser();

        saveToDB(entityManager, firstUser);

        showUsers(entityManager);



    }

    private static void saveToDB(EntityManager entityManager, User firstUser) {
        entityManager.getTransaction().begin();
        entityManager.persist(firstUser);
        entityManager.getTransaction().commit();
    }

    private static User createUser() {
        User firstUser = new User();
        Scanner scanner = new Scanner(System.in);

        System.out.println("login: ");
        firstUser.setLogin(scanner.nextLine());
        System.out.println("name: ");
        firstUser.setName(scanner.nextLine());
        System.out.println("surname: ");
        firstUser.setSurname(scanner.nextLine());
        System.out.println("password: ");
        firstUser.setPassword(scanner.nextLine());
        System.out.println("email: ");
        firstUser.setEmail(scanner.nextLine());
        return firstUser;
    }

    private static void showUsers (EntityManager entityManager){
        List<User> userList = entityManager
                .createQuery("from User", User.class)
                .getResultList();
        userList.forEach(user-> System.out.println(user));
    }
}
