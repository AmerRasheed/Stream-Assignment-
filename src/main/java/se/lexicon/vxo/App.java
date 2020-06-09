package se.lexicon.vxo;


import se.lexicon.vxo.model.Gender;
import se.lexicon.vxo.model.Person;
import se.lexicon.vxo.service.People;
import se.lexicon.vxo.service.PeopleImpl;

public class App
{
    public static void main( String[] args ) {
        System.out.println("Hello World");
/*
        int counter=0;
        for(Person p:People.INSTANCE.getPeople()) {
            System.out.println(p);
                counter++;
        }
            System.out.println(counter);
  */
      //  People.INSTANCE.getPeople();
    }
}


// All persons with name Andersson

/*   int counter=0;
        for(Person p:People.INSTANCE.getPeople()) {
            if (p.getLastName().equals("Andersson"))
            counter++;
        }
            System.out.println(counter);*/

// All females
    /*
int counter=0;
        for(Person p:People.INSTANCE.getPeople()) {
                if (p.getGender().equals(Gender.FEMALE))
                counter++;
                }
                System.out.println(counter);
*/