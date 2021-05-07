package dbModelsnDAOs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;

public interface UserRepository extends JpaRepository<User, String> {

	@Query("SELECT user.Username FROM User user WHERE user.Username = :login")
	String findRegistredLogin(@Param("login") String login);

	@Query("SELECT user FROM User user WHERE user.Name = :Name")
	List<User> findAllByName(@Param("Name") String Name);

	@Query("SELECT user FROM User user WHERE user.Surname = :Surname")
	List<User> findAllBySurname(@Param("Surname") String Surname);

	@Query("SELECT user FROM User user WHERE user.Name = :Name AND user.Surname = :Surname")
	List<User> findAllByNameAndSurname(@Param("Name") String Name, @Param("Surname") String Surname);

	
	
	
	/*
	 * wykonujac zapytania natywne czesto chcemy dostać jako wynik np kolumny z 2 tabel, nie ma takiej klasy w programie aby wpisać jako wynki metody
	 * metoda z tego interfejsu moze zwrocic dowolny obiekt pod warunkiem ze wszystkie pola obiektu odpowiadaja kolumna w tablei zwroconej
	 * w srodku jest resultset, kolumny z rs sa wpisywane na pola obiektu zwracanego, jezeli bedzie cos za duzo cos za mało to wyjatek
	 * nie trzeba w biegu robić klas anonimowych aby miec na co wpisyac wynki takich zapytan
	 * metoda taka musi zwracac listę tablic, kazdy index tablicy to kolumna z teblicy wynikowej sql, resultset ...
	 * tablica ta przechowuje referencję typu object (polimorfizm) 
	 * obiekty wpisane w index takiej tablicy są faktycznie takiej klasy jakiej klasy jest kolumna tabeli (javowy odpowiednik)
	 * obiekty te są wpisane polimorficznie na referencje typu object w konkretne indexy tablicy odpowiadające kolejności z tabeli wynikowej sql
	 * musisz wiedziec za tem w jakiej kolejności są kolumny w tablicy wynikowej (jak w liscie select w zapytaniu) - zawsze przetestuj zapytanie w pgAdmin
	 * skoro polimorficznie psisano te obiekty na ref object to przy pomocy rzutowania mówimy kompilatorowi jaka jest orginalna klasa tego obiektu
	 * rzutowanie jest odwrotnoscia polimorfizmu - obiekt raz stworzony przy pomocy słowa kluczowego new zawsze zachowuje swoją klasę
	 * po zrzutowaniu obiektu z tablicy Object[x] mozemy zrobić z tym co chcemy
	 *  mozna powiedziec ze List<Object[]> to taki resultset - dziala tak samo
	 */
	
	@Query(value="SELECT person.role, friends.friend_name FROM person JOIN friends on person.username = friends.user_name", nativeQuery = true)
	List<Object[]> joinNative();
	
	@Query(value="SELECT notify_table.id, notify_table.tresc FROM notify_table", nativeQuery = true)
	List<Object[]> bigIntegerNative();
	
	
	/*
	 * jezeli mamy do ubrania w obiekt wynikową tabele sql to jest opcja skorzystania z obiektu ktory juz mamy 
	 * przy pomocy domyslnej metody interfejsu jak ponizej
	 * w interfejsie w domyslnej metodie wywołujemy metode @Query ktora zwraca te problematyczną tabele i piszemy algorytm ktory przepisze nam
	 * odpowiednie tablice na obiekt klasy ktora juz mamy 
	 * niezadeklarowane pola obiektu beda nullami zadeklarowane ? - beda czym chcesz z tabeli wynikowej sql jedynie pilnuj typu danych kolumna sql <-> pole
	 */
	
	public default List<User> xd(){
		
		List<User> lista = new ArrayList<>();
		
		List<Object[]> pp = this.joinNative();
		
		for(Object[] x : pp) {
			
			User u = new User();
			u.role = (String) x[0];
			u.Surname = (String) x[1];
			
			lista.add(u);
		}
				
		return lista;		
	}
	

}
