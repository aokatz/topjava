package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumOfCalories = mealList.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .map(m -> new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), caloriesPerDay < sumOfCalories.get(m.getDate())))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededUsingLoops(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumOfCalories = new HashMap<>();
        mealList.forEach(
                meal -> sumOfCalories.put(meal.getDate(), sumOfCalories.getOrDefault(meal.getDate(), 0) + meal.getCalories())
        );

        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        mealList.forEach(
                meal -> {
                    if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                        userMealWithExceeds.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesPerDay < sumOfCalories.get(meal.getDate())));
                    }
                }
        );
        return userMealWithExceeds;
    }

}
