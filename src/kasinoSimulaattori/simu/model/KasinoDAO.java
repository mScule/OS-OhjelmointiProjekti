package kasinoSimulaattori.simu.model;

import java.sql.*;
import java.util.ArrayList;

/**
 * Kasinosimulaattorin tulosten käsittelyyn tarkoitettu dao luokka.
 * @author Vilhelm
 */
public class KasinoDAO {
	
	// Tietokannan yhteys tietoja
	private final String
		TIETOKANNAN_NIMI = "kasino",
		URL = "jdbc:mariadb://localhost";
	
	private String
		kayttaja = "root",
		salasana = "41115dc5-b149-4753-9894-e7b08764ed2b";
	
	// Luokka on singleton
	
	private static KasinoDAO instanssi;
	
	private KasinoDAO() {}
	
	public static KasinoDAO getInstanssi() {
		if(instanssi == null)
			instanssi = new KasinoDAO();
		
		return instanssi;
	}
	
	public void setKayttaja(String kayttaja) {
		this.kayttaja = kayttaja;
	}
	
	public void setSalasana(String salasana) {
		this.salasana = salasana;
	}

	/**
	 * Yrittää luoda yhteyden kasinon tuloksia ylläpitävään relaatiotietokantaan.
	 * @return Palauttaa tietokantaan yhteyden luoneen yhteysolion jos yhteys onnistuu.
	 *         Yhteyden luonnin epäonnistuessa palautuu null pointteri.
	 */
	private Connection luoYhteys(String tietokanta) throws SQLException {
		
		Connection yhteys;
		
		if (!tietokanta.equals(""))
			tietokanta = "/" + tietokanta;
		
		final String KOOTTU_URL = URL + tietokanta;

		yhteys = DriverManager.getConnection(
			KOOTTU_URL + "?user=" + kayttaja + "&password=" + salasana
		);

		return yhteys;
	}
	
	/**
	 * Kokeileee luoda yhteyden tietokantaan.
	 */
	public boolean yhteysOnnistuu() {
		try (Connection yhteys = luoYhteys(TIETOKANNAN_NIMI)) {
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	/**
	 * Yrittää luoda tietokannan, ja lisätä sinne taulukon "tulokset"
	 */
	public boolean yritaLuodaTietokanta() {
		try (
			Connection luotavaTietokanta = luoYhteys("");
			Statement  tietokannanLuonti = luotavaTietokanta.createStatement();
		) {
			tietokannanLuonti.executeUpdate("CREATE DATABASE " + TIETOKANNAN_NIMI);
			
			tietokannanLuonti.close();
			luotavaTietokanta.close();
			
			try (
				Connection luotuTietokanta   = luoYhteys(TIETOKANNAN_NIMI);
				Statement  taulukoidenLuonti = luotuTietokanta.createStatement();
			){
				taulukoidenLuonti.executeUpdate(
					"CREATE TABLE tulokset ( "                 +
					"	TulosID int NOT NULL AUTO_INCREMENT, " +

					"	Aika              REAL, " +

					"	Mainostus         REAL, " +
					"	MaksimiPanos      REAL, " +
					"	MinimiPanos       REAL, " + 
					"	Yllapito          REAL, " +
					"	Tasapeliprosentti REAL, " +
					"	Voittoprosentti   REAL, " +

					"	BlackjackPoydat INT, " +
					"	Baarit          INT, " +
					"	Sisaankaynnit   INT, " +
					"	Uloskaynnit     INT, " +

					"	Rahat  REAL, " +
					"	Voitot REAL, " +

					"	SaapuneetAsiakkaat INT, "         +
					"	PalvellutAsiakkaat INT, "         +
					"	KeskimaarainenJonotusaika REAL, " +
					"	Kokonaisoleskeluaika REAL, "      +

					"	KeskimaarainenOnnellisuus REAL, "  +
					"	KeskimaarainenPaihtymys REAL, "    +
					"	KeskimaarainenVarallisuus REAL, "  +
					"	KeskimaarainenLapimenoaika REAL, " +

					"	PRIMARY KEY (TulosID) " +
					"); "
				);
			} catch (SQLException e) { return false; }
		} catch (SQLException e)     { return false; }
		
		return true;
	}
	
	/**
	 * Yrittää lisätä annetut tulokset tietokantaan.
	 * @param tulokset Lisättävät tulokset.
	 * @return true Jos lisäys onnistuu. false Jos lisäys epäonnistuu.
	 */
	public boolean lisaaTulokset(KasinoTulokset tulokset) {
		try(
			Connection connection = luoYhteys(TIETOKANNAN_NIMI);
			Statement  statement  = connection.createStatement();
		) {
			statement.executeUpdate(
				"INSERT INTO tulokset(" +
			
					"Aika,"              +
					"Mainostus,"         +
					"MaksimiPanos,"      + 
					"MinimiPanos,"       +
					"Yllapito,"          +
					"TasapeliProsentti," +
					"VoittoProsentti,"   +
					
					"BlackjackPoydat," +
					"Baarit,"          +
					"Sisaankaynnit,"   +
					"Uloskaynnit,"     +
					
					"Rahat,"  +
					"Voitot," +
					
					"SaapuneetAsiakkaat," +
					"PalvellutAsiakkaat," +
					
					"KeskimaarainenJonotusaika," +
					"Kokonaisoleskeluaika,"      +
					
					"KeskimaarainenOnnellisuus,"  +
					"KeskimaarainenPaihtymys,"    +
					"KeskimaarainenVarallisuus,"  +
					"KeskimaarainenLapimenoaika)" +
					
				"VALUES (" +
					
				tulokset.getAika()              + "," +
				tulokset.getMainostus()         + "," +
				tulokset.getMaksimiPanos()      + "," +
				tulokset.getMinimiPanos()       + "," +
				tulokset.getYllapito()          + "," +
				tulokset.getTasapeliProsentti() + "," +
				tulokset.getVoittoProsentti()   + "," +
				
				tulokset.getBlackjackPoydat() + "," +
				tulokset.getBaarit()          + "," +
				tulokset.getSisaankaynnit()   + "," +
				tulokset.getUloskaynnit()     + "," +
				
				tulokset.getRahat()  + "," +
				tulokset.getVoitot() + "," +
				
				tulokset.getSaapuneetAsiakkaat() + "," +
				tulokset.getPalvellutAsiakkaat() + "," +
				
				tulokset.getKeskJonotusaika()      + "," +
				tulokset.getKokonaisoleskeluaika() + "," +
				
				tulokset.getKeskOnnellisuus()  + "," +
				tulokset.getKeskPaihtymys()    + "," +
				tulokset.getKeskVarallisuus()  + "," +
				tulokset.getKeskLapimenoaika() + ")"
			);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Yrittää hakea jokaisen tulos tietueen tietokannasta.
	 * @return Haun onnistuessa tulokset palautetaan taulukkomuodossa indeksinä ensimmäisestä viimeiseen.
	 *         Epäonnistuessa metodi palauttaa null pointteri.
	 */
	public KasinoTulokset[] haeTulokset() {
		ArrayList<KasinoTulokset> haetut = new ArrayList<KasinoTulokset>();
		
		try(
			Connection connection = luoYhteys(TIETOKANNAN_NIMI);
			Statement  statement  = connection.createStatement();
			ResultSet  resultSet  = statement.executeQuery("SELECT * FROM tulokset");
		) {
			while(resultSet.next()) {
				haetut.add(new KasinoTulokset(
					resultSet.getDouble("Aika"),
					resultSet.getDouble("Mainostus"),
					resultSet.getDouble("MaksimiPanos"),
					resultSet.getDouble("MinimiPanos"),
					resultSet.getDouble("Yllapito"),
					resultSet.getDouble("TasapeliProsentti"),
					resultSet.getDouble("VoittoProsentti"),
					
					resultSet.getInt("BlackjackPoydat"),
					resultSet.getInt("Baarit"),
					resultSet.getInt("Sisaankaynnit"),
					resultSet.getInt("Uloskaynnit"),
					
					resultSet.getDouble("Rahat"),
					resultSet.getDouble("Voitot"),
					
					resultSet.getInt("SaapuneetAsiakkaat"),
					resultSet.getInt("PalvellutAsiakkaat"),
					
					resultSet.getDouble("KeskimaarainenJonotusaika"),
					resultSet.getDouble("Kokonaisoleskeluaika"),
					
					resultSet.getDouble("KeskimaarainenOnnellisuus"),
					resultSet.getDouble("KeskimaarainenPaihtymys"),
					resultSet.getDouble("KeskimaarainenVarallisuus"),
					resultSet.getDouble("KeskimaarainenLapimenoaika")
				));
			}
		} catch (SQLException e){
			return null;
		}
		
		KasinoTulokset[] tulokset = new KasinoTulokset[haetut.size()];
		
		for(int i = 0; i < haetut.size(); i++)
			tulokset[i] = haetut.get(i);
		
		return tulokset;
	}
}
