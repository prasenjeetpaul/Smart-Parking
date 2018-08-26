<?php
	include('connect.php');

	$slotID = $_POST['slotID'];

	$query = mysqli_query($con, "SELECT slotID FROM parking_slots where slotID = '" . $slotID . "';");
	
	if (mysqli_num_rows($query) == 0) {
		if (mysqli_query($con, "INSERT INTO parking_slots(slotID) values ('" .$slotID. "');")) {
			echo 'Success';
		} else {
			echo 'Fail';
		}
	} else {
		echo 'Exists';
	}
?>