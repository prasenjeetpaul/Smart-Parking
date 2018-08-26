<?php
	include('connect.php');

	$slotID = $_POST['slotID'];

	$slot_query = mysqli_query($con, "SELECT slotID FROM parking_slots where slotID = '" . $slotID . "';");
	
	if (mysqli_num_rows($slot_query) > 0) {
		$empID = $_POST['empID'];
		
		$emp_query = mysqli_query($con, "SELECT loginTime FROM emp_parking where empID = " . $empID . ";");
		
		if (mysqli_num_rows($emp_query) > 0) {
			$row = mysqli_fetch_assoc($emp_query);
			
			if (mysqli_query($con, "UPDATE parking_slots SET slotType = 'Dedicated', estimatedFillTime = TIME '" . $row['loginTime'] . "' WHERE slotID = '" . $slotID . "';") && mysqli_query($con, "UPDATE emp_parking SET slotID = '" . $slotID . "', stars = 5 WHERE empID = " . $empID . ";")) {
				echo 'Success';
			} else {
				echo 'Fail';
			}
		} else {
			echo 'Invalid Emp';
		}
	} else {
		echo 'Invalid Slot';
	}
?>