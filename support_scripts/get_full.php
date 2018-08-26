<?php
	include('connect.php');

	date_default_timezone_set('Asia/Kolkata');
	$time = time();
	
	$query = mysqli_query($con, "SELECT estimatedFullTime FROM parking_prediction WHERE date = '" . date('Y-m-d', $time) . "';");
	$row = mysqli_fetch_assoc($query);
	
	echo $row['estimatedFullTime'];
?>