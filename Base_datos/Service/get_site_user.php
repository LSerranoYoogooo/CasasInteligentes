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
    $sql = $conn->prepare("SELECT st_id, st_name, st_latitud, st_longitud, st_img FROM site WHERE usr_id = :user");
    $sql->execute(array('user' => $_POST['user']));
    $resultado = $sql->fetchAll();

    foreach ($resultado as $row) {
      $responseTmp;
      $responseTmp["id"] = $row["st_id"];
      $responseTmp["name"] = $row["st_name"];
      $responseTmp["latitud"] = $row["st_latitud"];
      $responseTmp["longitud"] = $row["st_longitud"];
      $responseTmp["img_name"] = $row["st_img"];
      array_push($response, $responseTmp);
    }

    echo json_encode($response);
    }catch(PDOException $e){
      throw $e;
      $response = $e;
    }
}
?>