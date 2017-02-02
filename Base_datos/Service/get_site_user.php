<?php
$response = [];
if (!empty($_POST)) {
  $username = "SHusr2017";
  $password = "XJVy1000mTa0";
  $servername = "localhost";
  $dbname = "SMART_HOME";
  try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password, array(\PDO::MYSQL_ATTR_INIT_COMMAND =>  'SET NAMES utf8'));
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $sql = $conn->prepare("SELECT s.st_id, s.st_name, s.st_latitud, s.st_longitud, s.st_img, ip.ipa_ip, ip.ipa_port FROM site s, ip_address ip WHERE s.usr_id = :user and ip.usr_id = :user group by s.st_id");
    $sql->execute(array('user' => $_POST['user']));
    $resultado = $sql->fetchAll();

    foreach ($resultado as $row) {
      $responseTmp;
      $responseTmp["id"] = $row["st_id"];
      $responseTmp["name"] = $row["st_name"];
      $responseTmp["latitud"] = $row["st_latitud"];
      $responseTmp["longitud"] = $row["st_longitud"];
      $responseTmp["img_name"] = $row["st_img"];
      $responseTmp["ip"] = $row["ipa_ip"];
      $responseTmp["port"] = $row["ipa_port"];
      array_push($response, $responseTmp);
    }

    echo json_encode($response);
    }catch(PDOException $e){
      throw $e;
      $response = $e;
    }
}
?>