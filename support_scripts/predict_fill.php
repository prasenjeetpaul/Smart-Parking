<?php
	include('connect.php');

	$avg = mysqli_query($con, "SELECT slotID, SEC_TO_TIME(AVG(TIME_TO_SEC(loginTime))) AS loginTime FROM parking_history GROUP BY slotID;");
	
	while ($row = mysqli_fetch_assoc($avg)) {
		if (mysqli_query($con, "UPDATE parking_slots SET estimatedFillTIme = TIME '" . $row['loginTime'] . "' WHERE slotID = '" . $row['slotID'] . "';")) {
			echo 'Success';
		} else {
			echo 'Fail';
		}
	}
?>