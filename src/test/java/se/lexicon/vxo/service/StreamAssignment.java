package se.lexicon.vxo.service;

import org.junit.jupiter.api.Test;
import se.lexicon.vxo.model.Gender;
import se.lexicon.vxo.model.Person;
import se.lexicon.vxo.model.PersonDto;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import javax.swing.plaf.IconUIResource;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Your task is not make all tests pass (except task1 because its non testable).
 * You have to solve each task by using a java.util.Stream or any of it's variance.
 * You also need to use lambda expressions as implementation to functional interfaces.
 * (No Anonymous Inner Classes or Class implementation of functional interfaces)
 */
public class StreamAssignment {

    private static List<Person> people = People.INSTANCE.getPeople();

    /**
     * Turn integers into a stream then use forEach as a terminal operation to print out the numbers
     */
    @Test
    public void task1() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        integers.stream().forEach(System.out::println);

    }

    /**
     * Turning people into a Stream count all members
     */
    @Test
    public void task2() {
        long amount = 0;
        amount = people.stream()
                .count();
        assertEquals(10000, amount);
    }

    /**
     * Count all people that has Andersson as lastName.
     */
    @Test
    public void task3() {
        long amount = 0;
        int expected = 90;
        amount = people.stream().filter(person -> person.getLastName().equals("Andersson")).toArray().length;
        assertEquals(expected, amount);
    }

    /**
     * Extract a list of all female
     */
    @Test
    public void task4() {
        int expectedSize = 4988;

        List<Person> females = null;
        int fcount = 0;

        females = people.stream().filter(person -> person.getGender().equals(Gender.FEMALE)).collect(Collectors.toList());
        // females LIST of all females
        assertNotNull(females);

        assertEquals(expectedSize, females.size());
    }

    /**
     * Extract a TreeSet with all birthDates
     */
    @Test
    public void task5() {
        int expectedSize = 8882;
        Set<LocalDate> dates = null;

        dates = people.stream()
                .collect(() -> new TreeSet(),                       //(Supplier<TreeSet>) TreeSet::new, STYLE 2
                        (set, p) -> set.add(p.getDateOfBirth()),
                        ((treeSet, treeSet2) -> treeSet.addAll(treeSet)));

        assertNotNull(dates);
        assertTrue(dates instanceof TreeSet);
        assertEquals(expectedSize, dates.size());
    }

    /**
     * Extract an array of all people named "Erik"
     */
    @Test
    public void task6() {
        int expectedLength = 3;

        Person[] result = null;

        result = people.stream().filter(person -> person.getFirstName().equals("Erik")).toArray(Person[]::new);

        assertNotNull(result);

        assertEquals(expectedLength, result.length);
    }

    /**
     * Find a person that has id of 5436
     */
    @Test
    public void task7() {
        Person expected = new Person(5436, "Tea", "Håkansson", LocalDate.parse("1968-01-25"), Gender.FEMALE);

        Optional<Person> optional = null;

        optional = people.stream().filter(person -> person.getPersonId() == 5436).findFirst();

        assertNotNull(optional);
        assertTrue(optional.isPresent());
        assertEquals(expected, optional.get());
    }

    /**
     * Using min() define a comparator that extracts the oldest person i the list as an Optional
     */

    @Test
    public void task8() {
        LocalDate expectedBirthDate = LocalDate.parse("1910-01-02");

        Optional<Person> optional = null;
        LocalDate current;
        LocalDate today = LocalDate.now();

        optional = people.stream().min(Comparator.comparing(Person::getDateOfBirth));


        assertNotNull(optional);
        assertEquals(expectedBirthDate, optional.get().getDateOfBirth());
    }

    /**
     * Map each person born before 1920-01-01 into a PersonDto object then extract to a List
     */

    @Test
    public void task9() {
        int expectedSize = 892;
        LocalDate date = LocalDate.parse("1920-01-01");
        LocalDate today = LocalDate.now();

        List<PersonDto> dtoList = null;

        dtoList = people.stream().filter(person -> person.getDateOfBirth().isBefore(date))
                .map(person -> new PersonDto(person.getPersonId(), person.getFirstName())).collect(Collectors.toList());

        assertNotNull(dtoList);
        assertEquals(expectedSize, dtoList.size());
    }

    /**
     * In a Stream Filter out one person with id 5914 from people
     * and take the birthdate and build a string from data that the date contains then
     * return the string.
     */

    @Test
    public void task10() {
        String expected = "WEDNESDAY 19 DECEMBER 2012";
        int personId = 5914;

        Optional<String> optional = null;

        LocalDate option = people.stream().filter(person -> person.getPersonId() == personId).findFirst().get().getDateOfBirth();

        optional = Optional.of(option.format(new DateTimeFormatterBuilder().appendPattern(("eeee dd MMMM YYYY")).toFormatter(Locale.ENGLISH)).toUpperCase());

        assertNotNull(optional);
        assertTrue(optional.isPresent());
        assertEquals(expected, optional.get());
    }

    /**
     * Get average age of all People by turning people into a stream and use defined ToIntFunction personToAge
     * changing type of stream to an IntStream.
     */

    @Test
    public void task11() {
        ToIntFunction<Person> personToAge =
                person -> Period.between(person.getDateOfBirth(), LocalDate.parse("2019-12-20")).getYears();

        //int age =Period.between(person.getDateOfBirth(), LocalDate.parse("2019-12-20")).getYears();

        double expected = 54.42;
        double averageAge = 0;

        //.forEach(people.);

        // people.stream().mapToInt().
        averageAge = people.stream().collect(Collectors.averagingInt(personToAge));


        //  personToAge.applyAsInt(people.)
        // personToAge.applyAsInt(people.addAll());

        //  averageAge=personToAge /  people.size();

        // System.out.println(people.stream().filter(person -> per));

        assertTrue(averageAge > 0);
        assertEquals(expected, averageAge, .01);
    }

    /**
     * Extract from people a sorted string array of all firstNames that are palindromes. No duplicates
     */
    @Test
    public void task12() {
        String[] expected = {"Ada", "Ana", "Anna", "Ava", "Aya", "Bob", "Ebbe", "Efe", "Eje", "Elle", "Hannah", "Maram", "Natan", "Otto"};
        String[] result = null;

        List<String> temp = null;
            temp = people.stream().filter(person -> person.getFirstName()
                .equalsIgnoreCase(new StringBuilder(person.getFirstName()).reverse().toString()))
                .map(person -> person.getFirstName())
                .distinct().sorted()
                .collect(Collectors.toList());
//issue is to convert List of string to String array

        System.out.println(temp);
        result= new String[temp.size()];

        result=temp.toArray(result);

        assertNotNull(result);
        assertArrayEquals(expected, result);
    }

    /**
     * Extract from people a map where each key is a last name with a value containing a list
     * of all that has that lastName
     */

    @Test
    public void task13() {
        int expectedSize = 107;
        Map<String, List<Person>> personMap = null;

        personMap
                = people.stream().collect(Collectors.groupingBy(person -> person.getLastName()));
        //(Map<String, List<Person>>)
        // last name
        // against every last name get a key
        // value of key is a LIST
        // int s = personMap.size();
        // System.out.println(s);
        assertNotNull(personMap);
        assertEquals(expectedSize, personMap.size());
    }

    /**
     * Create a calendar using Stream.iterate of year 2020. Extract to a LocalDate array
     */

    @Test
    public void task14() {
        LocalDate[] _2020_dates = null;

        int limit = Year.of(2020).isLeap() ? 366 : 365;    // if leap year (? TRUE) return 366 else (: FALSE) return 365
        LocalDate start = LocalDate.ofYearDay(2020, 1);
        //i++                                  Like for loop incrementer
        _2020_dates = Stream.iterate(start, date -> date.plusDays(1))  // Every element of stream is being incremented (by using iterator)
                .limit(limit)                                         // within the limit of 366 days
                .toArray(LocalDate[]::new);                           // each array element is being populated with produced date (TWO line above)
        assertNotNull(_2020_dates);
        assertEquals(366, _2020_dates.length);
        assertEquals(LocalDate.parse("2020-01-01"), _2020_dates[0]);
        assertEquals(LocalDate.parse("2020-12-31"), _2020_dates[_2020_dates.length - 1]);
    }

}
