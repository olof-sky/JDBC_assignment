package org.skylan;


import org.skylan.data.PeopleDaoImpl;
import org.skylan.models.Person;

public class App
{
    public static void main( String[] args )
    {
        Person person = new Person(17, "Hans", "Åkesson");
        PeopleDaoImpl peopleDao = new PeopleDaoImpl();
        peopleDao.deleteById(17);
    }
}
