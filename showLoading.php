<?php
$con=mysqli_connect("localhost","root","","imgloading");
$query_result=mysqli_query($con,"select img from img where id=1");
while($row=mysqli_fetch_row($query_result))
{
	$img=$row[0];
}
echo '<img src="data:image/*;base64,'.base64_encode($img).'" width="32" heigth="32"/>';
?>
