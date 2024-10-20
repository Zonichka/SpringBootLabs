package ru.golovina.MyFirstAppSpringBoot.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController
{
    // ArrayList для хранения значений
    private final List<String> arrayList = new ArrayList<>();

    // HashMap для хранения значений
    private final Map<Integer, String> hashMap = new HashMap<>();
    private int mapKey = 1; // Счетчик ключей для HashMap

    // Конструктор, добавляющий начальные значения
    public HelloController() {
        // Добавляем 11 элементов в ArrayList
        for (int i = 1; i <= 9; i++) {
            arrayList.add("Array Item " + i);
        }

        // Добавляем 7 элементов в HashMap
        for (int i = 1; i <= 5; i++) {
            hashMap.put(mapKey++, "Map Item " + i);
        }
    }

    // Метод для приветствия
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello, %s!", name);
    }

    // Метод для добавления строки в ArrayList
    @GetMapping("/update-array")
    public String updateArrayList(@RequestParam(value = "value") String value) {
        arrayList.add(value);
        return "Added: " + value;
    }

    // Метод для отображения всех элементов ArrayList
    @GetMapping("/show-array")
    public List<String> showArrayList() {
        return arrayList;
    }

    // Метод для добавления строки в HashMap
    @GetMapping("/update-map")
    public String updateHashMap(@RequestParam(value = "value") String value) {
        hashMap.put(mapKey++, value);
        return "Added to map: " + value;
    }

    // Метод для отображения всех элементов HashMap
    @GetMapping("/show-map")
    public Map<Integer, String> showHashMap() {
        return hashMap;
    }

    // Метод для отображения количества элементов в ArrayList и HashMap
    @GetMapping("/show-all-length")
    public String showAllLength() {
        return "ArrayList size: " + arrayList.size() + ", HashMap size: " + hashMap.size();
    }
}

