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
    $sql = $conn->prepare("SELECT ipa_ip, ipa_port FROM ip_address WHERE st_id = :site_id");
  	$sql->execute(array('site_id' => $_POST['st_id']));
  	$resultado = $sql->fetchAll();

    if(isset($resultado)){
      $response["status"] = "false";
    }

    foreach ($resultado as $row) {
      $response["status"] = "true";
			$response["ip"] = $row["ipa_ip"];
			$response["port"] = $row["ipa_port"];

		}
    echo json_encode($response);
  	}catch(PDOException $e){
  		throw $e;
  }
}
?>
