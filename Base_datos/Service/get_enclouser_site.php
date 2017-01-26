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
    $sql = $conn->prepare("SELECT enc_id, enc_name, enc_img FROM enclouser WHERE st_id = :site_id");
    $sql->execute(array('site_id' => $_POST['site_id']));
    $resultado = $sql->fetchAll();

    foreach ($resultado as $row) {
      $responseTmp;
      $responseTmp["id"] = $row["enc_id"];
      $responseTmp["name"] = $row["enc_name"];
      $responseTmp["img"] = $row["enc_img"];
      array_push($response, $responseTmp);
    }

    echo json_encode($response);
    }catch(PDOException $e){
      throw $e;
      $response = $e;
    }
}
?>
