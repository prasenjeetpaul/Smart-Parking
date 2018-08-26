<?php
	include('connect.php');
	
	$empID = $_POST['empID'];
	$password = $_POST['password'];
	
	$query = mysqli_query($con, "SELECT empID, password, slotID FROM emp_parking where empID = " . $empID . ";");
	
	if (mysqli_num_rows($query) > 0) {
		$row = mysqli_fetch_assoc($query);
		
		if ($empID == $row['empID'] && $password == $row['password']) {
			echo json_encode($row);
		} else {
			echo 'Fail';
		}
	} else {
		echo 'Invalid';
	}
?>