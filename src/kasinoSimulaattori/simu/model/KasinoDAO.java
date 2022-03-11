package kasinoSimulaattori.simu.model;

import java.sql.*;
import java.util.ArrayList;

/**
 * Kasinosimulaattorin tulosten käsittelyyn tarkoitettu dao luokka.
 * @author Vilhelm
 */
public class KasinoDAO {
	
	// Luokka on singleton
	
	private static KasinoDAO instanssi;
	
	private KasinoDAO() {}
	
	public static KasinoDAO getInstanssi() {
		if(instanssi == null)
			instanssi = new KasinoDAO();
		
		return instanssi;
	}

	/**
	 * Yrittää luoda yhteyden kasinon tuloksia ylläpitävään relaatiotietokantaan.
	 * @return Palauttaa tietokantaan yhteyden luoneen yhteysolion jos yhteys onnistuu.
	 *         Yhteyden luonnin epäonnistuessa palautuu null pointteri.
	 */
	private Connection luoYhteys() throws SQLException {
		
		Connection yhteys;
		
		final String
			URL      = "jdbc:mariadb://localhost/kasino",
			KAYTTAJA = "root",
			SALASANA = "41115dc5-b149-4753-9894-e7b08764ed2b";

		yhteys = DriverManager.getConnection(
			URL + "?user=" + KAYTTAJA + "&password=" + SALASANA
		);

		return yhteys;
	}
	
	/**
	 * Yrittää lisätä annetut tulokset tietokantaan.
	 * @param tulokset Lisättävät tulokset.
	 * @return true Jos lisäys onnistuu. false Jos lisäys epäonnistuu.
	 */
	public boolean lisaaTulokset(KasinoTulokset tulokset) {
		try(
			Connection connection = luoYhteys();
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
			Connection connection = luoYhteys();
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
