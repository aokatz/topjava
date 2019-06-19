package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.*;
import java.util.stream.Collectors;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
//        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
//            System.out.println(appCtx.getBean(MealRestController.class).getAll());
//
//
//        }

        class User {
            int id;
            String Name;

            public User(int id, String name) {
                this.id = id;
                Name = name;
            }

            public String getName() {
                return Name;
            }

            @Override
            public String toString() {
                return "User{" +
                        "id=" + id +
                        ", Name='" + Name + '\'' +
                        '}';
            }

        }

        Map<Integer, User> test = new HashMap<>();
        test.put(1, new User(4, "А"));
        test.put(2, new User(3, "А"));
        test.put(3, new User(2, "А"));
        test.put(4, new User(1,"А"));



        System.out.println(new ArrayList<>(test.values()).stream().sorted(Comparator.comparing(User::getName)).collect(Collectors.toList()));
        System.out.println(test.values().stream().sorted(Comparator.comparing(User::getName)).collect(Collectors.toList()));

    }
}
