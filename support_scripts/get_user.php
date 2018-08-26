<?php
	include('connect.php');
	
	$empID = $_POST['empID'];
	$query = mysqli_query($con, "SELECT loginTime, logoutTime, slotID, failedInstanceNumber, stars, isDelay, isFloat FROM emp_parking WHERE empID = " . $empID . ";");
	
	echo json_encode(mysqli_fetch_assoc($query));
?>