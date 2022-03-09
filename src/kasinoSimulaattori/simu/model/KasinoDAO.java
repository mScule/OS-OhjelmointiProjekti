package kasinoSimulaattori.simu.model;

import java.sql.*;
import java.util.ArrayList;
import kasinoSimulaattori.simu.framework.Trace;

public class KasinoDAO {
	
	private KasinoDAO() {}

	private static Connection luoYhteys() {
		Connection connection = null;
		
		final String
			URL      = "jdbc:mariadb://localhost/kasino",
			USER     = "root",
			PASSWORD = "olso";
		
		try {
			connection = DriverManager.getConnection(
				URL + "?user=" + USER + "&password=" + PASSWORD
			);
		} catch (SQLException e) {
			Trace.out(Trace.Level.ERR, e.getMessage());
		}
		
		return connection;
	}
	
	public static void lisaaTulokset(KasinoTulokset tulokset) {
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
			Trace.out(Trace.Level.ERR, e.getMessage());
		}
	}
	
	public static KasinoTulokset[] haeTulokset() {
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
			Trace.out(Trace.Level.ERR, e.getMessage());
		}
		
		KasinoTulokset[] tulokset = new KasinoTulokset[haetut.size()];
		
		for(int i = 0; i < haetut.size(); i++)
			tulokset[i] = haetut.get(i);
		
		return tulokset;
	}
}
