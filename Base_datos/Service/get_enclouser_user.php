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
    $sql = $conn->prepare("SELECT st_id FROM site WHERE usr_id = :user");
    $sql->execute(array('user' => $_POST["usr_id"]));
    $resultado = $sql->fetchAll();

    foreach ($resultado as $row) {
      $sql2 = $conn->prepare("SELECT enc_id, st_id, enc_name, enc_img FROM enclouser WHERE st_id = :site_id");
      $sql2->execute(array('site_id' => $row["st_id"]));
      $resultado2 = $sql2->fetchAll();

      foreach ($resultado2 as $row) {
        $responseTmp2;
        $responseTmp2["id"] = $row["enc_id"];
        $responseTmp2["st_id"] = $row["st_id"];
        $responseTmp2["name"] = $row["enc_name"];
        $responseTmp2["img"] = $row["enc_img"];
        array_push($response, $responseTmp2);
      }
    }
    echo json_encode($response);
    }catch(PDOException $e){
      throw $e;
      $response = $e;
    }
}
?>