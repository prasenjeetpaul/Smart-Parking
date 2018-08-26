<?php
	include('connect.php');

	date_default_timezone_set('Asia/Kolkata');
	$time = time();
	
	$query = mysqli_query($con, "SELECT slotID FROM parking_slots where slotType = 'Dedicated' AND isAvailable = 1;");
	
	while ($row = mysqli_fetch_assoc($query)) {
		mysqli_query($con, "UPDATE emp_parking SET failedInstanceNumber = failedInstanceNumber+1, stars = stars-failedInstanceNumber, lastFailedInstanceDate = DATE '" . date('Y-m-d', $time) . "' WHERE slotID = '" . $row['slotID'] . "' AND isDelay IS NULL AND isFloat IS NULL;");
		
		$subquery = mysqli_query($con, "SELECT slotID FROM emp_parking WHERE stars <= 0;");
		
		while ($subrow = mysqli_fetch_assoc($subquery)) {
			mysqli_query($con, "UPDATE parking_slots SET slotType = 'Floating' WHERE slotID = '" . $subrow['slotID'] . "';");
		}
		
		if (mysqli_query($con, "UPDATE emp_parking SET slotID = NULL, failedInstanceNumber = 0, stars = NULL, lastFailedInstanceDate = NULL, isDelay = NULL, isFloat = NULL WHERE stars <= 0;")) {
			echo 'Success';
		} else {
			echo 'Fail';
		}
	}
?>