<?php
	include('connect.php');

	$empID = $_POST['empID'];

	$query = mysqli_query($con, "SELECT empID FROM emp_parking where empID = " . $empID . ";");
	
	if (mysqli_num_rows($query) == 0) {
		$password = $_POST['password'];
		$loginTime = $_POST['loginTime'];
		$logoutTime = $_POST['logoutTime'];
		
		if (mysqli_query($con, "INSERT INTO emp_parking(empID, password, loginTime, logoutTime) values (" .$empID. ", '" . $password . "', TIME '" . $loginTime . "', TIME '" . $logoutTime . "');")) {
			echo 'Success';
		} else {
			echo 'Fail';
		}
	} else {
		echo 'Exists';
	}
?>