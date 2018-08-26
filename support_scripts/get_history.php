<?php
	include('connect.php');
	
	$results = array();
	
	$total_query = mysqli_query($con, "SELECT count(*) AS total FROM parking_slots;");
	$total_row = mysqli_fetch_assoc($total_query);
	
	$query = mysqli_query($con, "SELECT date, loginTime FROM parking_history GROUP BY date, loginTime;");
	while ($row = mysqli_fetch_assoc($query)) {
		$subquery = mysqli_query($con, "SELECT '" . $row['date'] . "' AS date, '" . $row['loginTime'] . "' AS loginTime, " . $total_row['total'] . "-count(*) AS available FROM parking_history WHERE date = DATE '" . $row['date'] . "' AND loginTime <= TIME '" . $row['loginTime'] . "';");
		
		while ($subrow = mysqli_fetch_assoc($subquery)) {
			array_push($results, $subrow);
		}
	}
	
	echo json_encode($results);
?>