<?php
$id=$_GET["id"];

$con=mysqli_connect("localhost","root","","imgloading");
$query_result=mysqli_query($con,"select img from img where id=".$id);
while($row=mysqli_fetch_row($query_result))
{
	$img=$row[0];
}
echo $img;
?>
