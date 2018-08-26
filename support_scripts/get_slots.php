<?php
	include('connect.php');
	
	$results = array();
	
	$query = mysqli_query($con, "SELECT parking_slots.slotID AS slotID, parking_history.loginTime AS loginTime FROM parking_slots, parking_history WHERE parking_slots.slotID = parking_history.slotID;");
	
	while ($row = mysqli_fetch_assoc($query)) {
		array_push($results, $row);
	}
	
	echo json_encode($results);
?>