package com.lifeline.lifeline2.controllers;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.lifeline.lifeline2.models.Counsellor;
import com.lifeline.lifeline2.models.Doctor;
import com.lifeline.lifeline2.services.DoctorService;
import com.lifeline.lifeline2.services.LoginService;


@Controller
public class DoctorController {

	@Autowired
	private DoctorService doctorService = new DoctorService();
	@Autowired
	private LoginService loginService = new LoginService();

	@GetMapping("/addDoctor")
	public String addDoctor(@RequestParam String firstName, @RequestParam String lastName,
							@RequestParam String email, @RequestParam String address1,
							@RequestParam String address2, @RequestParam String mobileNumber,
							@RequestParam String password, @RequestParam Date birthday,
							@RequestParam String registrationNumber) {
		System.out.println("Going to add a Doctor > " + firstName + "  " + mobileNumber);
		Doctor doctor = new Doctor();

		doctor.setFname(firstName);
		doctor.setLname(lastName);
		doctor.setEmail(email);
		doctor.setMobile(Long.parseLong(mobileNumber));
		String address = address1 + " " + address2;
		doctor.setAddr(address);
		doctor.setDid(registrationNumber);
		doctor.setBirthday(birthday);
		System.out.println("Doctor info : " + doctor);

		doctorService.saveUser(doctor);

		loginService.saveLoginCreds(email, password, "D");

		return "redirect:/user_login";
	}

	@GetMapping("/doctor/{cid}")
	public String goDoctor(@PathVariable String cid, Model model) {
		model.addAttribute("cid", cid);
		System.out.println("goDoctor:: The doctor cid is : " + cid);
		return "doctor";
	}

	public String goDoctorProfile(String id, Model model) {
		System.out.println("The Doctor id is : " + id);
		Doctor doctor = doctorService.getDoctor(id);
		System.out.println(doctor);
		model.addAttribute("type", "D");
		model.addAttribute(doctor);
		return "profile";
	}

	// Soham API Starts HERE.......


	@GetMapping("/getSelfAssessmentScores")  //API #1
	public ResponseEntity<JSONArray> getSelfAssessmentScores(Model model) throws Exception {

		Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver

		String url = "jdbc:mysql://localhost:3306/spmdb";
		String user = "root";
		String password = "root123";
		String query = "SELECT patient.first_name, patient.last_name,patient.needTreatment, patient.email,\n" +
				"self_assessment.question1, self_assessment.question2,self_assessment.question3,self_assessment.question4,self_assessment.question5,self_assessment.question6,self_assessment.question7,self_assessment.question8,self_assessment.question9,\n" +
				"self_assessment.self_assessment_score\n" +
				"FROM patient\n" +
				"JOIN self_assessment\n" +
				"ON patient.self_assessment_id=self_assessment.self_assessment_id;";

		JSONObject jsonObject = null;
		Connection con = null;
		Statement st = null;
		JSONArray result = null;

		try {

			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();

			ResultSet resultSet = st.executeQuery(query);//Executing Query

			//Converting Result Set To JSON:

			ResultSetMetaData md = resultSet.getMetaData();
			int numCols = md.getColumnCount();
			List<String> colNames = IntStream.range(0, numCols)
					.mapToObj(i -> {
						try {
							return md.getColumnName(i + 1);
						} catch (SQLException e) {
							e.printStackTrace();
							return "Exception occurred";
						}
					})
					.collect(Collectors.toList());

			result = new JSONArray();
			while (resultSet.next()) {
				JSONObject row = new JSONObject();
				colNames.forEach(cn -> {
					try {
						row.put(cn, resultSet.getObject(cn));
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				result.add(row);// Contains the JSON_ARRAY
			}


			//Conversion Ended....


			//Printing The JSON ARRAY :

			jsonObject = new JSONObject();
			jsonObject.put("result", result);

			// Default print without indent factor
			System.out.println(jsonObject);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			st.close();
			con.close();
			System.out.println("Connection Closed Successfully..");

		}

		// Pretty print with 2 indent factor
		System.out.println(jsonObject.toString());


//		System.out.println("SOHAM INSIDE SELF ASSESSMENT API : "+pid);
		System.out.println("HIT THE END, It worked, NEW NEW");

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/getAllDoctors")  //API #3
	public ResponseEntity<JSONArray> getAllDoctors(Model model) throws Exception {

		System.out.println("Inside getAllDoctors");

		Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver
		String url = "jdbc:mysql://localhost:3306/spmdb";
		String user = "root";
		String password = "root123";
		String query = "SELECT doctor_id, first_name, last_name, email\n" +
				"\t\tFROM doctor;";

		Connection con = null;
		Statement st = null;
		JSONArray result = null;

		try {

			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();

			ResultSet resultSet = st.executeQuery(query);//Executing Query

			//Converting Result Set To JSON:

			ResultSetMetaData md = resultSet.getMetaData();
			int numCols = md.getColumnCount();
			List<String> colNames = IntStream.range(0, numCols)
					.mapToObj(i -> {
						try {
							return md.getColumnName(i + 1);
						} catch (SQLException e) {
							e.printStackTrace();
							return "Exception occurred";
						}
					})
					.collect(Collectors.toList());

			result = new JSONArray();
			while (resultSet.next()) {
				JSONObject row = new JSONObject();
				colNames.forEach(cn -> {
					try {
						row.put(cn, resultSet.getObject(cn));
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				result.add(row);// Contains the JSON_ARRAY
			}


			//Conversion Ended....


			//Printing The JSON ARRAY :

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", result);

			// Default print without indent factor
			System.out.println(jsonObject);

			// Pretty print with 2 indent factor
			System.out.println(jsonObject.toString());
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			st.close();
			con.close();
			System.out.println("Connection Closed Successfully...");
		}


		System.out.println("HIT THE END, API 3 worked");

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

//	-----------------Adding Two APIs to View Future and Past Appointments----------
@GetMapping("/getFutureAppointments/{doctor_email}")  //API #4
public ResponseEntity<JSONArray> getFutureAppointments(@PathVariable String doctor_email, Model model) throws Exception {

	Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver

	String url = "jdbc:mysql://localhost:3306/spmdb";
	String user = "root";
	String password = "root123";
	String query = "select patient.email, patient.first_name, patient.last_name, self_assessment.self_assessment_score, self_assessment.appointment_status from patient JOIN self_assessment ON \n" +
			"patient.self_assessment_id = self_assessment.self_assessment_id where patient.doctor_app_id = (\n" +
			"select doctor_id from doctor where email = '"+doctor_email+"') and patient.doctor_app_id IN (select doctor_id from appointment where appointment_time  >= CURDATE());";

	Connection con = null;
	Statement st = null;
	JSONArray result = null;


	try {
		con = DriverManager.getConnection(url, user, password);
		st = con.createStatement();

		ResultSet resultSet = st.executeQuery(query);//Executing Query

		//-------------------------

		//Converting Result Set To JSON:

		ResultSetMetaData md = resultSet.getMetaData();
		int numCols = md.getColumnCount();
		List<String> colNames = IntStream.range(0, numCols)
				.mapToObj(i -> {
					try {
						return md.getColumnName(i + 1);
					} catch (SQLException e) {
						e.printStackTrace();
						return "Exception occurred";
					}
				})
				.collect(Collectors.toList());

		result = new JSONArray();
		while (resultSet.next()) {
			JSONObject row = new JSONObject();
			colNames.forEach(cn -> {
				try {
					row.put(cn, resultSet.getObject(cn));
				} catch (Exception e) {
					e.printStackTrace();

				}
			});
			result.add(row);// Contains the JSON_ARRAY
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);

		// Default print without indent factor
		System.out.println(jsonObject);

		// Pretty print with 2 indent factor
		System.out.println(jsonObject.toString());

	}
	catch (Exception e){
		System.out.println(e);

	}
	finally {
		st.close();
		con.close();
		System.out.println("Connection Closed");
	}

	//Printing The JSON ARRAY :


	System.out.println("HIT THE END, API 4 worked");

	return new ResponseEntity<>(result, HttpStatus.OK);
}

	@GetMapping("/getPastAppointments/{doctor_email}")  //API #4
	public ResponseEntity<JSONArray> getPastAppointments(@PathVariable String doctor_email, Model model) throws Exception {

		Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver

		String url = "jdbc:mysql://localhost:3306/spmdb";
		String user = "root";
		String password = "root123";
		String query = "select patient.email, patient.first_name, patient.last_name, self_assessment.self_assessment_score, self_assessment.appointment_status from patient JOIN self_assessment ON \n" +
				"patient.self_assessment_id = self_assessment.self_assessment_id where patient.doctor_app_id = (\n" +
				"select doctor_id from doctor where email = '"+doctor_email+"') and patient.doctor_app_id IN (select doctor_id from appointment where appointment_time  < CURDATE());";

		Connection con = null;
		Statement st = null;
		JSONArray result = null;


		try {
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();

			ResultSet resultSet = st.executeQuery(query);//Executing Query

			//-------------------------

			//Converting Result Set To JSON:

			ResultSetMetaData md = resultSet.getMetaData();
			int numCols = md.getColumnCount();
			List<String> colNames = IntStream.range(0, numCols)
					.mapToObj(i -> {
						try {
							return md.getColumnName(i + 1);
						} catch (SQLException e) {
							e.printStackTrace();
							return "Exception occurred";
						}
					})
					.collect(Collectors.toList());

			result = new JSONArray();
			while (resultSet.next()) {
				JSONObject row = new JSONObject();
				colNames.forEach(cn -> {
					try {
						row.put(cn, resultSet.getObject(cn));
					} catch (Exception e) {
						e.printStackTrace();

					}
				});
				result.add(row);// Contains the JSON_ARRAY
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("result", result);

			// Default print without indent factor
			System.out.println(jsonObject);

			// Pretty print with 2 indent factor
			System.out.println(jsonObject.toString());

		}
		catch (Exception e){
			System.out.println(e);

		}
		finally {
			st.close();
			con.close();
			System.out.println("Connection Closed");
		}

		//Printing The JSON ARRAY :


		System.out.println("HIT THE END, API 4 worked");

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

//Update need Treatment Status in Patient Table:
@PutMapping("/updateTreatmentStatus")
public ResponseEntity<String> updateTreatmentStatus(@RequestBody String payload) throws Exception {

	System.out.println("Inside updateAppointment:" + payload);

	Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver



	//Taking Data From the Payload
	Object obj= JSONValue.parse(payload);

	JSONObject jsonObject = (JSONObject) obj;


	// POST Data Parameters
	String patient_email = (String) jsonObject.get("patient_email");

	System.out.println(patient_email);

	String url = "jdbc:mysql://localhost:3306/spmdb";
	String user = "root";
	String password = "root123";
	String query = "UPDATE patient\n" +
			"SET needTreatment='true'\n" +
			"WHERE email='"+patient_email+"'";

	System.out.println("Final Query is : "+query);
	Connection con = null;
	Statement st = null;


	//----------------------------------------EXECUTING QUERY----------------
	try {
		con=DriverManager.getConnection(url,user,password);
		st=con.createStatement();

		st.executeUpdate(query);//ExecutingQuery

	}
	catch( Exception e){
		System.out.println(e);
	}

	finally {
		st.close();
		con.close();
		System.out.println("Connection Closed Successfully");
	}


	System.out.println("API SUCCESS");
	//------------------------------------------------------------------------

	return ResponseEntity.ok("status: success");
}






//############################REPORT_MODULE################################################################
//Report BE API CODE:

//GET DAILY REPORT
@GetMapping("/dailyPatientCount")  //API #1
public ResponseEntity<JSONArray> dailyPatientCount(Model model) throws Exception {

	System.out.println("Inside dailyPatientCount");

	Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver
	String url = "jdbc:mysql://localhost:3306/spmdb";
	String user = "root";
	String password = "root123";
	String query = "select DATE(date_of_birth) as DAY, count(email) as COUNT from patient group by DATE(date_of_birth);";

	Connection con = null;
	Statement st = null;
	JSONArray result = null;

	try {

		con = DriverManager.getConnection(url, user, password);
		st = con.createStatement();

		ResultSet resultSet = st.executeQuery(query);//Executing Query

		//Converting Result Set To JSON:

		ResultSetMetaData md = resultSet.getMetaData();
		int numCols = md.getColumnCount();
		List<String> colNames = IntStream.range(0, numCols)
				.mapToObj(i -> {
					try {
						return md.getColumnName(i + 1);
					} catch (SQLException e) {
						e.printStackTrace();
						return "Exception occurred";
					}
				})
				.collect(Collectors.toList());

		result = new JSONArray();
		while (resultSet.next()) {
			JSONObject row = new JSONObject();
			colNames.forEach(cn -> {
				try {
					row.put(cn, resultSet.getObject(cn));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			result.add(row);// Contains the JSON_ARRAY
		}


		//Conversion Ended....


		//Printing The JSON ARRAY :

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);

		// Default print without indent factor
		System.out.println(jsonObject);

		// Pretty print with 2 indent factor
		System.out.println(jsonObject.toString());
	} catch (Exception e) {
		System.out.println(e);
	} finally {
		st.close();
		con.close();
		System.out.println("Connection Closed Successfully...");
	}


	System.out.println("HIT THE END, API 3 worked");

	return new ResponseEntity<>(result, HttpStatus.OK);
}


//GET Monthly Patient Count
@GetMapping("/monthlyPatientCount")  //API #2
public ResponseEntity<JSONArray> monthlyPatientCount(Model model) throws Exception {

	System.out.println("Inside monthlyPatientCount");

	Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver
	String url = "jdbc:mysql://localhost:3306/spmdb";
	String user = "root";
	String password = "root123";
	String query = "select MONTH(date_of_birth) as MONTH ,YEAR(date_of_birth) as YEAR , count(email) as COUNT\n" +
			"from patient group by MONTH(date_of_birth), YEAR(date_of_birth);";

	Connection con = null;
	Statement st = null;
	JSONArray result = null;

	try {

		con = DriverManager.getConnection(url, user, password);
		st = con.createStatement();

		ResultSet resultSet = st.executeQuery(query);//Executing Query

		//Converting Result Set To JSON:

		ResultSetMetaData md = resultSet.getMetaData();
		int numCols = md.getColumnCount();
		List<String> colNames = IntStream.range(0, numCols)
				.mapToObj(i -> {
					try {
						return md.getColumnName(i + 1);
					} catch (SQLException e) {
						e.printStackTrace();
						return "Exception occurred";
					}
				})
				.collect(Collectors.toList());

		result = new JSONArray();
		while (resultSet.next()) {
			JSONObject row = new JSONObject();
			colNames.forEach(cn -> {
				try {
					row.put(cn, resultSet.getObject(cn));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			result.add(row);// Contains the JSON_ARRAY
		}


		//Conversion Ended....


		//Printing The JSON ARRAY :

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);

		// Default print without indent factor
		System.out.println(jsonObject);

		// Pretty print with 2 indent factor
		System.out.println(jsonObject.toString());
	} catch (Exception e) {
		System.out.println(e);
	} finally {
		st.close();
		con.close();
		System.out.println("Connection Closed Successfully...");
	}


	System.out.println("HIT THE END, API 3 worked");

	return new ResponseEntity<>(result, HttpStatus.OK);
}

//Get Weekly Patient Count
@GetMapping("/weeklyPatientCount")  //API #3
public ResponseEntity<JSONArray> weeklyPatientCount(Model model) throws Exception {

	System.out.println("Inside weeklyPatientCount");

	Class.forName("com.mysql.jdbc.Driver"); //JDBC Driver
	String url = "jdbc:mysql://localhost:3306/spmdb";
	String user = "root";
	String password = "root123";
	String query = "select WEEK(date_of_birth) as WEEK,MONTH(date_of_birth) as MONTH,YEAR(date_of_birth) as YEAR, count(email) as COUNT from patient group by WEEK(date_of_birth),MONTH(date_of_birth), YEAR(date_of_birth);";

	Connection con = null;
	Statement st = null;
	JSONArray result = null;

	try {

		con = DriverManager.getConnection(url, user, password);
		st = con.createStatement();

		ResultSet resultSet = st.executeQuery(query);//Executing Query

		//Converting Result Set To JSON:

		ResultSetMetaData md = resultSet.getMetaData();
		int numCols = md.getColumnCount();
		List<String> colNames = IntStream.range(0, numCols)
				.mapToObj(i -> {
					try {
						return md.getColumnName(i + 1);
					} catch (SQLException e) {
						e.printStackTrace();
						return "Exception occurred";
					}
				})
				.collect(Collectors.toList());

		result = new JSONArray();
		while (resultSet.next()) {
			JSONObject row = new JSONObject();
			colNames.forEach(cn -> {
				try {
					row.put(cn, resultSet.getObject(cn));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			result.add(row);// Contains the JSON_ARRAY
		}


		//Conversion Ended....


		//Printing The JSON ARRAY :

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);

		// Default print without indent factor
		System.out.println(jsonObject);

		// Pretty print with 2 indent factor
		System.out.println(jsonObject.toString());
	} catch (Exception e) {
		System.out.println(e);
	} finally {
		st.close();
		con.close();
		System.out.println("Connection Closed Successfully...");
	}


	System.out.println("HIT THE END, API 3 worked");

	return new ResponseEntity<>(result, HttpStatus.OK);
}



}
