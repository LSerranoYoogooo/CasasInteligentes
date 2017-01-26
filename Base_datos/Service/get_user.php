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
    $sql = $conn->prepare("SELECT usr_id, usr_name, usr_email, usr_service_type, default_site FROM user_app WHERE usr_email = :email and usr_pass = SHA2(:pass,384)");
  	$sql->execute(array('email' => $_POST['email'], 'pass' => $_POST['pass']));
  	$resultado = $sql->fetchAll();

    if(isset($resultado)){
      $response["status"] = "false";
    }

    foreach ($resultado as $row) {
      $response["status"] = "true";
			$response["id"] = $row["usr_id"];
			$response["name"] = $row["usr_name"];
      $response["email"] = $row["usr_email"];
      $response["service_type"] = $row["usr_service_type"];
      $response["site_id"] = $row["default_site"];
		}
    echo json_encode($response);
  	}catch(PDOException $e){
  		throw $e;
  	}
}
?>