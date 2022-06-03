package org.service.crud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.WebMethod;
import javax.jws.WebService;


/*
 * WEB SERVICE SOAP:
 * 
 * WSDL: WEB SEVICE DESCRIPTION LANGUAJE, ES EL LENGUAJE ESPECIFICADO DEL WEB SERVICE.
 * 
 * UDDI: UNIVERSAL DESCRIPTION AND DISCOVERY INTEGRATION, PERMITE BUSCAR Y DESCUBRIR
 * WEB SERVICES(PUBLIC, PRIVATE).
 * 
 * SOAP: SIMPLE OBJECT ACCESS PROTOCOL, PROTOCOLO DE DATOS QUE EMPLEA EL WEB SERVICE SOAP
 * ES DEL TIPO XML.
 * 
 * 
 * ..............................................................................................
 * 
 * REGLAS DE CREACION DE WEB SERVICE SOAP
 * 
 *  1.- TODOS LOS METODOS DEBEN COMENZAR CON MINUSCULA, Y NO SE Y NOS SE PUEDEN REESTRUCTURAR.
 *  
 *  2.- AGREGAR LAS ANOTACIONES: @WebService, @WebMethod
 *  
 *  .............................................................................................
 *  
 *  SI SE DESEA HACER ALGUN CAMBIO, UNA VEZ YA CREADO EL WEB SERVICE.
 *  
 *  1.- STOP SERVER APACHE
 *  
 *  2.- ADD AND REMOVE - REMOVE EL PROYECTO DEL SERVIDOR Y LIMPIAR CLEAN
 *  
 *  3.- WEBCONTENT -WSDL (ELIMINA LA CARPETA)
 *  
 *  4.- VOLVER A CREAR EL WEB SERVICE SOAP
 *  
 *  ..............................................................................................
 *  
 *                                       REMOTAMENTE
 *  WEB SERVICE  SOAP: WEB SERVICE SERVIDOR ---------------> WEB SERVICE CLIENTE
 *  					CLASS JAVA SERVICE                        JSP'S
 *  
 *  ..............................................................................................
 *  
 *  NOTA: ***IMPORTANTE GUARDAR LA CLASE ANTES DE GENERAR EL WEB SERVICE SOAP***
 */

@WebService
public class ServiceCrudPersona {

	static Connection connection = null;
	static String driver = "oracle.jdbc.driver.OracleDriver";
	static String URL = "jdbc:oracle:thin:@localhost:1521:orcl";

	@WebMethod
	public static void connectDataBaseOracle() throws IOException, SQLException {
		try {
			Class.forName(driver).newInstance();
			System.out.println("CARGO DRIVER: ojdbc6.jar");
		} catch (Exception e) {
			System.out.println("Exception driver:" + e.getMessage());
		}
		try {
			connection = DriverManager.getConnection(URL, "System", "Astrix12");
			System.out.println("CONEXIÓN EXITOSA: Oracle11g");
		} catch (Exception e) {
			System.out.println("Exception connection:" + e.getMessage());
		}
	}

	@WebMethod
	public static String altaPersona(int id, String nombre, String apepat, String tel) 
		throws SQLException, IOException{
		connectDataBaseOracle();
		try {

			String sql = "INSERT INTO PERSONA (ID,NOMBRE,APEPAT,TEL) VALUES (?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, nombre);
			ps.setString(3, apepat);
			ps.setString(4, tel);
			ps.executeQuery();
		} catch (Exception e) {
			System.out.println("ERROR:" + e);
		}
	}

	@WebMethod
	public static void modificarPersona(String nombre, String apepat, String tel, int id) 
			throws SQLException, IOException {
		connectDataBaseOracle();
		try {
			
			String sql = "UPDATE PERSONA SET NOMBRE = ?, APEPAT = ?, " + " TEL = ? WHERE ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, nombre);
			ps.setString(2, apepat);
			ps.setString(3, tel);
			ps.setInt(4, id);
			ps.execute();
			System.out.println("REALIZO EXITOSAMENTE EL CAMBIO");
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		}
	}

	@WebMethod
	public static void eliminaPersona(int id) {
		try {
			connectDataBaseOracle();

			String sql = "DELETE FROM PERSONABATCH WHERE ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
			System.out.println("REALIZO EXITOSAMENTE LA ELIMINACIÓN");
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		}
	}

	@WebMethod
	public static String consultaIdPersona(int id) throws SQLException, IOException {
			connectDataBaseOracle();
			String nombre = null;
			String apepat = null;
			String tel = null;
			try {
			String sql = "SELECT * FROM PERSONABATCH WHERE ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nombre = rs.getString("nombre");
				apepat = rs.getString("apepat");
				tel = rs.getString("tel");
			}
		} catch (Exception e) {
			System.out.println("ERROR:" + e);
		}
			return "CONSULTA:" + id + "," + nombre + "," + apepat + "," + tel;
	}
	
	public static void consultaIndividualEstudiante(int id) {
		try {
			connectDataBaseOracle();

			String sql = "SELECT * FROM ESTUDIANTE WHERE ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next()) {
				System.out.println(resultSet.getInt("id") 
						+ "," + resultSet.getString("nombre")
						+ "," + resultSet.getString("apepat")
						+ "," + resultSet.getString("telefono"));
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		}
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		// insertEstudiante(1001, "JUAN", "SANCHEZ", "22-22-22-22-22");
		//modificarEstudiante("CARLOS", "RAMIREZ", "77.7.7.7.7", 1001);
		//eliminarEstudiante(1001);
		//consultaGeneralEstudiante();
		consultaIndividualEstudiante(1);
	}

}

}
