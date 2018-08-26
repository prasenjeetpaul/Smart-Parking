<?php
	include('connect.php');
	
	$results = array();
	
	$query = mysqli_query($con, "SELECT empID FROM emp_parking WHERE slotID IS NULL;");
	while ($row = mysqli_fetch_assoc($query)) {
		array_push($results, $row);
	}

	echo json_encode($results);
?>