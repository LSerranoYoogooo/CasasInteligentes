<?php
$sites = [];
$enclousers = [];
$controls = [];
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

    foreach ($resultado as $row) {
			$response["usr_id"] = $row["usr_id"];
			$response["usr_name"] = $row["usr_name"];
      $response["usr_email"] = $row["usr_email"];
      $response["usr_service_type"] = $row["usr_service_type"];
      $response["site_id"] = $row["default_site"];

      $sql2 = $conn->prepare("SELECT s.st_id, st_name, s.st_latitud, s.st_longitud, s.st_img, ip.ipa_ip, ip.ipa_port FROM site s, ip_address ip WHERE s.usr_id = :user and ip.usr_id = :user group by s.st_id");
      $sql2->execute(array('user' => $row["usr_id"]));
      $resultado2 = $sql2->fetchAll();

      foreach ($resultado2 as $row) {
        $responseTmp2;
        $responseTmp2["id"] = $row["st_id"];
        $responseTmp2["name"] = $row["st_name"];
        $responseTmp2["latitud"] = $row["st_latitud"];
        $responseTmp2["longitud"] = $row["st_longitud"];
        $responseTmp2["img_name"] = $row["st_img"];
        $responseTmp2["ip"] = $row["ipa_ip"];
        $responseTmp2["port"] = $row["ipa_port"];
        array_push($sites, $responseTmp2);

        $sql3 = $conn->prepare("SELECT enc_id, st_id, enc_name, enc_img FROM enclouser WHERE st_id = :site_id");
        $sql3->execute(array('site_id' => $row["st_id"]));
        $resultado3 = $sql3->fetchAll();

        foreach ($resultado3 as $row) {
          $responseTmp3;
          $responseTmp3["id"] = $row["enc_id"];
          $responseTmp3["st_id"] = $row["st_id"];
          $responseTmp3["name"] = $row["enc_name"];
          $responseTmp3["img"] = $row["enc_img"];
          array_push($enclousers, $responseTmp3);

          $sql4 = $conn->prepare("SELECT ctrl_id, enc_id, ctrl_name, ctrl_voice_on, ctrl_voice_off, ctrl_channel, ctrl_real_state, ctrl_img FROM control WHERE enc_id = :enc_id");
          $sql4->execute(array('enc_id' =>$row["enc_id"]));
          $resultado4 = $sql4->fetchAll();

          foreach ($resultado4 as $row) {
            $responseTmp4;
            $responseTmp4["id"] = $row["ctrl_id"];
            $responseTmp4["enc_id"] = $row["enc_id"];
            $responseTmp4["name"] = $row["ctrl_name"];
            $responseTmp4["voice_on"] = $row["ctrl_voice_on"];
            $responseTmp4["voice_off"] = $row["ctrl_voice_off"];
            $responseTmp4["channel"] = $row["ctrl_channel"];
            $responseTmp4["state"] = $row["ctrl_real_state"];
            $responseTmp4["img"] = $row["ctrl_img"];
            array_push($controls, $responseTmp4);
          }
          
        }
        
      }
      $response["enclousers"] = $enclousers;
      $response["controls"] = $controls;
      $response["sites"] = $sites;
		}
    echo json_encode($response);
  	}catch(PDOException $e){
  		throw $e;
  	}
}
?>
