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
      $sql2 = $conn->prepare("SELECT enc_id FROM enclouser WHERE st_id = :site_id");
      $sql2->execute(array('site_id' => $row["st_id"]));
      $resultado2 = $sql2->fetchAll();

      foreach ($resultado2 as $row) {
        $sql4 = $conn->prepare("SELECT ctrl_id, enc_id, ctrl_name, ctrl_voice_on, ctrl_voice_off, ctrl_channel, ctrl_real_state, ctrl_img FROM control WHERE enc_id = :enc_id");
            $sql4->execute(array('enc_id' =>$row["enc_id"]));
            $resultado4 = $sql4->fetchAll();

            foreach ($resultado4 as $row) {
              $responseTmp2;
              $responseTmp2["id"] = $row["ctrl_id"];
              $responseTmp2["enc_id"] = $row["enc_id"];
              $responseTmp2["name"] = $row["ctrl_name"];
              $responseTmp2["voice_on"] = $row["ctrl_voice_on"];
              $responseTmp2["voice_off"] = $row["ctrl_voice_off"];
              $responseTmp2["channel"] = $row["ctrl_channel"];
              $responseTmp2["state"] = $row["ctrl_real_state"];
              $responseTmp2["img"] = $row["ctrl_img"];
              array_push($response, $responseTmp2);
            }
      }
    }
    echo json_encode($response);
    }catch(PDOException $e){
      throw $e;
      $response = $e;
    }
}
?>