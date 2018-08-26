<?php
	include('connect.php');

	date_default_timezone_set('Asia/Kolkata');
	$time = time();
	
	$query = mysqli_query($con, "SELECT empID FROM emp_vacation WHERE date = '" . date('Y-m-d', $time) . "';");
	
	while ($row = mysqli_fetch_assoc($query)) {
		if (mysqli_query($con, "UPDATE emp_parking SET isFloat = 1 WHERE empID = '" . $row['empID'] . "' AND slotID IS NOT NULL;")) {
			echo 'Success';
		} else {
			echo 'Fail';
		}
	}
?>