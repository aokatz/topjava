package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS_USER1.forEach(m -> this.save(m,1));
        MealsUtil.MEALS_USER2.forEach(m -> this.save(m,2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals;
        if (repository.containsKey(userId)) {
            meals = repository.get(userId);
        } else {
            meals = new ConcurrentHashMap<>();
        }

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            repository.put(userId, meals);
            return meal;
        }
        // treat case: update, but absent in storage
        Meal updatedMeal;
        updatedMeal = meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        repository.put(userId, meals);
        return updatedMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.containsKey(userId)) {
            return repository.get(userId).remove(id) != null;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.containsKey(userId)) {
            return repository.get(userId).get(id);
        } else {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        if (repository.containsKey(userId)) {
            return repository.get(userId).values()
                    .stream()
                    .sorted(Comparator.comparing(Meal::getDate).reversed())
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        if (repository.containsKey(userId)) {
            return repository.get(userId).values()
                    .stream()
                    .filter(m -> MealsUtil.isBetween(m.getDate(), startDate, endDate))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
}

