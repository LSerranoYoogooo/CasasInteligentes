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
    $sql = $conn->prepare("SELECT ctrl_id, enc_id, ctrl_name, ctrl_voice_on, ctrl_voice_off, ctrl_channel, ctrl_real_state, ctrl_img FROM control WHERE enc_id = :enc_id");
  	$sql->execute(array('enc_id' => $_POST['enc_id']));
  	$resultado = $sql->fetchAll();

    foreach ($resultado as $row) {
      $responseTmp;
      $responseTmp["id"] = $row["ctrl_id"];
      $responseTmp["enc_id"] = $row["enc_id"];
      $responseTmp["name"] = $row["ctrl_name"];
      $responseTmp["voice_on"] = $row["ctrl_voice_on"];
      $responseTmp["voice_off"] = $row["ctrl_voice_off"];
      $responseTmp["channel"] = $row["ctrl_channel"];
      $responseTmp["state"] = $row["ctrl_real_state"];
      $responseTmp["img"] = $row["ctrl_img"];
      array_push($response, $responseTmp);
		}
    echo json_encode($response);
  	}catch(PDOException $e){
  		throw $e;
  	}
}
?>