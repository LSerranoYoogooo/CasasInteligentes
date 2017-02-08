<?php
if (!empty($_POST)) {
	$username = "SHusr2017";
  $password = "XJVy1000mTa0";
  $servername = "localhost";
  $dbname = "SMART_HOME";
  try{
		$conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password, array(\PDO::MYSQL_ATTR_INIT_COMMAND =>  'SET NAMES utf8'));
	  $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		$sql = $conn->prepare("CALL  set_Default_Site(:user, :name , :latitud, :longitud, :img, :ip_address, :ip_port)");
		$sql->execute(array('user'=>$_POST['user'], 'name'=>$_POST['name'], 'latitud'=>$_POST['lat'], 'longitud'=>$_POST['lon'], 'img'=>$_POST['img'], 'ip_address'=>$_POST['ip'], 'ip_port'=>$_POST['port']));

		$response["status"] = "true";
		echo json_encode($response);

	}catch(PDOException $e){
		throw $e;
	}
}
?>