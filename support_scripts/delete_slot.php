<?php
	include('connect.php');

	$slotID = $_POST['slotID'];

	$query = mysqli_query($con, "SELECT slotID FROM parking_slots where slotID = '" . $slotID . "';");
	
	if (mysqli_num_rows($query) > 0) {
		if (mysqli_query($con, "DELETE FROM parking_slots WHERE slotID = '" . $slotID . "';")) {
			echo 'Success';
		} else {
			echo 'Fail';
		}
	} else {
		echo 'Invalid';
	}
?>