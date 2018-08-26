<?php
	include('connect.php');
	
	$slotID = $_POST['slotID'];

	$query = mysqli_query($con, "SELECT slotID FROM parking_slots where slotID = '" . $slotID . "';");
	
	if (mysqli_num_rows($query) > 0) {
		$isAvailable = $_POST['isAvailable'];
		
		if ($isAvailable == '0') {
			if (mysqli_query($con, "UPDATE parking_slots SET isAvailable = 0 WHERE slotID = '" . $slotID . "';")) {
				date_default_timezone_set('Asia/Kolkata');
				$time = time();
				
				if (mysqli_query($con, "INSERT INTO parking_history(slotID, date, loginTime) VALUES ('" . $slotID . "', DATE '" . date('Y-m-d', $time) . "', TIME '" . date('H:i:s', $time) . "');")) {
					echo 'Success';
				} else {
					echo 'Fail';
				}
			} else {
				echo 'Fail';
			}
		} else {
			if (mysqli_query($con, "UPDATE parking_slots SET isAvailable = 1 WHERE slotID = '" . $slotID . "';")) {
				date_default_timezone_set('Asia/Kolkata');
				
				if (mysqli_query($con, "UPDATE parking_history SET logoutTime = TIME '" . date('H:i:s', time()) . "' WHERE slotID = '" . $slotID . "' AND logoutTime IS NULL;")) {
					echo 'Success';
				} else {
					echo 'Fail';
				}
			} else {
				echo 'Fail';
			}
		}
	} else {
		echo 'Invalid';
	}
?>