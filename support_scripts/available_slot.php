<?php
	include('connect.php');
	
	$results = array();
	
	$query = mysqli_query($con, "SELECT slotID, estimatedFillTime FROM parking_slots WHERE slotType = 'Floating' AND isAvailable = 1;");
	while ($row = mysqli_fetch_assoc($query)) {
		array_push($results, $row);
	}
	
	$slotquery = mysqli_query($con, "SELECT slotID FROM parking_slots WHERE SlotType = 'Dedicated' AND IsAvailable = 1;");
	
	while ($row = mysqli_fetch_assoc($slotquery)) {
		$subquery = mysqli_query($con, "SELECT slotID FROM emp_parking WHERE isFloat = 1;");
	}
	
	while ($subrow = mysqli_fetch_assoc($subquery)) {
		$finalquery = mysqli_query($con, "SELECT slotID, estimatedFillTime FROM parking_slots WHERE slotID = '" . $subrow['slotID'] . "';");
	}

	while ($finalrow = mysqli_fetch_assoc($finalquery)) {
		array_push($results, $finalrow);
	}
	
	echo json_encode($results);
?>