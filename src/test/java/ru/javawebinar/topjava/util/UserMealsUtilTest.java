package ru.javawebinar.topjava.util;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserMealsUtilTest {

    private List<UserMeal> mealList;

    @Before
    public void init() {
        mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
    }

    @Test
    public void getFilteredWithExceededTest() {
        List<UserMealWithExceed> expectedUserMealWithExceed = Arrays.asList(
                new UserMealWithExceed(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, true),
                new UserMealWithExceed(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, false)
        );
        assertEquals(expectedUserMealWithExceed, UserMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        assertEquals(expectedUserMealWithExceed, UserMealsUtil.getFilteredWithExceededUsingLoops(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }



}