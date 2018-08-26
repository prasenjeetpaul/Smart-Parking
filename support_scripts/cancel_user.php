<?php
	include('connect.php');

	$empID = $_POST['empID'];

	$query = mysqli_query($con, "SELECT empID FROM emp_parking where empID = '" . $empID . "';");
	
	if (mysqli_num_rows($query) > 0) {
		if (mysqli_query($con, "UPDATE emp_parking SET isFloat = 1 WHERE empID = '" . $empID . "';")) {
			echo 'Success';
		} else {
			echo 'Fail';
		}
	} else {
		echo 'Invalid';
	}
?>