<?php
	include('connect.php');
	
	$empID = $_POST['empID'];
	$password = $_POST['password'];
	
	$query = mysqli_query($con, "SELECT empID, password FROM parking_admin where empID = " . $empID . ";");
	
	if (mysqli_num_rows($query) > 0) {
		$row = mysqli_fetch_assoc($query);
		
		if ($empID == $row['empID'] && $password == $row['password']) {
			echo 'Success';
		} else {
			echo 'Fail';
		}
	} else {
		echo 'Invalid';
	}
?>