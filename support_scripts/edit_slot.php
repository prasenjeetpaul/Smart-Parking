<?php
	include('connect.php');

	$slotID = $_POST['slotID'];

	$query = mysqli_query($con, "SELECT slotID FROM parking_slots where slotID = '" . $slotID . "';");
	
	if (mysqli_num_rows($query) > 0) {
		$slotType = $_POST['slotType'];
		
		if (mysqli_query($con, "UPDATE parking_slots SET slotType = '" . $slotType . "' WHERE slotID = '" . $slotID . "';")) {
			if ($slotType == 'Floating') {
				if (mysqli_query($con, "UPDATE emp_parking SET slotID = NULL WHERE slotID = '" . $slotID . "';")) {
					echo 'Success';
				} else {
					echo 'Fail';
				}
			} else {
				echo 'Success';
			}
		} else {
			echo 'Fail';
		}
	} else {
		echo 'Invalid';
	}
?>