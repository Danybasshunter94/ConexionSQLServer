package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AccesoDatoSQLServer {

	private Connection con;
	
	public void getConexion() {
		try {
			con = DriverManager.
					getConnection
					(
					//linea para ejecutar la conexion con el servidor SQL
					"jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;integratedSecurity=true;databaseName=AdventureWorks2012"
					,
					//nombre del servidor y de la instancia
					"T103-PC13\\SQLEXPRESS"
					,
					//iria la clave, en caso de que tuviera
					""
					);
			
			System.out.println("Conexion establecida con la bd");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		 
	}
	
	public ArrayList<ArrayList<String>> getRegistrosTablaBD(String tabla, String bd) {
		ArrayList<ArrayList<String>> lista = new ArrayList<>();
		this.getConexion();
		try {
			Statement sentencia = con.createStatement();
			ResultSet rs = sentencia.executeQuery("SELECT * FROM " + bd + "." + tabla);
			ResultSetMetaData rsmd = rs.getMetaData();
			int col = rsmd.getColumnCount();
			while (rs.next()) {
				ArrayList<String> registro = new ArrayList<>();
				ArrayList<String> nombreCol = new ArrayList<>();
				for (int i = 1; i <= col; i++) {
					if (lista.size() == 0) {
						nombreCol.add((rsmd.getColumnName(i)));
					}
					registro.add(rs.getString(i));
				}
				if (lista.size() == 0) {
					lista.add(nombreCol);
				}
				lista.add(registro);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}
	
	public void mostrarTablaBD(ArrayList<ArrayList<String>> arrayList){
		for(int i=0; i < arrayList.size(); i++){
			for(int j=0; j < arrayList.get(i).size(); j++){
				System.out.print(arrayList.get(i).get(j) + "" + "\t");
			}
			System.out.println();
		}
	}
	
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AccesoDatoSQLServer acceso = new AccesoDatoSQLServer();
		acceso.getConexion();
		
		acceso.mostrarTablaBD(acceso.getRegistrosTablaBD("HumanResources.Department" , "AdventureWorks2012"));
	}

}
