<?php
	include('connect.php');
	
	$estimatedFullTime = $_POST['estimatedFullTime'];
	
	date_default_timezone_set('Asia/Kolkata');
	$time = time();
	
	if (mysqli_query($con, "INSERT INTO parking_prediction VALUES (DATE '" . date('Y-m-d', $time) . "', TIME '" . $estimatedFullTime . "');")) {
		echo 'Success';
	} else {
		echo 'Fail';
	}
?>