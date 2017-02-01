<?php
if (!empty($_POST)) {
	$username = "SHusr2017";
  $password = "XJVy1000mTa0";
  $servername = "localhost";
  $dbname = "SMART_HOME";
  try{
		$conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password, array(\PDO::MYSQL_ATTR_INIT_COMMAND =>  'SET NAMES utf8'));
	  $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		$sql = $conn->prepare("CALL set_Control(:enc_id , :name, :voice_on, :voice_off, :channel, :img)");
		$sql->execute(array('enc_id'=>$_POST['enc_id'], 'name'=>$_POST['name'], 'voice_on'=>$_POST['voice_on'], 'voice_off'=>$_POST['voice_off'], 'channel'=>$_POST['channel'], 'img'=>$_POST['img']));

		$response["status"] = "true";
		echo json_encode($response);

	}catch(PDOException $e){
		throw $e;
	}
}
?>