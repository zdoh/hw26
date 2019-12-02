package ru.zdoher.otus.hw26.service;

import org.springframework.stereotype.Service;
import ru.zdoher.otus.hw26.domain.Bug;

import java.util.Random;

@Service
public class ProgrammerService {

    public Bug findProblem(Bug bug) throws Exception {
        System.out.println("Ищу проблему №" + bug.getName());
        Thread.sleep(2000 + new Random().nextInt(10) * 500);
        System.out.println("Проблему №" + bug.getName() + " нашел");
        return bug;
    }

    public Bug fixProblem(Bug bug) throws Exception {
        System.out.println("Приступил к решению проблемы №" + bug.getName());
        Thread.sleep(2000 + new Random().nextInt(10) * 500);
        System.out.println("Проблему №" + bug.getName() + " решил");
        return bug;
    }

    public Bug testFixedProblem(Bug bug) throws Exception {
        System.out.println("Тестирую решенную проблему №" + bug.getName());
        Thread.sleep(2000 + new Random().nextInt(10) * 500);
        System.out.println("Проблему №" + bug.getName() + " оттестирована и работает хорошо");
        return bug;
    }

    public Bug rest(Bug bug) throws Exception {
        System.out.println("Пью чай, ем тортики после решения №" + bug.getName());
        Thread.sleep(2000 + new Random().nextInt(10) * 500);
        System.out.println("Отдохнул после решения проблемы №" + bug.getName() + " и готов вновь работать");
        return bug;
    }

}
