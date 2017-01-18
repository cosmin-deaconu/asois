<?php

$host="localhost";
$user="id334779_cosmin1";
$password="12345";
$db="id334779_dsadsad";

$sql = "select * from CLUJ where date like'%January%';";

$con= mysqli_connect($host,$user,$password,$db);

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_array($result)){

array_push($response,array("id"=>$row[0],"date"=>$row[1],"totalnumber"=>$row[2]));
}

echo json_encode(array("server_response"=>$response));

mysqli_close($con);


?>