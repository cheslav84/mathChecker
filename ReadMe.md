## MathChecker
### Завдання
Тестове завдання розраховане на визначення загального рівня володіння засобами
мови Java. Припустимо часткове виконання задання.
Розробити застосування, яке допомагатиме вчителю математики.
Застосування повинне надавати такі можливості:
1. Вводити математичні рівняння, що містять числа (цілі, або десяткові дроби), а
   також математичні операції +, -, \*, / та круглі дужки, рівень вкладеності дужок –
   довільний. У всіх рівняннях невідома величина позначається англійською літерою
   x.
2. Перевіряти введене рівняння на коректність розміщення дужок
3. Перевіряти коректність введеного виразу (не повинно бути 2 знаків
   математичних операцій поспіль, наприклад, неприпустимий вираз 3+\*4, в той
   же час, вираз 4\*\-7 є допустимим).
   Приклади коректних рівнянь:
   2\*x+5=17, -1.3\*5/x=1.2, 2\*x=10, 2\*x+5+х+5=10, 17=2\*x+5
4. Якщо рівняння є коректним, зберегти його у БД.
5. Надати можливість ввести корені рівняння, під час введення перевіряти, чи є
   задане число коренем, і якщо так – зберігати його в БД.
6. Реалізувати функції пошуку рівнянь у БД за їхніми коренями. Наприклад,
   можливий запит: знайти всі рівняння, що мають один із зазначених коренів або
   знайти всі рівняння, які мають рівно один корінь.
7. Проект має бути реалізований з використанням системи збирання Maven в
   одному із середовищ розробки: IntelliJ IDEA або Eclipse.
8. Проект має бути завантажений у репозиторій GitHub та надано посилання для
   його отримання. Також допустимо надіслати архів із проектом.


    Примітки.
    У роботі використовувати Java 11 чи 17.
    Рекомендується використання таких СУБД: MySQL, MariaDB чи PostgreSQL.
    Вітається використання JUnit та інших засобів тестування.
    Дозволяється використовувати файли (замість БД) для зберігання інформації про введені та редаговані рівняння.
    Число вважаємо коренем рівняння, якщо при підстановці цього числа замість всіх входжень x, різниця між значеннями 
    лівої та правої частин рівняння не перевищує 10-9
    
*Обов'язковими для виконання є пп.1-3 та 8. Якщо п.4-7 не виконуються, виконати
    додаткове завдання:*
- *Порахувати кількість чисел у рівнянні, введеному користувачем.*


### Запуск програми
Як СУБД в рішенні використано MySQL.

Перед запуском проекту слід:
1. Як Deployment ***directory*** зазначити ***MathChecker/src/main/webapp***. Context path "/".
2. Проект виконано в *Java 17*, відповідно, для запуску необхний *Tomcat 10*.
3. Створити в MySQL схему під назвою equations;
4. У файлі ***flyway.conf*** замінити "*user*" та "*password*" на власні. Файл flyway.conf розміщений в корені проекту.
5. У файлі ***context.xml*** замінити "*username*" та "*password*" на власні.
    Файл знаходиться: ***MathChecker\src\main\webapp\META-INF***
6. Запустити процедуру міграції дадих в БД: ***Maven - mathchecker\Plugings\flyway\flyway:migrate***
7. Для запису логів у файл замінити директорію ***D:/Courses/TestWorks/*** на власну у файлі ***log4j2.properties*** 
   (файл знаходиться: ***MathChecker\src\main\resources***)

Примітки:
1. Приклади рівнянь:

        2\*x+5=17, корінь: 6.
        -1.3\*5/x=1.2, корінь: -5.416666667.
        2\*x=10, корінь: 5.
        2\*x+5+х+5=10, корінь: 0.
        17=2\*x+5, корінь: 6.
        (x^2-24)^0.5=1, корені: 5 та -5.


2. В проекті вирористано примітивну Dependency Injection. 
3. Для всіх нетривіальних рішень додається опис.