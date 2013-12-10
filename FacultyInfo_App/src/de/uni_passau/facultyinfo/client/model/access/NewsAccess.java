package de.uni_passau.facultyinfo.client.model.access;

import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.News;

public class NewsAccess {
	private static final String RESSOURCE = "/news";

	protected NewsAccess() {
	}

	public ArrayList<News> getNews() {
		RestConnection<News> restConnection = new RestConnection<News>(
				News.class);
		ArrayList<News> news = createNewsSampleData();
		// ArrayList<News> news = restConnection.getRessourceAsList(RESSOURCE);
		return news;
	}

	private ArrayList<News> createNewsSampleData() {
		ArrayList<News> newsList = new ArrayList<News>();

		newsList.add(new News(
				UUID.randomUUID().toString(),
				"Protokolle der Evaluation WS 12/13",
				"Die Ergebnisse der Qualitätsevaluation unserer Fachschaft vom Wintersemester 2012/13 sind...",
				"http://www.neu.fs-wiwi.de/index.php/de/aktuelles/565-protokolle-evaluation",
				"Die Ergebnisse der Qualitätsevaluation unserer Fachschaft vom Wintersemester 2012/13 sind bekannt und wurden von uns bei den entsprechenden Stellen vorgestellt, damit sich das Angebot in Zukunft stetig in Eurem Sinne verbessert. Die Gesprächsprotokolle findest du hier: Protokoll Auslandsamt Protokoll Bibliothek Protokoll Mensa Protokoll Rechenzentrum Protokoll Sprachenzentrum Protokoll Zentrum für Schlüsselqualifikationen",
				new Date(2013, 10, 26)));
		newsList.add(new News(
				UUID.randomUUID().toString(),
				"Orientierungswoche Wintersemester 2013/2014",
				"Um dir deinen Start an der Uni Passau zu erleichtern, organisiert dir deine Fachschaft eine Orientierungswoche...",
				"http://www.neu.fs-wiwi.de/index.php/de/aktuelles/563-orientierungswoche-wintersemester-20132014",
				"Um dir deinen Start an der Uni Passau zu erleichtern, organisiert dir deine Fachschaft eine Orientierungswoche für Erstsemester. Die sogenannte O-Woche findet stets eine Woche vor Vorlesungsbeginn statt und ist eine gute Möglichkeit für dich deine Kommilitonen, die Stadt Passau, wie auch die Uni kennen zu lernen und deine Fragen und Orientierungslosigkeit zum Studium loszuwerden. In der O-Woche bieten wir dir ein umfassendes Programm von Uni-Führungen, Stadtführungen bis hin zu Sport und zum Kneipenbummel. Für jeden ist etwas dabei! Die O-Woche im Wintersemester 2013/2014 findet vom 07. bis 11. Oktober 2013 statt! Der Ablaufplan der O-Woche 2013 ist fertig, du findest ihn unter folgendem Link! >> Link zum O-Wochenplan Unsere Erstsemesterbroschüre, das sogenannte Quietschie-Heft, beantwortet alle Fragen zum Studienstart und bietet darüber hinaus interessante Informationen rund um das Leben und Studieren in Passau! Du kannst es unter folgendem Link downloaden, außerdem verteilen wir das Heft in gedruckter Form während der Orientierungswoche! >> Link zur Erstsemesterbroschüre",
				new java.util.Date(2013, 9, 26)));
		newsList.add(new News(
				UUID.randomUUID().toString(),
				"Verlängerte Bibliotheksöffnungszeiten",
				"Nachtschwärmer aufgepasst: Die Klausurenphase hat mal wieder begonnen und die Bibliotheken haben länger für euch geöffnet. Lesesaal Wirtschaft: verlängert ab Mo, 8. Juli bis...",
				"http://www.neu.fs-wiwi.de/index.php/de/aktuelles/481-verlaengerte-bibliotheksoeffnungszeiten",
				"Nachtschwärmer aufgepasst: Die Klausurenphase hat mal wieder begonnen und die Bibliotheken haben länger für euch geöffnet. Lesesaal Wirtschaft: verlängert ab Mo, 8. Juli bis So, 28. Juli Mo-Fr von 08:00-02:00 Uhr Sa von 09:00-22:00 Uhr So von 11:00-19.00 Uhr verlängert ab Mo, 29. Juli bis Di, 6. August Mo-Do von 08:00-24:00 Uhr Fr von 08:00-22:00 Uhr Sa von 09:00-22:00 Uhr So von 11:00-19:00 Uhr NEU: Ab dem 08. Juli wird vom Bibliothekspersonal das Einhalten der Parkuhrregelung überprüft - sollte die eine Stunde Freihalten des Platzes überschritten werden, wird das Personal die Unterlagen beiseite schieben und für andere Kommilitonen den Tisch zur Verfügung stellen. Es wird in dem Fall ein Hinweiszettel vorzufinden sein, auf welchem notiert ist, dass das Bibliothekspersonal die Unterlagen beiseite geräumt hat. Viel Erfolg beim lernen und bei euren Klausuren! Eure Fachschaft Wirtschaft",
				new Date(2013, 7, 8)));

		return newsList;
	}
}
