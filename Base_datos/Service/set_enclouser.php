<?php
//require("config.inc.php");
if (!empty($_POST)) {
	$username = "SHusr2017";
  $password = "XJVy1000mTa0";
  $servername = "localhost";
  $dbname = "SMART_HOME";
  try{
		$conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password, array(\PDO::MYSQL_ATTR_INIT_COMMAND =>  'SET NAMES utf8'));
	  $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		$sql = $conn->prepare("CALL  set_Enclouser(:st_id , :name, :img)");
		$sql->execute(array('st_id'=>$_POST['site_id'], 'name'=>$_POST['name'], 'img'=>$_POST['img']));
		$response["status"] = "true";
		echo json_encode($response);

	}catch(PDOException $e){
		throw $e;
	}
}
?>